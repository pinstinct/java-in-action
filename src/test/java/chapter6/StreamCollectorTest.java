package chapter6;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import constant.CaloricLevel;
import constant.Type;
import domain.Dish;
import domain.Transaction;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("스트림으로 데이터 수집")
public class StreamCollectorTest {

  List<Transaction> transactions = asList(
      new Transaction(2000, Currency.getInstance("USD")),
      new Transaction(1500, Currency.getInstance("USD")),
      new Transaction(1000, Currency.getInstance("KRW")),
      new Transaction(3000, Currency.getInstance("KRW")));

  List<Dish> menus = asList(
      new Dish("pork", false, 800, Type.MEAT),
      new Dish("beef", false, 700, Type.MEAT),
      new Dish("chicken", false, 400, Type.MEAT),
      new Dish("french fries", true, 530, Type.OTHER),
      new Dish("rice", true, 350, Type.OTHER),
      new Dish("season fruit", true, 120, Type.OTHER),
      new Dish("pizza", true, 550, Type.OTHER),
      new Dish("prawns", false, 300, Type.FISH),
      new Dish("salmon", false, 450, Type.FISH)
  );

  @Nested
  @DisplayName("컬렉터란 무엇인가")
  class Collectors {

    @Test
    @DisplayName("통화별로 트랜잭션을 그룹화한 코드(명령형 버전)")
    void test1() {
      Map<Currency, List<Transaction>> transactionByCurrencies = new HashMap<>();

      for (Transaction transaction : transactions) {
        Currency currency = transaction.getCurrency();
        List<Transaction> transactionsForCurrency = transactionByCurrencies.get(currency);
        if (transactionsForCurrency == null) {  // 현재 통화를 그룹화하는 맵에 항목이 없으면 항목을 생성
          transactionsForCurrency = new ArrayList<>();
          transactionByCurrencies.put(currency, transactionsForCurrency);
        }
        transactionsForCurrency.add(transaction);
      }
      System.out.println(transactionByCurrencies);
    }

    @Test
    @DisplayName("통화별로 트랜잭션을 그룹화한 코드(함수형 버전)")
    void test2() {
      Map<Currency, List<Transaction>> result = transactions.stream()
          .collect(groupingBy(Transaction::getCurrency));
      System.out.println(result);
    }
  }

  @Nested
  @DisplayName("리듀싱과 요약")
  class ReduceAndSummarize {

    @Test
    @DisplayName("counting")
    void test1() {
      Long howManyDishes = menus.stream()
          .collect(counting());
      System.out.println(howManyDishes);

      long howManyDishes2 = menus.stream().count();
      System.out.println(howManyDishes2);
    }

    @Test
    @DisplayName("스트림값에서 최댓값과 최솟값 검색")
    void test2() {
      Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
      Optional<Dish> mostCalorieDish = menus.stream()
          .collect(maxBy(dishCaloriesComparator));  // maxBy(Comparator)
      System.out.println(mostCalorieDish);

      Optional<Dish> mostCalorieDish2 = menus.stream()
          .max(dishCaloriesComparator);
      System.out.println(mostCalorieDish2);
    }

    @Test
    @DisplayName("요약 연산")
    void test3() {
      // 합계
      Integer totalCalories = menus.stream().collect(summingInt(Dish::getCalories));
      System.out.println(totalCalories);

      // 평균
      Double avgCalories = menus.stream().collect(averagingInt(Dish::getCalories));
      System.out.println(avgCalories);

      // 요소 수, 합계, 평균, 최댓값, 최소값 정보 수집
      IntSummaryStatistics menuStatistics = menus.stream()
          .collect(summarizingInt(Dish::getCalories));
      System.out.println(menuStatistics);
    }

    @Test
    @DisplayName("문자열 연결")
    void test4() {
      String shortMenu = menus.stream()
          .map(Dish::getName).collect(joining());  // joining() 메서드는 내부적으로 StringBuilder 이용
      System.out.println(shortMenu);

      // 연결된 두 요소 사이에 구분자 추가
      String shortMenu2 = menus.stream()
          .map(Dish::getName).collect(joining(", "));
      System.out.println(shortMenu2);
    }

    @Test
    @DisplayName("범용 리듀싱 요약 연산")
    void test5() {
      Integer totalCalories = menus.stream()
          // reducing(시작값 또는 반환값, 변환 함수, BinaryOperator)
          .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
      System.out.println(totalCalories);

      Optional<Dish> mostCalorieDish = menus.stream()
          // reducing(자신을 반환하는 항등 함수): 스트림의 첫 번째 요소가 시작값이다.
          .collect(reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
      System.out.println(mostCalorieDish);
    }

    @Test
    @DisplayName("컬렉션 프레임워크 유연성")
    void test6() {
      Integer totalCalories = menus.stream()
          .collect(reducing(0,  // 초기값
              Dish::getCalories,  // 합계 함수
              Integer::sum));  // 변환 함수
      System.out.println(totalCalories);
    }

    @Test
    @DisplayName("컬렉션 프레임워크 유연성")
    void test7() {
      Integer totalCalories = menus.stream()
          .map(Dish::getCalories)
          .reduce(Integer::sum).get();
      System.out.println(totalCalories);

      // 간결하고 가독성이 좋으며, IntStream 덕분에 자동 언박싱 과정을 회피하므로 성능도 좋다.
      int totalCalories2 = menus.stream().mapToInt(Dish::getCalories).sum();
      System.out.println(totalCalories2);
    }
  }

