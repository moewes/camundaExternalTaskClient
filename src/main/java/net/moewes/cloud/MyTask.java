package net.moewes.cloud;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.camunda.bpm.client.ExternalTaskClient;

public class MyTask implements Runnable {

  Logger log = Logger.getLogger(MyTask.class.getName());

  ExternalTaskClient client;

  @Override
  public void run() {
    log.info("I'm running");

    client = ExternalTaskClient.create()
        .baseUrl("http://localhost:8080/camunda/api")
        .asyncResponseTimeout(1000)
        .build();

    // subscribe to the topic
    client.subscribe("MyTopic")
        .handler((externalTask, externalTaskService) -> {

          log.info("Start Task");

          Map<String, Object> variables = new HashMap<>();
          /*
          // add the invoice object and its id to a map
          Map<String, Object> variables = new HashMap<>();
          variables.put("invoiceId", invoice.id);
          variables.put("invoice", invoiceValue);
          */
          externalTaskService.complete(externalTask, variables);

          log.info("Task completed");

        }).open();
  }

  public void stop() {

    client.stop();
    log.info("Service stoppend");
  }
}
