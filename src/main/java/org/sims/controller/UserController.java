/**************************************************************
 * File name    : UserController.java
 * Author       : Jason Leaster
 * Date         : 2016.08.06
 *
 * Description  :
 *      This file implement a org.sims.controller for business logic with
 * users.
 *****************************************************************/
package org.sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.sims.exception.DuplicatedPrimaryKeyException;
import org.sims.form.RegisterForm;
import org.sims.model.User;
import org.sims.service.UserService;
import org.sims.util.AttributesKey;
import org.sims.util.URLs;
import org.sims.util.Views;

@EnableWebMvc
@Controller
@RequestMapping(value = URLs.USERS)
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = URLs.SEARCH, method = RequestMethod.GET)
    public String searchGet(){
        return Views.USER_SEARCH;
    }

    //http://stackoverflow.com/questions/26615416/406-spring-mvc-json-not-acceptable-according-to-the-request-accept-headers
    @RequestMapping(value = URLs.SEARCH, method = RequestMethod.POST)
    public String searchPost(User user, Model model){
        User userInDB = userService.getById(user.getEmail());
        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_USER, userInDB);
        return Views.USER_SHOW;
    }

    @RequestMapping(value = URLs.QUERY + "/all")
    public String query(Model model){
        // TODO xxxx
        return Views.USER_CREATE;
    }

    @RequestMapping(value = URLs.CREATE, method = RequestMethod.GET)
    public String createGet(Model model){
        model.addAttribute(new User());
        return Views.USER_CREATE;
    }

    @RequestMapping(value = URLs.CREATE, method = RequestMethod.POST)
    public String createPost(RegisterForm form, Model model, BindingResult bindingResult) throws Exception{

        if( bindingResult.hasErrors()){
            return Views.LOGIN;
        }

        User user = form.toUser();
        String userID = user.getEmail();
        // User already registered.
        if(userService.getById(userID) != null){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "User already existed!");
            return Views.USER_CREATE;
        }
        // The password is not coincident
        if(! form.getConfirmedPassword().equals(
                form.getPassword())){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "password confirmed error!");
            return Views.USER_CREATE;
        }

        try{
            userService.add(user);
        }catch (DuplicatedPrimaryKeyException e){
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, e.toString());
            return Views.USER_CREATE;
        }

        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_USER, user);
        return Views.USER_SHOW;
    }

    @RequestMapping(value = URLs.MODIFY + "/{userID:.+}", method = RequestMethod.GET)
     public String modify(@PathVariable("userID") String userID, Model modrel){
        User userInDB = userService.getById(userID);
        modrel.addAttribute(AttributesKey.MODEL_ATTRIBUTES_USER, userInDB);
        return Views.USER_MODIFY;
    }

    @RequestMapping(value = URLs.MODIFY + URLs.UPDATE, method = RequestMethod.POST)
    public String modifyUpdate(User user, Model model, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  Views.USER_MODIFY;
        }
        userService.modify(user);
        return Views.USER_SHOW;
    }

    /*
    * In this function, the id in url is a string which is org.sims.email.
    * @PathVariable will truncate when it meet dot in the string.
    * Eg:
    *   "Eric@gmail.com" will truncate into "Eric@gmail"
    *
    * The solution is to use regex to match the string in the url.
    * */
    @RequestMapping(value = URLs.DELETE + "/{id:.+}", method = RequestMethod.POST)
    public String delete(@PathVariable("id") String id, Model model){
        if(userService.getById(id) != null){
            userService.delete(id);
        }else {
            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "User does not exist");
        }
        return Views.HOME;
    }
}