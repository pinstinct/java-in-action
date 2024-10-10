package chapter3;

import static chapter3.ExecuteAroundPattern.processFile;

import java.io.BufferedReader;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExecuteAroundPatternTest {

  @Test
  @DisplayName("1단계: 동작 파라미터화를 기억하라")
  void test2() throws IOException {
    // 4단계: 람다 전달
    String oneLine = processFile((BufferedReader br) -> br.readLine());  // 한 번에 한 줄
    System.out.println(oneLine);

    String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());  // 한 번에 두 줄
    System.out.println(twoLines);
  }
}
