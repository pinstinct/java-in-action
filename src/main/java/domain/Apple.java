package domain;

import constant.Colors;

public class Apple {

  private int weight = 0;
  private Colors color;

  public Apple(int weight, Colors color) {
    this.weight = weight;
    this.color = color;
  }

  @Override
  public String toString() {
    return "Apple{" + "weight=" + weight + ", color='" + color + '\'' + '}';
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public Colors getColor() {
    return color;
  }

  public void setColor(Colors color) {
    this.color = color;
  }
}
