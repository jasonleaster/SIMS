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
import org.springframework.web.bind.annotation.ResponseBody;
import sims.form.LoginForm;
import sims.form.RegisterForm;
import sims.model.User;
import sims.service.UserService;
import sims.util.MsgAndContext;
import sims.util.URLs;
import sims.util.Views;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    public String loginPost(LoginForm form, BindingResult bindingResult, Model model,
                            HttpServletRequest request, HttpServletResponse response){

        if( bindingResult.hasErrors()){
            return Views.LOGIN;
        }

        User user = form.toUser();
        User userInDB = userService.getById(user.getEmail());

        if (userInDB == null){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "The user does not exist.");
            return Views.LOGIN;
        }else if (! userInDB.getPassword().equals(user.getPassword())){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "The password is not correct! Please try again!");
            return Views.LOGIN;
        }

        request.getSession().setAttribute(MsgAndContext.SESSION_CONTEXT_USER, userInDB);
        model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_USER, userInDB);
        return Views.HOME;
    }

    @RequestMapping(value = URLs.LOGOUT, method = RequestMethod.GET)
    public String logOut(HttpServletRequest request){

        request.getSession().invalidate();

        return "redirect:" + URLs.LOGIN;
    }

    @RequestMapping(value = URLs.REGISTER, method = RequestMethod.GET)
    public String registrationGET(){
        return Views.REGISTER;
    }

    @RequestMapping(value = URLs.REGISTER, method = RequestMethod.POST)
    public String registrationPost(RegisterForm form, Model model){

        if(userService.getById(form.getEmail()) != null){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "The User already exist!");
            return Views.REGISTER;
        }

        if( ! form.getConfirmedPassword().equals(form.getPassword()) ){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "Password confirmed error!");
            return Views.REGISTER;
        }else{
            User user = form.toUser();
            userService.add(user);
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_USER, user);
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, null);
            return "redirect:" + URLs.LOGIN;
        }
    }
}