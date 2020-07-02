package com.cs2c.project.module.ddos.domain;

import com.cs2c.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 关键字表 proc_keyword
 * 
 * @author Joenas
 * @date 2018-10-13
 */
public class DDOS extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	public DDOS(String message) {
		this.message = message;
	}
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "DDOS{" +
				"message='" + message + '\'' +
				'}';
	}
}
