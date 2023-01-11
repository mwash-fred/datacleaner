package co.wmc.datacleaner.Utils;

import io.micrometer.common.util.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

@Component
public class ExcelReader {

    @Value("${file.upload.store}")
    private String uploadLocation;

    public Map<String, Object> disbursemetDateHandler(MultipartFile file) throws IOException {
        File convFile = new File(uploadLocation + file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        Workbook workbook = WorkbookFactory.create(convFile);
        //iterating over rows and columns
        Sheet sheet = workbook.getSheetAt(0);
        Map<String, Object> accountsList= new HashMap<>();
        int count = 0;
        for (Row row : sheet) {
            if (isRowEmpty(row)) continue;
            if (count > 0) {
                Function<Cell, String> stringValues = (cell) -> {
                    if (Objects.isNull(cell)) return null;
                    switch (cell.getCellType()) {
                        case STRING -> {
                            return cell.getStringCellValue();
                        }
                        case NUMERIC -> {
                            int num = (int) cell.getNumericCellValue();
                            return String.valueOf(num);
                        }
                        case BOOLEAN -> {
                            return String.valueOf(cell.getBooleanCellValue());
                        }
                        default -> {
                            return null;
                        }
                    }
                };

                Function<Cell, Double> numericValues = (cell) -> {
                    if (Objects.isNull(cell)) return null;
                    return switch (cell.getCellType()) {
                        case STRING -> Double.valueOf(cell.getStringCellValue());
                        case NUMERIC -> cell.getNumericCellValue();
                        default -> null;
                    };
                };

                Function<Cell, Date> dateValues = (cell) -> {
                    if (Objects.isNull(cell)) return null;
                    if (DateUtil.isCellDateFormatted(cell)) return cell.getDateCellValue();
                    return null;
                };

                String account = stringValues.apply(sheet.getRow(count).getCell(CellReference.convertColStringToIndex("A")));
                Date dibursementDate = dateValues.apply(sheet.getRow(count).getCell(CellReference.convertColStringToIndex("B")));
                accountsList.put(account, dibursementDate);
            }
            count += 1;
        }
        return accountsList;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }

}
