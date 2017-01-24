package org.sims.controller.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.sims.model.User;
import org.sims.service.UserService;
import org.sims.util.AttributesKey;
import org.sims.util.PageInfo;
import org.sims.util.URLs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = URLs.API + URLs.USERS)
public class RestfulUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = URLs.QUERY, produces={"application/json; charset=UTF-8"})
    public @ResponseBody List<User>
    query(User form,
          @RequestParam(value = "pageNum", required = false) Integer pageNum,
          HttpServletRequest request) throws Exception {

        List<User> searchResults = new ArrayList<>();

        if(form == null){
            return searchResults;
        }

        HttpSession session = request.getSession();
        User oldForm = (User) session.getAttribute(AttributesKey.SESSION_ATTRIBUTES_USER_QUERY_FORM);
        if (pageNum != null) {
            form = oldForm;
        } else {
            session.setAttribute(AttributesKey.SESSION_ATTRIBUTES_USER_QUERY_FORM, form);
        }

        PageInfo pageInfo;

        int pageSize = 4;
        if (pageNum == null) {
            pageInfo = new PageInfo(0, pageSize, new ArrayList<>());
        } else {
            pageInfo = new PageInfo((pageNum.intValue() - 1) * pageSize, pageSize, new ArrayList<>());
        }

        pageInfo.setURL(request.getRequestURI());

        searchResults = userService.pagedFuzzyQuery(form, pageInfo);

        return searchResults;
    }
}
