package org.rayjars.appdirect.web;

import org.rayjars.appdirect.exceptions.AppException;
import org.rayjars.appdirect.xml.beans.Event;
import org.rayjars.appdirect.xml.beans.Result;
import org.rayjars.appdirect.xml.beans.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

    @RequestMapping(value = "/assign", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result assign(@RequestParam(value = "url") String url) throws AppException {

        Event event = signAndfetch(url);
        String id = getAccountIdentifier(event);
        User user = event.getPayload().getUser();
        repository.assignUser(id, user);

        return ResponseHelper.success("Assign user with success "+user.getUuid());
    }


    @RequestMapping(value = "/unassign", method = RequestMethod.GET)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result unassign(@RequestParam(value = "url") String url) throws AppException {

        Event event = signAndfetch(url);
        String id = getAccountIdentifier(event);
        User user = event.getPayload().getUser();
        repository.unassignUser(id, user);

        return ResponseHelper.success("UnAssign user with success "+user.getUuid());
    }


}
