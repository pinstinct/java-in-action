package chapter1;

import static java.util.stream.Collectors.groupingBy;
import static org.assertj.core.api.Assertions.assertThat;

import domain.Transaction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FilterTransactionTest {

  List<Transaction> transactions = Arrays.asList(new Transaction(2000, Currency.getInstance("USD")),
      new Transaction(1500, Currency.getInstance("USD")),
      new Transaction(1000, Currency.getInstance("KRW")),
      new Transaction(3000, Currency.getInstance("KRW")));

  @Test
  @DisplayName("리스트에서 고가의 트랜잭션만 필터링한 다음에 통화로 결과를 그룹화")
  void test1() {
    Map<Currency, List<Transaction>> transactionsByCurrencies = new HashMap<>();

    for (Transaction transaction : transactions) {
      if (transaction.getPrice() > 1000) {  // 고가의 트랜잭션 필터링
        Currency currency = transaction.getCurrency();
        List<Transaction> transactionForCurrency = transactionsByCurrencies.get(currency);
        if (transactionForCurrency == null) {  // 현재 통화가 그룹화된 맵에 없으면 새로 추가
          transactionForCurrency = new ArrayList<>();
          transactionsByCurrencies.put(currency, transactionForCurrency);
        }
        transactionForCurrency.add(transaction);
      }
    }

    assertThat(transactionsByCurrencies.get(Currency.getInstance("USD")).size()).isEqualTo(2);
    assertThat(transactionsByCurrencies.get(Currency.getInstance("KRW")).size()).isEqualTo(1);
    System.out.println(transactionsByCurrencies);
  }

  @Test
  @DisplayName("스트림 API를 이용해 test1과 동일한 테스트 만들기")
  void test2() {
    Map<Currency, List<Transaction>> result = transactions.stream()
        .filter(transaction -> transaction.getPrice() > 1000)  // 고가의 트랜잭션 필터링
        .collect(groupingBy(Transaction::getCurrency));  // 통화로 그룹화

    assertThat(result.get(Currency.getInstance("USD")).size()).isEqualTo(2);
    assertThat(result.get(Currency.getInstance("KRW")).size()).isEqualTo(1);
    assertThat(result.get(Currency.getInstance("CHF"))).isNull();
    System.out.println(result);
  }
}
