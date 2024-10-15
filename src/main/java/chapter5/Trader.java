package chapter5;

public record Trader(String name, String city) {

  @Override
  public String toString() {
    return "Trader{" + "name='" + name + '\'' + ", city='" + city + '\'' + '}';
  }
}
