/**************************************************************
 * File name    : UserController.java
 * Author       : Jason Leaster
 * Date         : 2016.08.06
 *
 * Description  :
 *      This file implement a controller for business logic with
 * users.
 *****************************************************************/
package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sims.model.User;
import sims.service.UserService;
import sims.util.URLs;
import sims.util.Views;

@EnableWebMvc
@Controller
@RequestMapping(value = URLs.USERS)
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = URLs.SEARCH, method = RequestMethod.GET)
    public String searchGet(Model model){
        model.addAttribute(new User());
        return Views.USER_SEARCH;
    }

    //http://stackoverflow.com/questions/26615416/406-spring-mvc-json-not-acceptable-according-to-the-request-accept-headers
    @RequestMapping(value = URLs.SEARCH, method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody User searchPost(User user, Model model){
        return userService.getById(user.getEmail());
    }

    @RequestMapping(value = URLs.CREATE, method = RequestMethod.GET)
    public String createGet(Model model){
        model.addAttribute(new User());
        return Views.USER_CREATE;
    }

    @RequestMapping(value = URLs.CREATE, method = RequestMethod.POST)
    public String createPost(User user, Model model){
        userService.add(user);
        return Views.HOME;
    }

    @RequestMapping(value = URLs.MODIFY, method = RequestMethod.GET)
    public String modifySearchGet(Model model){
        return searchGet(model);
    }

    @RequestMapping(value = URLs.MODIFY, method = RequestMethod.POST)
     public String modifySearchPost(User user, Model modrel){
        return Views.USER_MODIFY;
    }

    @RequestMapping(value = URLs.MODIFY + URLs.UPDATE, method = RequestMethod.POST)
    public String modifyUpdate( Model model){
        return Views.HOME;
    }

    @RequestMapping(value = URLs.DELETE, method = RequestMethod.GET)
    public String deleteGet(Model model){
        model.addAttribute(new User());
        return Views.USER_DELETE;
    }

    @RequestMapping(value = URLs.DELETE, method = RequestMethod.POST)
    public String deletePost(User user){
        userService.delete(user.getEmail());
        return Views.HOME;
    }
}