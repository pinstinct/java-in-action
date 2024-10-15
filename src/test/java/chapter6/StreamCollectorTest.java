package chapter6;

import static java.util.stream.Collectors.groupingBy;

import domain.Transaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("스트림으로 데이터 수집")
public class StreamCollectorTest {

  List<domain.Transaction> transactions = Arrays.asList(
      new Transaction(2000, Currency.getInstance("USD")),
      new Transaction(1500, Currency.getInstance("USD")),
      new Transaction(1000, Currency.getInstance("KRW")),
      new Transaction(3000, Currency.getInstance("KRW")));

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


}
