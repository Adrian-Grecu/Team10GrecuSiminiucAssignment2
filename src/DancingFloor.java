import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class DancingFloor {
    private final int SPACE = 6;
    private Lock lock = new ReentrantLock();
    private Condition[] listenToDj = new Condition[SPACE];
    private Condition freeSpace;
    private boolean[] isSpace = new boolean[SPACE];
    private int inside = 0;
    private boolean vipInClub =false;


    public DancingFloor() {
        for (int i = 0; i < SPACE; i++) {
            listenToDj[i] = lock.newCondition();
            isSpace[i] = true;
        }
        freeSpace = lock.newCondition();
    }
    private boolean seatAvailable() {
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
    int enter() {
        int entryNr = -1;
        lock.lock();
        try {
            while (!seatAvailable() || vipInClub) {
                freeSpace.await();
            }
            if (Thread.currentThread().getName().contains("Vip"))
            {
                while(inside>SPACE/2)
                {
                    vipInClub =true;
                    freeSpace.await();
                }
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
    void leave(int seatNr) {
        lock.lock();
        try {
            assert inside >= 0 : "We cannot get less the zero customers in the club";

            isSpace[seatNr] = true;
            inside--;
            if (Thread.currentThread().getName().contains("Vip"))
            {
                vipInClub=false;
            }
            freeSpace.signal();
        } finally {
            lock.unlock();
        }
    }
}