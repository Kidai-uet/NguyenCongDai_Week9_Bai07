package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Customer đại diện cho một khách hàng trong hệ thống ngân hàng.
 */
public class Customer {
  private long idNumber;
  private String fullName;
  private List<Account> accountList;

  /**
   * Constructor không tham số (phục vụ MyTest).
   */
  public Customer() {
    this(0L, "");
  }

  /**
   * Constructor khởi tạo khách hàng với số CMND và họ tên.
   *
   * @param idNumber số CMND của khách hàng.
   * @param fullName họ tên của khách hàng.
   */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
    this.accountList = new ArrayList<>();
  }

  /**
   * Lấy số CMND của khách hàng.
   *
   * @return số CMND.
   */
  public long getIdNumber() {
    return idNumber;
  }

  /**
   * Cập nhật số CMND của khách hàng.
   *
   * @param idNumber số CMND mới.
   */
  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  /**
   * Lấy họ tên của khách hàng.
   *
   * @return họ tên khách hàng.
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Cập nhật họ tên của khách hàng.
   *
   * @param fullName họ tên mới.
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Lấy danh sách tài khoản của khách hàng.
   *
   * @return danh sách các tài khoản.
   */
  public List<Account> getAccountList() {
    return accountList;
  }

  /**
   * Cập nhật danh sách tài khoản cho khách hàng.
   *
   * @param accountList danh sách tài khoản mới.
   */
  public void setAccountList(List<Account> accountList) {
    if (accountList == null) {
      this.accountList = new ArrayList<>();
    } else {
      this.accountList = accountList;
    }
  }

  /**
   * Thêm một tài khoản mới cho khách hàng.
   *
   * @param account đối tượng tài khoản cần thêm.
   */
  public void addAccount(Account account) {
    if (account == null) {
      return;
    }
    if (!accountList.contains(account)) {
      accountList.add(account);
    }
  }

  /**
   * Xóa một tài khoản khỏi danh sách của khách hàng.
   *
   * @param account đối tượng tài khoản cần xóa.
   */
  public void removeAccount(Account account) {
    if (account == null) {
      return;
    }
    accountList.remove(account);
  }

  /**
   * Trả về thông tin khách hàng dưới dạng văn bản.
   *
   * @return chuỗi chứa thông tin CMND và họ tên khách hàng.
   */
  public String getCustomerInfo() {
    return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
  }
}