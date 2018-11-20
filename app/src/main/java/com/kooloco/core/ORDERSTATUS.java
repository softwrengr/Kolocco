package com.kooloco.core;

/**
 * Created by hlink44 on 13/12/17.
 */

public enum ORDERSTATUS {
    PENDING("pending", 0), ACCEPT("accept", 1), COMPLATE("complete", 2);

    private String status;

    private int key = 0;

    ORDERSTATUS(String status, int key) {
        this.status = status;
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public int getKey() {
        return key;
    }

}
