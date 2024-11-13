package school.sptech;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class DBConnectionProvider {

    private final DataSource dataSource;

    public DBConnectionProvider() {


        String dbUrl = "jdbc:mysql://54.173.93.245/discharge";
        String dbUser = "root";
        String dbPassword = "123";

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl); //"jdbc:mysql://IPV4 publico/discharge"
        basicDataSource.setUsername(dbUser);
        basicDataSource.setPassword(dbPassword);
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        this.dataSource = basicDataSource;
    }

    public JdbcTemplate getConnection() {
        return new JdbcTemplate(dataSource);
    }
}
