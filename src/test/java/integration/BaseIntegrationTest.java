//package integration;
//
//import org.junit.After;
//import org.junit.Before;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//
//import javax.sql.DataSource;
//
//@ContextConfiguration(classes = {TestConfig.class})
//@ActiveProfiles("unit-test")
//public class BaseIntegrationTest {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Before
//    public void init() {
//        initialise();
//    }
//
//    @After
//    public void after() {
//        scrap();
//    }
//
//    public void initialise() {
//        JdbcTemplate template = new JdbcTemplate(dataSource);
//        template.execute("CREATE TABLE user(" +
//                "id int NOT NULL AUTO_INCREMENT, " +
//                "uuid character(36) NOT NULL UNIQUE, " +
//                "username character(25) NOT NULL UNIQUE, " +
//                "name character(25), " +
//                "surname character(25), " +
//                "email varchar(50), " +
//                "psswd_salt character(25) NOT NULL, " +
//                "type varchar(15) NOT NULL, " +
//                "PRIMARY KEY(id) " +
//                ")");
//    }
//
//    protected void scrap() {
//        ((EmbeddedDatabase)dataSource).shutdown();
//    }
//
//}
