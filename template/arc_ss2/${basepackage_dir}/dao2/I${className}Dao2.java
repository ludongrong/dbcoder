<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
int create(Map<String, String> ${classNameLower}Map);

int[] create(final List<${className}> ${classNameLower}s);

int update(String pk, Map<String, Object> ${classNameLower}Map);

int[] update(final List<${className}> ${classNameLower}s);

int update(${className} ${classNameLower});

int del();

int delById(String pk);

int delByDynamicCondition(DynamicCondition dyc, Map<String, Object> input);

List<${className}> find();

/**
 * 根据ID查找${className}
 * 
 * @param id
 * @return
 */
${className} findById(String id);

List<${className}> findByColmun(String colmun, String value);

List<${className}> findByDynamicCondition(DynamicCondition dyc, Map<String, Object> input);

int count();

int countByDynamicCondition(DynamicCondition dyc, Map<String, Object> input);