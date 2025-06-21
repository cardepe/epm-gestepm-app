package com.epm.gestepm.model.customer.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.customer.dao.entity.Customer;
import com.epm.gestepm.modelapi.customer.dto.CustomerDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapCUToCustomerDto {

    CustomerDto from(Customer entity);

    List<CustomerDto> from(List<Customer> list);

    default Page<CustomerDto> from(Page<Customer> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
