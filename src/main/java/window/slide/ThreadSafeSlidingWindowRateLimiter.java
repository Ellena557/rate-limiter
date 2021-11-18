package window.slide;

public class ThreadSafeSlidingWindowRateLimiter implements RateLimiter {
    @Override
    public boolean isHandled(Request request) {
        return false;
    }

    @Override
    public void handle(Request request) {

    }
}
