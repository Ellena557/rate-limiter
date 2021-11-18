# Описание работы алгоритма

Если кратко:
* Request: запрос
* RequestSender: служебный класс для отправки запросов
* SlidingWindowRateLimiter: однопоточная реализация (тут есть system.out и т.д. для наглядности)
* ThreadSafeSlidingWindowRateLimiter: как раз то, что требовалось в задании: многопоточная реализация
И тесты:
* SlidingWindowRateLimiterTest: на однопоточную реализацию
* ThreadSafeSlidingWindowRateLimiterTest: на многопоточную