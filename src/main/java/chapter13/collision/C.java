package chapter13.collision;

public class C implements B, A {


  // chapter13.collision.C inherits unrelated defaults for hello() from types chapter13.collision.B and chapter13.collision.A
  // 위의 에러가 발생하므로 아래와 같이 명시적으로 선택
  @Override
  public void hello() {
    B.super.hello();
  }


}
