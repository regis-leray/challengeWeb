package org.rayjars.appdirect.web;

import org.rayjars.appdirect.Subscription;
import org.rayjars.appdirect.exceptions.AppException;
import org.rayjars.appdirect.exceptions.UnknownErrorException;
import org.rayjars.appdirect.xml.beans.Event;
import org.rayjars.appdirect.xml.beans.Result;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subscription")
public class SubscriptionController extends AbstractController {


    @RequestMapping(value = "/order", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Result order(@RequestParam(value = "url") String url) throws UnknownErrorException {

        //need to valid authorization header
        Event event = signAndfetch(url);

        Subscription account = new Subscription()
                .setCompany(event.getPayload().getCompany())
                .setSubscription(event.getPayload().getOrder());

        Subscription created = accountDao.create(account);

        return ResponseHelper.success("Subscription creation successful", created.getId());
    }

    @RequestMapping(value = "/change", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result change(@RequestParam(value = "url") String url) throws AppException {

        Event event = signAndfetch(url);
        String accountIdentifier = getAccountIdentifier(event);
        Subscription updated = accountDao.update(accountIdentifier, event.getPayload().getOrder());

        return ResponseHelper.success("The subscription has been updated id = "+ updated.getId());
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result cancel(@RequestParam(value = "url") String url) throws AppException {

        Event event = signAndfetch(url);

        String accountIdentifier = getAccountIdentifier(event);
        accountDao.delete(accountIdentifier);

        return ResponseHelper.success("The subscription has been deleted id = "+ accountIdentifier);
    }

}