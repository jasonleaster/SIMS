///**************************************************************
// * File name    : LoginController.java
// * Author       : Jason Leaster
// * Date         : 2016.07.24
// *
// * Description  :
// *     #LoginController will be responsible for requests of login
// * and registration.
// *****************************************************************/
//package org.sims.controller;
//
//import io.mybatis.service.AbstractService;
//import org.sims.dao.UserMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.sims.exception.DuplicatedPrimaryKeyException;
//import org.sims.form.LoginForm;
//import org.sims.form.RegisterForm;
//import org.sims.model.Book;
//import org.sims.model.User;
//import org.sims.util.AttributesKey;
//import org.sims.util.URLs;
//import org.sims.util.Views;
//import org.sims.util.WebCookie;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.List;
//
//
//@Controller
//public class LoginController {
//
//    @Autowired
//    @Qualifier("userService")
//    private AbstractService<User, Long, UserMapper> userService;
//
//    @Autowired
//    private BookService bookService;
//
//    private static final int BOOK_SHOW_IN_HOMEPAGE = 6;
//
//    @RequestMapping(value = {URLs.LOGIN, URLs.ROOT}, method = RequestMethod.GET)
//    public String loginGet(){
//        return Views.LOGIN;
//    }
//
//    /**
//     * Logic Control flow:
//     *
//     * 1. Query the user and determine whether the user in the system or not.
//     * 2. If the user is not in the system( not registered ), RETURN register view page.
//     * 3. If the user's password isn't correct, RETURN login view page.
//     * 4. Otherwise, login success.
//     *      4.1 Add the user into global session as "user" attribute.
//     *      4.2 If the user is administrator, add the user into the global session as "admin" attribute.
//     *      4.3 (Optional) Get the most popular books in the system and add them into
//     *          the view model as attribute in the returned view page.
//     *      4.4 RETURN home page or login success page.
//     *
//     * @param form      The login form
//     * @param model     The model which used to represent attributes in Spring MVC.
//     * @param request   The HttpServletRequest
//     * @param response  The HttpServletResponse
//     */
//    @RequestMapping(value = {URLs.LOGIN, URLs.ROOT}, method = RequestMethod.POST)
//    public String loginPost(LoginForm form, Model model,
//                            HttpServletRequest request, HttpServletResponse response){
//
//        User user = form.toUser();
//        User userInDB = userService.getById(user.getEmail());
//
//        if (userInDB == null){
//            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "The user does not exist.");
//            return Views.REGISTER;
//        }else if (! userInDB.getPassword().equals(user.getPassword())){
//            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "The password is not correct! Please try again!");
//            return Views.LOGIN;
//        }
//
//        if(form.isRememberMe()){
//            WebCookie.addCookie(response, WebCookie.COOKIE_NAME, userInDB.getEmail(), WebCookie.MAX_AGE);
//        }
//
//        HttpSession session = request.getSession();
//        session.setAttribute(AttributesKey.SESSION_ATTRIBUTES_USER, userInDB);
//
//        if(userInDB.isAdministrator()){
//            session.setAttribute(AttributesKey.SESSION_ATTRIBUTES_ADMIN, userInDB);
//        }else{
//            session.setAttribute(AttributesKey.SESSION_ATTRIBUTES_ADMIN, null);
//        }
//
//        List<Book> books = bookService.getPopularBook(BOOK_SHOW_IN_HOMEPAGE);
//
//        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOKS, books);
//
//        return Views.HOME;
//    }
//
//    @RequestMapping(value = URLs.LOGOUT)
//    public String logOut(HttpServletRequest request){
//
//        request.getSession().invalidate();
//
//        return "redirect:" + URLs.LOGIN;
//    }
//
//    @RequestMapping(value = URLs.REGISTER, method = RequestMethod.GET)
//    public String registrationGET(){
//        return Views.REGISTER;
//    }
//
//
//    /**
//     * Logic Control Flow:
//     * 1.Determine whether the user already existed(registered) in the system.
//     *   If the user already in the system, return remind information to tell the user
//     *   the ID have been used for others and try another ID information.
//     *   In the implementation of this system, the ID information is email.
//     *   RETURN to the register view page.
//     *
//     * 2.Check the confirmed-password equals to the password or not.
//     *   If they does not equals to each other, RETURN and back to the register view page.
//     *
//     * 3.Otherwise, the user register successful.
//     *      3.1 Add the user into the system.
//     *      3.2 Redirect to login view page.
//     *
//     * @param form      The register form. All information of user will binding into #RegisterForm
//     * @param model     The model which used to represent attributes in Spring MVC.
//     */
//    @RequestMapping(value = URLs.REGISTER, method = RequestMethod.POST)
//    public String registrationPost(RegisterForm form, Model model) throws Exception{
//
//        if(form == null){
//            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "empty registration information");
//            return Views.REGISTER;
//        }
//
//        if(userService.getById(form.getEmail()) != null){
//            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "The User already exist!");
//            return Views.REGISTER;
//        }
//
//        if( ! form.getConfirmedPassword().equals(form.getPassword()) ){
//            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, "Password confirmed error!");
//            return Views.REGISTER;
//        }else{
//            User user = form.toUser();
//            try {
//                userService.add(user);
//            }catch (DuplicatedPrimaryKeyException e){
//                model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, e.toString());
//                return Views.REGISTER;
//            }
//
//            model.addAttribute(AttributesKey.SESSION_ATTRIBUTES_USER, user);
//            model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_ERR_MSG, null);
//            return "redirect:" + URLs.LOGIN;
//        }
//    }
//
//    @RequestMapping(value = URLs.HOMEPAGE)
//    public String home(Model model){
//
//        List<Book> books = bookService.getPopularBook(BOOK_SHOW_IN_HOMEPAGE);
//
//        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_BOOKS, books);
//        return Views.HOME;
//    }
//}