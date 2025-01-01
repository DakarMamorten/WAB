package com.project.wab.mapper;

import com.project.wab.domain.Address;
import com.project.wab.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author "Vladyslav Paun"
 */
@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address addressDtoToAddress(AddressDTO source);

    AddressDTO AddressToAddressDto(Address destination);

    void updateAddressFromDto(@MappingTarget Address target, AddressDTO source);
}