  @Nested
  @DisplayName("그룹화")
  class Grouping {

    @Test
    @DisplayName("분류 함수")
    void test1() {
      Map<Type, List<Dish>> dishesByType = menus.stream()
          .collect(groupingBy(Dish::getType));  // 분류 함수(classification function): 함수를 기준으로 스트림을 그룹화
      System.out.println(dishesByType);
    }

    @Test
    @DisplayName("메서드 참조를 분류 함수로 사용할 수 없는 경우")
    void test2() {
      Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = menus.stream()
          .collect(groupingBy(dish -> {
            if (dish.getCalories() <= 400) {
              return CaloricLevel.DIET;
            } else if (dish.getCalories() <= 700) {
              return CaloricLevel.NORMAL;
            } else {
              return CaloricLevel.FAT;
            }
          }));
      System.out.println(dishesByCaloricLevel);
    }

    @Nested
    @DisplayName("그룹화된 요소 조작")
    class Element {

      @Test
      @DisplayName("문제점- 조건을 만족하는 요리가 없는 경우 키 자체가 사라짐")
      void test1() {
        // 500 칼로리만 넘는 요리만 필터링
        Map<Type, List<Dish>> caloricDishesByType = menus.stream()
            .filter(dish -> dish.getCalories() > 500)
            .collect(groupingBy(Dish::getType));
        System.out.println(caloricDishesByType);
        // 결과: {OTHER=[Dish{name='french fries'}, Dish{name='pizza'}], MEAT=[Dish{name='pork'}, Dish{name='beef'}]}
        // FISH 키 자체가 사라짐
      }

      @Test
      @DisplayName("groupingBy 팩토리 메서드를 오버로드해 문제 해결")
      void test2() {
        Map<Type, List<Dish>> caloricDishesByType = menus.stream()
            .collect(
                groupingBy(Dish::getType,
                    filtering(dish -> dish.getCalories() > 500, toList())));  // filtering
        System.out.println(caloricDishesByType);
      }

      @Test
      @DisplayName("mapping 메서드")
      void test3() {
        Map<Type, List<String>> dishNamesByType = menus.stream()
            .collect(groupingBy(Dish::getType, mapping(Dish::getName, toList())));
        System.out.println(dishNamesByType);
      }

      @Test
      @DisplayName("flatMapping")
      void test4() {
        Map<String, List<String>> dishTags = new HashMap<>();
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));

        Map<Type, Set<String>> dishNamesByType = menus.stream()
            .collect(groupingBy(Dish::getType,
                flatMapping(dish -> dishTags.get(dish.getName()).stream(),
                    toSet())));  // 집합으로 그룹화해 중복 태그 제거
        System.out.println(dishNamesByType);
      }
    }

    @Nested
    @DisplayName("다수준 그룹화")
    class MultiLevelGroup {

      @Test
      void test1() {
        Map<Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = menus.stream()
            .collect(groupingBy(Dish::getType,  // 첫 번째 수준의 분류 함수
                groupingBy(dish -> {  // 두 번째 수준의 분류 함수
                  if (dish.getCalories() <= 400) {
                    return CaloricLevel.DIET;
                  } else if (dish.getCalories() <= 700) {
                    return CaloricLevel.NORMAL;
                  } else {
                    return CaloricLevel.FAT;
                  }
                })
            ));
        System.out.println(dishesByTypeCaloricLevel);
      }
    }

    @Nested
    @DisplayName("서브그룹으로 데이터 수집")
    class SubGroup {

      @Test
      @DisplayName("groupingBy(분류함수, counting 컬렉터)")
      void test1() {
        Map<Type, Long> typesCount = menus.stream()
            .collect(groupingBy(Dish::getType, counting()));
        System.out.println(typesCount);
      }

      @Test
      @DisplayName("groupingBy(분류함수, maxBy 컬렉터")
      void test2() {
        Map<Type, Optional<Dish>> mostCaloricByType = menus.stream()
            .collect(groupingBy(Dish::getType, maxBy(Comparator.comparingInt(Dish::getCalories))));
        System.out.println(mostCaloricByType);
      }

      @Test
      @DisplayName("컬렉터 결과를 다른 형식에 적용하기 - 위의 결과에서 Optional 제거")
      void test3() {
        Map<Type, Dish> mostCaloricByType = menus.stream()
            .collect(groupingBy(Dish::getType,  // 분류 함수
                collectingAndThen(
                    maxBy(Comparator.comparingInt(Dish::getCalories)),  // 감싸인 컬렉터
                    Optional::get  // 반환 함수
                )
            ));
        System.out.println(mostCaloricByType);
      }

