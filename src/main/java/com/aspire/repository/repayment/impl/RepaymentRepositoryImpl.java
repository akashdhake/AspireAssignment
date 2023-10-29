package com.aspire.repository.repayment.impl;

import com.aspire.repository.repayment.RepaymentRepository;
import com.aspire.constants.RepaymentStatus;
import com.aspire.exceptions.RepaymentNotFoundException;
import com.aspire.exceptions.RepaymentsAlreadyExistException;
import com.aspire.model.InMemoryStore;
import com.aspire.model.MoneyAmount;
import com.aspire.model.Repayment;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class RepaymentRepositoryImpl implements RepaymentRepository {

    @Override
    public List<Repayment> createRepayments(Long loanId, List<Repayment> repayments) {

        if (!InMemoryStore.repaymentsByLoan.containsKey(loanId)) {
            InMemoryStore.repaymentsByLoan.put(loanId, repayments);
            return InMemoryStore.repaymentsByLoan.get(loanId);
        }
        throw new RepaymentsAlreadyExistException();
    }

    @Override
    public List<Repayment> findRepaymentsByLoanId(Long loanId) {
        if (InMemoryStore.repaymentsByLoan.containsKey(loanId)) {
            return InMemoryStore.repaymentsByLoan.get(loanId);
        }
        throw new RepaymentNotFoundException();
    }

    @Override
    public Repayment getRepayment(Long repaymentId) {

        Optional<Repayment> foundRepayment = InMemoryStore.repaymentsByLoan.values()
                .stream()
                .flatMap(List::stream)
                .filter(repayment -> repayment.getId() == repaymentId)
                .findFirst();

        if (foundRepayment.isPresent()) {
            return foundRepayment.get();
        }
        throw new RepaymentNotFoundException();
    }

    @Override
    public Repayment performRepayment(Long loanId, Long repaymentId, MoneyAmount moneyAmount) {

        if (!InMemoryStore.repaymentsByLoan.containsKey(loanId)) {
            throw new RepaymentNotFoundException();
        }

        List<Repayment> repayments = InMemoryStore.repaymentsByLoan.get(loanId);

        Optional<Repayment> repaymentOptional = repayments.stream().filter(repayment -> repayment.getId() == repaymentId).findFirst();

        if (repaymentOptional.isEmpty()) {
            throw new RepaymentNotFoundException();
        }

        Repayment repayment = repaymentOptional.get();
        repayment.setStatus(RepaymentStatus.PAID);
        repayment.setPayDate(Instant.now());
        repayment.setMoneyAmount(moneyAmount);

        return repayment;
    }
}
