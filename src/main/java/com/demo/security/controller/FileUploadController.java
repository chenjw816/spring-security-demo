package com.demo.security.controller;

import com.demo.security.utils.JSONBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;

/**
 * Created by yamorn on 2014/11/23.
 */
@Controller
public class FileUploadController {
    private static final int BUFFER_SIZE = 1024;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @RequestMapping("/upload")
    public String upload(){
        return "demo/fileUpload";
    }
    @RequestMapping(value = "/uploadAction", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public
    @ResponseBody
    String handleFormUpload(@RequestParam("name") String name,
                            @RequestParam("file") MultipartFile file,Principal user,HttpServletRequest request) {
        String root = request.getServletContext().getRealPath("/");
        String result;
        if (!file.isEmpty()) {
            File repository = new File(root,"upload");
            if (!repository.exists()) {
                System.out.println(repository.getAbsolutePath() + " not exist.");
            } else {
                new ProgressReport(new ProgressReportHandler(name, file, repository, user)).start();
            }
            result = JSONBuilder.builder().inflate("status", "OK").build().toString();
        } else {
            result = JSONBuilder.builder().inflate("status", "ERROR").build().toString();
        }

        return result;
    }

    private class ProgressReportHandler implements ReportHandler {
        private File repository;
        private String name;
        private MultipartFile multipartFile;
        private ProgressStatus progressStatus = new ProgressStatus();
        private Principal user;

        public ProgressReportHandler(String name, MultipartFile multipartFile, File repository,Principal user) {
            this.name = name;
            this.multipartFile = multipartFile;
            this.repository = repository;
            this.user=user;
        }

        @Override
        public void setProgressStatus(ProgressStatus progressStatus) {
            this.progressStatus = progressStatus;
        }

        @Override
        public ProgressStatus getProgressStatus() {
            return progressStatus;
        }

        @Override
        public void init() {
            progressStatus.setTotal(multipartFile.getSize());
            progressStatus.setPassed(0L);
        }

        @Override
        public void doJob() throws Exception {
            BufferedInputStream inputStream = null;
            BufferedOutputStream outputStream = null;
            try {
                inputStream = new BufferedInputStream(multipartFile.getInputStream());
                outputStream = new BufferedOutputStream(new FileOutputStream(new File(repository, name)));
                int len = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((len = inputStream.read(buffer)) != -1) {
                    progressStatus.setPassed(progressStatus.getPassed() + len);
                    outputStream.write(buffer, 0, len);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            }

        }

        @Override
        public void report(long passed) {
            System.out.println("passed="+passed);
            String message=JSONBuilder.builder()
                    .inflate("name", "upload")
                    .inflate("total", progressStatus.getTotal())
                    .inflate("passed", passed).build().toString();

            messagingTemplate.convertAndSend("/queue/"+user.getName(),message);
        }
    }

}
