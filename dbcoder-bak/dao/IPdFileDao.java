package io.github.ludongrong.dbcoder.dao;

import java.util.List;
import java.util.Map;

import io.github.ludongrong.dbcoder.powerdesigner.entity.PdFileBo;
import io.github.ludongrong.dbcoder.sql.DynamicCondition;

public interface IPdFileDao {

    int create(PdFileBo pdFile);

    int delete(DynamicCondition dyc, Map<String, Object> param);

    int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param);

    long count(DynamicCondition dyc, Map<String, Object> param);

    List<PdFileBo> query(DynamicCondition dyc, Map<String, Object> param);

    List<PdFileBo> query(int offset, int limit, DynamicCondition dyc, Map<String, Object> param);
}
