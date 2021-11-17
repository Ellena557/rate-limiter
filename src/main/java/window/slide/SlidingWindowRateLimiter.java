package window.slide;


public class SlidingWindowRateLimiter implements RateLimiter {
    @Override
    public boolean isHandled(Request request) {
        return false;
    }

    @Override
    public void handle() {

    }
}
