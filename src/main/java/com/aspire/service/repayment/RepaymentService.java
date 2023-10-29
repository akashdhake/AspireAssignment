package com.aspire.service.repayment;

import com.aspire.model.Loan;
import com.aspire.model.Repayment;
import com.aspire.model.RepaymentRequestDto;

import java.util.List;

public interface RepaymentService {

    List<Repayment> createRepayments(Loan loan);

    List<Repayment> getRepaymentsForLoan(String userName, Long loanId);

    Repayment makePayment(String userName, RepaymentRequestDto repaymentRequest);

}
