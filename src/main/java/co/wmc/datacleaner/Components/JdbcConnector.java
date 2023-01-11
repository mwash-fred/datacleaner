package co.wmc.datacleaner.Components;

import co.wmc.datacleaner.Utils.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component @Slf4j
public class JdbcConnector {

    @Autowired
    private DataSource dataSource;

    public ResultSet connection(String query, QueryType queryType, boolean withWhere, List<String> params) throws SQLException {
        Connection conn = dataSource.getConnection();
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet rs = null;
        if(withWhere){
            int count = 1;
            for(String param :  params){
                statement.setString(count, param);
                count++;
            }
        }
        switch(queryType){
            case UPDATE:
                log.info("Updating {}", params);
                statement.executeUpdate();
                conn.commit();
            case DELETE:
                statement.execute();
            case INSERT:
                statement.execute();
            default:
                rs = statement.executeQuery();
        }
        conn.close();
        return rs;
    }
}
