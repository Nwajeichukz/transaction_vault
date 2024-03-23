package com.mendusa.transactions.utils;

import com.mendusa.transactions.dto.AppResponse;
import com.mendusa.transactions.dto.EmailDto;
import com.mendusa.transactions.service.email.EmailServiceImpl;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrUtils {
    private final EmailServiceImpl emailService;

    public static EmailDto writeToCsv(List<?> data) {

//todo: you didn't even bother to fix the last change request i made

        try (CSVWriter writer = new CSVWriter(new FileWriter("response.csv"))) {

            // Write the header
            writer.writeNext(headerOfReceipt(data));

            for (String[] csvData : getFieldsValue(data)) {

//                String[] eachTemp = csvData.toArray(new String[0]);
                writer.writeNext(csvData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        try (BufferedReader reader = new BufferedReader(new FileReader("response.csv"))) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();


            String line;
            // Read each line from the CSV file
            while ((line = reader.readLine()) != null) {
                // Write the line to the ByteArrayOutputStream
                byteArrayOutputStream.write(line.getBytes());
                // Add a newline character if needed
                byteArrayOutputStream.write(System.lineSeparator().getBytes());
            }

            System.out.println(byteArrayOutputStream);

            // Get the resulting byte array
            byte[] resultByteArray = byteArrayOutputStream.toByteArray();
//todo: you didn't even bother to fix the last change request i made
            // email messenger
            EmailDto emailDto = EmailDto.builder()
                    .recipient("nwajeigoddowell@gmail.com")
                    .subject("RECEIPT")
                    .messageBody("Here is your receipts for your last 10 transactions")
                    .attachment(resultByteArray) //todo: attachment cannot always be singular
                    .build();

            return emailDto;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new EmailDto();
    }

    // todo: why is this return a dto
    // todo: why is the exception not handled internally?
    public static EmailDto writeToExcelSheet(List<?> data) throws IllegalAccessException {

        String[] header = headerOfReceipt(data);

        String[][] ListOfValues = getFieldsValue(data);

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet("Sheet1"); //todo: make sheet name customizable

//           write to headers
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

            // email messenger
// todo: this violates programming laws
            // email messenger
            EmailDto emailDto = EmailDto.builder()
                    .recipient("nwajeigoddowell@gmail.com")
                    .subject("RECEIPT")
                    .messageBody("Here is your receipts for your last 10 transactions")
                    .attachment(resultByteArray) //todo: attachment cannot always be singular
                    .build();


            return emailDto;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new EmailDto();
    }



    public static String[] headerOfReceipt(List<?> list) {

        Field[] declaredFields = list.get(0).getClass().getDeclaredFields();
        final int arraySize = declaredFields.length;
        String[] stringList = new String[arraySize];

        for (int i = 0; i < arraySize; i++) stringList[i] = declaredFields[i].getName();


        return stringList;
    }


    public static String[][] getFieldsValue(List<?> list) throws IllegalAccessException {
        Class<?> clazz = list.get(0).getClass();

        Field[] declaredFields = clazz.getDeclaredFields();
        int length = declaredFields.length;
        String[][] result = new String[list.size()][length];

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

        return result;

    }


}
