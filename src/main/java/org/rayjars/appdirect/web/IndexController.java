package org.rayjars.appdirect.web;

import org.rayjars.appdirect.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

    private SubscriptionRepository accountDAO;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("index")
                .addObject("accounts", accountDAO.all());
    }

    @Autowired
    public void setAccountDAO(SubscriptionRepository accountDAO) {
        this.accountDAO = accountDAO;
    }
}
