package com.craftly.craftlycore.messaging;

public interface KafkaTopics {
    String USER_CREATED = "user.created";
    String USER_RESET_PASSWORD = "user.reset_password";
    String USER_CHANGED_EMAIL = "user.changed_email";
    String USER_CONFIRMED_BY_EMAIL = "user.confirmed_by_email";
    String USER_USED_2FA = "user.used_2fa";

    String MAIL_SENT_VERIFICATION_MESSAGE = "mail.sent_verification_message";
    String MAIL_SENT_FORGOT_PASSWORD_MESSAGE = "mail.sent_verification_message";
    String MAIL_SENT_NEW_VERIFICATION_MESSAGE = "mail.sent_new_verification_message";
}