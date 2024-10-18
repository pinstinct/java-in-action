package chapter7;

import java.util.Spliterator;
import java.util.function.Consumer;

// 임의의 위치가 아닌 단어가 끝나는 위치에서 분할하는 Spliterator 구현체
public class WordCounterSpliterator implements Spliterator<Character> {

  private final String string;
  private int currentChar = 0;

  public WordCounterSpliterator(String string) {
    this.string = string;
  }

  @Override
  public boolean tryAdvance(Consumer<? super Character> action) {
    action.accept(string.charAt(currentChar++));  // 현재 문자를 소비
    return currentChar < string.length();  // 소비할 문자가 남아있으면 true 반환
  }

  @Override
  public Spliterator<Character> trySplit() {
    int currentSize = string.length() - currentChar;
    // 파싱할 문자열을 순차 처리할 수 있을 만큼 충분히 작아졌음을 알리는 null 반환
    if (currentSize < 10) {
      return null;
    }

    for (int splitPos = currentSize / 2 + currentChar;
        splitPos < string.length(); splitPos++) {  // 파싱할 문자열의 중간을 분할 위치로 설정
      if (Character.isWhitespace(string.charAt(splitPos))) { // 다음 공백이 나올 때까지 분할위치를 뒤로 이동
        Spliterator<Character> spliterator =
            new WordCounterSpliterator(string.substring(currentChar, splitPos));  // 처음부터 분할 위치까지 문자열을 파싱할 새로운 WordCounterSpliterator 생성
            currentChar = splitPos;  // 이 WordCounterSpliterator의 시작 위치를 분할 위치로 설정
            return spliterator;  // 공백을 찾았고 문자열을 분리했으므로 루프를 종료
      }
    }
    return null;
  }

  @Override
  public long estimateSize() {
    return string.length() - currentChar;
  }

  @Override
  public int characteristics() {
    return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
  }
}
