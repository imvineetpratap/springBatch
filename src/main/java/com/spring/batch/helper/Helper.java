package com.spring.batch.helper;

import com.spring.batch.entity.Customer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class Helper {
    public static String[] HEADERS = {

            "id",
            "first_name",
            "last_name",
            "gender", "country",
            "contact", "email",
            "date_of_birth"


    };
    public static String Sheet_Name = "customer_Data";


    public static ByteArrayInputStream dataToExcel(List<Customer> list) throws IOException {
        //create work book
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();


        // Create a cell style for the header
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        try {
            //create sheet
            Sheet sheet = workbook.createSheet(Sheet_Name);
            //create row: HEADER ROW
            Row row = sheet.createRow(0);

            for(int i = 0; i < HEADERS.length; i++) {
              Cell cell= row.createCell(i);
              cell.setCellValue(HEADERS[i]);
              cell.setCellStyle(headerStyle);
            }
            //value rows
            int rowIndex=1;
            for(Customer c:list){
            Row dataRow=sheet.createRow(rowIndex);
            rowIndex++;
            dataRow.createCell(0).setCellValue(c.getId());
                dataRow.createCell(1).setCellValue(c.getFirstName());
                dataRow.createCell(2).setCellValue(c.getLastName());
                dataRow.createCell(3).setCellValue(c.getGender());
                dataRow.createCell(4).setCellValue(c.getCountry());
                dataRow.createCell(5).setCellValue(c.getContactNo());
                dataRow.createCell(6).setCellValue(c.getEmail());
                dataRow.createCell(7).setCellValue(c.getDob());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("failed to import data in excel");
            return null;
        }
        finally {
            workbook.close();
            out.close();
        }
    }
}
