package com.aspire.repository.loan.impl;

import com.aspire.constants.LoanStatus;
import com.aspire.exceptions.LoanNotFoundException;
import com.aspire.model.InMemoryStore;
import com.aspire.repository.loan.LoanRepository;
import com.aspire.model.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class LoanRepositoryImpl implements LoanRepository {

    @Override
    public Loan findByLoanId(Long loanId) {
        if (InMemoryStore.loans.containsKey(loanId)) {
            return InMemoryStore.loans.get(loanId);
        }
        throw new LoanNotFoundException();
    }

    @Override
    public List<Loan> findByCustomerId(Long customerId) {
        return InMemoryStore.loans.values()
                .stream()
                .filter(loan -> loan.getUserId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public Loan createLoan(Loan loan) {
        InMemoryStore.loans.put(loan.getId(), loan);
        return loan;
    }

    @Override
    public Loan updateLoanStatus(Long loanId, LoanStatus loanStatus) {
        if (InMemoryStore.loans.containsKey(loanId)) {
            Loan loan = InMemoryStore.loans.get(loanId);
            loan.setStatus(loanStatus);
            return loan;
        }
        throw new LoanNotFoundException();
    }
}
