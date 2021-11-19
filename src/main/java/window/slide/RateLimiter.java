package window.slide;

import java.util.Collection;

public interface RateLimiter {
    boolean isHandled(Request request);

    Collection getServedRequests();
}
