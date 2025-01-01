package com.craftly.sagamodule.activity;

import com.craftly.craftlycore.models.saga.SagaEventDTO;

public interface SagaActivity {

    void processStepOne(SagaEventDTO sagaEvent);

    void processStepTwo(SagaEventDTO sagaEvent);

    void completeSaga(SagaEventDTO sagaEvent);
}
