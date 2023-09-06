package ru.skypro.teamwork.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface MessageService {

    void queryHandler(Update update);

    void messageHandler(Update update);

    void byAccessSwitch(Long chatId, String messageText);

    void sendMsg(Long chatId, String text);

    void sendMsg(SendMessage message);
}
