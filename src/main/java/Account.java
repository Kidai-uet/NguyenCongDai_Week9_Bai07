package main.java;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp trừu tượng đại diện cho một tài khoản ngân hàng.
 * Đã sửa lỗi: Viết lại Javadoc đầy đủ và đúng format.
 */
public abstract class Account {
  // Sửa lỗi: Đặt tên hằng số theo chuẩn UPPER_SNAKE_CASE
  public static final String CHECKING_TYPE = "CHECKING";
  public static final String SAVINGS_TYPE = "SAVINGS";

  // Sử dụng Logger để thay thế cho System.out
  private static final Logger logger = LoggerFactory.getLogger(Account.class);

  // Sửa lỗi: Đổi tên biến _accNum và B thành tên rõ nghĩa, camelCase
  private long accountNumber;
  private double balance;
  protected List<Transaction> transactionList;

  /**
   * Khởi tạo tài khoản với số tài khoản và số dư.
   * Đã sửa lỗi: Thụt lề 2 spaces đồng nhất.
   *
   * @param accountNumber số tài khoản.
   * @param balance       số dư ban đầu.
   */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactionList = new ArrayList<>();
  }

  /**
   * Lấy số tài khoản.
   *
   * @return số tài khoản hiện tại.
   */
  public long getAccountNumber() {
    return accountNumber;
  }

  /**
   * Cập nhật số tài khoản.
   *
   * @param accountNumber số tài khoản mới.
   */
  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * Lấy số dư tài khoản.
   *
   * @return số dư hiện tại.
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Cập nhật số dư tài khoản.
   *
   * @param balance số dư mới.
   */
  protected void setBalance(double balance) {
    this.balance = balance;
  }

  /**
   * Lấy danh sách giao dịch của tài khoản.
   *
   * @return danh sách các giao dịch.
   */
  public List<Transaction> getTransactionList() {
    return transactionList;
  }

  /**
   * Cập nhật danh sách giao dịch.
   *
   * @param transactionList danh sách mới.
   */
  public void setTransactionList(List<Transaction> transactionList) {
    if (transactionList == null) {
      this.transactionList = new ArrayList<>();
    } else {
      this.transactionList = transactionList;
    }
  }

  /**
   * Phương thức nạp tiền (phải được triển khai ở lớp con).
   *
   * @param amount số tiền nạp.
   */
  public abstract void deposit(double amount);

  /**
   * Phương thức rút tiền (phải được triển khai ở lớp con).
   *
   * @param amount số tiền rút.
   * @throws Exception nếu có lỗi xảy ra.
   */
  public abstract void withdraw(double amount) throws Exception;

  /**
   * Thực hiện logic trừu tượng cho việc nạp tiền.
   *
   * @param amount số tiền cần nạp.
   * @throws InvalidFundingAmountException nếu số tiền nạp <= 0.
   */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance = balance + amount;
  }

  /**
   * Thực hiện logic trừu tượng cho việc rút tiền.
   *
   * @param amount số tiền cần rút.
   * @throws Exception nếu số tiền rút <= 0 hoặc vượt quá số dư.
   */
  protected void doWithdrawing(double amount) throws Exception {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance = balance - amount;
  }

  /**
   * Thêm một giao dịch mới vào danh sách.
   *
   * @param transaction đối tượng giao dịch cần thêm.
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactionList.add(transaction);
    }
  }

  /**
   * Lấy lịch sử giao dịch.
   * Đã sửa lỗi: Dùng StringBuilder để tối ưu và ngắt dòng code quá dài.
   *
   * @return chuỗi lịch sử giao dịch.
   */
  public String getTransactionHistory() {
    StringBuilder sb = new StringBuilder();
    sb.append("Lịch sử giao dịch của tài khoản ");
    sb.append(accountNumber);
    sb.append(":\n");

    for (int i = 0; i < transactionList.size(); i++) {
      sb.append(transactionList.get(i).getTransactionSummary());
      if (i < transactionList.size() - 1) {
        sb.append("\n");
      }
    }

    logger.debug("Đã truy xuất lịch sử cho tài khoản: {}", accountNumber);
    return sb.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    Account other = (Account) obj;
    return this.accountNumber == other.accountNumber;
  }

  @Override
  public int hashCode() {
    int result = (int) (accountNumber ^ (accountNumber >>> 32));
    return result;
  }
}