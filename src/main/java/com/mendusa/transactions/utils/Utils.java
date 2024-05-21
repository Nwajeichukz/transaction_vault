package com.mendusa.transactions.utils;

import com.mendusa.transactions.dto.PaymentRateCount;
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
import java.util.List;

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


    public static List<PaymentRateCount> getMethodSuccessRate(List<Tuple> objectList, String paymentMethod) {
        List<PaymentRateCount> rateCountList = new ArrayList<>();


        int total = 0;
        int success = 0;

        for (Tuple transaction : objectList) {
            String response = transaction.get("responseCode", String.class);
            String getPaymentMethod = transaction.get("paymentMethod", String.class);
            Long totalSuccess = transaction.get("totalCount", Long.class);

            if (getPaymentMethod.equalsIgnoreCase(paymentMethod)) {
                total += totalSuccess;
                if ("00".equals(response)) success += totalSuccess;
            }
        }

        PaymentRateCount paymentRateCount = new PaymentRateCount(paymentMethod, total, success);
        rateCountList.add(paymentRateCount);

        log.info("--> total total {}", total);
        log.info("--> total success {}", success);
        return rateCountList;
    }


    public static List<PaymentRateCount> getProviderSuccess(List<Tuple> objectList, String provider) {
        List<PaymentRateCount> rateCountList = new ArrayList<>();

        int total = 0;
        int success = 0;

        for (Tuple transaction : objectList) {
            String response = transaction.get("responseCode", String.class);
            String getProvider = transaction.get("provider", String.class);
            Long totalSuccess = transaction.get("totalCount", Long.class);

            if (getProvider == null) getProvider = "providerNull";

            if (getProvider.equalsIgnoreCase(provider)) {
                total += totalSuccess;
                if ("00".equals(response)) success += totalSuccess;
            }


        }
        PaymentRateCount paymentRateCount = new PaymentRateCount(provider, total, success);
        rateCountList.add(paymentRateCount);

        return rateCountList;
    }


}