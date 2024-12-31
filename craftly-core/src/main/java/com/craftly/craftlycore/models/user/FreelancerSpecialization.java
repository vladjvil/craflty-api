package com.craftly.craftlycore.models.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FreelancerSpecialization {
    PROGRAMMER("Программист"),
    DESIGNER("Дизайнер"),
    PROJECT_MANAGER("Проджект-менеджер"),
    COPYWRITER("Копирайтер"),
    TESTER("Тестировщик"),
    DEVOPS("DevOps-инженер"),
    SEO_SPECIALIST("SEO-специалист"),
    CONTENT_MANAGER("Контент-менеджер"),
    SMM_MANAGER("SMM-менеджер"),
    DATA_SCIENTIST("Data Scientist"),
    DATA_ENGINEER("Data Engineer"),
    BACKEND_DEVELOPER("Backend-разработчик"),
    FRONTEND_DEVELOPER("Frontend-разработчик"),
    FULLSTACK_DEVELOPER("Fullstack-разработчик"),
    GAME_DEVELOPER("Разработчик игр"),
    MOBILE_DEVELOPER("Мобильный разработчик"),
    SYSTEM_ADMINISTRATOR("Системный администратор"),
    BUSINESS_ANALYST("Бизнес-аналитик"),
    UI_UX_DESIGNER("UI/UX-дизайнер"),
    ARCHITECT("Архитектор ПО"),
    MACHINE_LEARNING_ENGINEER("Инженер машинного обучения"),
    ROBOTICS_ENGINEER("Инженер робототехники"),
    VIDEO_EDITOR("Видео-редактор"),
    AUDIO_ENGINEER("Звукорежиссер"),
    TRANSLATOR("Переводчик"),
    LEGAL_ADVISOR("Юридический консультант"),
    FINANCIAL_ADVISOR("Финансовый консультант"),
    SALES_MANAGER("Менеджер по продажам"),
    MARKETING_SPECIALIST("Маркетолог"),
    CYBERSECURITY_SPECIALIST("Специалист по кибербезопасности"),
    TECHNICAL_WRITER("Технический писатель"),
    PRODUCT_MANAGER("Продукт-менеджер"),
    VIRTUAL_ASSISTANT("Виртуальный ассистент");

    private final String type;
}
