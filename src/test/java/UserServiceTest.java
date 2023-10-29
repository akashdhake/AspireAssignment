import com.aspire.constants.*;
import com.aspire.model.*;
import com.aspire.repository.loan.LoanRepository;
import com.aspire.repository.loan.impl.LoanRepositoryImpl;
import com.aspire.repository.repayment.RepaymentRepository;
import com.aspire.repository.repayment.impl.RepaymentRepositoryImpl;
import com.aspire.repository.user.UserRepository;
import com.aspire.repository.user.impl.UserRepositoryImpl;
import com.aspire.service.loan.LoanService;
import com.aspire.service.loan.impl.LoanServiceImpl;
import com.aspire.service.repayment.RepaymentService;
import com.aspire.service.repayment.impl.RepaymentServiceImpl;
import com.aspire.service.user.UserService;
import com.aspire.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceTest {

    private UserService userService;
    private LoanService loanService;
    private RepaymentService repaymentService;


    private UserRepository userRepository;
    private LoanRepository loanRepository;
    private RepaymentRepository repaymentRepository;

    @BeforeEach
    public void setup() {
        userRepository = new UserRepositoryImpl();
        loanRepository = new LoanRepositoryImpl();
        repaymentRepository = new RepaymentRepositoryImpl();
        userService = new UserServiceImpl(userRepository);
        repaymentService = new RepaymentServiceImpl(repaymentRepository, loanRepository, userService);
        loanService = new LoanServiceImpl(loanRepository, repaymentService, userService);

        User user = new User("akash", UserRole.CUSTOMER);
        userService.createUser(user);

        User userAdmin = new User("admin", UserRole.ADMIN);
        userService.createUser(userAdmin);
    }

    @Test
    public void testCreateUser() {
        User userByName = userService.getUserByName("akash");
        assertEquals("akash", userByName.getName());
        assertEquals(UserRole.CUSTOMER, userByName.getRole());
        User userAdminByName = userService.getUserByName("admin");
        assertEquals("admin", userAdminByName.getName());
        assertEquals(UserRole.ADMIN, userAdminByName.getRole());
    }

    @Test
    public void testCreateLoan() {

        User user = userService.getUserByName("akash");

        LoanRequestDto loanRequest = LoanRequestDto.builder().terms(3).repaymentFrequency(RepaymentFrequency.WEEKLY)
                .moneyAmount(MoneyAmount.builder().amount(10000).currency(Currency.USD).build())
                .build();

        Loan loan = loanService.createLoan("akash", loanRequest);

        assertTrue(loan.getId() > 0);
        assertEquals(10000, loan.getMoneyAmount().getAmount());
        assertEquals(Currency.USD, loan.getMoneyAmount().getCurrency());
        assertEquals(3, loan.getTerms());
        assertEquals(LoanStatus.PENDING, loan.getStatus());
        assertEquals(RepaymentFrequency.WEEKLY, loan.getFrequency());
        assertEquals(user.getId(), loan.getUserId());

        List<Repayment> repaymentsForLoan = repaymentService.getRepaymentsForLoan("akash", loan.getId());
        assertEquals(3, repaymentsForLoan.size());
        assertEquals(loan.getId(), repaymentsForLoan.get(0).getLoanId());
        assertEquals(RepaymentStatus.PENDING, repaymentsForLoan.get(0).getStatus());
        assertEquals(loan.getRequestedDate().plusSeconds(7 * 24 * 60 * 60).getEpochSecond(), repaymentsForLoan.get(0).getDueDate().getEpochSecond(), 0.1);
    }

    @Test
    public void testMakeRepayment() {

        LoanRequestDto loanRequest = LoanRequestDto.builder().terms(3).repaymentFrequency(RepaymentFrequency.WEEKLY)
                .moneyAmount(MoneyAmount.builder().amount(10000).currency(Currency.USD).build())
                .build();

        Loan loan = loanService.createLoan("akash", loanRequest);

        List<Repayment> repaymentsForLoan = repaymentService.getRepaymentsForLoan("akash", loan.getId());

        RepaymentRequestDto repaymentRequestDto = RepaymentRequestDto.builder()
                .loanId(loan.getId())
                .moneyAmount(MoneyAmount.builder().amount(3334).currency(Currency.USD).build())
                .repaymentId(repaymentsForLoan.get(0).getId())
                .build();


        Repayment repayment = repaymentService.makePayment("akash", repaymentRequestDto);
        assertNotNull(repayment);

        assertEquals(loan.getId(), repayment.getLoanId());
        assertEquals(RepaymentStatus.PAID, repayment.getStatus());
        assertEquals(3334, repayment.getMoneyAmount().getAmount());
        assertEquals(Instant.now().getEpochSecond(), repayment.getPayDate().getEpochSecond(), 0.1);
    }
}
