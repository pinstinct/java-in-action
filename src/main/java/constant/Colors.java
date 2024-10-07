package constant;

public enum Colors {
  GREEN("green");

  private final String value;

  Colors(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
