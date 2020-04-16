/** 
 * This is the class "ComputerResource". Which will be to represent a REST controller of Computer model.
 * 
 * @author Tales Mateus de Oliveira Ferreira <talesmateus1999@hotmail.com>
 */
package com.pml.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pml.domain.Computer;
import com.pml.services.ComputerService;

@RestController
@RequestMapping(value = "/api/computers")
public class ComputerResource {
	@Autowired
	private ComputerService service;
	
	@GetMapping
	public List<Computer> list() {
		return this.service.list();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Computer> search(@PathVariable String id) {
		Computer object = this.service.search(id);
		if(object == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(object);
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody Computer object) {
		object = this.service.insert(object);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(object.getPatrimonyId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Boolean> delete(@PathVariable String id) {
		Computer existingObject = this.service.search(id);
		if(existingObject == null)
			return ResponseEntity.notFound().build();
		
		this.service.delete(existingObject.getPatrimonyId());
		return ResponseEntity.ok(true);
	}

	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<Computer> update(@Valid @RequestBody Computer object) {
		Computer existingObject = this.service.search(object.getPatrimonyId());
		if(existingObject == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(this.service.update(object));
		
	}
	
}
