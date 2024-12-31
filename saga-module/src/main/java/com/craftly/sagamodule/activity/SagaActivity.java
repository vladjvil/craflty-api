package com.craftly.sagamodule.activity;

import com.craftly.craftlycore.saga.SagaEventDTO;

public interface SagaActivity {

    void processStepOne(SagaEventDTO sagaEvent);

    void processStepTwo(SagaEventDTO sagaEvent);

    void completeSaga(SagaEventDTO sagaEvent);
}
