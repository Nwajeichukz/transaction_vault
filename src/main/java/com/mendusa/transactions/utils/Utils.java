package com.mendusa.transactions.utils;

import com.mendusa.transactions.dto.ByteAttachmentAndFileNameDto;
import com.opencsv.CSVWriter;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Utils {

    public static ByteAttachmentAndFileNameDto writeToCsv(List<?> data) {

        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(byteOutputStream, StandardCharsets.UTF_8));
        ){
            // Write the header
            csvWriter.writeNext(headerOfReceipt(data));

            for (String[] csvData : getFieldsValue(data)) {
                csvWriter.writeNext(csvData);
            }


            // Retrieve the CSV data as a byte array
            byte[] resultByteArray = byteOutputStream.toByteArray();

            String fileName = "data.csv";

            ByteAttachmentAndFileNameDto byteAttachmentAndFileNameDto = new ByteAttachmentAndFileNameDto(fileName, resultByteArray);

            return byteAttachmentAndFileNameDto;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteAttachmentAndFileNameDto("",new byte[]{});

    }

    public static ByteAttachmentAndFileNameDto writeToExcelSheet(List<?> data)  {

        String[] header = headerOfReceipt(data);

        String[][] ListOfValues = getFieldsValue(data);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            //Customize sheet name
            Sheet sheet = workbook.createSheet("Sheet1");
            workbook.setSheetName(workbook.getSheetIndex(sheet), "transactionSheet");

            //write to headers
            int headerNumCell = 0;
            Row headerRow = sheet.createRow(0);

            for (String headerValue : header) headerRow.createCell(headerNumCell++).setCellValue(headerValue);

            int numRow = 1;

            //creating DATA ROWS FOR EACH
            for (String[] dataValues : ListOfValues) {
                Row row = sheet.createRow(numRow++);
                int numCell = 1;
                for (String values : dataValues) {
                    Cell cell = row.createCell(numCell++);
                    cell.setCellValue(values);
                }
            }

            workbook.write(byteArrayOutputStream);

            // Get the resulting byte array
            byte[] resultByteArray = byteArrayOutputStream.toByteArray();

            String fileName = "data.xlsx";

            ByteAttachmentAndFileNameDto byteAttachmentAndFileNameDto = new ByteAttachmentAndFileNameDto(fileName, resultByteArray);

            return byteAttachmentAndFileNameDto;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new ByteAttachmentAndFileNameDto("",new byte[]{});
    }



    public static String[] headerOfReceipt(List<?> list) {

        Field[] declaredFields = list.get(0).getClass().getDeclaredFields();
        final int arraySize = declaredFields.length;
        String[] stringList = new String[arraySize];

        for (int i = 0; i < arraySize; i++) stringList[i] = declaredFields[i].getName();


        return stringList;
    }


    public static String[][] getFieldsValue(List<?> list){
        Class<?> clazz = list.get(0).getClass();

        Field[] declaredFields = clazz.getDeclaredFields();
        int length = declaredFields.length;
        String[][] result = new String[list.size()][length];

        try{
            for (int i = 0; i <  list.size(); i++) {
                Object data = list.get(i);
                String[] valueList = new String[length];
                Field[] fields = data.getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);
                    valueList[j] = String.valueOf(fields[j].get(data));
                }

                result[i] = valueList;
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }


        return result;
    }

}