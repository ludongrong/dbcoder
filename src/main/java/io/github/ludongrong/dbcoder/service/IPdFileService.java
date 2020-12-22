package io.github.ludongrong.dbcoder.service;

import java.util.List;
import java.util.Map;

import io.github.ludongrong.dbcoder.entity.PdFileBo;
import io.github.ludongrong.dbcoder.sql.DynamicCondition;

public interface IPdFileService {
    
	boolean create(PdFileBo pdFile);

	int delete(DynamicCondition dyc, Map<String, Object> param);
	
	int delete(Map<String, Object> param);
	
	int delete(String Id);

	int update(Map<String, Object> input, DynamicCondition dyc, Map<String, Object> param);

	int update(Map<String, Object> input, Map<String, Object> param);
	
	int update(PdFileBo pdFile, String Id);
	
	long count(DynamicCondition dyc, Map<String, Object> param);
	
	long count(Map<String, Object> param);
	
	List<PdFileBo> list(DynamicCondition dyc, Map<String, Object> param);
	
	List<PdFileBo> list(Map<String, Object> param);

	List<PdFileBo> list(int offset, int limit, DynamicCondition dyc, Map<String, Object> param);
	
	List<PdFileBo> list(int offset, int limit, Map<String, Object> param);
	
	PdFileBo get(String Id);
}
