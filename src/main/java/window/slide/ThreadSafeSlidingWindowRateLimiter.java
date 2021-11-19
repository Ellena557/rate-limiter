package window.slide;


import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeSlidingWindowRateLimiter implements RateLimiter {
    private final int windowCapacity;
    private final long windowDuration;
    private volatile LinkedList<Request> servedRequests;
    private AtomicInteger requestNumber;

    private ReentrantLock lock = new ReentrantLock();

    public ThreadSafeSlidingWindowRateLimiter(int windowCapacity, long windowDuration) {
        this.windowCapacity = windowCapacity;
        this.windowDuration = windowDuration * 1_000_000_000;
        this.servedRequests = new LinkedList<>();
        this.requestNumber = new AtomicInteger(0);
    }

    @Override
    public boolean isHandled(Request request) {
        long currentTime = System.nanoTime();

        // the start of the window for the period
        long windowStart = currentTime - windowDuration;

        lock.lock();
        try {
            requestNumber.set(countRequestNumber(windowStart));
            if (requestNumber.get() >= windowCapacity) {
                return false;
            }
            servedRequests.add(request);
            return true;
        } finally {
            lock.unlock();
        }
    }

    private int countRequestNumber(long windowStart) {
        int numRequests = 0;
        for (Request currentRequest : servedRequests) {
            if (currentRequest.getCreationTimestamp() > windowStart) {
                numRequests += 1;
            }
        }

        return numRequests;
    }

    @Override
    public LinkedList<Request> getServedRequests() {
        return servedRequests;
    }

    public int getRequestNumber() {
        return requestNumber.get();
    }
}
