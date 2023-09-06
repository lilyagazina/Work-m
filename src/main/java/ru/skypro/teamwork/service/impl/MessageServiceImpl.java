package ru.skypro.teamwork.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.teamwork.constant.MessageEnum;
import ru.skypro.teamwork.service.MessageService;
import ru.skypro.teamwork.service.UserService;

@RequiredArgsConstructor
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    private final TelegramBot telegramBot;
    private final UserService userService;
    private final GuestMenuService guestMenuService;
    private final AdminMenuService adminMenuService;
    private final VolunteerMenuService volunteerMenuService;
    private final OwnerMenuService ownerMenuService;
    /**
     * Обработка Query
     */
    @Override
    public void queryHandler(Update update) {
        Long chatId = update.callbackQuery().from().id();
        if (userService.isNew(chatId)) {
            String firstName = (update.callbackQuery().from().firstName());
            String username = update.callbackQuery().from().username();
            String lastName = update.callbackQuery().from().lastName();
            sendHello(chatId,firstName, username, lastName);
        }
        byAccessSwitch(chatId, update.callbackQuery().data());
    }
    /**
     * Обработка message
     */
    @Override
    public void messageHandler(Update update) {
        Long chatId = update.message().chat().id();
        if (userService.isNew(chatId)) {
            String firstName = (update.message().from().firstName());
            String username = update.message().from().username();
            String lastName = update.message().from().lastName();
            sendHello(chatId,firstName, username, lastName);
        }
        byAccessSwitch(chatId, update.message().text());
    }
    /**
     * Вызов меню в зависимости от AccessLevel
     */
    @Override
    public void byAccessSwitch(Long chatId, String messageText) {

        switch (userService.getAccessLevel(chatId)) {
            case "guest"-> sendMsg(guestMenuService.mainMenu(chatId, messageText));
            case "volunteer" -> sendMsg(volunteerMenuService.mainMenu(chatId, messageText));
            case "owner" -> sendMsg(ownerMenuService.mainMenu(chatId, messageText));
            case "admin" -> sendMsg(adminMenuService.mainMenu(chatId, messageText));
        }
    }
    /**
     * Приветствие для нового пользователя
     * Добавление нового пользователя со статусом Guest
     */
    private void sendHello(Long chatId, String firstName, String username, String lastName) {
        String name= (firstName == null ? "" : firstName) +
                (username == null ? "" : " \"" + username + "\"") +
                (lastName == null ? "" : " " + lastName);
        sendMsg(chatId, "Привет, " + name + "!" + MessageEnum.NEW_USER_MESSAGE.getMessage());
        userService.addUser(chatId, name);
    }
    /**
     * Отправка текста в чат
     */
    @Override
    public void sendMsg(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        sendMsg(message);
    }
    /**
     * Отправка message в чат
     */
    @Override
    public void sendMsg(SendMessage message) {
        try {
            SendResponse response = telegramBot.execute(message);
            log.info("Sent: {}", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
