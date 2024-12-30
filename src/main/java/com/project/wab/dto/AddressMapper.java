package com.project.wab.dto;

import com.project.wab.domain.Address;
import org.springframework.stereotype.Component;

/**
 * @author "Vladyslav Paun"
 */
@Component
public class AddressMapper {

    public Address mapToAddress(AddressDTO addressDTO) {
        Address address = new Address();
        address.setFirstName(addressDTO.getFirstName());
        address.setLastName(addressDTO.getLastName());
        address.setStreetName(addressDTO.getStreetName());
        address.setHouse(addressDTO.getHouse());
        address.setFlat(addressDTO.getFlat());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setCity(addressDTO.getCity());
        address.setPhone(addressDTO.getPhone());
        address.setEmail(addressDTO.getEmail());
        return address;
    }
    public AddressDTO mapToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setFirstName(address.getFirstName());
        addressDTO.setLastName(address.getLastName());
        addressDTO.setStreetName(address.getStreetName());
        addressDTO.setHouse(address.getHouse());
        addressDTO.setFlat(address.getFlat());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setCity(address.getCity());
        addressDTO.setPhone(address.getPhone());
        addressDTO.setEmail(address.getEmail());
        return addressDTO;
    }
}

