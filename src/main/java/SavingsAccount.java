import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp tài khoản tiết kiệm.
 */
public class SavingsAccount extends Account {
  private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

  public static final double MAX_WITHDRAW = 1000.0;
  public static final double MIN_BALANCE = 5000.0;

  /**
   * Khởi tạo tài khoản tiết kiệm.
   *
   * @param accountNumber số tài khoản.
   * @param balance       số dư ban đầu.
   */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction t = new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS,
          amount,
          initialBalance,
          finalBalance
      );
      addTransaction(t);
    } catch (InvalidFundingAmountException e) {
      logger.error("Lỗi nạp tiền: {}", e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAW) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MIN_BALANCE) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();
      Transaction t = new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS,
          amount,
          initialBalance,
          finalBalance
      );
      addTransaction(t);
    } catch (Exception e) {
      logger.warn("Lỗi rút tiền: {}", e.getMessage());
    }
  }
}