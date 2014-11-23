package com.demo.security.controller;

/**
 * Created by yamorn on 2014/11/23.
 */
public interface ReportHandler {
    public void setProgressStatus(ProgressStatus progressStatus);
    public ProgressStatus getProgressStatus();
    public abstract void init();
    public abstract void doJob() throws Exception;
    public void report(long passed);
}
