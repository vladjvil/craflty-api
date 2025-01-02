package com.craftly.craftlyauth.messaging.producer;

import com.craftly.craftlycore.messaging.KafkaTopics;
import com.craftly.craftlycore.models.auth.mail.UserPasswordResetMessage;
import com.craftly.craftlycore.models.user.UserCreateDTO;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserActionsProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson = new GsonBuilder().create();

    public void executeCreatingUser(UserCreateDTO userCreateDTO) {
        String message = gson.toJson(userCreateDTO);
        kafkaTemplate.send(KafkaTopics.USER_CREATED, message);
    }

    public void executeResetPassword(UserPasswordResetMessage userPasswordResetMessage) {
        String message = gson.toJson(userPasswordResetMessage);
        kafkaTemplate.send(KafkaTopics.USER_RESET_PASSWORD, message);
    }

    public void executeChangeEmail() {
    }

    public void executeConfirmByEmail(String email) {
        kafkaTemplate.send(KafkaTopics.USER_CONFIRMED_BY_EMAIL, email);
    }

    public void executeUse2FA(String email) {
        kafkaTemplate.send(KafkaTopics.USER_USED_2FA, email);
    }
}