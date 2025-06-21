package com.epm.gestepm.rest.customer;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.customer.dto.CustomerDto;
import com.epm.gestepm.modelapi.customer.dto.creator.CustomerCreateDto;
import com.epm.gestepm.modelapi.customer.dto.finder.CustomerByProjectIdFinderDto;
import com.epm.gestepm.modelapi.customer.service.CustomerService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.customer.mappers.MapCUToCustomerByProjectIdFinderDto;
import com.epm.gestepm.rest.customer.mappers.MapCUToCustomerCreateDto;
import com.epm.gestepm.rest.customer.mappers.MapCUToCustomerResponse;
import com.epm.gestepm.rest.customer.operations.FindCustomerV1Operation;
import com.epm.gestepm.rest.customer.request.CustomerFindRestRequest;
import com.epm.gestepm.rest.customer.response.ResponsesForCustomer;
import com.epm.gestepm.restapi.openapi.api.ProjectCustomerV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.customer.security.CustomerPermission.PRMT_EDIT_CU;
import static com.epm.gestepm.modelapi.customer.security.CustomerPermission.PRMT_READ_CU;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class CustomerController extends BaseController implements ProjectCustomerV1Api, ResponsesForCustomer {

    private final CustomerService customerService;

    public CustomerController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                             final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                             final CustomerService customerService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.customerService = customerService;
    }
    
    @Override
    @RequirePermits(value = PRMT_READ_CU, action = "Find customer")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<FindCustomerByProjectIdV1200Response> findCustomerByProjectIdV1(final Integer projectId) {

        final CustomerFindRestRequest req = new CustomerFindRestRequest(projectId);

        final CustomerByProjectIdFinderDto finderDto = getMapper(MapCUToCustomerByProjectIdFinderDto.class).from(req);
        final CustomerDto dto = this.customerService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindCustomerV1Operation());
        final Customer data = getMapper(MapCUToCustomerResponse.class).from(dto);

        return toResCustomerResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_CU, action = "Create customer")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<FindCustomerByProjectIdV1200Response> createProjectCustomerV1(final Integer projectId, final CreateProjectCustomerV1Request reqCreateCustomer) {

        final CustomerCreateDto createDto = getMapper(MapCUToCustomerCreateDto.class).from(reqCreateCustomer);
        createDto.setProjectId(projectId);

        final CustomerDto customerDto = this.customerService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Customer data = getMapper(MapCUToCustomerResponse.class).from(customerDto);

        final FindCustomerByProjectIdV1200Response response = new FindCustomerByProjectIdV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}
