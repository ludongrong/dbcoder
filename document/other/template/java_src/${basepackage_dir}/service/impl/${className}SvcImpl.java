<#include "/copyright_include/class.header"/>
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.service;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ${className}SvcImpl implements ${className}Svc {
    
	// 包含 “dao” 区域
	private ${className}Dao ${classNameLower}Dao;
	
	public void set${className}Dao(${className}Dao dao) {
		this.${classNameLower}Dao = dao;
	}
	
	public ${className}Dao get${className}Dao() {
		return this.${classNameLower}Dao;
	}

	@Override
    public ${className} create(${className} model) {
    	return this.${classNameLower}Dao.create(model);
    }
    
    @Override
    public List<${className}> creates(List<${className}> models) {
    	return this.${classNameLower}Dao.creates(models);
    }
	
    @Override
	public int destroy(String pk) {
		return this.${classNameLower}Dao.destroy(pk);
	}
	
    @Override
	public int[] destroys(String[] pks) {
		return this.${classNameLower}Dao.destroys(pks);
	}
	
    @Override
	public int update(${className} model) {
		return this.${classNameLower}Dao.update(model);
	}
	
    @Override
	public int[] updates(List<${className}> models) {
		return this.${classNameLower}Dao.updates(models);
	}
	
	@Transactional(readOnly=true)
	@Override
	public ${className} detail(String pk) {
		return this.${classNameLower}Dao.detail(pk);
	}
	
	@Override
	public boolean isExist(${className} model){
		int count =  this.${classNameLower}Dao.count(model.getId());
		if(count > 0){
			return true;
		}
		
		return false;
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<${className}> findAll() {
		return this.${classNameLower}Dao.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<${className}> find(Map<String, Object> params) {
		return this.${classNameLower}Dao.find(params);
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<${className}> findPage(Map<String, Object> params) {
		return this.${classNameLower}Dao.findPage(params);
	}
	
}
