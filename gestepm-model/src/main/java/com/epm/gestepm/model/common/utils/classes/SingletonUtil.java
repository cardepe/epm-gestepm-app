package com.epm.gestepm.model.common.utils.classes;

import com.epm.gestepm.model.expense.service.mapper.ExpenseMapper;
import com.epm.gestepm.modelapi.absencetype.dto.AbsenceType;
import com.epm.gestepm.modelapi.absencetype.service.AbsenceTypeService;
import com.epm.gestepm.modelapi.paymenttype.dto.PaymentType;
import com.epm.gestepm.modelapi.paymenttype.service.PaymentTypeService;
import com.epm.gestepm.modelapi.pricetype.dto.PriceType;
import com.epm.gestepm.modelapi.pricetype.service.PriceTypeService;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.role.service.RoleService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Getter
@Named("singletonUtil")
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingletonUtil implements ServletContextListener {

	private final AbsenceTypeService absenceTypeService;

	private final PaymentTypeService paymentTypeService;

	private final PriceTypeService priceTypeService;

	private final RoleService roleService;

	private List<AbsenceType> absenceTypes;

	private List<PaymentType> paymentTypes;
	
	private List<PriceType> priceTypes;

	private String priceTypesJson;
	
	private List<Role> roles;

    public SingletonUtil(AbsenceTypeService absenceTypeService, PaymentTypeService paymentTypeService, PriceTypeService priceTypeService, RoleService roleService) {
        this.absenceTypeService = absenceTypeService;
        this.paymentTypeService = paymentTypeService;
        this.priceTypeService = priceTypeService;
        this.roleService = roleService;
    }

    @Override
	public void contextInitialized(ServletContextEvent sce) {

		this.absenceTypes = absenceTypeService.findAll();
		this.absenceTypes.sort(Comparator.comparing(AbsenceType::getName));

		this.paymentTypes = paymentTypeService.findAll();
		
		this.priceTypes = priceTypeService.findAll();
		this.priceTypesJson = ExpenseMapper.mapAndSerializePriceTypesToJson(this.priceTypes);
		
		this.roles = roleService.getAll();
		Collections.reverse(this.roles);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Do not need it
	}
	
	public AbsenceType getAbsenceTypeById(Long id) {
		return this.absenceTypes.stream()
                .filter(absenceType -> absenceType.getId().equals(id))
                .findAny()
                .orElse(null);
	}
	
	public PaymentType getPaymentTypeById(Long id) {
		return this.paymentTypes.stream()
                .filter(paymentType -> paymentType.getId().equals(id))
                .findAny()
                .orElse(null);
	}
	
	public PriceType getPriceTypeById(Long id) {
		return this.priceTypes.stream()
                .filter(priceType -> priceType.getId().equals(id))
                .findAny()
                .orElse(null);
	}
}
