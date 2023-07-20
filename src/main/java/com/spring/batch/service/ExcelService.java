package com.spring.batch.service;

import com.spring.batch.entity.Customer;
import com.spring.batch.helper.Helper;
import com.spring.batch.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    private CustomerRepo customerRepo;

    public ByteArrayInputStream getActualData() throws IOException {
        List<Customer> all= customerRepo.findAll();
   ByteArrayInputStream byt=  Helper.dataToExcel(all);
return  byt;
    }
}
