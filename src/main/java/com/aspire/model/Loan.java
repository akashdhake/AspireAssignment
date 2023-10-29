package com.aspire.model;


import com.aspire.constants.LoanStatus;
import com.aspire.constants.RepaymentFrequency;
import lombok.Data;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class Loan {

    private long id;
    private MoneyAmount moneyAmount;
    private int terms;
    private RepaymentFrequency frequency;
    private LoanStatus status;
    private Instant requestedDate;
    private Instant completionDate;
    private long userId;

    private static final AtomicLong idCounter = new AtomicLong(1);

    public Loan(MoneyAmount moneyAmount, int terms, RepaymentFrequency frequency, long userId) {
        this.id = generateCustomId();
        this.requestedDate = Instant.now();
        this.moneyAmount = moneyAmount;
        this.terms = terms;
        this.frequency = frequency;
        this.status = LoanStatus.PENDING;
        this.userId = userId;
    }

    private long generateCustomId() {
        return idCounter.getAndIncrement();
    }

}
