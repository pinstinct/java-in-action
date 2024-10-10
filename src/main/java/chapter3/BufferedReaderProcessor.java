package chapter3;

import java.io.BufferedReader;
import java.io.IOException;

// 2단계: 함수형 인터페이스를 이용해서 동작 전달
@FunctionalInterface
public interface BufferedReaderProcessor {

  // BufferedReader -> String & IOException 던질 수 있는 시그니처
  String process(BufferedReader b) throws IOException;
}
