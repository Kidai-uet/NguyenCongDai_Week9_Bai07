package main.java;

/**
 * Ngoại lệ chung trong hệ thống ngân hàng.
 */
public class BankException extends Exception {

  /**
   * Khởi tạo ngoại lệ với thông báo lỗi.
   *
   * @param message thông báo lỗi chi tiết.
   */
  public BankException(String message) {
    super(message);
  }
}