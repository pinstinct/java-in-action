package chapter6;


import static chapter6.PrimeNumber.isPrime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PrimeNumberCollector implements
    // 1단계: Collector 클레스 시그니처 정의 (스트림 요소의 형식, 중간 결과를 누적하는 객체의 형식, 최종 결과 형식)
    Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

  // 2단계: 리듀싱 연산 구현
  @Override
  public Supplier<Map<Boolean, List<Integer>>> supplier() {
    return () -> new HashMap<Boolean, List<Integer>>() {{
      put(true, new ArrayList<Integer>());
      put(false, new ArrayList<Integer>());
    }};
  }

  // 2단계: 리듀싱 연산 구현
  @Override
  public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
    return (acc, candidate) -> {
      acc.get(isPrime(acc.get(true), candidate))  // isPrime 결과에 따라 소수 리스트와 비소수 리스트 생성
          .add(candidate);  // candidate를 알맞은 리스트에 추가
    };
  }

  // 3단계: 병렬 실행할 수 있는 컬렉터 만들기(가능하다면)
  @Override
  public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
    return (map1, map2) -> {
      // 알고리즘 자체가 순차적이어서 병렬로 사용 불가
      // 따라서 combiner 메서드는 호출될 일이 없으므로 빈 구현으로 남겨둘 수 있다.
      // 또는 UnsupportedOperationException 을 던지도록 구현하는 방법도 좋다.
      // 학습 목적으로 구현
      map1.get(true).addAll(map2.get(true));
      map1.get(false).addAll(map2.get(false));
      return map1;
    };
  }

  // 4단계: finisher 메서드와 컬렉터의 characteristics 메서드
  @Override
  public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
    // accumulator 메서드의 형식은 컬렉터 결과 형식과 같으므로 변환 과정이 필요 없다.
    // 따라서 항등 함수 identity를 반환하도록 구현
    return Function.identity();
  }

  // 4단계: finisher 메서드와 컬렉터의 characteristics 메서드
  @Override
  public Set<Characteristics> characteristics() {
    return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
  }
}
