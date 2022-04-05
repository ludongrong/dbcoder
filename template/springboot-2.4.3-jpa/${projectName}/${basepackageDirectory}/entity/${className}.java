package ${basepackage}.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "${tableName}")
public class ${className} extends BaseEntity implements Serializable {

	<#list columns as column>
	<#if column.name != "ID" && column.name != "CREATE_TIME" && column.name != "MODIFY_TIME">
	private ${column.javaType} ${column.javaNameVariable};
	
	</#if>
	</#list>
	/**
	<#list parentReferences as reference>
	private ${reference.className} ${reference.classNameVariable};
    
	</#list>
	<#list parentSelfReferences as reference>
	private ${reference.className} ${reference.classNameVariable};
    
	</#list>
	<#list childReferences as reference>
	private List<${reference.className}> ${reference.classNameVariable}List;

	</#list>
	<#list childSelfReferences as reference>
	private List<${reference.className}> ${reference.classNameVariable}List;

	</#list>
	*/
	
	public ${className}() {
		super();
	}
	
	/**
	 * 把父类的 jpa 注解提上来
	 * 这么做的原因
	 * 比如说不是每个实体的主键是ID, 有可能是复合主键.
	 */
	
	@Id
	@Column(name = "ID", length = 32, nullable = false, unique = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return super.getId();
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = false, insertable = true, updatable = false)
	public Date getCreateTime() {
		return super.getCreateTime();
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_TIME", nullable = false, insertable = true, updatable = true)
	public Date getModifyTime() {
		return super.getModifyTime();
	}
	
	/**
	 * 自身 jpa 注解
	 */
	
	<#list columns as column>
	<#if column.name != "ID" && column.name != "CREATE_TIME" && column.name != "MODIFY_TIME">
	@Column(name = "${column.name}", nullable = ${column.mandatory?then('true', 'false')})
	public ${column.javaType} get${column.javaName}() {
		return this.${column.javaNameVariable};
	}
	
	public void set${column.javaName}(${column.javaType} ${column.javaNameVariable}) {
		this.${column.javaNameVariable}=${column.javaNameVariable};
	}
	
	</#if>
	</#list>
}
