package com.mendusa.transactions.utils;

import com.mendusa.transactions.dto.MethodDto;
import com.mendusa.transactions.dto.PaymentRateCount;
import com.mendusa.transactions.dto.ProviderDto;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class Utils {

    public static byte[] writeToCsv(List<?> data) {


        try (ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
             CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(byteOutputStream, StandardCharsets.UTF_8))
        ) {

            // Write the header
            csvWriter.writeNext(headerOfReceipt(data));


            for (String[] csvData : getFieldsValue(data)) {
                csvWriter.writeNext(csvData);
            }

            // Retrieve the CSV data as a byte array
            return byteOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new byte[]{};

    }

    public static byte[] writeToExcelSheet(List<?> data) {

        String[] header = headerOfReceipt(data);

        String[][] listOfValues = getFieldsValue(data);

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
            for (String[] dataValues : listOfValues) {
                Row row = sheet.createRow(numRow++);
                int numCell = 0;
                for (String values : dataValues) {
                    Cell cell = row.createCell(numCell++);
                    cell.setCellValue(values);
                }
            }

            workbook.write(byteArrayOutputStream);

            // Get the resulting byte array
            return byteArrayOutputStream.toByteArray();

        } catch (IOException ex) {
            log.error("An error occurred while writing to file", ex);
        }

        return new byte[]{};
    }


    public static String[] headerOfReceipt(List<?> list) {

        Field[] declaredFields = list.get(0).getClass().getDeclaredFields();
        final int arraySize = declaredFields.length;
        String[] stringList = new String[arraySize];

        for (int i = 0; i < arraySize; i++) stringList[i] = declaredFields[i].getName();


        return stringList;
    }


    public static String[][] getFieldsValue(List<?> list) {
        Class<?> clazz = list.get(0).getClass();

        Field[] declaredFields = clazz.getDeclaredFields();
        int length = declaredFields.length;
        String[][] result = new String[list.size()][length];

        try {
            for (int i = 0; i < list.size(); i++) {
                Object data = list.get(i);
                String[] valueList = new String[length];
                Field[] fields = data.getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; j++) {
                    fields[j].setAccessible(true);
                    valueList[j] = String.valueOf(fields[j].get(data));
                }

                result[i] = valueList;
            }
        } catch (IllegalAccessException e) {
            log.error("An error occurred while getting object fields", e);
        }


        return result;
    }

    public static Map<String, PaymentRateCount> getMethodSuccessRate(List<MethodDto> methodTransactions) {
        Map<String, PaymentRateCount> rateCountMap = new HashMap<>();

        for (MethodDto transaction : methodTransactions) {
            final PaymentRateCount rate = rateCountMap.getOrDefault(transaction.getMethod(), new PaymentRateCount(
                    transaction.getMethod(), 0, 0));

            if ("00".equals(transaction.getResponseCode())) rate.setSuccess(transaction.getCount());

            rate.setTotal(rate.getTotal() + transaction.getCount());

            rateCountMap.put(transaction.getMethod(), rate);
        }

        return rateCountMap;
    }


    public static Map<String, PaymentRateCount> getProviderSuccessRate(List<ProviderDto> providerTransactions) {
        Map<String, PaymentRateCount> rateCountMap = new HashMap<>();

        for (ProviderDto transaction : providerTransactions) {
            final PaymentRateCount rate = rateCountMap.getOrDefault(transaction.getProvider(), new PaymentRateCount(
                    transaction.getProvider(), 0, 0));

            if ("00".equals(transaction.getResponseCode())) rate.setSuccess(transaction.getCount());

            rate.setTotal(rate.getTotal() + transaction.getCount());

            rateCountMap.put(transaction.getProvider(), rate);
        }

        return rateCountMap;
    }
}