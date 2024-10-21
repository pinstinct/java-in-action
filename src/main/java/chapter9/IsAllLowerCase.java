package chapter9;

public class IsAllLowerCase implements ValidationStrategy {

  @Override
  public boolean execute(String s) {
    return s.matches("[a-z]+");
  }
}
