package com.demo.security.controller;

/**
 * Created by yamorn on 2014/11/23.
 */
public class ProgressStatus {
    private long passed;
    private long total;
    public ProgressStatus(){}

    public ProgressStatus(long passed, long total) {
        this.passed=passed;
        this.total=total;
    }
    public long getPassed() {
        return passed;
    }

    public void setPassed(long passed) {
        this.passed = passed;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
