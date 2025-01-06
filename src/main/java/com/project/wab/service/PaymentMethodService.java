package com.project.wab.service;

import com.project.wab.domain.PaymentMethod;
import com.project.wab.dto.ReferenceBookDto;
import com.project.wab.repository.PaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public List<ReferenceBookDto> findAllDto() {
        return paymentMethodRepository.findAllDto();
    }

    public PaymentMethod getReferenceById(Long id) {
        return paymentMethodRepository.getReferenceById(id);
    }
}
