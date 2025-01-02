package com.epm.gestepm.modelapi.personalexpense.dto.updater;

import com.epm.gestepm.modelapi.personalexpense.dto.PaymentTypeEnumDto;
import com.epm.gestepm.modelapi.personalexpense.dto.PriceTypeEnumDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PersonalExpenseUpdateDto {

    @NotNull
    private Integer id;

    private LocalDateTime startDate;

    private String description;

    private PriceTypeEnumDto priceType;

    private Double quantity;

    private Double amount;

    private PaymentTypeEnumDto paymentType;

    private List<Integer> fileIds;

}
