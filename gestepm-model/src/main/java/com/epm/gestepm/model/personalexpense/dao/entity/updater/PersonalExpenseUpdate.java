package com.epm.gestepm.model.personalexpense.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.personalexpense.dao.entity.creator.PersonalExpenseFileCreate;
import com.epm.gestepm.modelapi.personalexpense.dto.PaymentTypeEnumDto;
import com.epm.gestepm.modelapi.personalexpense.dto.PriceTypeEnumDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseAttributes.*;

@Data
public class PersonalExpenseUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String description;

    private PriceTypeEnumDto priceType;

    private Double quantity;

    private Double amount;

    private PaymentTypeEnumDto paymentType;

    private List<PersonalExpenseFileCreate> files;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PE_ID, this.id);
        map.putTimestamp(ATTR_PE_START_DATE, this.startDate);
        map.putTimestamp(ATTR_PE_END_DATE, this.endDate);
        map.put(ATTR_PE_DESCRIPTION, this.description);
        map.putEnum(ATTR_PE_PRICE_TYPE, this.priceType);
        map.put(ATTR_PE_QUANTITY, this.quantity);
        map.put(ATTR_PE_AMOUNT, this.amount);
        map.putEnum(ATTR_PE_PAYMENT_TYPE, this.paymentType);

        return map;
    }

}
