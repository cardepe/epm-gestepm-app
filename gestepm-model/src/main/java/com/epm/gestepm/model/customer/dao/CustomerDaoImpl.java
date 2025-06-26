package com.epm.gestepm.model.customer.dao;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.jdbc.api.datasource.SQLDatasource;
import com.epm.gestepm.lib.jdbc.api.query.SQLQuery;
import com.epm.gestepm.lib.jdbc.api.query.fetch.SQLQueryFetchOne;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.model.customer.dao.entity.Customer;
import com.epm.gestepm.model.customer.dao.entity.creator.CustomerCreate;
import com.epm.gestepm.model.customer.dao.entity.finder.CustomerByIdFinder;
import com.epm.gestepm.model.customer.dao.entity.finder.CustomerByProjectIdFinder;
import com.epm.gestepm.model.customer.dao.mappers.CustomerRowMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DAO;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.model.customer.dao.constants.CustomerQueries.*;

@AllArgsConstructor
@Component("customerDao")
@EnableExecutionLog(layerMarker = DAO)
public class CustomerDaoImpl implements CustomerDao {

    private final SQLDatasource sqlDatasource;

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find customer by ID",
            msgOut = "Querying to find customer by ID OK",
            errorMsg = "Failed query to find customer by ID")
    public Optional<Customer> find(CustomerByIdFinder finder) {

        final SQLQueryFetchOne<Customer> sqlQuery = new SQLQueryFetchOne<Customer>()
                .useRowMapper(new CustomerRowMapper())
                .useQuery(QRY_LIST_OF_CU)
                .useFilter(FILTER_CU_BY_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Querying to find customer by project",
            msgOut = "Querying to find customer by project OK",
            errorMsg = "Failed query to find customer by project")
    public Optional<Customer> find(CustomerByProjectIdFinder finder) {

        final SQLQueryFetchOne<Customer> sqlQuery = new SQLQueryFetchOne<Customer>()
                .useRowMapper(new CustomerRowMapper())
                .useQuery(QRY_LIST_OF_CU)
                .useFilter(FILTER_CU_BY_P_ID)
                .withParams(finder.collectAttributes());

        return this.sqlDatasource.fetch(sqlQuery);
    }

    @Override
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Persisting new customer",
            msgOut = "New customer persisted OK",
            errorMsg = "Failed to persist new customer")
    public Customer create(CustomerCreate create) {

        final AttributeMap params = create.collectAttributes();

        final SQLQuery sqlQuery = new SQLQuery()
                .useQuery(QRY_CREATE_CU)
                .withParams(params);

        this.sqlDatasource.execute(sqlQuery);

        final CustomerByProjectIdFinder finder = new CustomerByProjectIdFinder();
        finder.setProjectId(create.getProjectId());

        return this.find(finder).orElse(null);
    }
}
