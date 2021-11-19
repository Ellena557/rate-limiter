package window.slide;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ThreadSafeSlidingWindowRateLimiterTest {

    /**
     * Простой тест - проверяем только итоговый результат
     */
    @Test
    public void simpleTest() {
        ThreadSafeSlidingWindowRateLimiter rateLimiter = new ThreadSafeSlidingWindowRateLimiter(5, 3);
        RequestSender requestSender = new RequestSender(rateLimiter);

        requestSender.sendRequests(100, 1);
        Assert.assertTrue(rateLimiter.getRequestNumber() <= 5);
    }

    /**
     * Итеративный тест - отправляем запросы по одному со случайной задержкой и после
     * каждого запроса проверяем размер окна (не превышает ли он заданное значение).
     */
    @Test
    public void iterativeTest() {
        ThreadSafeSlidingWindowRateLimiter rateLimiter = new ThreadSafeSlidingWindowRateLimiter(5, 3);
        RequestSender requestSender = new RequestSender(rateLimiter);
        Random random = new Random();

        for (int i = 0; i < 250; i++) {
            requestSender.sendOneRequest();

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Assert.assertTrue(rateLimiter.getRequestNumber() <= 5);
        }
    }

    /**
     * Многопоточный тест: проверка корректной работоспособности в случае запуска
     * нескольких потоков.
     *
     * @throws InterruptedException
     */
    @Test
    public void multiThreadTest() throws InterruptedException {
        ThreadSafeSlidingWindowRateLimiter rateLimiter = new ThreadSafeSlidingWindowRateLimiter(5, 3);
        ExecutorService service = Executors.newFixedThreadPool(15);

        ArrayList<Callable<ArrayList<Integer>>> tasks = new ArrayList<>();
        Callable<ArrayList<Integer>> callable = new RequestGenerator(rateLimiter);
        for (int i = 0; i < 15; i++) {
            tasks.add(callable);
        }

        List<Future<ArrayList<Integer>>> futures = service.invokeAll(tasks);


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // check that we still have 15 futures
        Assert.assertEquals(15, futures.size());

        // check that all sizes are NOT greater than 5
        Assert.assertTrue(futures.stream().allMatch(it -> {
            try {
                return it.get().stream().allMatch(size -> size <= 5);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }));
    }

    private class RequestGenerator implements Callable {

        private final ThreadSafeSlidingWindowRateLimiter threadSafeSlidingWindowRateLimiter;

        public RequestGenerator(ThreadSafeSlidingWindowRateLimiter threadSafeSlidingWindowRateLimiter) {
            this.threadSafeSlidingWindowRateLimiter = threadSafeSlidingWindowRateLimiter;
        }

        @Override
        public Object call() {
            ArrayList<Integer> sizes = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Random r = new Random();
                String key = String.valueOf(r.nextInt(100000));
                Request request = new Request(key);
                threadSafeSlidingWindowRateLimiter.isHandled(request);
                sizes.add(threadSafeSlidingWindowRateLimiter.getRequestNumber());
            }
            return sizes;
        }
    }
}
