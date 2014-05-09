package com.moscue.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
name="stock_meta",
uniqueConstraints={@UniqueConstraint(columnNames={"code"})}
)
public class StockMeta {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String code;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
