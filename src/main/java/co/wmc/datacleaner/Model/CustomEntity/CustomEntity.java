package co.wmc.datacleaner.Model.CustomEntity;

import co.wmc.datacleaner.Components.JdbcConnector;
import co.wmc.datacleaner.Utils.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j @Component
public class CustomEntity {

    @Autowired
    private JdbcConnector connector;

    @Value("${custom.sql.disbursementdate}")
    private String disbursementDateQuery;

    @Value("${custom.sql.accountsn}")
    private String selectAccountSn;

    @Value("{custom.sql.loanAccountSn}")
    private String updateLoanQuery;

    public List<String> getAccoutSn(String accountReference) throws SQLException {
        ResultSet resultSet = connector.connection(selectAccountSn, QueryType.SELECT, true, List.of(accountReference));
        List<String> results = new ArrayList<>();
        while (resultSet.next()){
            //log.info("Result {}", resultSet.getString(1));
            results.add(resultSet.getString(1));
        }
        return results;
    }

    public void updateDisbursementDate(int accountSn, Date disbursementDate) throws SQLException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(disbursementDate);
        connector.connection(disbursementDateQuery, QueryType.UPDATE, true, List.of(dateString,String.valueOf(accountSn)));
    }

    public void updateLoansWithAccountSn(int loanSn, int accountSn) throws SQLException {
        connector.connection(updateLoanQuery, QueryType.UPDATE, true,List.of(String.valueOf(accountSn), String.valueOf(loanSn)));
    }


}
