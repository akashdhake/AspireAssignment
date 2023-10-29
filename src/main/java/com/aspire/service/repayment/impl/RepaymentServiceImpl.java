package com.aspire.service.repayment.impl;

import com.aspire.exceptions.LoanAlreadyPAIDException;
import com.aspire.exceptions.RepaymentAlreadyDoneException;
import com.aspire.exceptions.RepaymentAmountLesserException;
import com.aspire.repository.loan.LoanRepository;
import com.aspire.repository.repayment.RepaymentRepository;
import com.aspire.service.user.UserService;
import com.aspire.utils.RepaymentCalculator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.aspire.constants.LoanStatus;
import com.aspire.constants.RepaymentStatus;
import com.aspire.constants.UserRole;
import com.aspire.exceptions.IllegalOperationException;
import com.aspire.model.Loan;
import com.aspire.model.Repayment;
import com.aspire.model.RepaymentRequestDto;
import com.aspire.model.User;
import com.aspire.service.repayment.RepaymentService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RepaymentServiceImpl implements RepaymentService {

    private final RepaymentRepository repaymentRepository;

    private final LoanRepository loanRepository;

    private final UserService userService;

    @Override
    public List<Repayment> createRepayments(Loan loan) {
        log.info("Calculating next n repayments");
        List<Repayment> repayments = RepaymentCalculator.calculateNextNRepayments(loan, loan.getTerms());
        return repaymentRepository.createRepayments(loan.getId(), repayments);
    }

    @Override
    public List<Repayment> getRepaymentsForLoan(String userName, Long loanId) {
        User user = userService.getUserByName(userName);
        Loan loan = loanRepository.findByLoanId(loanId);
        if (loan.getUserId() != user.getId() && UserRole.CUSTOMER.equals(user.getRole())) {
            throw new IllegalOperationException();
        }
        return repaymentRepository.findRepaymentsByLoanId(loanId);
    }

    @Override
    public Repayment makePayment(String userName, RepaymentRequestDto repaymentRequest) {

        User user = userService.getUserByName(userName);
        Loan loan = loanRepository.findByLoanId(repaymentRequest.getLoanId());
        if (loan.getUserId() != user.getId() && UserRole.CUSTOMER.equals(user.getRole())) {
            throw new IllegalOperationException();
        }

        // Check if loan is not already PAID
        if (LoanStatus.REPAID.equals(loan.getStatus())) {
            throw new LoanAlreadyPAIDException();
        }

        // Check if repaymentId exists
        Repayment repayment = repaymentRepository.getRepayment(repaymentRequest.getRepaymentId());

        // Check if repayment is not already paid
        if (RepaymentStatus.PAID.equals(repayment.getStatus())) {
            throw new RepaymentAlreadyDoneException();
        }

        // Check if amount greater than repayment amount
        if (repayment.getMoneyAmount().getAmount() > repaymentRequest.getMoneyAmount().getAmount()) {
            throw new RepaymentAmountLesserException();
        }

        repayment.getMoneyAmount().setAmount(repaymentRequest.getMoneyAmount().getAmount());
        repayment.setPayDate(Instant.now());
        repayment.setStatus(RepaymentStatus.PAID);

        // If al repayments are done mark Loan status as REPAID
        List<Repayment> repayments = repaymentRepository.findRepaymentsByLoanId(loan.getId());
        long count = repayments.stream().filter(emi -> emi.getStatus() == RepaymentStatus.PAID).count();
        if (count == loan.getTerms()) {
            loanRepository.updateLoanStatus(loan.getId(), LoanStatus.REPAID);
        }
        return repayment;
    }
}
