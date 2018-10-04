public class Vip extends Thread implements Customer {
    private final DancingFloor dancingFloor;

    public Vip(DancingFloor dancingFloor,String name) {
        super(name);
        this.dancingFloor = dancingFloor;
    }

    @Override
    public void listen() {
        try {
            Thread.sleep(4000);
            System.out.println(getName() + " is listening");
        } catch (Exception e) {
        }
    }

    @Override
    public void await() {
         try {
            Thread.sleep(4000);
            System.out.println(getName() + " is waiting");
        } catch (Exception e) {
        }
    }
}
