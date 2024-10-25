package chapter11;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OptionalMain {

  public String getCarInsuranceName(Optional<Person> person) {
    return person.flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown");
  }

  public Set<String> getCarInsuranceNames(List<Person> persons) {
    return persons.stream()
        // 사람 목록을 각 사람이 보유한 Optional<Car> 스트림으로 변환
        .map(Person::getCar)
        // flatMap 연산을 이용해 Optional<Car>을 해당 Optional<Insurance>로 변환
        .map(optCar -> optCar.flatMap(Car::getInsurance))
        // Optional<Insurance>를 해당 이름의 Optional<String>으로 매핑
        .map(optIns -> optIns.map(Insurance::getName))
        // Stream<Optional<String>>을 현재 이름을 포함하는 Stream<String>으로 변환
        .flatMap(Optional::stream)
        // 결과 문자열을 중복되지 않도록 집합으로 수집
        .collect(Collectors.toSet());
  }

  public Insurance findCheapestInsurance(Person person, Car car) {
    // 다양한 보험회사가 제공하는 서비스 조회
    // 모든 결과 데이터 비교
    Insurance cheapestCompany = new Insurance();
    return cheapestCompany;
  }

  public Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person,
      Optional<Car> car) {
    // null 확인 코드와 크게 다른 점이 없다.
    if (person.isPresent() && car.isPresent()) {
      return Optional.of(findCheapestInsurance(person.get(), car.get()));
    } else {
      return Optional.empty();
    }
  }

  // 위의 nullSafeFindCheapestInsurance 메서드 리팩토링
  public Optional<Insurance> nullSafeFindCheapestInsurance2(Optional<Person> person,
      Optional<Car> car) {
    return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
  }

  public String getCarInsuranceName(Optional<Person> person, int minAge) {
    return person.filter(p -> p.getAge() >= minAge)
        .flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown");
  }
}
