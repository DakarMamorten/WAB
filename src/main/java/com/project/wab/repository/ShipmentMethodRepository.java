package com.project.wab.repository;

import com.project.wab.domain.ShipmentMethod;
import com.project.wab.dto.ReferenceBookDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Repository
public interface ShipmentMethodRepository extends JpaRepository<ShipmentMethod, Long> {
    @Query(value = "select new com.project.wab.dto.ReferenceBookDto(sm.id,sm.code,sm.name) from ShipmentMethod sm")
    List<ReferenceBookDto> findAllDto();
}
