package org.seckill.dao;

import org.seckill.model.SuccessKilled;
import org.seckill.model.SuccessKilledKey;

public interface SuccessKilledMapper {
    int deleteByPrimaryKey(SuccessKilledKey key);

    int insert(SuccessKilled record);

    int insertSelective(SuccessKilled record);

    SuccessKilled selectByPrimaryKey(SuccessKilledKey key);

    int updateByPrimaryKeySelective(SuccessKilled record);

    int updateByPrimaryKey(SuccessKilled record);
}