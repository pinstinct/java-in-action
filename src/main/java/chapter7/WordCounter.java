package chapter7;

import java.util.stream.Stream;

// 문자열 스트림을 탐색하면서 단어 수를 세는 클래스
// 자바에는 튜플이 없으므로 변수 상태를 캡슐화하는 새로운 클래스 생성
public class WordCounter {

  private final int counter;
  private final boolean lastSpace;

  public WordCounter(int counter, boolean lastSpace) {
    this.counter = counter;
    this.lastSpace = lastSpace;
  }

  public static int countWords(Stream<Character> stream) {
    WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
        WordCounter::accumulate, WordCounter::combine);
    return wordCounter.getCounter();
  }

  // 반복 알고리즘처럼 accumulate 메서드는 문자열의 문자를 하나씩 탐색한다.
  public WordCounter accumulate(Character c) {
    if (Character.isWhitespace(c)) {
      return lastSpace ? this : new WordCounter(counter, true);
    } else {  // 문자를 하나씩 탐색하다 공백 문자를 만나면 지금까지 탐색한 문자를 단어로 간주하여 단어 수를 증가
      return lastSpace ? new WordCounter(counter + 1, false) : this;
    }
  }

  // 문자열 서브 스트림을 처리한 WordCounter 결과를 합친다.
  public WordCounter combine(WordCounter wordCounter) {
    return new WordCounter(counter + wordCounter.counter,  // 두 WordCounter 의 counter 값을 더한다.
        wordCounter.lastSpace);  // counter 값만 더할 것이므로 마지막 공백은 신경쓰지 않는다.
  }

  public int getCounter() {
    return counter;
  }
}
