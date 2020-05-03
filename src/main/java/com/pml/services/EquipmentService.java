/** 
 * This is the class "EquipmentService". Which will be able to intermediate the repository class and the service class.
 * 
 * @author Tales Mateus de Oliveira Ferreira <talesmateus1999@hotmail.com>
 */
package com.pml.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pml.domain.Equipment;
import com.pml.repositories.EquipmentRepository;
import com.pml.services.exceptions.ObjectNotFoundException;

public abstract class EquipmentService {
	@Autowired
	private EquipmentRepository repository;
		
	// Simple search methods
	public Equipment findById(Long id) {
		Optional<Equipment> object = this.repository.findById(id);
		return object.orElseThrow(()-> new ObjectNotFoundException("Equipment not found: id: '" + id + "'. Type: " + object.getClass().getName()));
	}
	
	public Equipment findByPatrimonyId(String patrimonyId) {
		Optional<Equipment> object = this.repository.findByPatrimonyId(patrimonyId);
		return object.orElseThrow(()-> new ObjectNotFoundException("Equipment not found: patrimonyId: '" + patrimonyId + "'. Type: " + object.getClass().getName()));
	}

	// Auxiliary methods
	/**
	 * Recover data of created date and updates the last modified date.
	 * @param object Equipment
	 * @return void
	 */
	protected void recoverData(Equipment object) {
		Equipment oldObject = this.findById(object.getId());
		object.setCreatedDate(oldObject.getCreatedDate());
		object.setLastModifiedDate(new Date());
	}
	
	/**
	 * Verify if already exists the patrimony id requested.
	 * @param patrimonyId String
	 * @return boolean
	 */
	protected boolean alreadyExists(String patrimonyId) {	
		if(patrimonyId == null)
			return false;
		
		Optional<Equipment> objectByPatrimonyId = this.repository.findByPatrimonyId(patrimonyId);
		
		if(objectByPatrimonyId.isEmpty())
			return false;
		return true;
	}
	
	/**
	 * Verify if the object in a question has its patrimony id changed.
	 * @param object Equipment
	 * @return boolean
	 */
	protected boolean patrimonyIdIsChanged(Equipment object) {	
		Optional<Equipment> objectByPatrimonyId = this.repository.findByPatrimonyId(object.getPatrimonyId());	
		// Generates an exception if object doesn't exists 
		Equipment objectById = this.findById(object.getId());
		
		if(objectByPatrimonyId.isEmpty())
			return true;
		
		if(objectById.getPatrimonyId().equals(objectByPatrimonyId.get().getPatrimonyId()))
			return false;		
		return true;
	}
	
	
	
}