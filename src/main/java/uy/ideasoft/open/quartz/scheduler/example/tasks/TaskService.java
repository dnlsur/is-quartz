package uy.ideasoft.open.quartz.scheduler.example.tasks;

public interface TaskService {

    void setNodeName(String s);

    void run(String id) throws Exception;
}
