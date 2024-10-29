package chapter13;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

public class ArrayList<E>
    extends AbstractList<E>  // 한 개의 클래스 상속
    implements List<E>, RandomAccess, Cloneable, Serializable  // 네 개의 인터페이스 구현
{

  @Override
  public E get(int index) {
    return null;
  }

  @Override
  public int size() {
    return 0;
  }
}
