//package integration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
//import javax.sql.DataSource;
//
//@Configuration
//@Profile("unit-test")
//public class TestConfig {
//    @Bean
//    public DataSource getDataSource() {
//        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
//        EmbeddedDatabase db = dbBuilder.setType(EmbeddedDatabaseType.H2)
//                .build();
//        return db;
//    }
//}
