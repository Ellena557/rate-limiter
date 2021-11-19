package window.slide;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;


public class SlidingWindowRateLimiterTest {

    /**
     * Простой тест - проверяем только итоговый результат
     */
    @Test
    public void simpleTest() {
        SlidingWindowRateLimiter rateLimiter = new SlidingWindowRateLimiter(5, 3);
        RequestSender requestSender = new RequestSender(rateLimiter);

        requestSender.sendRequests(200, 2);
        Assert.assertTrue(rateLimiter.getServedRequests().size() <= 5);
    }

    /**
     * Итеративный тест - отправляем запросы по одному со случайной задержкой и после
     * каждого запроса проверяем размер окна (не превышает ли он заданное значение).
     */
    @Test
    public void iterativeTest() {
        SlidingWindowRateLimiter rateLimiter = new SlidingWindowRateLimiter(5, 3);
        RequestSender requestSender = new RequestSender(rateLimiter);
        Random random = new Random();

        for (int i = 0; i < 250; i++) {
            requestSender.sendOneRequest();

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Assert.assertTrue(rateLimiter.getServedRequests().size() <= 5);
        }
    }
}
