/** 
 * This is the class "PrinterService". Which will be able to intermediate the repository class and the service class.
 * 
 * @author Tales Mateus de Oliveira Ferreira <talesmateus1999@hotmail.com>
 */
package com.pml.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pml.domain.Printer;
import com.pml.dto.PrinterNewDTO;
import com.pml.repositories.PrinterRepository;
import com.pml.services.exceptions.ConflictOfObjectsException;
import com.pml.services.exceptions.IllegalArgException;
import com.pml.services.exceptions.InvalidQueryException;
import com.pml.services.exceptions.ObjectNotFoundException;
import com.pml.util.ServiceUtil;

@Service
public class PrinterService extends EquipmentService {
	@Autowired
	private PrinterRepository repository;
	@Autowired
	private SectorService sectorService;

	// List search methods
	public List<Printer> findAll() {
		return this.repository.findByOrderByPatrimonyId();
	}
	
	public Page<Printer> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		try {
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.fromString(direction), orderBy);
    		        	
        	if(!ServiceUtil.parameterExistsInTheClass(orderBy, Printer.class)) 
        		throw new InvalidQueryException("The value of orderBy parameter: '" + orderBy + "' doesn't exists in the '" + Printer.class.getSimpleName() + "' class.");
        	return this.repository.findPageByOrderByPatrimonyId(pageRequest);
            
    	}
    	catch (IllegalArgumentException e) {
    		throw new IllegalArgException("The value of direction parameter: '" + direction + "' is invalid, this value must be 'ASC' or 'DESC'.");
		}
	}

	// Simple search methods	
	@Override
	public Printer findById(Long id) {
		return this.repository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Printer not found: id: '" + id + "'. Type: " + Printer.class.getSimpleName()));
	}
	
	public Page<Printer> search(Integer page, Integer linesPerPage, String direction, String orderBy, String searchTerm) {
		try {
    		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.fromString(direction), orderBy);
    		        	
        	if(!ServiceUtil.parameterExistsInTheClass(orderBy, Printer.class)) 
        		throw new InvalidQueryException("The value of orderBy parameter: '" + orderBy + "' doesn't exists in the '" + Printer.class.getSimpleName() + "' class.");
        	return repository.search(searchTerm.toLowerCase(), pageRequest);
            
    	}
    	catch (IllegalArgumentException e) {
    		throw new IllegalArgException("The value of direction parameter: '" + direction + "' is invalid, this value must be 'ASC' or 'DESC'.");
		}
    }

	// Create, update and delete methods
	public Printer insert(Printer object) {
		if(object.getPatrimonyId() != null) {
			if(object.getPatrimonyId().equals(""))
				object.setPatrimonyId(null);
			else {
				if(this.alreadyExistsWithPatrimonyId(object))
					throw new ConflictOfObjectsException("This equipment already exists: patrimonyId: '" + object.getPatrimonyId() + "'.");
			}
		}
		object.setId(null);
		object.setCreatedDate(new Date());
		object.setLastModifiedDate(new Date());
		return this.repository.save(object);
	}

	public void delete(Long id) {
		this.findById(id);
		this.repository.deleteById(id);
	}

	public Printer update(Printer object) {
		this.retrievesAndUpdatesDateData(object);
		
		if(object.getPatrimonyId() != null) {
			if(object.getPatrimonyId().equals(""))
				object.setPatrimonyId(null);
			else {
				if(this.isPatrimonyIdChanged(object)){
					if(this.alreadyExistsWithPatrimonyId(object))
						throw new ConflictOfObjectsException("This equipment already exists: patrimonyId: '" + object.getPatrimonyId() + "'.");
				}
			}
		}
		
		return this.repository.saveAndFlush(object);		
	}
	
	// Auxiliary methods
	/**
	 * Convert the PrinterNewDTO object to a Printer object. 
	 * @param objectNewDTO PrinterDTO
	 * @return Printer
	 */
	public Printer fromDTO(PrinterNewDTO objectNewDTO) {		
		Printer object = new Printer(
				null, objectNewDTO.getPatrimonyId(), null, null, null,
				objectNewDTO.getManufacturer(), objectNewDTO.getModel(), objectNewDTO.getDescription(), 
				null, objectNewDTO.getIpAddress(), objectNewDTO.getMacAddress(),
				objectNewDTO.getHostName());
		
		object.setSector(this.sectorService.findById(objectNewDTO.getSectorId()));
		
		if(object.getHostName() == null) 
			object.setHostName(object.generateHostName());
		else {
			if(objectNewDTO.getHostName().isEmpty()) 
				object.setHostName(object.generateHostName());
		}
			
		
		return object;
	}
	
	
	
}
