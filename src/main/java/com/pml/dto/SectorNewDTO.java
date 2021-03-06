package com.pml.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SectorNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "{not.empty}")
	@Size(min = 3, max = 30, message = "{name.size}")
	private String name;
	@Pattern(regexp = "(\\(\\d{2}\\)\\s)(\\d{4,5}\\-\\d{4})", message = "{phone.pattern}")
	private String phone;
	
	public SectorNewDTO() {
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}	
	
	

}
