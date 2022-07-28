<#include "/copyright_include/class.header"/>
<#assign className = table.className>
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

public class BaseModel implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;
    
    private String id;
    private Date createDate;
    private Date modifyDate;
    
	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id;
	}
	
	public Date getCreateDate() {
	    return this.createDate;
	}

	public void setCreateDate(Date createDate) {
	    this.createDate = createDate;
	}

	public Date getModifyDate() {
	    return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
	    this.modifyDate = modifyDate;
	}
	
	public int hashCode(){
		return ((this.id == null) ? System.identityHashCode(this) : this.id.hashCode());
	}

	public boolean equals(Object obj){
	  if (this == obj)
	    return true;

	  if (obj == null)
	    return false;

	  if (super.getClass().getPackage() != obj.getClass().getPackage())
	    return false;

	  BaseModel other = (BaseModel)obj;
	  if (this.id == null) {
	    if (other.getId() == null) break label69;
	    return false;
	  }

	  label69: return (this.id.equals(other.getId()));
	}
	  
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
	
}



