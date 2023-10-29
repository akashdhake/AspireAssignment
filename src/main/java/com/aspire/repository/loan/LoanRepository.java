package com.aspire.repository.loan;

import com.aspire.constants.LoanStatus;
import com.aspire.model.Loan;

import java.util.List;

public interface LoanRepository {

    Loan findByLoanId(Long loanId);

    List<Loan> findByCustomerId(Long customerId);

    Loan createLoan(Loan loan);

    Loan updateLoanStatus(Long loanId, LoanStatus loanStatus);
}
