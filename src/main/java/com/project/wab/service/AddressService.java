package com.project.wab.service;

import com.project.wab.domain.Address;
import com.project.wab.dto.AddressDTO;
import com.project.wab.exception.AddressNotFoundException;
import com.project.wab.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author "Vladyslav Paun"
 */
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final PaymentMethodService paymentMethodService;
    private final ShipmentMethodService shipmentMethodService;


    public Address findById(Long addressId) {
        return addressRepository.findById(addressId).orElseThrow(AddressNotFoundException::new);
    }

    public Address save(AddressDTO dto) {
        Address address = new Address();
        address.setFirstName(dto.getFirstName());
        address.setLastName(dto.getLastName());
        address.setEmail(dto.getEmail());
        address.setAddress1(dto.getAddress1());
        address.setAddress2(dto.getAddress2());
        address.setPhone(dto.getPhone());
        address.setPostalCode(dto.getPostalCode());
        address.setCity(dto.getCity());
        var paymentMethod = paymentMethodService.getReferenceById(dto.getPaymentMethodId());
        address.setPaymentMethod(paymentMethod);
        var shipmentMethod = shipmentMethodService.getReferenceById(dto.getShippingMethodId());
        address.setShipmentMethod(shipmentMethod);
        return addressRepository.save(address);
    }

    @Transactional
    public AddressDTO convertToDTO(Address address) {
        var addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setFirstName(address.getFirstName());
        addressDTO.setLastName(address.getLastName());
        addressDTO.setEmail(address.getEmail());
        addressDTO.setAddress1(address.getAddress1());
        addressDTO.setAddress2(address.getAddress2());
        addressDTO.setPhone(address.getPhone());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setCity(address.getCity());
        addressDTO.setPaymentMethod(address.getPaymentMethod().getName());
        addressDTO.setShippingMethod(address.getShipmentMethod().getName());
        return addressDTO;
    }
}
