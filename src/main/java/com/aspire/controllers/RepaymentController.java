package com.aspire.controllers;

import com.aspire.service.repayment.RepaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.aspire.model.Repayment;
import com.aspire.model.RepaymentRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/repayments")
public class RepaymentController {

    private final RepaymentService repaymentService;

    @PostMapping
    public ResponseEntity<Repayment> addRepayment(@RequestBody @Valid RepaymentRequestDto repaymentRequest, Authentication authentication) {
        Repayment repayment = repaymentService.makePayment(authentication.getName(), repaymentRequest);
        return new ResponseEntity<>(repayment, HttpStatus.CREATED);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<List<Repayment>> getRepaymentsForLoan(@PathVariable @Positive Long loanId, Authentication authentication) {
        List<Repayment> repayments = repaymentService.getRepaymentsForLoan(authentication.getName(), loanId);
        return new ResponseEntity<>(repayments, HttpStatus.OK);
    }
}
