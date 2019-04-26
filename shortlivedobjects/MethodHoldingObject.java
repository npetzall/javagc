import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MethodHoldingObject {

  public static final void main(String...args) {
    MethodHoldingObject methodHoldingObject = new MethodHoldingObject();
    methodHoldingObject.run();
  }

  public void run() {
    MemoryConsumer memConsumer = new MemoryConsumer(100000);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
    }
    //memConsumer = null;
    memoryIntensiveMethod();
  }

  private void memoryIntensiveMethod() {
      List<MemoryConsumer> memConsumers = new ArrayList<>(50);
      for(int i=1; i <= 50; i++) {
        System.out.println("Creating memory consumer #" + i);
        memConsumers.add(new MemoryConsumer(1000));
        Thread.yield();
      }
      memConsumers.stream().map(m -> m.getSize()).collect(Collectors.summingInt(Integer::intValue));
  }

  class MemoryConsumer {
    private List<Object> objects = new ArrayList<>();
    public MemoryConsumer(int numberOfObjects) {
      objects = new ArrayList<>(numberOfObjects);
      for(int i=0; i < numberOfObjects; i++) {
        objects.add(new Object());
      }
    }
    public int getSize() {
      return objects.size();
    }
  }
}
