package sims.controller.restful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sims.model.Record;
import sims.service.RecordService;
import sims.util.AttributesKey;
import sims.util.PageInfo;
import sims.util.URLs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = URLs.API + URLs.RECORDS)
public class RestfulRecordController {

    @Autowired
    private RecordService recordService;

    @RequestMapping(value = URLs.QUERY, produces={"application/json; charset=UTF-8"})
    public @ResponseBody List<Record>
    query(Record form,
          @RequestParam(value = "pageNum", required = false) Integer pageNum,
          HttpServletRequest request) throws Exception {

        List<Record> records = new ArrayList<>();

        if(form == null){
            return records;
        }

        HttpSession session = request.getSession();
        Record oldForm = (Record) session.getAttribute(AttributesKey.SESSION_ATTRIBUTES_RECORD_QUERY_FORM);
        if (pageNum != null) {
            form = oldForm;
        } else {
            session.setAttribute(AttributesKey.SESSION_ATTRIBUTES_RECORD_QUERY_FORM, form);
        }

        if (form.getId() != null) {
            // Unfinished
            return null;
            //queryBookByISBN(form.getIsbn(), model);
        }

        PageInfo pageInfo;

        int pageSize = 4;
        if (pageNum == null) {
            pageInfo = new PageInfo(0, pageSize, new ArrayList());
        } else {
            pageInfo = new PageInfo((pageNum.intValue() - 1) * pageSize, pageSize, new ArrayList());
        }

        pageInfo.setURL(request.getRequestURI());

        List<Record> recordsInDB = null;
        try {
            recordsInDB = recordService.pagedFuzzyQuery(form, pageInfo);
        }catch (Exception e){
        }

        return records;
    }
}
