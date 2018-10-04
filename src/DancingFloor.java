import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DancingFloor {
    private final int SPACE = 20;
    private Lock lock = new ReentrantLock();
    private Condition[] listenToDj = new Condition[SPACE];
    private Condition freeSpace;
    private boolean[] isSpace = new boolean[SPACE];
    private int inside = 0;

    public DancingFloor() {
        for (int i = 0; i < SPACE; i++) {
            listenToDj[i] = lock.newCondition();
            isSpace[i] = true;
        }
        freeSpace = lock.newCondition();
    }
    public boolean seatAvailable() {
        return inside < SPACE - 1;
    }
    private int getAvailableSeat() {
        for(int i = 0; i < SPACE; i++) {
            if (isSpace[i]) {
                isSpace[i] = false;
                return i;
            }
        }
        assert false : "We should not reach this piece of code";
        return -1;
    }
    public int enter() {
        int entryNr = -1;
        lock.lock();
        try {
            while (!seatAvailable()) {
                freeSpace.await();
            }
            assert inside < SPACE;
            inside++;
            entryNr = getAvailableSeat();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
        return entryNr;
    }
    public void leave(int seatNr) {
        lock.lock();
        try {
            assert inside >= 0 : "We cannot get less the zero customers in the club";
            isSpace[seatNr] = true;
            inside--;
            freeSpace.signal();
        } finally {
            lock.unlock();
        }
    }
}