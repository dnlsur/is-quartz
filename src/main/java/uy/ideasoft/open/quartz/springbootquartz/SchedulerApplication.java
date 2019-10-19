package uy.ideasoft.open.quartz.springbootquartz;

import org.quartz.Scheduler;
import uy.ideasoft.open.quartz.springbootquartz.config.SchedulerProvider;

import javax.sql.DataSource;


public class SchedulerApplication {

  public static void main(String[] args) throws Exception {
    SchedulerProvider provider = new SchedulerProvider();
    DataSource ds = provider.dataSource();
    provider.runLiquibase();

    try {
      Scheduler sched = provider.getScheduler();

    } catch (Exception e) {
      throw e;
    }



  }

}
