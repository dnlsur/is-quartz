package uy.ideasoft.open.quartz.scheduler.test

import org.scalatest.{FlatSpec, Matchers}
import uy.ideasoft.open.quartz.scheduler.config.SchedulerProvider
import uy.ideasoft.open.quartz.scheduler.example.tasks.{TaskService, TaskServiceImpl}

class SchedulerTests extends FlatSpec with Matchers {

//    "A SchedulerProvider" should "obtain an scheduler" in {
//      val sp = new SchedulerProvider()
//
//      val sched = sp.getScheduler
//
//      sched should not be null
//    }

    "A TaskServiceImpl" should "run with and without exceptions" in {
      val ts = new TaskServiceImpl

      an [Exception] should be thrownBy ts.run("ex-1")
      ts.run("ok-2")
    }

}
