package chapter3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteAroundPattern {

  // 함수형 인터페이스 자리에 람다 사용 할 수 있도록 수정
  static String processFile(BufferedReaderProcessor p) throws IOException {
    File file = new File(
        ExecuteAroundPattern.class.getClassLoader().getResource("data.txt").getFile());
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      // 3단계: 동작 실행
      return p.process(br);
    }
  }
}
