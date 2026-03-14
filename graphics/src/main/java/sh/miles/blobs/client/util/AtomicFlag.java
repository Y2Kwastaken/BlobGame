package sh.miles.blobs.client.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class AtomicFlag {
    private final Lock lock = new ReentrantLock();
    private volatile boolean flag;

    public AtomicFlag(boolean value) {
        this.flag = value;
    }

    public boolean observe() {
        lock.lock();
        boolean value = flag;
        lock.unlock();
        return value;
    }

    public void flip() {
        lock.lock();
        this.flag = !this.flag;
        lock.unlock();
    }
}
