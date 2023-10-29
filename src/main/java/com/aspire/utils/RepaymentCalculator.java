package com.aspire.utils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.aspire.model.MoneyAmount;
import com.aspire.model.Repayment;
import com.aspire.model.Loan;

public class RepaymentCalculator {

    public static List<Repayment> calculateNextNRepayments(Loan loan, int n) {
        List<Repayment> repayments = new ArrayList<>();
        Instant nextRepaymentDate = Instant.now();

        for (int i = 0; i < n; i++) {
            switch (loan.getFrequency()) {
                case WEEKLY:
                    nextRepaymentDate = nextRepaymentDate.plusSeconds(7 * 24 * 60 * 60);
                    break;
                case MONTHLY:
                    nextRepaymentDate = nextRepaymentDate.plusSeconds(30 * 24 * 60 * 60);
                    break;
                case YEARLY:
                    nextRepaymentDate = nextRepaymentDate.plusSeconds(365 * 24 * 60 * 60);
                    break;
            }

            Repayment repayment = new Repayment(loan.getId(), calculateRepaymentAmount(loan.getMoneyAmount(), loan.getTerms(), n), nextRepaymentDate);
            repayments.add(repayment);
        }

        return repayments;
    }

    private static MoneyAmount calculateRepaymentAmount(MoneyAmount loanAmount, int loanTerm, int n) {
        return new MoneyAmount(loanAmount.getAmount() / n, loanAmount.getCurrency());
    }
}
