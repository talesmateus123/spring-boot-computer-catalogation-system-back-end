package com.pml.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pml.domain.enums.ArchitectureType;
import com.pml.domain.enums.EquipmentType;

@Entity
public class Processor extends Electronic {
	private static final long serialVersionUID = 1L;
	@Size(max = 30, message = "{processorName.size}")
	private String processorName;
	@NotNull(message = "{not.null}")
	private Integer architecture;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "processor")
	@JsonIgnore
	private Computer computer;
	
	public Processor() {
		super();
		this.setEquipmentType(EquipmentType.PROCESSOR);	
		this.setItComposed(false);
	}
	
	public Processor(Long id, Date createdDate, Date lastModifiedDate, Date deletedDate, String manufacturer,
			String model, String description, String processorNumber, 
			ArchitectureType architecture, Computer computer) {
		super(id, createdDate, lastModifiedDate, deletedDate, EquipmentType.PROCESSOR, manufacturer, model, description, false);
		this.processorName = processorNumber;
		this.architecture = architecture.getCod();
		this.setItComposed(false);
	}
	
	public String getProcessorName() {
		return processorName;
	}
	
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	
	public ArchitectureType getArchitecture() {
		return ArchitectureType.toEnum(architecture);
	}
	
	public void setArchitecture(ArchitectureType architecture) {
		this.architecture = architecture.getCod();
	}
	
	public Computer getComputer() {
		return computer;
	}
	
	public void setComputer(Computer computer) {
		this.computer = computer;
	}
	
	
	
}
