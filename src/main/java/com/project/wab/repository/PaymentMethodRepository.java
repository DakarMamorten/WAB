package com.project.wab.repository;

import com.project.wab.domain.PaymentMethod;
import com.project.wab.dto.ReferenceBookDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    @Query(value = "select new com.project.wab.dto.ReferenceBookDto(pm.id,pm.code,pm.name) from PaymentMethod pm")
    List<ReferenceBookDto> findAllDto();
}
