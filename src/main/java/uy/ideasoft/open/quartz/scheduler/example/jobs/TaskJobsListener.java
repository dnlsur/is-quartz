package uy.ideasoft.open.quartz.scheduler.example.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskJobsListener implements JobListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getName() {
        return "Quartz Test Listener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        logger.info("Quartz Job to be executed " + context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        logger.info("Quartz Job execution vetoed " + context.getJobDetail().getKey().getName());
    }

    @Override
    public void jobWasExecuted(
        JobExecutionContext context, JobExecutionException jobException) {
        logger.info("Quartz Job was executed " + context.getJobDetail().getKey().getName() +
            (jobException != null ? ", with error" : "")
        );
    }
}
