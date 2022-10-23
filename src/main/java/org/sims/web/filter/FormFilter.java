/**************************************************************
 * File name    : Filter.java
 * Author       : Jason Leaster
 * Date         : 2016.09.22
 *
 * Description  :
 *     For every HTTP request, #FormFilter works like a filter to
 * redirect un-login user to the login page.
 *****************************************************************/
package org.sims.web.filter;

import org.springframework.web.filter.OncePerRequestFilter;
import org.sims.domain.User;
import org.sims.util.AttributesKey;
import org.sims.util.URLs;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormFilter extends OncePerRequestFilter {

    String[] NOT_FILTER = new String[] {URLs.LOGIN, URLs.REGISTER, URLs.PATH_STATIC};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(AttributesKey.SESSION_ATTRIBUTES_USER);

        if(user == null && ! isLoginURL(request.getRequestURL().toString(), request)){
            request.getRequestDispatcher(URLs.LOGIN).forward(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isLoginURL(String requestURL, HttpServletRequest request){
        if(request.getContextPath().equalsIgnoreCase(requestURL) ||
                (request.getContextPath() + "/").equalsIgnoreCase(requestURL)){
            return true;
        }

        for(String url : NOT_FILTER){
            if(requestURL != null && requestURL.indexOf(url) >= 0){
                return true;
            }
        }

        return false;
    }
}
