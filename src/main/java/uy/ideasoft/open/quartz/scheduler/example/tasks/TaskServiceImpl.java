package uy.ideasoft.open.quartz.scheduler.example.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TaskServiceImpl implements TaskService {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private String nodeName;

  public void setNodeName(String s) {
    nodeName = s;
  }

  public void run(String id) throws Exception {
    logger.info("Running job on " + nodeName + ", job id " + id);
    if (id.startsWith("ex")) {
      throw new Exception("Failed");
    }
    try {
      Thread.sleep(TimeUnit.SECONDS.toMillis(2));
    } catch (InterruptedException e) {
      logger.error("Error", e);
    }
    logger.info("Completed Task for job id " + id);
  }
}

