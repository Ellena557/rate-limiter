package window.slide;

public class Request {
    private final String key;
    private boolean status;

    public Request(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
