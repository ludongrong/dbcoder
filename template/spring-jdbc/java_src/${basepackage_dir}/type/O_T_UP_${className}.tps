<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUp = className?upper_case>
create or replace type O_T_UP_${classNameUp} as object
(
   STATIC FUNCTION ADD_SELF()RETURN  INTEGER,
   STATIC FUNCTION DELETE_SELF()RETURN INTEGER,
   STATIC FUNCTION UPDATE_SELF()RETURN INTEGER,
   STATIC FUNCTION DETAIL()RETURN INTEGER
)