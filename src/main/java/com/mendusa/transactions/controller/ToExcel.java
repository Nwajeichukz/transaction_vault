package com.mendusa.transactions.controller;
//
public class ToExcel {
////}
//package com.line.medusa_merchant.export;
//
//import com.google.gson.Gson;
//import com.line.medusa_merchant.exceptions.GeneralException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.jetbrains.annotations.NotNull;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
//@Slf4j
//@Service
//public class ToExcel {
//
//    Map<Integer, String> classFields;
//    CellStyle amountStyle;
//    CellStyle dateStyle;
//    Workbook workbook;
//    Sheet sheet;
//    DataFormat format;
//
//    public ToExcel(Class<?> aClass) {
//        this.classFields = getClassFields(aClass);
//    }
//
//    public ToExcel() {
//
//    }
//
//
//    /**
//     * This public method initiate the creation of the Excel Sheet
//     */
//
//    public byte[] writeExcel(List<?> objectList, String table) {
//        createExcelVariables(table);
//
//        int rowCount = 0;
//
//        createHeaderRow(sheet, rowCount);
//
//        for (int i = 0; i < objectList.size(); i++) {
//            Row row = sheet.createRow(++rowCount);
//            JSONObject jsonObject = getObjectAsJson(objectList.get(i));
//            writeBook(jsonObject, row, rowCount);
//        }
//
//        return createExcelByte();
//    }
//
//
//    /**
//     * Concatenates passed in value plus date & export keyword
//     * to generate Excel File name
//     */
//    public String getFileName(String baseName) {
//        baseName = baseName.concat("_Export");
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//        String dateTimeInfo = dateFormat.format(new Date());
//        return baseName.concat(String.format("_%s.xlsx", dateTimeInfo));
//    }
//
//
//    /**
//     * Generate the Excel file to specified filepath
//     */
//    protected String createExcelFile(String excelFilePath) {
//        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
//            workbook.write(outputStream);
//            return excelFilePath;
//        } catch (Exception e) {
//            throw new GeneralException("Error Creating file {} " + e.getMessage());
//        }
//    }
//
//    protected byte[] createExcelByte() {
//
//        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
//            this.workbook.write(baos);
//            return baos.toByteArray();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//    public String createFile(MultipartFile file) {
//        String filePath = "";
//        try {
//            String uploadDir = System.getProperty("user.dir");
//            log.info("user dir:{}", uploadDir);
//            filePath = uploadDir + File.separator + StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
//            Path copyLocation = Paths
//                    .get(filePath);
//
//            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
//
//
//        } catch (Exception e) {
//            throw new RuntimeException("Could not store file " + file.getOriginalFilename()
//                    + ". Please try again!");
//        }
//        return filePath;
//    }
//
//    /**
//     * Generate the necessary excel variables
//     */
//    protected void createExcelVariables(String table) {
//        this.workbook = new XSSFWorkbook();
//        this.sheet = workbook.createSheet(table);
//        this.format = workbook.createDataFormat();
//
//        this.amountStyle = createAmountStyle(sheet.getWorkbook(), format);
//        this.dateStyle = createDateStyle(sheet.getWorkbook());
//    }
//
//
//    /**
//     * This Creates Each Column in the Excel and populates the Value
//     */
//    public void writeBook(JSONObject jsonObject, Row row, int rowCount) {
//
//        Cell cell = row.createCell(0);
//        cell.setCellValue(rowCount);
//
//        for (Map.Entry<Integer, String> entry : classFields.entrySet()) {
//            cell = row.createCell(entry.getKey());
//            if (jsonObject.has(entry.getValue())) {
//
//                String entryValue = entry.getValue();
//                if (entryValue.equals("amount") || entryValue.equals("transactionFee")
//                        || entryValue.equals("settlementImpact") || entryValue.equals("totalTransactions")) {
//                    setAmount(getObjectAsString(jsonObject.get(entry.getValue())), cell, amountStyle);
//                } else if (entry.getValue().equals("transactionDate")) {
//                    setDate(getObjectAsString(jsonObject.get(entry.getValue())), cell, dateStyle);
//                } else {
//                    cell.setCellValue(getObjectAsString(jsonObject.get(entry.getValue())));
//                }
//
//            } else {
//                cell.setCellValue("");
//            }
//        }
//    }
//
//
//    /**
//     * Get Object as a String, checkmate null pointer Exception
//     */
//    private String getObjectAsString(Object o) {
//        if (Objects.isNull(o)) {
//            return "";
//        }
//        return o.toString();
//    }
//
//    /**
//     * Converts the Object to a JSONObject
//     */
//    public JSONObject getObjectAsJson(Object object) {
//        String jsonInString = new Gson().toJson(object);
//        return new JSONObject(jsonInString);
//    }
//
//
//    /**
//     * Defines the Header Styles and sets up the header
//     */
//    public void createHeaderRow(Sheet sheet, int rowNumber) {
//        CellStyle cellStyle = getHeaderCellStyle(sheet);
//
//        setUpHeaderCells(sheet, cellStyle, rowNumber);
//    }
//
//    @NotNull
//    protected CellStyle getHeaderCellStyle(Sheet sheet) {
//        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
//        Font font = sheet.getWorkbook().createFont();
//        font.setBold(true);
//        font.setFontHeightInPoints((short) 11);
//        cellStyle.setFont(font);
//        return cellStyle;
//    }
//
//
//    /**
//     * Sets up the Header of the Excel sheet using the Class fields
//     */
//    private void setUpHeaderCells(Sheet sheet, CellStyle headerStyle, int rowNumber) {
//        Row row = sheet.createRow(rowNumber);
//
//        Cell cellNo = row.createCell(0);
//        cellNo.setCellStyle(headerStyle);
//        cellNo.setCellValue("No.");
//
//        for (Map.Entry<Integer, String> entry : classFields.entrySet()) {
//            Cell cell = row.createCell(entry.getKey());
//            cell.setCellStyle(headerStyle);
//            cell.setCellValue(formatHeader(entry.getValue()));
//        }
//    }
//
//
//    /**
//     * format Class field to look readable in Excel header
//     * from e.g maskedPan to Masked Pan
//     */
//    private String formatHeader(String fieldName) {
//        String[] split = fieldName.split("(?=\\p{Upper})");
//
//        //check if field is all caps, ignore all caps field
//        if (fieldName.length() != split.length) {
//
//            StringBuilder header = new StringBuilder();
//
//            for (int i = 0; i < split.length; i++) {
//                if (i == 0) {
//                    header.append(capitalize(split[i]));
//                } else {
//                    header.append(" ").append(split[i]);
//                }
//            }
//            return header.toString();
//        } else {
//            return fieldName;
//        }
//    }
//
//
//    /**
//     * Capitalize the first Letter of the passed in String
//     */
//    private static String capitalize(String str) {
//        if (Objects.isNull(str) || str.isEmpty()) {
//            return str;
//        }
//
//        return for3Line(str.substring(0, 1).toUpperCase() + str.substring(1));
//    }
//
//
//    /**
//     * Properly format Company Name
//     */
//    private static String for3Line(String name) {
//        if (name.equals("_3line")) {
//            return "3LINE";
//        }
//        return name;
//    }
//
//
//    /**
//     * Get all fields of a class and returns an
//     * Hashmap containing the index and the field name
//     */
//    private Map<Integer, String> getClassFields(Class<?> tClass) {
//        Map<Integer, String> fieldsMap = new HashMap<>();
//        Field[] allFields = tClass.getDeclaredFields();
//        int count = 1;
//
//        for (Field field : allFields) {
//            fieldsMap.put(count, field.getName());
//            count++;
//        }
//
//        return fieldsMap;
//    }
//
//
//    /**
//     * Create a style for Date Column/Cell
//     */
//    private CellStyle createDateStyle(Workbook workbook) {
//        CellStyle dateCellStyle = workbook.createCellStyle();
//        CreationHelper creationHelper = workbook.getCreationHelper();
//        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
//        return dateCellStyle;
//    }
//
//
//    /**
//     * Create a style for Amount Column/Cell
//     */
//    CellStyle createAmountStyle(Workbook workbook, DataFormat format) {
//        CellStyle amountCellStyle = workbook.createCellStyle();
//        amountCellStyle.setAlignment(HorizontalAlignment.RIGHT);
//        amountCellStyle.setDataFormat(format.getFormat("#,#0.0"));
//        return amountCellStyle;
//    }
//
//
//    /**
//     * Format Cell with Date style
//     */
//    private void setDate(String date, Cell cell, CellStyle style) {
//        if (Objects.nonNull(date)) {
//            cell.setCellValue(date);
//            cell.setCellStyle(style);
//            return;
//        }
//        cell.setCellValue("");
//    }
//
//
//    /**
//     * Format Cell with Amount style
//     */
//    private void setAmount(String amount, Cell cell, CellStyle style) {
//        if (Objects.nonNull(amount) && !amount.isEmpty()) {
//            cell.setCellValue(amount);
//            cell.setCellStyle(style);
//            return;
//        }
//        cell.setCellValue("");
//    }
}