package com.project.wab.service;

import com.project.wab.domain.ShipmentMethod;
import com.project.wab.dto.ReferenceBookDto;
import com.project.wab.repository.ShipmentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class ShipmentMethodService {
    private final ShipmentMethodRepository shipmentMethodRepository;

    public List<ReferenceBookDto> findAllDto() {
        return shipmentMethodRepository.findAllDto();
    }

    public ShipmentMethod getReferenceById(Long shippingMethodId) {
        return shipmentMethodRepository.getReferenceById(shippingMethodId);
    }
}
