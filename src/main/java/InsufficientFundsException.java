package main.java;

import java.util.Locale;

/**
 * Ngoại lệ khi số dư tài khoản không đủ.
 */
public class InsufficientFundsException extends BankException {

  /**
   * Khởi tạo ngoại lệ với số tiền yêu cầu.
   *
   * @param amount số tiền.
   */
  public InsufficientFundsException(double amount) {
    super("Số dư tài khoản không đủ $"
        + String.format(Locale.US, "%.2f", amount)
        + " để thực hiện giao dịch");
  }
}