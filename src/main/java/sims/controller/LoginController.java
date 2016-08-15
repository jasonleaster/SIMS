/**************************************************************
 * File name    : LoginController.java
 * Author       : Jason Leaster
 * Date         : 2016.07.24
 *
 * Description  :
 *     #LoginController will be responsible for requests of login
 * and registration.
 *****************************************************************/
package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sims.form.RegisterForm;
import sims.model.User;
import sims.service.UserService;
import sims.util.URLs;
import sims.util.Views;


@Controller
public class LoginController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }

    @RequestMapping(value = {URLs.LOGIN, URLs.ROOT}, method = RequestMethod.GET)
    public String loginGet(){
        return Views.LOGIN;
    }

    @RequestMapping(value = {URLs.LOGIN, URLs.ROOT}, method = RequestMethod.POST)
    public String loginPost(User user, BindingResult bindingResult, Model model){
        if( bindingResult.hasErrors()){
            return Views.REGISTER;
        }else{
            user = userService.getById(user.getEmail());
            if(user != null){
                model.addAttribute(user);
                return Views.HOME;
            }else {
                return Views.REGISTER;
            }
        }
    }

    @RequestMapping(value = URLs.REGISTER, method = RequestMethod.GET)
    public String registrationGET(){
        return Views.REGISTER;
    }

    @RequestMapping(value = URLs.REGISTER, method = RequestMethod.POST)
    public String registrationPost(RegisterForm form, Model model){
        if(form.getConfirmedPassword() == form.getPassword()){
            userService.add(form.toUser());
            return "redirect:" + URLs.LOGIN;
        }else{
            return "redirect:" + URLs.REGISTER;
        }
    }
}