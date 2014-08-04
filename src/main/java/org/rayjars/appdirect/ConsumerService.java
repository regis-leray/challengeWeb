package org.rayjars.appdirect;

import org.openid4java.OpenIDException;
import org.openid4java.association.AssociationSessionType;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.*;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ConsumerService - Relying Party Service
 * NOTE: Some part of the code has been adopted from `OpenID4Java` library wiki.
 */
public class ConsumerService {


    final static String YAHOO_ENDPOINT = "https://me.yahoo.com";
    final static String GOOGLE_ENDPOINT = "https://www.google.com/accounts/o8/id";

    private static ConsumerManager manager = null;

    //http://stackoverflow.com/questions/7645226/verification-failure-while-using-openid4java-for-login-with-google
    static {
        manager = new ConsumerManager();
        manager.setAssociations(new InMemoryConsumerAssociationStore());
        manager.setNonceVerifier(new InMemoryNonceVerifier(5000));
        manager.setMinAssocSessEnc(AssociationSessionType.DH_SHA256);
    }


    @SuppressWarnings("unchecked")
    public void authRequest(String userSuppliedString, String returnToUrl,
                                    HttpServletRequest httpReq,
                                    HttpServletResponse httpResp)
            throws IOException, ServletException {

        try {

            // perform discovery on the user-supplied identifier
            List<DiscoveryInformation> discoveries = manager.discover(userSuppliedString);

            // attempt to associate with the OpenID provider
            // and retrieve one service endpoint for authentication
            DiscoveryInformation discovered = manager.associate(discoveries);

            // store the discovery information in the user's session
            httpReq.getSession().setAttribute("discovered", discovered);

            // obtain a AuthRequest message to be sent to the OpenID provider
            AuthRequest authReq = manager.authenticate(discovered, returnToUrl);


            FetchRequest fetch = FetchRequest.createFetchRequest();
            if (userSuppliedString.startsWith(GOOGLE_ENDPOINT)) {
                fetch.addAttribute("email",
                        "http://axschema.org/contact/email", true);
                fetch.addAttribute("firstName",
                        "http://axschema.org/namePerson/first", true);
                fetch.addAttribute("lastName",
                        "http://axschema.org/namePerson/last", true);
            } else if (userSuppliedString.startsWith(YAHOO_ENDPOINT)) {
                fetch.addAttribute("email",
                        "http://axschema.org/contact/email", true);
                fetch.addAttribute("fullname",
                        "http://axschema.org/namePerson", true);
            } else { // works for myOpenID
                fetch.addAttribute("fullname",
                        "http://schema.openid.net/namePerson", true);
                fetch.addAttribute("email",
                        "http://schema.openid.net/contact/email", true);
            }

            // attach the extension to the authentication request
            authReq.addExtension(fetch);

            SRegRequest sregReq = SRegRequest.createFetchRequest();
            sregReq.addAttribute("fullname", false);
            sregReq.addAttribute("nickname", false);
            sregReq.addAttribute("dob", false);
            sregReq.addAttribute("email", true);

            authReq.addExtension(sregReq);

            httpResp.sendRedirect(authReq.getDestinationUrl(true));

        } catch (OpenIDException e) {
            throw new ServletException(e);
        }
    }

    // processing the authentication response
    public Identifier verifyResponse(HttpServletRequest httpReq, HttpServletResponse resp)
            throws ServletException {
        try {

            // extract the parameters from the authentication response
            // (which comes in as a HTTP request from the OpenID provider)
            ParameterList parameterList = new ParameterList(httpReq.getParameterMap());
            //parameterList.addParams(params);

            // retrieve the previously stored discovery information
            DiscoveryInformation discovered = (DiscoveryInformation) httpReq.getSession().getAttribute("discovered");

            // extract the receiving URL from the HTTP request
            StringBuffer receivingURL = httpReq.getRequestURL();
            String queryString = httpReq.getQueryString();
            if (queryString != null && queryString.length() > 0)
                receivingURL.append("?").append(httpReq.getQueryString());

            // verify the response; ConsumerManager needs to be the same
            // (static) instance used to place the authentication request
            VerificationResult verification = manager.verify(receivingURL.toString(), parameterList, discovered);

            // examine the verification result and extract the verified
            // identifier
            Identifier verified = verification.getVerifiedId();
            if (verified != null) {
                AuthSuccess authSuccess = (AuthSuccess) verification.getAuthResponse();

                receiveSimpleRegistration(httpReq, authSuccess);

                receiveAttributeExchange(httpReq, authSuccess);
                return verified; // success
            }
        } catch (OpenIDException e) {
            // present error to the user
            throw new ServletException(e);
        }
        return null;
    }

    /**
     * @param httpReq
     * @param authSuccess
     * @throws MessageException
     */
    private void receiveSimpleRegistration(HttpServletRequest httpReq, AuthSuccess authSuccess) throws MessageException {
        if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
            MessageExtension ext = authSuccess.getExtension(SRegMessage.OPENID_NS_SREG);
            if (ext instanceof SRegResponse) {
                SRegResponse sregResp = (SRegResponse) ext;
                Map<String, String> attributes = new LinkedHashMap<String, String>();
                for (Iterator iter = sregResp.getAttributeNames().iterator(); iter.hasNext(); ) {
                    String name = (String) iter.next();
                    String value = sregResp.getParameterValue(name);
                    attributes.put(name, value);
                    System.out.println("NS: " + name + ": " + value);
                }
                httpReq.setAttribute("registration", attributes);

            }
        }
    }

    /**
     * @param httpReq
     * @param authSuccess
     * @throws MessageException
     */
    private void receiveAttributeExchange(HttpServletRequest httpReq, AuthSuccess authSuccess) throws MessageException {
        if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
            FetchResponse fetchResp = (FetchResponse) authSuccess.getExtension(AxMessage.OPENID_NS_AX);
            List<String> aliases = fetchResp.getAttributeAliases();
            Map<String, String> attributes = new LinkedHashMap<String, String>();
            for (Iterator<String> iter = aliases.iterator(); iter.hasNext(); ) {
                String alias = (String) iter.next();
                List<String> values = fetchResp.getAttributeValues(alias);
                if (values.size() > 0) {
                    String[] arr = new String[values.size()];
                    values.toArray(arr);
                    attributes.put(alias, arr[0]);
                    System.out.println("AX: " + alias + ": " + arr[0]);
                }
            }
            httpReq.setAttribute("attributes", attributes);
        }
    }
}