package com.aspire.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class RepaymentRequestDto {

    @Positive(message = "loanId must be positive")
    private Long loanId;
    @Positive(message = "repaymentId must be positive")
    private Long repaymentId;
    private MoneyAmount moneyAmount;

}


