package window.slide;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WindowRateConfiguration {
    @Bean
    public RateLimiter RateLimiter() {
        return new ThreadSafeSlidingWindowRateLimiter(100, 1);
    }
}
