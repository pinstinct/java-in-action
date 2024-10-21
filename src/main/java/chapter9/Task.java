package chapter9;

public interface Task {

  public static void doSomething(Task a) {
    a.execute();
  }

  public static void doSomething(Runnable r) {

    r.run();
  }

  public void execute();

}


