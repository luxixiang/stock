package com.moscue.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
/**
 * 持仓
 * @author Aaron
 *
 */
@Entity
public class Balance {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Double remain;
	private long holderId;
	private Date updateTime;
	private Date addTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getRemain() {
		return remain;
	}

	public void setRemain(Double remain) {
		this.remain = remain;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public long getHolderId() {
		return holderId;
	}

	public void setHolderId(long holderId) {
		this.holderId = holderId;
	}
}
