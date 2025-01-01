package com.craftly.craftlyauth.messaging.producer;

import com.craftly.craftlycore.exception.auth.MessageSerializationException;
import com.craftly.craftlycore.models.auth.mail.ForgotPasswordMessage;
import com.craftly.craftlycore.models.auth.mail.NewEmailVerificationMessage;
import com.craftly.craftlycore.models.auth.mail.VerificationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailActionsProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson = new GsonBuilder().create();

    public void executeSendVerificationMessage(VerificationMessage verificationMessage) {
        String message = gson.toJson(verificationMessage);
        kafkaTemplate.send("sendVerificationMessage", message);
    }

    public void executeSendForgotPasswordMessage(ForgotPasswordMessage forgotPasswordMessage) {
        String message = gson.toJson(forgotPasswordMessage);
        kafkaTemplate.send("sendForgotPasswordMessage", message);
    }

    public void executeSendNewEmailVerificationMessage(NewEmailVerificationMessage verificationMessage) {
        String message = gson.toJson(verificationMessage);
        kafkaTemplate.send("sendNewEmailVerificationMessage", message);
    }

}