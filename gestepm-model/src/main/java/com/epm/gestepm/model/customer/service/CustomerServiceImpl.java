package com.epm.gestepm.model.customer.service;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.model.customer.dao.CustomerDao;
import com.epm.gestepm.model.customer.dao.entity.Customer;
import com.epm.gestepm.model.customer.dao.entity.creator.CustomerCreate;
import com.epm.gestepm.model.customer.dao.entity.finder.CustomerByProjectIdFinder;
import com.epm.gestepm.model.customer.service.mapper.*;
import com.epm.gestepm.modelapi.customer.dto.CustomerDto;
import com.epm.gestepm.modelapi.customer.dto.creator.CustomerCreateDto;
import com.epm.gestepm.modelapi.customer.dto.finder.CustomerByProjectIdFinderDto;
import com.epm.gestepm.modelapi.customer.exception.CustomerNotFoundException;
import com.epm.gestepm.modelapi.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.customer.security.CustomerPermission.PRMT_EDIT_CU;
import static com.epm.gestepm.modelapi.customer.security.CustomerPermission.PRMT_READ_CU;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    
    @Override
    @RequirePermits(value = PRMT_READ_CU, action = "Find customer by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding customer by ID, result can be empty",
            msgOut = "Found customer by ID",
            errorMsg = "Failed to find customer by ID")
    public Optional<CustomerDto> find(final CustomerByProjectIdFinderDto finderDto) {
        final CustomerByProjectIdFinder finder = getMapper(MapCUToCustomerByProjectIdFinder.class).from(finderDto);

        final Optional<Customer> found = this.customerDao.find(finder);

        return found.map(getMapper(MapCUToCustomerDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_CU, action = "Find customer by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding customer by ID, result is expected or will fail",
            msgOut = "Found customer by ID",
            errorMsg = "Customer by ID not found")
    public CustomerDto findOrNotFound(final CustomerByProjectIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new CustomerNotFoundException(finderDto.getProjectId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_CU, action = "Create new customer")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new customer",
            msgOut = "New customer created OK",
            errorMsg = "Failed to create new customer")
    public CustomerDto create(CustomerCreateDto createDto) {
        final CustomerCreate create = getMapper(MapCUToCustomerCreate.class).from(createDto);

        final Customer result = this.customerDao.create(create);

        return getMapper(MapCUToCustomerDto.class).from(result);
    }
}
