package com.aspire.model;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Getter
public class InMemoryStore {

    public static final Map<Long, User> users = new HashMap<>();
    public static final Map<Long, Loan> loans = new HashMap<>();
    public static final Map<Long, List<Repayment>> repaymentsByLoan = new HashMap<>();

}
