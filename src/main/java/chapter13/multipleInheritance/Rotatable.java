package chapter13.multipleInheritance;

public interface Rotatable {

  void setRotationAngle(int angleInDegrees);

  int getRotationAngle();

  // 기본 구현
  default void rotateBy(int angleInDegrees) {
    setRotationAngle(getRotationAngle() + angleInDegrees % 360);
  }
}
