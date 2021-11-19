package window.slide;

import java.util.Random;

public class RequestSender {
    private final RateLimiter rateLimiter;

    public RequestSender(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public void sendOneRequest() {
        Request request = new Request(generateKey(), System.nanoTime());
        rateLimiter.isHandled(request);
    }

    public void sendRequests(int numRequests, int waitBound) {
        for (int i = 0; i < numRequests; i++) {
            System.out.println("Send request number " + i);
            Request request = new Request(generateKey(), System.nanoTime());
            rateLimiter.isHandled(request);
            try {
                Random random = new Random();
                Thread.sleep(random.nextInt(100));
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
