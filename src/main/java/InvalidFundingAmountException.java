package main.java;

import java.util.Locale;

/**
 * Ngoại lệ khi số tiền giao dịch không hợp lệ.
 */
public class InvalidFundingAmountException extends BankException {

  /**
   * Khởi tạo ngoại lệ với số tiền bị lỗi.
   *
   * @param amount số tiền.
   */
  public InvalidFundingAmountException(double amount) {
    super("Số tiền không hợp lệ: $"
        + String.format(Locale.US, "%.2f", amount));
  }
}