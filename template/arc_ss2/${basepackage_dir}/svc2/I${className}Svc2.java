<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
${className} create(${className} ${classNameLower});
	
List<${className}> create(List<${className}> ${classNameLower}s);

void update(${className} ${classNameLower});

void update(List<${className}> ${classNameLower}s);

void update(List<${className}> ${classNameLower}s, int num);

void del();

void delById(String pk);

List<${className}> find();

${className} findById(String pk);

List<${className}> create(List<${className}> ${classNameLower}s, int num);

boolean existById(String pk);
