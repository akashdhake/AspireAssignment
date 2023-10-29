package com.aspire.model;

import com.aspire.constants.RepaymentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

@Data
@NoArgsConstructor
public class Repayment {

    private long id;
    private MoneyAmount moneyAmount;
    private Instant dueDate;
    private Instant payDate;
    private RepaymentStatus status;
    private long loanId;

    private static final AtomicLong idCounter = new AtomicLong(1);

    public Repayment(Long loanId, MoneyAmount moneyAmount, Instant dueDate) {
        this.id = generateCustomId();
        this.moneyAmount = moneyAmount;
        this.dueDate = dueDate;
        this.status = RepaymentStatus.PENDING;
        this.loanId = loanId;
    }

    private long generateCustomId() {
        return idCounter.getAndIncrement();
    }
}
