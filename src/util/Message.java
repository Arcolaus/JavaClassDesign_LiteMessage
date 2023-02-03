package util;

public class Message extends Data {
    public String message;
    private boolean sendResult;

    public Message(String owner, String receiver, String message) {
        super(owner);
        this.receiver = receiver;
        this.message = message;
        this.sendResult=false;
    }

    public void setSendResult(boolean sendResult) {
        this.sendResult = sendResult;
    }

    public boolean sendResult() {
        return sendResult;
    }

    public String getMessage() {
        return message;
    }
}
