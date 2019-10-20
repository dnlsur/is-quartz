package uy.ideasoft.open.quartz.scheduler.example;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


import org.quartz.JobDetail;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.utils.Key;
import uy.ideasoft.open.quartz.scheduler.example.jobs.TaskJob;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


public class JobsService {

  private final String groupName = "myGroup";

  private final Scheduler scheduler;

  public JobsService(Scheduler scheduler) {

    this.scheduler = scheduler;
  }

  public List<String> addNewJobs(int jobs) throws SchedulerException {
    LinkedList<String> list = new LinkedList<>();
    for (int i = 0; i < jobs; i++) {
      list.add(addNewJob());
    }
    return list.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
  }

  public String addNewJob() throws SchedulerException {
    UUID uuid = UUID.randomUUID();
    String prefix = (uuid.getLeastSignificantBits() % 2 == 0) ? "ex-" : "ok-";
    String id = prefix + uuid.toString();

    JobDetail job =
        newJob(TaskJob.class)
            .withIdentity(id, groupName)
            .requestRecovery(true)
            .build();

    Trigger trigger =
        newTrigger()
            .withIdentity(id + "-trigger", groupName)
            .startNow()
            .withSchedule(
                simpleSchedule().withIntervalInSeconds(3)
            )
            .build();

    scheduler.scheduleJob(job, trigger);

    return id;
  }

  public boolean deleteJob(String id) throws SchedulerException {
    JobKey jobKey = new JobKey(id, groupName);
    return scheduler.deleteJob(jobKey);
  }

  public List<String> getJobs() throws SchedulerException {
    return scheduler
        .getJobKeys(GroupMatcher.jobGroupEquals(groupName))
        .stream()
        .map(Key::getName)
        .sorted(Comparator.naturalOrder())
        .collect(Collectors.toList());
  }

  /**
   * Check realization was inspired by https://stackoverflow.com/a/31479434/285571
   */
//  public List<JobStatus> getJobsStatuses() throws SchedulerException {
//    LinkedList<JobStatus> list = new LinkedList<>();
//    for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
//      JobDetail jobDetail = scheduler.getJobDetail(jobKey);
//      List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
//      for (Trigger trigger : triggers) {
//        Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//        if (Trigger.TriggerState.COMPLETE.equals(triggerState)) {
//          list.add(new JobStatus(jobKey.getName(), true));
//        } else {
//          list.add(new JobStatus(jobKey.getName(), false));
//        }
//      }
//    }
//    list.sort(Comparator.comparing(o -> o.id));
//    return list;
//  }
}
