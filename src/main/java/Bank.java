import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp Bank quản lý danh sách khách hàng và các tài khoản liên quan.
 * Đã sửa lỗi: Tối ưu import và bổ sung Javadoc.
 */
public class Bank {
  private static final Logger logger = LoggerFactory.getLogger(Bank.class);

  // Hằng số cho biểu thức chính quy để tránh Magic String
  private static final String ID_NUMBER_REGEX = "\\d{9}";

  // Danh sách khách hàng
  private List<Customer> customerList;

  /**
   * Khởi tạo ngân hàng với danh sách khách hàng trống.
   */
  public Bank() {
    this.customerList = new ArrayList<>();
  }

  /**
   * Lấy danh sách khách hàng.
   *
   * @return danh sách khách hàng hiện tại.
   */
  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Cập nhật danh sách khách hàng.
   *
   * @param customerList danh sách khách hàng mới.
   */
  public void setCustomerList(List<Customer> customerList) {
    if (customerList == null) {
      this.customerList = new ArrayList<>();
    } else {
      this.customerList = customerList;
    }
  }

  /**
   * Đọc danh sách khách hàng từ InputStream.
   *
   * @param inputStream luồng dữ liệu đầu vào.
   */
  public void readCustomerList(InputStream inputStream) {
    logger.info("Bắt đầu đọc dữ liệu khách hàng...");
    if (inputStream == null) {
      return;
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      Customer currentCustomer = null;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
          continue;
        }

        currentCustomer = processDataLine(line, currentCustomer);
      }
    } catch (IOException e) {
      logger.error("Lỗi khi đọc file danh sách khách hàng: {}", e.getMessage());
    }
  }

  /**
   * Xử lý từng dòng dữ liệu từ file.
   *
   * @param line dòng dữ liệu cần đọc.
   * @param current đối tượng khách hàng hiện tại đang xử lý.
   * @return đối tượng khách hàng sau khi xử lý.
   */
  private Customer processDataLine(String line, Customer current) {
    int lastSpaceIndex = line.lastIndexOf(' ');
    if (lastSpaceIndex <= 0) {
      return current;
    }

    String token = line.substring(lastSpaceIndex + 1).trim();

    // Nếu là dòng thông tin khách hàng (chứa CMND 9 số)
    if (token.matches(ID_NUMBER_REGEX)) {
      String name = line.substring(0, lastSpaceIndex).trim();
      Customer newCustomer = new Customer(Long.parseLong(token), name);
      customerList.add(newCustomer);
      logger.debug("Đã thêm khách hàng mới: {}", name);
      return newCustomer;
    }

    // Nếu là dòng thông tin tài khoản
    if (current != null) {
      addAccountToCustomer(line, current);
    }
    return current;
  }

  /**
   * Thêm tài khoản mới cho khách hàng dựa trên dữ liệu dòng.
   *
   * @param line chuỗi thông tin tài khoản.
   * @param customer đối tượng khách hàng.
   */
  private void addAccountToCustomer(String line, Customer customer) {
    String[] parts = line.split("\\s+");
    if (parts.length < 3) {
      return;
    }

    try {
      long accountNumber = Long.parseLong(parts[0]);
      double balance = Double.parseDouble(parts[2]);
      String type = parts[1];

      if (Account.CHECKING_TYPE.equals(type)) {
        customer.addAccount(new CheckingAccount(accountNumber, balance));
      } else if (Account.SAVINGS_TYPE.equals(type)) {
        customer.addAccount(new SavingsAccount(accountNumber, balance));
      }
    } catch (NumberFormatException e) {
      logger.warn("Dữ liệu tài khoản không hợp lệ: {}", line);
    }
  }

  /**
   * Lấy thông tin khách hàng theo thứ tự CMND.
   *
   * @return chuỗi thông tin khách hàng.
   */
  public String getCustomersInfoByIdOrder() {
    customerList.sort(Comparator.comparingLong(Customer::getIdNumber));

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < customerList.size(); i++) {
      sb.append(customerList.get(i).getCustomerInfo());
      if (i < customerList.size() - 1) {
        sb.append("\n");
      }
    }
    return sb.toString();
  }

  /**
   * Lấy thông tin khách hàng theo thứ tự tên.
   *
   * @return chuỗi thông tin khách hàng.
   */
  public String getCustomersInfoByNameOrder() {
    List<Customer> sortedList = new ArrayList<>(customerList);
    sortedList.sort((c1, c2) -> {
      int nameCompare = c1.getFullName().compareTo(c2.getFullName());
      if (nameCompare != 0) {
        return nameCompare;
      }
      return Long.compare(c1.getIdNumber(), c2.getIdNumber());
    });

    StringBuilder sb = new StringBuilder();
    for (Customer customer : sortedList) {
      sb.append(customer.getCustomerInfo()).append("\n");
    }
    return sb.toString().trim();
  }
}