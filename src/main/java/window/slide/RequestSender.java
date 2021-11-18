package window.slide;

import java.util.Random;

public class RequestSender {
    private final SlidingWindowRateLimiter slidingWindowRateLimiter;

    public RequestSender(SlidingWindowRateLimiter slidingWindowRateLimiter) {
        this.slidingWindowRateLimiter = slidingWindowRateLimiter;
    }

    public void sendOneRequest() {
        Request request = new Request(generateKey(), System.nanoTime());
        slidingWindowRateLimiter.isHandled(request);
    }

    public void sendRequests(int numRequests, int waitBound) {
        for (int i = 0; i < numRequests; i++) {
            System.out.println("Send request number " + i);
            Request request = new Request(generateKey(), System.nanoTime());
            slidingWindowRateLimiter.isHandled(request);
            try {
                Random random = new Random();
                System.out.println(random.nextInt(waitBound) * 1_000);
                Thread.sleep(random.nextInt(waitBound) * 1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateKey() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100000));
    }
}
