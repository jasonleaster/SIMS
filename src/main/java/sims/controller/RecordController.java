package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sims.model.Record;
import sims.service.BookService;
import sims.service.RecordService;
import sims.service.UserService;
import sims.util.MsgAndContext;
import sims.util.PageInfo;
import sims.util.URLs;
import sims.util.Views;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = URLs.RECORDS)
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @RequestMapping(value = URLs.QUERY )
    public String queryByUserId(Record record,
                                @RequestParam(value = "pageNum", required = false) Integer pageNum,
                                Model model, HttpServletRequest request){

        PageInfo pageInfo;

        int pageSize = 1;
        if(pageNum == null){
            pageInfo = new PageInfo(0, pageSize, new ArrayList());
        }else{
            pageInfo = new PageInfo((pageNum.intValue() - 1) * pageSize, pageSize, new ArrayList());
        }

        pageInfo.setURL(request.getRequestURI());

        try {
            List<Record> records = recordService.pagedFuzzyQuery(record, pageInfo);

            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_RECORDS, records);
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_PAGEINFO, pageInfo);
        }catch (Exception ignore){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "search Exception");
            return Views.HOME;
        }


        return Views.RECORD_RESULT_TABLE;
    }
    
}
