package chapter13;

import chapter13.library.Resizable;
import java.util.List;

public class Utils {
  public static void paint(List<Resizable> list) {
    list.forEach(resizable -> {
      resizable.setAbsoluteSize(42, 42);
      resizable.draw();
    });
  }
}
