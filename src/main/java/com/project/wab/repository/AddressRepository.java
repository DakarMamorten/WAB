package com.project.wab.repository;

import com.project.wab.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author "Vladyslav Paun"
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
}
