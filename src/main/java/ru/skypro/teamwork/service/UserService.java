package ru.skypro.teamwork.service;

import ru.skypro.teamwork.entity.User;

public interface UserService {

    void addUser(Long chatId, String name);

    User getUser(Long chatId);

    void setAccessLevel(Long chatId, String accessLevel);

    String getAccessLevel(Long chatId);

    void setState(Long chatId, String currentState);

    String getState(Long chatId);

    boolean isNew(Long id);
}
