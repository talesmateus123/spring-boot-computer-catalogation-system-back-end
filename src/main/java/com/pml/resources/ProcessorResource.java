/** 
 * This is the class "ProcessorResource". Which will be to represent a REST controller of Processor model.
 * 
 * @author Tales Mateus de Oliveira Ferreira <talesmateus1999@hotmail.com>
 */
package com.pml.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pml.domain.Processor;
import com.pml.dto.ProcessorDTO;
import com.pml.dto.ProcessorNewDTO;
import com.pml.services.ProcessorService;

@RestController
@RequestMapping(value = "/api/processors")
public class ProcessorResource {
	@Autowired	
	private ProcessorService service;
	
	@GetMapping
	public ResponseEntity<List<ProcessorDTO>> findAll() {
		List<Processor> objects = this.service.findAll();
		List<ProcessorDTO> objectsDTO = objects.stream().map(
				obj -> new ProcessorDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objectsDTO);
	}
	
	@GetMapping("/available")
	public ResponseEntity<List<ProcessorDTO>> findAllWithoutComputer() {
		List<Processor> objects = this.service.findAllWithoutComputer();
		List<ProcessorDTO> objectsDTO = objects.stream().map(
				obj -> new ProcessorDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objectsDTO);
	}
	
	@GetMapping("/page")
	public ResponseEntity<Page<ProcessorDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "motherBoardName") String orderBy) {
		
		Page<Processor> objects = this.service.findPage(page, linesPerPage, direction, orderBy);
		Page<ProcessorDTO> objectsDTO = objects.map(obj -> new ProcessorDTO(obj));
		return ResponseEntity.ok().body(objectsDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Processor> findById(@PathVariable Long id) {
		Processor object = this.service.findById(id);
		return ResponseEntity.ok().body(object);
	}
	
	@GetMapping("/search")
    public Page<Processor> search(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "linesPerPage", required = false, defaultValue = "10") int linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction, 
            @RequestParam(value = "orderBy", defaultValue = "manufacturer") String orderBy,
    		@RequestParam("searchTerm") String searchTerm) {
        return service.search(page, linesPerPage, direction, orderBy, searchTerm);
    }

	// @PreAuthorize("hasAnyRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ProcessorNewDTO objectDTO) {
		Processor object = this.service.fromDTO(objectDTO);
		object = this.service.insert(object);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{patrimonyId}").buildAndExpand(object.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// @PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		this.service.delete(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Updates the processor referred by id.
	 * @param objectDTO
	 * @param id
	 * @return ResponseEntity<Void>
	 */
	// @PreAuthorize("hasAnyRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ProcessorNewDTO objectDTO, @PathVariable Long id) {
		Processor object = this.service.fromDTO(objectDTO);
		object.setId(id);
		
		this.service.update(object);
		return ResponseEntity.noContent().build();		
	}
	
	
	
}
