package window.slide;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WindowRateRun {
    private final RateLimiter rateLimiter;
    public WindowRateRun(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @RequestMapping(value = "/request/{key}", method = RequestMethod.POST)
    public void sendRequest(@PathVariable("key") String key) {
        Request request = new Request(key);
        this.rateLimiter.isHandled(request);
        System.out.println("Received request with key: " + key);
    }
}
