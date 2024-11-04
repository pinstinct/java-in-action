package chapter16;

import static chapter16.Shop.findPriceWithExecutor;
import static chapter16.Shop.findPrices;
import static chapter16.Shop.findPricesAsync;
import static chapter16.Shop.findPricesAsyncSimple;
import static chapter16.Shop.findPricesParallel;
import static chapter16.Shop.findPricesWithCF;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ShopTest {

  @Nested
  @DisplayName("비블록 코드 만들기")
  class NonBlockTest {

    @Test
    @DisplayName("findPrices 결과와 성능 확인")
    void test1() {
      long start = System.nanoTime();
      // 원하는 제품의 가격을 검색
      System.out.println(findPrices("myPhone27s"));
      long duration = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Done in " + duration + "m sec");
    }

    @Test
    @DisplayName("병렬 스트림으로 요청 병렬화하기")
    void test2() {
      long start = System.nanoTime();
      // 원하는 제품의 가격을 검색
      System.out.println(findPricesParallel("myPhone27s"));
      long duration = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Done in " + duration + "m sec");
      System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    @DisplayName("CompletableFuture로 비동기 호출 구현하기")
    void test3() {
      long start = System.nanoTime();
      // 원하는 제품의 가격을 검색
      System.out.println(findPricesWithCF("myPhone27s"));
      long duration = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Done in " + duration + "m sec");
    }

    @Test
    @DisplayName("커스텀 Executor 사용하기")
    void test4() {
      long start = System.nanoTime();
      // 원하는 제품의 가격을 검색
      System.out.println(findPriceWithExecutor("myPhone27s"));
      long duration = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Done in " + duration + "m sec");
    }
  }

  @Nested
  @DisplayName("비동기 작업")
  class AsyncTest {

    @Test
    @DisplayName("가장 간단한 findPricesAsync")
    void test1() {
      long start = System.nanoTime();
      System.out.println(findPricesAsyncSimple("myPhone27s"));
      long duration = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Done in " + duration + "m sec");
    }

    @Test
    @DisplayName("CompletableFuture를 조합해서 성능 개선")
    void test2() {
      long start = System.nanoTime();
      System.out.println(findPricesAsync("myPhone27s"));
      long duration = (System.nanoTime() - start) / 1_000_000;
      System.out.println("Don in " + duration + "m sec");
    }
  }
}