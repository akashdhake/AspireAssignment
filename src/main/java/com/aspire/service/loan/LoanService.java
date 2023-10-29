package com.aspire.service.loan;

import com.aspire.constants.LoanStatus;
import com.aspire.model.Loan;
import com.aspire.model.LoanRequestDto;

import java.util.List;

public interface LoanService {

    Loan createLoan(String userName, LoanRequestDto loanRequest);

    Loan approveLoan(Long loanId);

    Loan getLoanById(String userName, Long loanId);

    List<Loan> getLoansByUserName(String userName);

    List<Loan> getLoansByUserId(Long userId);

    Loan updateLoanStatus(Long loanId, LoanStatus loanStatus);
}
