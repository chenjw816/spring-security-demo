package com.demo.security.controller;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;
@RunWith(JUnit4.class)
public class ReportHandlerTest {
    @Test
    @Ignore
    public void testProgressReport(){
        ProgressReport progressReport=new ProgressReport(new ReportHandler() {
            private ProgressStatus progressStatus=new ProgressStatus();
            @Override
            public void setProgressStatus(ProgressStatus progressStatus) {
                this.progressStatus=progressStatus;
            }

            @Override
            public ProgressStatus getProgressStatus() {
                return this.progressStatus;
            }

            @Override
            public void init() {
                progressStatus.setTotal(100);
                progressStatus.setPassed(0);
            }

            @Override
            public void doJob() {
                for(int i=0;i<100;i++){
                    progressStatus.setPassed(progressStatus.getPassed() + 1);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void report(long passed) {
                System.out.println("passed:"+passed);
            }
        });
        progressReport.start();
    }
}