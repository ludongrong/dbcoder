package ${basepackage}.entity;.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.onemysoft.wms.dbcoder.entity.UUIDEntity;

public class LastUpdateListener {

	@PreUpdate
	@PrePersist
	public void setModifyTime(UUIDEntity uuidEntity) {
		uuidEntity.setModifyTime(new Date());
	}

	@PreUpdate
	@PrePersist
	public void setLastUpdate(UUIDEntity uuidEntity) {
		uuidEntity.setCreateTime(new Date());
	}
}
