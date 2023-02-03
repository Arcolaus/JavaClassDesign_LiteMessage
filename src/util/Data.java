package util;

import java.io.Serializable;

public class Data implements Serializable {
    protected String owner;
    protected String receiver;

    public Data(String owner) {
        this.owner = owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getOwner() {
        return this.owner;
    }
}

