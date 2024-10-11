package domain;

import constant.Colors;

public class Apple {

  private Integer weight = 0;
  private Colors color;

  public Apple(int weight, Colors color) {
    this.weight = weight;
    this.color = color;
  }

  public Apple() {

  }

  public Apple(Integer weight) {
    this.weight = weight;
  }

  public Apple(Integer weight, Integer height, Colors colors) {
    this.weight = weight;
    this.color = colors;
  }

  @Override
  public String toString() {
    return "Apple{" + "weight=" + weight + ", color='" + color + '\'' + '}';
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public Colors getColor() {
    return color;
  }

  public void setColor(Colors color) {
    this.color = color;
  }
}
