package ru.skypro.teamwork.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.teamwork.entity.User;
import ru.skypro.teamwork.repository.UserRepository;
import ru.skypro.teamwork.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    /**
     * Добавление нового пользователя со статусом гость
     */
    @Override
    @Transactional
    public void addUser(Long chatId, String name) {
        userRepository.save(
                User.builder()
                        .chatId(chatId)
                        .fullName(name)
                        .accessLevel("guest")
                        .build());
    }
    /**
     * Получение пользователя по id чата
     */
    @Override
    public User getUser(Long chatId) {
        return userRepository.findFirstByChatId(chatId).get();
    }
    /**
     * Проверка есть ли пользователь в базе
     */
    public boolean isNew(Long chatId) {
        return userRepository.findFirstByChatId(chatId).isEmpty();
    }
    /**
     * Присвоение нового уровня доступа
     */
    @Override
    @Transactional
    public void setAccessLevel(Long chatId, String accessLevel) {
        User user = userRepository.findFirstByChatId(chatId).get();
        user.setAccessLevel(accessLevel);
        userRepository.save(user);
    }
    /**
     * Получение уровня доступа
     */
    @Override
    public String getAccessLevel(Long chatId) {
        return userRepository.findFirstByChatId(chatId).get().getAccessLevel();
    }
    /**
     * Присвоение метки следующей операции
     */
    @Override
    @Transactional
    public void setState(Long chatId, String currentState) {
        User user = userRepository.findFirstByChatId(chatId).get();
        user.setCurrentState(currentState);
        userRepository.save(user);
    }
    /**
     * Получение метки следующей операции
     */
    @Override
    public String getState(Long chatId) {
        return userRepository.findFirstByChatId(chatId).get().getCurrentState();
    }
}