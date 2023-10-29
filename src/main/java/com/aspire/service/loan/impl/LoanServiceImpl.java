package com.aspire.service.loan.impl;

import com.aspire.model.Loan;
import com.aspire.model.LoanRequestDto;
import com.aspire.model.User;
import com.aspire.repository.loan.LoanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.aspire.constants.LoanStatus;
import com.aspire.constants.UserRole;
import com.aspire.exceptions.IllegalOperationException;
import com.aspire.service.loan.LoanService;
import com.aspire.service.repayment.RepaymentService;
import com.aspire.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    private final RepaymentService repaymentService;

    private final UserService userService;

    @Override
    public Loan createLoan(String userName, LoanRequestDto loanRequest) {
        log.info("Getting user details from name");
        User user = userService.getUserByName(userName);
        Loan loan = new Loan(loanRequest.getMoneyAmount(), loanRequest.getTerms(), loanRequest.getRepaymentFrequency(), user.getId());
        log.info("Creating loan for userId - " + user.getId() + ", Name - " + user.getName());
        loanRepository.createLoan(loan);
        log.info("Creating Repayments");
        repaymentService.createRepayments(loan);
        log.info("Loan successfully created");
        return loan;
    }

    @Override
    public Loan approveLoan(Long loanId) {
        log.info("Checking if loan exists");
        loanRepository.findByLoanId(loanId);
        Loan loan;
        log.info("Updating loan status to PENDING");
        loan = loanRepository.updateLoanStatus(loanId, LoanStatus.APPROVED);
        return loan;
    }

    @Override
    public Loan getLoanById(String userName, Long loanId) {

        User user = userService.getUserByName(userName);
        Loan loan = loanRepository.findByLoanId(loanId);

        if (loan.getUserId() != user.getId() && UserRole.CUSTOMER.equals(user.getRole())) {
            throw new IllegalOperationException();
        }
        return loan;
    }

    @Override
    public List<Loan> getLoansByUserName(String userName) {
        User user = userService.getUserByName(userName);
        return getLoansByUserId(user.getId());
    }

    @Override
    public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByCustomerId(userId);
    }

    @Override
    public Loan updateLoanStatus(Long loanId, LoanStatus loanStatus) {
        return loanRepository.updateLoanStatus(loanId, loanStatus);
    }
}
