package sims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sims.service.RecordService;
import sims.util.URLs;

@Controller
@RequestMapping(value = URLs.RECORDS)
public class RecordController {

    private RecordService service;

    @RequestMapping(value = URLs.QUERY, method = RequestMethod.GET)
    public String query(){
        return "TODO XXX";
    }
    
}
