package window.slide;


import java.util.ArrayList;
import java.util.LinkedList;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final int windowCapacity;
    private final long windowDuration;
    private LinkedList<Request> servedRequests;

    /**
     * Rate Limiter по принципу Sliding Window
     *
     * @param windowDuration длительность по времени, сколько занимает окно (в секундах)
     * @param windowCapacity сколько запросов может пройти за это время
     */
    public SlidingWindowRateLimiter(int windowCapacity, long windowDuration) {
        this.windowCapacity = windowCapacity;
        this.windowDuration = windowDuration * 1_000_000_000;
        this.servedRequests = new LinkedList<>();
    }

    @Override
    public boolean isHandled(Request request) {
        long currentTime = System.nanoTime();

        // the start of the window for the period
        long windowStart = currentTime - windowDuration;

        if (countRequestNumber(windowStart) >= windowCapacity) {
            return false;
        }

        servedRequests.add(request);

        return true;
    }

    /**
     * Метод возвращает число запросов, которые уже были выполнены в рамках текущего
     * скользящего окна.
     *
     * @param windowStart момент начала окна
     * @return количество запросов
     */
    private int countRequestNumber(long windowStart) {
        int numRequests = 0;

        // collect old requests (that are out of the window), we will delete them to free space
        ArrayList<Request> oldRequests = new ArrayList<>();
        for (Request currentRequest : servedRequests) {
            if (currentRequest.getCreationTimestamp() > windowStart) {
                numRequests += 1;
            } else {
                oldRequests.add(currentRequest);
            }
        }

        // delete old requests
        servedRequests.removeAll(oldRequests);

        return numRequests;
    }

    @Override
    public LinkedList<Request> getServedRequests() {
        return servedRequests;
    }
}
