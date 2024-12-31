package com.craftly.sagamodule.workflow;

import com.craftly.craftlycore.saga.SagaEventDTO;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SagaWorkflow {

    @WorkflowMethod
    void executeSaga(SagaEventDTO sagaEvent);
}