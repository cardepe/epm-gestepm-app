package com.epm.gestepm.model.personalexpense.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.modelapi.personalexpense.dto.PaymentTypeEnumDto;
import com.epm.gestepm.modelapi.personalexpense.dto.PriceTypeEnumDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static com.epm.gestepm.model.personalexpense.dao.constants.PersonalExpenseAttributes.*;

@Data
public class PersonalExpenseCreate implements CollectableAttributes {

    @NotNull
    private Integer personalExpenseSheetId;

    @NotNull
    private LocalDateTime noticeDate;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private String description;

    @NotNull
    private PriceTypeEnumDto priceType;

    private Double quantity;

    @NotNull
    private Double amount;

    @NotNull
    private PaymentTypeEnumDto paymentType;

    private List<Integer> fileIds;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PE_PES_ID, this.personalExpenseSheetId);
        map.putTimestamp(ATTR_PE_NOTICE_DATE, this.noticeDate);
        map.putTimestamp(ATTR_PE_START_DATE, this.startDate);
        map.put(ATTR_PE_DESCRIPTION, this.description);
        map.putEnum(ATTR_PE_PRICE_TYPE, this.priceType);
        map.put(ATTR_PE_QUANTITY, this.quantity);
        map.put(ATTR_PE_AMOUNT, this.amount);
        map.putEnum(ATTR_PE_PAYMENT_TYPE, this.paymentType);

        return map;
    }

}
