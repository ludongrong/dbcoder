package ${basepackage}.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import ${basepackage}.entity.listener.LastUpdateListener;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(LastUpdateListener.class)
public class UUIDEntity implements Serializable, Persistable<String> {

	private static final long serialVersionUID = 3148176768559230877L;
	
	static final public String _ID_STRATEGY = "uuid";
	
	static final public String _ID_COLUMN = "ID";
	
	static final public String _ID = "id";
	
	static final public String _CREATETIME = "createTime";
	
	static final public String _MODIFYTIME = "modifyTime";
	
	static final public String _CREATE_TIME_COLUMN = "CREATE_TIME";
	
	static final public String _MODIFY_TIME_COLUMN = "MODIFY_TIME";
	
	@Id
	@Column(name = _ID_COLUMN, length = 32, nullable = false, unique = true)
	@GeneratedValue(generator = _ID_STRATEGY)
	@GenericGenerator(name = _ID_STRATEGY, strategy = _ID_STRATEGY)
	@Getter
	@Setter
	private String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = _CREATE_TIME_COLUMN, nullable = false, insertable = true, updatable = false)
	@Getter
	@Setter
	private Date createTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = _MODIFY_TIME_COLUMN, nullable = false, insertable = true, updatable = true)
	@Getter
	@Setter
	private Date modifyTime;

	@Transient
	private boolean isNew = true;

	public UUIDEntity() {
		super();
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	@PrePersist
	@PostLoad
	void markNotNew() {
		this.isNew = false;
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

		UUIDEntity other = (UUIDEntity) obj;
		if (this.id == null) {
			if (other.getId() != null) {
				return false;
			}
		}

		return (this.id.equals(other.getId()));
	}

}