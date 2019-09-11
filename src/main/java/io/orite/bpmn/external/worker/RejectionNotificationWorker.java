package io.orite.bpmn.external.worker;

import org.camunda.bpm.client.ExternalTaskClient;

import java.util.Collections;
import java.util.Date;

public class RejectionNotificationWorker {

    public static void main(final String[] args) {
        // bootstrap the client
        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/rest")
                .asyncResponseTimeout(20000)
                .lockDuration(10000)
                .maxTasks(1)
                .build();

        // subscribe to the topic
        client.subscribe("notification").handler((externalTask, externalTaskService) -> {
            String content = externalTask.getVariable("content");
            System.err.println("Sorry, your tweet has been rejected: " + content);
            externalTaskService.complete(externalTask, Collections.singletonMap("notficationTimestamp", new Date()));
        }).open();
    }
}
