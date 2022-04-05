package ${basepackage}.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 3148176768559230877L;
	
	private String id;

	private Date createTime;

	private Date modifyTime;

	public BaseEntity() {
		super();
	}
	
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	public int hashCode() {
		return ((this.id == null) ? System.identityHashCode(this) : this.id.hashCode());
	}

	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (super.getClass().getPackage() != obj.getClass().getPackage())
			return false;

		BaseEntity other = (BaseEntity) obj;
		if (this.id == null) {
			if (other.getId() != null) {
				return false;
			}
		}
		
		return (this.id.equals(other.getId()));
	}
	
}
