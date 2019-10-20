package uy.ideasoft.open.quartz.scheduler.example;

import org.quartz.Scheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uy.ideasoft.open.quartz.scheduler.config.SchedulerProvider;
import uy.ideasoft.open.quartz.scheduler.example.jobs.TaskJob;
import uy.ideasoft.open.quartz.scheduler.example.jobs.TaskJobsListener;
import uy.ideasoft.open.quartz.scheduler.example.tasks.TaskService;
import uy.ideasoft.open.quartz.scheduler.example.tasks.TaskServiceImpl;

import javax.sql.DataSource;


public class SchedulerApplication {
  private static final Logger logger = LoggerFactory.getLogger(SchedulerApplication.class);

  public static void main(String[] args) throws Exception {
    // Set up a simple configuration that logs on the console.
    SchedulerProvider provider = new SchedulerProvider();
    DataSource ds = provider.dataSource();
    provider.runLiquibase();

    try {
      Scheduler sched = provider.getScheduler();
      sched.start();

      TaskService ts = new TaskServiceImpl();
      if (args.length > 0) {
        ts.setNodeName(args[0]);
      }
      JobsService js = new JobsService(sched);
      TaskJob.taskService = ts;

      sched.getListenerManager().addJobListener(new TaskJobsListener(), GroupMatcher.groupEquals("myGroup"));
      js.addNewJobs(20);
      js.addNewJob();
      js.addNewJob();
      js.addNewJob();

    } catch (Exception e) {
      logger.error("Algo salió mal.");
      throw e;
    }



  }

}
