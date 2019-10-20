package uy.ideasoft.open.quartz.scheduler.example.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uy.ideasoft.open.quartz.scheduler.example.tasks.TaskService;

@DisallowConcurrentExecution
public class TaskJob implements Job {

    public static TaskService taskService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            String id = jobExecutionContext.getJobDetail().getKey().getName();
            logger.trace("TaskJob: " + id);
            taskService.run(id);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
