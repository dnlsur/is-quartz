package uy.ideasoft.open.quartz.springbootquartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob1 implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            String id = jobExecutionContext.getJobDetail().getKey().getName();
            //testService.run(id);
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}