      @Test
      @DisplayName("groupingBy와 함께 사용하는 다른 컬렉터 예제")
      void test4() {
        Map<Type, Integer> totalCaloriesByType = menus.stream()
            .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println(totalCaloriesByType);

        Map<Type, Set<CaloricLevel>> caloricLevelsByType = menus.stream()
            .collect(
                groupingBy(Dish::getType, mapping(dish -> {
                  if (dish.getCalories() <= 400) {
                    return CaloricLevel.DIET;
                  } else if (dish.getCalories() <= 700) {
                    return CaloricLevel.NORMAL;
                  } else {
                    return CaloricLevel.FAT;
                  }
                }, toSet()))  // toSet 컬렉터로 중복 제거
            );
        System.out.println(caloricLevelsByType);

        Map<Type, HashSet<CaloricLevel>> caloricLevelsByType2 = menus.stream().collect(
            groupingBy(Dish::getType, mapping(dish -> {
              if (dish.getCalories() <= 400)
                return CaloricLevel.DIET;
              else if (dish.getCalories() <= 700) {
                return CaloricLevel.NORMAL;
              } else
                return CaloricLevel.FAT;
            }, toCollection(HashSet::new)))
        );
        System.out.println(caloricLevelsByType2);
      }
    }
  }

  @Nested
  @DisplayName("분할")
  class Divide {

    @Test
    @DisplayName("분할 함수")
    void test1() {
      Map<Boolean, List<Dish>> partitionedMenu = menus.stream()
          .collect(partitioningBy(Dish::isVegetarian));  // 분할 함수
      System.out.println(partitionedMenu);

      System.out.println("===");

      List<Dish> vegetarianDishes = partitionedMenu.get(true);
      System.out.println(vegetarianDishes);

      System.out.println("===");

      List<Dish> vegetarianDishes2 = menus.stream().filter(Dish::isVegetarian).toList();
      System.out.println(vegetarianDishes2);
    }

    @Test
    @DisplayName("분할의 장점")
    void test2() {
      Map<Boolean, Map<Type, List<Dish>>> vegetarianDishesByType = menus.stream()
          .collect(partitioningBy(Dish::isVegetarian,  // 분할함수
              groupingBy(Dish::getType))  // 두 번째 컬렉터
          );
      System.out.println(vegetarianDishesByType);

      Map<Boolean, Dish> mostCaloricPartitionedByVegetarian = menus.stream().collect(
          partitioningBy(Dish::isVegetarian,
              collectingAndThen(maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get)));
      System.out.println(mostCaloricPartitionedByVegetarian);
    }

    @Test
    @DisplayName("퀴즈 6-2")
    void test3() {
      Map<Boolean, Map<Boolean, List<Dish>>> result1 = menus.stream().collect(
          partitioningBy(Dish::isVegetarian, partitioningBy(dish -> dish.getCalories() > 500)));
      System.out.println(result1);

      Map<Boolean, Long> result3 = menus.stream()
          .collect(partitioningBy(Dish::isVegetarian, counting()));
      System.out.println(result3);
    }

    @Test
    @DisplayName("숫자를 소수와 비소수로 분할하기")
    void test4() {
      Map<Boolean, List<Integer>> result = partitionPrimes(23);
      System.out.println(result);
    }

    // 주어진 수가 소수인지 아닌지 판단하는 프레디케이트
    public boolean isPrime(int candidate) {
      int candidateRoot = (int) Math.sqrt((double) candidate);  // 제곱근 이하의 수로 제한
      return IntStream.rangeClosed(2, candidateRoot)
          .noneMatch(i -> candidate % i == 0);  // 스트림의 모든 정수로 candidate를 나눌 수 없으면 참을 반환
    }

    public Map<Boolean, List<Integer>> partitionPrimes(int n) {
      return IntStream.rangeClosed(2, n).boxed().collect(partitioningBy(i -> isPrime(i)));
    }
  }

  @Nested
  @DisplayName("Collector 인터페이스")
  class CollectInterface {

    @Test
    @DisplayName("응용하기")
    void test1() {
      // 직접 생성한 컬렉터 이용
      List<Dish> dish = menus.stream()
          .collect(new ToListCollector<Dish>());  // 인스턴스화
      System.out.println(dish);

      System.out.println("===");
      // 위와 동일한 코드
      List<Dish> dish2 = menus.stream()
          .collect(toList());  // 팩토리
      System.out.println(dish2);
    }

    @Test
    @DisplayName("컬렉터 구현을 만들지 않고도 커스텀 수집 수행하기")
    void test2() {
      // 가독성이 떨어지는 방법
      // 적절한 클래스로 커스텀 컬렉터를 구현(위의 방법)하는 편이 중복을 피하고 재사용성을 높이는 데 도움이 됨
      List<Dish> dishes = menus.stream().collect(
          // 리스트에 수집
          ArrayList::new,  // 발행
          List::add,  // 누적
          List::addAll  // 합침
          // Characteristics 전달 불가
      );
      System.out.println(dishes);
    }
  }
}
