package chapter13;

import chapter13.library.Rectangle;
import chapter13.library.Resizable;
import chapter13.library.Square;
import java.util.Arrays;
import java.util.List;

// 사용자 구현을 포함해 다양한 Resizable 모양을 처리하는 게임 클래스 생성
public class Game {

  public static void test() {
    List<Resizable> resizableShapes = Arrays.asList(
        new Square(), new Rectangle(), new Ellipse()
    );
    Utils.paint(resizableShapes);
  }
}
