<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
//##################################
//#delete
//##################################
<#list table.columns as column>
@Override
public void deleteBy${column.columnName}(${column.javaType} ${column.columnNameLower}){
	int result = this.${classNameLower}Dao.deleteBy${column.columnName}(${column.columnNameLower});
	if (logger.isDebugEnabled()){
		logger.debug("result[{}]", result);
	}
	if (result < 1 && result != -2)
	{
		throw new RuntimeException("fail");
	}
}
</#list>

//##################################
//#update
//##################################
@Override
public void update(${className} ${classNameLower})
{
	int result = this.${classNameLower}Dao.update(${classNameLower});
	if (result != 1 && result != -2)
	{
		throw new RuntimeException("fail");
	}
}

@Override
public void update(List<${className}> ${classNameLower}s)
{
	int[] reuslts = this.${classNameLower}Dao.update(${classNameLower}s);
	for (int result : reuslts) {
		if (result < 1 && result != -2)
			throw new RuntimeException("fail");
		}
	}
}

@Override
public void update(List<${className}> ${classNameLower}s, int num)
{
	List<${className}> sub${className}s = new ArrayList<>();

	for (int i = 0; i < ${classNameLower}s.size(); i++)
	{
		sub${className}s.add(${classNameLower}s.get(i));

		if (i + 1 == ${classNameLower}s.size() || i == num)// {num}个时做一次插入
		{
			update(sub${className}s);
			sub${className}s.clear();
		}
	}
}

<#list table.columns as column>
@Override
public void update${column.columnName}ById(${column.javaType} ${column.columnNameLower}){
	int result = this.${classNameLower}Dao.update${column.columnName}ById(${column.columnNameLower});
	if (logger.isDebugEnabled()){
		logger.debug("result[{}]", result);
	}
	if (result < 1 && result != -2)
	{
		throw new RuntimeException("fail");
	}
}
</#list>

//##################################
//#find
//##################################
<#list table.columns as column>
@Override
public List<${className}> findBy${column.columnName}(${column.javaType} ${column.columnNameLower}){
	List<${className}> objList = this.${classNameLower}Dao.findBy${column.columnName}(${column.columnNameLower});
	if (logger.isDebugEnabled()){
		logger.debug("${column.columnNameLower}[{}],size[{}]", ${column.columnNameLower}, objList.size());
	}
	return objList;
}
</#list>

<#list table.columns as column>
@Override
public List<${className}> findBy${column.columnName}(${column.javaType} ${column.columnNameLower},boolean isContain${className}){
	List<${className}> objList = this.${classNameLower}Dao.findBy${column.columnName}(${column.columnNameLower});
	if (logger.isDebugEnabled()){
		logger.debug("${column.columnNameLower}[{}],size[{}]", ${column.columnNameLower}, objList.size());
	}

	List<${className}> objNewList = new ArrayList<>();
	if (isContain${className} && CollectionUtil.isNotEmpty(objList))
	{
		for (${className} ${classNameLower} : objList)
		{
			if (StrUtil.isNotEmpty(${classNameLower}.getId()))
			{
				List<dddd> ddddList = this.ddddDao.findBy${className}Id(${classNameLower}.getId());
				if (CollectionUtil.isNotEmpty(ddddList))
				{
					${classNameLower}.set(ddddList);
					objNewList.add(${classNameLower});
				}
			}
		}
	}
	
	return objNewList;
}
</#list>

<#list table.columns as column>
@Override
public boolean existBy${column.columnName}(${column.javaType} ${column.columnNameLower}){
	List<${className}> objList = this.${classNameLower}Dao.findBy${column.columnName}(${column.columnNameLower});
	if (logger.isDebugEnabled()){
		logger.debug("${column.columnNameLower}[{}],size[{}]", ${column.columnNameLower}, objList.size());
	}
	if(null!=objList&&objList.size()>0){
		return true;
	}
	return false;
}
</#list>