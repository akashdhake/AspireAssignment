package com.aspire.model;


import com.aspire.constants.RepaymentFrequency;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@Builder
public class LoanRequestDto {
    private MoneyAmount moneyAmount;
    @Positive(message = "terms must be positive")
    private int terms;
    private RepaymentFrequency repaymentFrequency;
}
