package com.epm.gestepm.model.customer.dao;

import com.epm.gestepm.model.customer.dao.entity.Customer;
import com.epm.gestepm.model.customer.dao.entity.creator.CustomerCreate;
import com.epm.gestepm.model.customer.dao.entity.finder.CustomerByIdFinder;
import com.epm.gestepm.model.customer.dao.entity.finder.CustomerByProjectIdFinder;

import javax.validation.Valid;
import java.util.Optional;

public interface CustomerDao {

    Optional<@Valid Customer> find(@Valid CustomerByIdFinder finder);

    Optional<@Valid Customer> find(@Valid CustomerByProjectIdFinder finder);

    @Valid
    Customer create(@Valid CustomerCreate create);

}
