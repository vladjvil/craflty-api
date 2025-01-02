package com.craftly.craftlyauth.messaging.producer;

import com.craftly.craftlycore.messaging.KafkaTopics;
import com.craftly.craftlycore.models.auth.mail.ForgotPasswordMessage;
import com.craftly.craftlycore.models.auth.mail.NewEmailVerificationMessage;

import com.craftly.craftlycore.models.auth.mail.VerificationMessage;
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
        kafkaTemplate.send(KafkaTopics.MAIL_SENT_VERIFICATION_MESSAGE, message);
    }

    public void executeSendForgotPasswordMessage(ForgotPasswordMessage forgotPasswordMessage) {
        String message = gson.toJson(forgotPasswordMessage);
        kafkaTemplate.send(KafkaTopics.MAIL_SENT_FORGOT_PASSWORD_MESSAGE, message);
    }

    public void executeSendNewEmailVerificationMessage(NewEmailVerificationMessage verificationMessage) {
        String message = gson.toJson(verificationMessage);
        kafkaTemplate.send(KafkaTopics.MAIL_SENT_NEW_VERIFICATION_MESSAGE, message);
    }

}