package chapter3;

import static chapter3.ExecuteAroundPattern.processFile;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExecuteAroundPatternTest {

  @Test
  @DisplayName("실행 어라운드 패턴을 위한 예제: 차원 처리 순환 패턴")
  void test1() throws IOException {
    System.out.println(processFile());
  }
}
