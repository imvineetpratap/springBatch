package com.spring.batch.controller;

import com.spring.batch.service.ExcelService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@RestController
@RequestMapping("/run")
public class CustomerController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
     @Autowired
    private ExcelService service;



    @PostMapping("/importCustomers")
    public void importCsvToDBJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
@RequestMapping("/excel")
    public ResponseEntity<Resource> download() throws IOException {
String filename="customer.xlsx";
ByteArrayInputStream actualData =service.getActualData();
    InputStreamResource file=new InputStreamResource(actualData);
  ResponseEntity<Resource> body=  ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+filename)
            .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
            .body(file);
    return body;
    }
}
