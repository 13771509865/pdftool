package com.neo.model;

import java.io.Serializable;
import java.util.Date;


/**
 *  Persistant Object
 */
public abstract class BasePO implements Serializable {
	private static final long serialVersionUID = 7427110976978065450L;
	protected Long id;
	protected Date gmtCreate;
	protected Date gmtModify;
	protected Integer status = 1;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(Date gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
