/** 
 * This is the class "ComputerUserDTO". That class will be to represent a computer user dto.
 * 
 * @author Tales Mateus de Oliveira Ferreira <talesmateus1999@hotmail.com>
 */
package com.pml.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ComputerUserNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "{not.empty}")
	@Size(min = 4, max = 20, message = "{name.size}")
	private String name;
	@Size(min = 4, max = 20, message = "{lastName.size}")
	private String lastName;
	@Email(message = "{email.pattern}")
	private String email;
	@NotNull(message = "{not.null}")
	private Integer sectorId;
	private List<Long> useTheComputersId = new ArrayList<>();
	
	public ComputerUserNewDTO() {		
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Integer getSectorId() {
		return sectorId;
	}
	
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public List<Long> getUseTheComputersId() {
		return useTheComputersId;
	}

	public void setUseTheComputersId(List<Long> useTheComputersId) {
		this.useTheComputersId = useTheComputersId;
	}
	


}
