package net.moewes.cloud;

import java.util.concurrent.Future;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

@Singleton
public class MyService {

  @Resource
  private ManagedExecutorService managedExecutorService;
  @Inject
  private Instance<MyTask> myTaskInstance;

  private MyTask myTask;
  private boolean isrunning;
  private Future<?> submit;

  public void start() {
    if (!isrunning) {
      myTask = myTaskInstance.get();
      submit = this.managedExecutorService.submit(myTask);
      isrunning =true;
    }
  }

  public void stop() {
    myTask.stop();
    submit.cancel(true);
    isrunning = false;
  }
}
