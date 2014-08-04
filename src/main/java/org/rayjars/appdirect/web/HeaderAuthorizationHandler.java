package org.rayjars.appdirect.web;

import org.rayjars.appdirect.OAuth;
import org.rayjars.appdirect.exceptions.UnauthorizedException;
import org.rayjars.appdirect.exceptions.UnknownErrorException;
import org.rayjars.appdirect.xml.XmlReader;
import org.rayjars.appdirect.xml.XmlWriter;
import org.rayjars.appdirect.xml.beans.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeaderAuthorizationHandler extends HandlerInterceptorAdapter {

    private OAuth oAuth;

    private XmlWriter writer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, UnknownErrorException {

        try {
            oAuth.validate(request);
        } catch (UnauthorizedException e) {
            Result result = ResponseHelper.error(e.getMessage(), e.getCode());
            response.setContentType(MediaType.APPLICATION_XML.toString());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            writer.write(result, response.getOutputStream());
            return false;
        }

        return true;
    }

    @Autowired
    public void setWriter(XmlWriter writer) {
        this.writer = writer;
    }

    @Autowired
    public void setoAuth(OAuth oAuth) {
        this.oAuth = oAuth;
    }
}
