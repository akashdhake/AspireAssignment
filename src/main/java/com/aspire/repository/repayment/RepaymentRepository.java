package com.aspire.repository.repayment;

import com.aspire.model.MoneyAmount;
import com.aspire.model.Repayment;

import java.util.List;

public interface RepaymentRepository {

    List<Repayment> createRepayments(Long loanId, List<Repayment> repayments);

    List<Repayment> findRepaymentsByLoanId(Long loanId);

    Repayment getRepayment(Long repaymentId);

    Repayment performRepayment(Long loanId, Long repaymentId, MoneyAmount moneyAmount);
}
