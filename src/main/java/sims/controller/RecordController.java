package sims.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import sims.model.Record;
import sims.service.RecordService;
import sims.util.MsgAndContext;
import sims.util.URLs;
import sims.util.Views;

import java.util.List;

@Controller
@RequestMapping(value = URLs.RECORDS)
public class RecordController {

    @Autowired
    private RecordService service;

    @RequestMapping(value = URLs.QUERY + "/user/{userId:.+}")
    public String queryByUserId(@PathVariable("userId") String userId, Model model){
        List<Record> records = service.getByUserId(userId);
        model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_RECORDS, records);
        return Views.RECORD_RESULT_TABLE;
    }

    @RequestMapping(value = URLs.QUERY + "/book/{bookId:.+}")
    public String queryByBookId(@PathVariable("bookId") String bookId, Model model){
        List<Record> records = service.getByBookId(bookId);
        model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_RECORDS, records);
        return Views.RECORD_RESULT_TABLE;
    }

    @RequestMapping(value = URLs.QUERY + "/type/{recordtype:.+}")
    public String queryByRecordType(@PathVariable("recordtype") String recordType, Model model){
        List<Record> records = service.getByRecordType(recordType);
        if(records != null){
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_RECORDS, records);
        }else{
            model.addAttribute(MsgAndContext.MODEL_ATTRIBUTES_ERR_MSG, "No such records");
        }

        return Views.RECORD_RESULT_TABLE;
    }
    
}
