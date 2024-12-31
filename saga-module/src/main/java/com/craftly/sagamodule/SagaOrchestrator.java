package com.craftly.sagamodule;

import com.craftly.craftlycore.saga.SagaEventDTO;
import com.craftly.sagamodule.workflow.SagaWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SagaOrchestrator {

    private final WorkflowClient workflowClient;

    public void startSaga(SagaEventDTO sagaEvent) {
        SagaWorkflow sagaWorkflow = workflowClient.newWorkflowStub(SagaWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("SAGA_TASK_QUEUE")
                        .build());
        sagaWorkflow.executeSaga(sagaEvent);
    }
}