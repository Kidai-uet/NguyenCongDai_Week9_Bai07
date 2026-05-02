import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.nio.file.Path;

public class OSPathTest {

  @Test
  public void testFilePath() {
    // Đã Refactor: Dùng java.nio.file.Path để tự động sinh đường dẫn chuẩn theo từng HĐH
    String universalPath = Path.of("logs", "transaction.log").toString();

    String systemPath = "logs" + File.separator + "transaction.log";
    assertEquals(universalPath, systemPath, "Đường dẫn phải khớp nhau trên mọi HĐH");
  }
}