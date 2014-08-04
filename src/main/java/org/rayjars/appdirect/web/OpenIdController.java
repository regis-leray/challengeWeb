package org.rayjars.appdirect.web;

import org.openid4java.discovery.Identifier;
import org.rayjars.appdirect.ConsumerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

@Controller
@RequestMapping("/openid")
public class OpenIdController {

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView index() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/openid_return", method = RequestMethod.GET)
    public ModelAndView openidReturn(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        ModelAndView index = new ModelAndView("login");
        ConsumerService service = new ConsumerService();
        Identifier identifier = service.verifyResponse(req, res);

        if (identifier == null) {
            index.addObject("error", "openid failed");
        }

        return index.addObject("identifier", identifier);
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(HttpStatus.OK)
    public Object login(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        ConsumerService service = new ConsumerService();
        int port = req.getServerPort();

        if (req.getScheme().equals("http") && port == 80) {
            port = -1;
        } else if (req.getScheme().equals("https") && port == 443) {
            port = -1;
        }

        URL serverURL = new URL(req.getScheme(), req.getServerName(), port, "");
        String returnToUrl = serverURL + "/openid/openid_return";
        String userSupplied = req.getParameter("openid");
        service.authRequest(userSupplied, returnToUrl, req, res);

        return "login";


    }


}
