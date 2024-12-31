package com.craftly.sagamodule.workflow;

import com.craftly.craftlycore.saga.SagaEventDTO;
import com.craftly.sagamodule.activity.SagaActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class SagaWorkflowImpl implements SagaWorkflow {

    private final SagaActivity sagaActivity = Workflow.newActivityStub(SagaActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofMinutes(5))
                    .build());

    @Override
    public void executeSaga(SagaEventDTO sagaEvent) {
        Workflow.getLogger(SagaWorkflowImpl.class).info("Executing saga for event: {}", sagaEvent);
        sagaActivity.processStepOne(sagaEvent);
        sagaActivity.processStepTwo(sagaEvent);
        sagaActivity.completeSaga(sagaEvent);
    }
}
