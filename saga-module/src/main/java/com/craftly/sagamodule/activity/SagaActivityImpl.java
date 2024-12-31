package com.craftly.sagamodule.activity;

import com.craftly.craftlycore.saga.SagaEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SagaActivityImpl implements SagaActivity {

    @Override
    public void processStepOne(SagaEventDTO sagaEvent) {

    }

    @Override
    public void processStepTwo(SagaEventDTO sagaEvent) {

    }

    @Override
    public void completeSaga(SagaEventDTO sagaEvent) {

    }
}