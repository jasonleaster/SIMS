package org.sims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sims.dao.RecordMapper;
import org.sims.model.Record;
import org.sims.service.RecordService;
import org.sims.util.PageInfo;
import org.sims.web.BaseDomain;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@SuppressWarnings("serial")
@Service("recordService")
public class RecordServiceImpl extends BaseDomain implements RecordService {

    @Autowired
    RecordMapper recordMapper;

    static private long recordsNumInDB;

    @Override
    public void init() {
        recordsNumInDB = recordMapper.countAll();
    }

    @Override
    public Record getById(int id) {

        Record record = null;
        try{
            record = recordMapper.selectByPrimaryKey(id);
        }catch (Exception ignore){
        }
        return record;
    }

    @Override
    public void add(Record record) {
        if(record == null){
            return;
        }

        recordMapper.insert(record);
        recordsNumInDB++;
    }

    @Override
    public void delete(int id) {
        if(getById(id) != null){
            recordMapper.deleteByPrimaryKey(id);
            recordsNumInDB--;
        }
    }

    @Override
    public void modify(Record record) {
        //recordMapper.updateByPrimaryKeySelective(record);
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<Record> pagedFuzzyQuery(Record record, PageInfo pageInfo) throws Exception{
        List<Record> records = new ArrayList<>();

        if(record == null){
            record = new Record();
        }

        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        if(record.getId() != null){
            records.add(this.getById(record.getId()));
            return records;
        }
        HashMap map = new HashMap();

        Field[] fields = record.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(record));
        }

        fields = pageInfo.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            map.put(field.getName(), field.get(pageInfo));
        }

        records = recordMapper.selectFuzzy(map);

        int totalCount = recordMapper.selectItemCount(map);
        pageInfo.setTotalCount(totalCount);
        return records;
    }

    @Override
    public long totalCountInDB(){
        return recordsNumInDB;
    }
}
