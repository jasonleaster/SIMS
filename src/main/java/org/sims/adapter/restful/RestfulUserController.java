package org.sims.adapter.restful;

import com.github.pagehelper.PageHelper;
import io.mybatis.mapper.example.Example;
import io.mybatis.service.AbstractService;
import org.sims.domain.User;
import org.sims.util.AttributesKey;
import org.sims.util.URLs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Restful 数据接口
 */
@RestController
@RequestMapping(value = URLs.API + URLs.USERS)
public class RestfulUserController {

    @Autowired
    @Qualifier("userService")
    private AbstractService userService;

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

        Example<User> example = new Example<>();
        example.createCriteria()
                .andEqualTo(User::getUserType, 1);

        PageHelper.startPage(pageNum, 2);

        searchResults = userService.findList(example);

        return searchResults;
    }
}
