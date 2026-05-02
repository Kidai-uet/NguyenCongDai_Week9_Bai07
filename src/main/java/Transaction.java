package main.java;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho một giao dịch ngân hàng.
 * Đã sửa lỗi: Thêm Javadoc đầy đủ cho class.
 */
public class Transaction {
  private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới.
   *
   * @param type           Loại giao dịch
   * @param amount         Số tiền giao dịch
   * @param initialBalance Số dư trước giao dịch
   * @param finalBalance   Số dư sau giao dịch
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  public double getFinalBalance() {
    return finalBalance;
  }

  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Chuyển đổi mã loại giao dịch thành chuỗi hiển thị.
   *
   * @param typeCode mã loại giao dịch cần chuyển đổi.
   * @return chuỗi mô tả loại giao dịch.
   */
  public static String getTypeString(int typeCode) {
    switch (typeCode) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Lấy tóm tắt thông tin giao dịch.
   *
   * @return chuỗi tóm tắt thông tin giao dịch.
   */
  public String getTransactionSummary() {
    logger.debug("Bắt đầu xử lý tóm tắt cho giao dịch loại: {}", this.type);

    String typeStr = getTypeString(type);
    String initialStr = String.format(Locale.US, "%.2f", initialBalance);
    String amountStr = String.format(Locale.US, "%.2f", amount);
    String finalStr = String.format(Locale.US, "%.2f", finalBalance);

    // Thụt lề sâu hơn 4 khoảng trắng so với chữ return (Indentation)
    return "- Kiểu giao dịch: " + typeStr
      + ". Số dư ban đầu: $" + initialStr
      + ". Số tiền: $" + amountStr
      + ". Số dư cuối: $" + finalStr + ".";
  }
}