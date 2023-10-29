package com.aspire.model;

import com.aspire.constants.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@Builder
public class MoneyAmount {

    @Positive(message = "amount must be positive")
    private double amount;
    private Currency currency;
}
