package com.demo.security.controller;

/**
 * Created by yamorn on 2014/11/23.
 */
public class ProgressReport {
    private ReportHandler reportHandler;
    private ProgressReportThread progressReportThread;

    public ProgressReport(ReportHandler reportHandler){
        this.reportHandler=reportHandler;
       this.progressReportThread=new ProgressReportThread(reportHandler);
    }
    public ProgressReport(ReportHandler reportHandler,long interval){
        this.reportHandler=reportHandler;
        this.progressReportThread=new ProgressReportThread(reportHandler,interval);
    }
    public void start(){
        reportHandler.init();
        progressReportThread.start();
        try {
            reportHandler.doJob();
            Thread.sleep(progressReportThread.getInterval() * 2);
            progressReportThread.interrupt();
            progressReportThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // Terminal report thread
            progressReportThread.interrupt();
        }

    }

    private class ProgressReportThread extends Thread{
        private ProgressStatus progressStatus;
        private long interval=200; //default
        private ReportHandler reportHandler;

        public ProgressReportThread(ReportHandler reportHandler) {
            this.reportHandler=reportHandler;
            this.progressStatus=reportHandler.getProgressStatus();
        }
        public ProgressReportThread(ReportHandler reportHandler, long interval) {
            if(interval!=0){
                this.interval = interval;
            }
            this.progressStatus=reportHandler.getProgressStatus();
            this.reportHandler=reportHandler;
        }

        public long getInterval() {
            return interval;
        }

        @Override
        public void run() {
            boolean flag = true;
            while (flag) {
                if(progressStatus.getPassed()==0){
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        flag = false;
                    }
                    continue;
                }
                reportHandler.report(progressStatus.getPassed());
                if (progressStatus.getPassed() == progressStatus.getTotal()) {
                    flag = false;
                }
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    flag = false;
                }
            }
            System.out.printf("Thread closed.");
        }
    }
}
