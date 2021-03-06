/** 
 * This is the class "ComputerRepository", extended by the interface "JpaRepository". Which will be to represent a computer repository.
 * 
 * @author Tales Mateus de Oliveira Ferreira <talesmateus1999@hotmail.com>
 */
package com.pml.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pml.domain.SupportInfo;
@Repository
public interface SupportInfoRepository extends JpaRepository<SupportInfo, Long>{

	
	
}
