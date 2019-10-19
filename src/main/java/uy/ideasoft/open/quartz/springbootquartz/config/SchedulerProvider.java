package uy.ideasoft.open.quartz.springbootquartz.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.postgresql.ds.PGPoolingDataSource;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Properties;

public class SchedulerProvider {

  public static final String CHANGELOG_MASTER = "db/changelog/master.xml";


  public Properties quartzProperties() {

    Properties properties = new Properties();
    try {
      properties.load(getClass().getClassLoader().getResourceAsStream("quartz.properties"));
    } catch (IOException e) {
//      Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
    }
    return properties;
  }


  public Scheduler getScheduler() throws Exception {
    StdSchedulerFactory sf = new StdSchedulerFactory();
    Properties props = quartzProperties();
    sf.initialize(props);
    Scheduler scheduler = sf.getScheduler();
    return scheduler;
  }

  public DataSource dataSource() {
    PGPoolingDataSource source = new PGPoolingDataSource();
    //source.setDataSourceName("Quartz Datasource");
    source.setServerName("localhost");
    source.setDatabaseName("quartz");
    source.setUser("postgres");
    source.setPassword("postgres");
    source.setMaxConnections(10);
    return source;
  }

  public Connection connection(DataSource source) {
    Connection conn = null;
    try {
      conn = source.getConnection();
      return conn;
      // use connection
    } catch (SQLException e) {
      e.printStackTrace();
      throw new NoSuchElementException(e.getMessage());
      // log error
    }
  }

  public void runLiquibase() {
    Liquibase liquibase = null;
    Connection c = null;
    try {
//    c = DriverManager.getConnection(DataSources.PROPERTIES.getProperty("jdbc.url"),
//        DataSources.PROPERTIES.getProperty("jdbc.username"),
//        DataSources.PROPERTIES.getProperty("jdbc.password"));
      c = connection(dataSource());
      Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));
      liquibase = new Liquibase(CHANGELOG_MASTER, new ClassLoaderResourceAccessor(), database);
      liquibase.update("quartz_tables");
    } catch (LiquibaseException e) {
      e.printStackTrace();
      throw new NoSuchElementException(e.getMessage());
    } finally {
      if (c != null) {
        try {
          c.rollback();
          c.close();
        } catch (SQLException e) {
          //nothing to do
        }
      }
    }
  }
}
