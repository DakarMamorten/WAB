package com.project.wab.repository;

import com.project.wab.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author "Vladyslav Paun"
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
