package com.aspire.controllers;


import com.aspire.service.loan.LoanService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.aspire.model.Loan;
import com.aspire.model.LoanRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
@Slf4j
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(@Valid @RequestBody LoanRequestDto loanRequest, Authentication authentication) {
        Loan loan = loanService.createLoan(authentication.getName(), loanRequest);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    @PostMapping("/admin/{loanId}/approve")
    public ResponseEntity<Loan> approveLoan(@PathVariable @Positive Long loanId) {
        Loan loan = loanService.approveLoan(loanId);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoan(@PathVariable @Positive Long loanId, Authentication authentication) {
        Loan loan = loanService.getLoanById(authentication.getName(), loanId);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Loan>> getLoansByUserId(Authentication authentication) {
        List<Loan> loans = loanService.getLoansByUserName(authentication.getName());
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }
}
