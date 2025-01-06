package com.epm.gestepm.model.personalexpense.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PersonalExpense implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer personalExpenseSheetId;

    @NotNull
    private LocalDateTime noticeDate;

    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    private String description;

    @NotNull
    private PriceTypeEnum priceType;

    private Double quantity;

    @NotNull
    private Double amount;

    @NotNull
    private PaymentTypeEnum paymentType;

    private List<Integer> fileIds;

}
