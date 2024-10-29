package chapter13.multipleInheritance;

// 모든 추상 메서드의 구현은 제공해야 하지만 디폴트 메서드의 구현은 제공할 필요가 없음
public class Monster implements Rotatable, Moveable, Resizable {

  @Override
  public int getX() {
    return 0;
  }

  @Override
  public void setX(int x) {

  }

  @Override
  public int getY() {
    return 0;
  }

  @Override
  public void setY(int y) {

  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public void setWidth(int width) {

  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public void setHeight(int height) {

  }

  @Override
  public void setAbsoluteSize(int width, int height) {

  }

  @Override
  public int getRotationAngle() {
    return 0;
  }

  @Override
  public void setRotationAngle(int angleInDegrees) {

  }
}
