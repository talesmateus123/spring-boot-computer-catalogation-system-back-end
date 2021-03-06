package com.pml.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pml.domain.enums.EquipmentType;
import com.pml.domain.enums.RamMemoryArchitecture;

@Entity
public class RamMemory extends Electronic {
	private static final long serialVersionUID = 1L;
	@NotNull(message = "{not.null}")
	private Double sizeInGB;
	@NotNull(message = "{not.null}")
	private Integer architecture;
	@ManyToOne
	@JoinColumn(name = "computer_id")
	@JsonBackReference
	private Computer computer;
	
	public RamMemory() {
		super();
		this.setEquipmentType(EquipmentType.RAM_MEMORY);
		this.setItComposed(false);
	}
	
	public RamMemory(Long id, Date createdDate, Date lastModifiedDate, Date deletedDate, String manufacturer,
			String model, String description, Double sizeInGB, RamMemoryArchitecture architecture, 
			Computer computer) {
		super(id, createdDate, lastModifiedDate, deletedDate, EquipmentType.RAM_MEMORY, manufacturer, model, description, false);
		this.sizeInGB = sizeInGB;
		this.architecture = architecture.getCod(); 
		this.computer = computer;
		this.setItComposed(false);
	}
	
	public Double getSizeInGB() {
		return sizeInGB;
	}
	
	public void setSizeInGB(Double sizeInGB) {
		this.sizeInGB = sizeInGB;
	}
	
	public RamMemoryArchitecture getArchitecture() {
		return RamMemoryArchitecture.toEnum(architecture);
	}
	
	public void setArchitecture(RamMemoryArchitecture architecture) {
		this.architecture = architecture.getCod();
	}

	public Computer getComputer() {
		return computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}
	
	
	
}
