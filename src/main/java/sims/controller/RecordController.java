package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sims.model.Record;
import sims.model.User;
import sims.service.RecordService;
import sims.util.AttributesKey;
import sims.util.PageInfo;
import sims.util.URLs;
import sims.util.Views;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = URLs.RECORDS)
public class RecordController {

    @Autowired
    private RecordService recordService;

    @RequestMapping(value = URLs.QUERY )
    public String query(Record recordForm,
                        @RequestParam(value = "pageNum", required = false) Integer pageNum,
                        Model model, HttpServletRequest request) throws Exception{

        HttpSession session = request.getSession();
        Record oldRecordForm = (Record)session.getAttribute( AttributesKey.SESSION_ATTRIBUTES_RECORD_QUERY_FORM);

        if(pageNum != null){
            recordForm = oldRecordForm;
        }else{
            session.setAttribute(AttributesKey.SESSION_ATTRIBUTES_RECORD_QUERY_FORM, recordForm);
        }

        /*
        * Normal user can't access others record information
        * */
        if(recordForm != null && recordForm.getUserId() != null){
            User userLoggedIn = (User)session.getAttribute(AttributesKey.SESSION_ATTRIBUTES_USER);
            if( userLoggedIn != null && userLoggedIn.isAdministrator() == false &&
                    ! userLoggedIn.getEmail().equals(recordForm.getUserId())
                    )
            {
                return Views.HOME;
            }
        }

        PageInfo pageInfo;

        int pageSize = 1;
        if(pageNum == null){
            pageInfo = new PageInfo(0, pageSize, new ArrayList<Record>());
        }else{
            pageInfo = new PageInfo((pageNum.intValue() - 1) * pageSize, pageSize, new ArrayList<Record>());
        }

        pageInfo.setURL(request.getRequestURI());

        List<Record> records = recordService.pagedFuzzyQuery(recordForm, pageInfo);

        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_RECORDS, records);
        model.addAttribute(AttributesKey.MODEL_ATTRIBUTES_PAGEINFO, pageInfo);

        return Views.RECORD_RESULT_TABLE;
    }

    @RequestMapping(value = URLs.DELETE + "/{id}")
    public String recordDelete(@PathVariable("id") int id){
        recordService.delete(id);
        return Views.HOME;
    }
}
