package ${basepackage}.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${basepackage}.entity.${className};
import ${basepackage}.repository.${className}Repository;
import ${basepackage}.service.${className}Service;

@Service
public class ${className}ServiceImpl implements ${className}Service {
    
	@Autowired(required = true)
	private ${className}Repository ${classNameVariable}Repository;
	
	@Transactional
	@Override
	public ${className} create(${className} ${classNameVariable}) {
		
		return ${classNameVariable}Repository.save(${classNameVariable});
	}

}
