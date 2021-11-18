package window.slide;

public interface RateLimiter {
    boolean isHandled(Request request);

    void handle(Request request);
}
