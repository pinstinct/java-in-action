package chapter3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAroundPattern {

  static String processFile() throws IOException {
    File file = new File(
        ExecuteAroundPattern.class.getClassLoader().getResource("data.txt").getFile());
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      return br.readLine();  // 실제 필요한 작업을 하는 코드 (한 줄만 읽기)
    }
  }
}
