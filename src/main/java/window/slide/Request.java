package window.slide;

public class Request {
    private final String key;
    private final Long creationTimestamp;

    public Request(String key, long creationTimestamp) {
        this.key = key;
        this.creationTimestamp = creationTimestamp;
    }

    public Request(String key) {
        this.key = key;
        this.creationTimestamp = System.nanoTime();
    }

    public String getKey() {
        return key;
    }

    public Long getCreationTimestamp() {
        return creationTimestamp;
    }
}
