package co.wmc.datacleaner.Resources;

import co.wmc.datacleaner.DTO.CustomResponse;
import co.wmc.datacleaner.Model.CustomEntity.CustomEntity;
import co.wmc.datacleaner.Utils.ExcelReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/cleaner")
@CrossOrigin @Slf4j
public class ApiEndpoints {
    @Autowired
    private CustomEntity customEntity;

    @Autowired
    private ExcelReader excelReader;

    @GetMapping("disbursemet-dates")
    public ResponseEntity<?> disbursementDates() throws SQLException {
        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        //.data(customEntity.getAccoutSn())
                        .message("Successflly Loaded")
                        .build()
        );
    }

    @PostMapping("disbursement-upload")
    public ResponseEntity<?> disbursementDates(@RequestParam("file")MultipartFile file) throws SQLException, IOException {
        Map<String, Object> data = excelReader.disbursemetDateHandler(file);
        for (String key : data.keySet()) {
            //Get Account Sn
            int accountSn = Integer.parseInt(String.valueOf(customEntity.getAccoutSn(key).stream().findFirst().get()));
            //Update Accounts
            customEntity.updateDisbursementDate(accountSn, (Date) data.get(key));
        }

        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("Successflly Loaded")
                        .build()
        );
    }

    @PostMapping("loan-account-sn")
    public ResponseEntity<?> updateLoanWithAcountSn(@RequestParam("file") MultipartFile file) throws SQLException, IOException {
        Map<String, Object> data = excelReader.disbursemetDateHandler(file);
        for (String key : data.keySet()) {
            //Update Accounts
            customEntity.updateLoansWithAccountSn(Integer.parseInt(key), Integer.parseInt((String) data.get(key)));
        }

        return ResponseEntity.ok().body(
                CustomResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .data(data)
                        .message("Successflly Loaded")
                        .build()
        );
    }
}
