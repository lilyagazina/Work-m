package ru.skypro.teamwork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.teamwork.entity.User;
import ru.skypro.teamwork.exeption.InvalidInputException;
import ru.skypro.teamwork.repository.UserRepository;
import ru.skypro.teamwork.service.UserService;

import java.util.regex.Pattern;
@Log4j2
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

    /** +
     * Удаление пользователя по id
     */
    @Transactional
    public User delete(Long id) {
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return user;
    }
    /**
     * Обновляет данные пользователя в БД
     */
    @Transactional
    public void update(User user, Integer id) {
        log.info("Updating myUser: " + user);
        validate(user);
        ModelMapper modelMapper = new ModelMapper();
        User userToUpdate = userRepository.findById(Long.valueOf(id)).orElseThrow();
        user.setId(id);
        modelMapper.map(user, userToUpdate);
        userRepository.save(userToUpdate);
    }
    /** +
     * Проверяет данные пользователя на корректность
     */
    private void validate(User user) {
        log.info("Проверка пользователя: " + user);
        if (user.getFullName() == null) {
            throw new InvalidInputException("Отсутствует имя пользователя");
        }
        if (user.getChatId() == 0L) {
            throw new InvalidInputException("Отсутствует chatId");
        }
        if (user.getPhoneNumbers() != null && !isPhoneValid(user.getPhoneNumbers())) {
            throw new InvalidInputException("Некорректный телефон");
        }
        if (user.getEmail() != null && !isEmailValid(user.getEmail())) {
            throw new InvalidInputException("Некорректная почта");
        }
    }


    /** +
     Проверяет, соответствует ли заданный номер телефона российскому формату.
     */
    private boolean isPhoneValid(String phone) {
        log.info("Проверка номера телефона: {}", phone);
        final String regexPattern = "^((\\+7|7|8)+([0-9]){10})$";
        return Pattern.compile(regexPattern).matcher(phone).matches();
    }
    /** Проверяет, соответствует ли заданный email адрес заданному шаблону.
     */
    private boolean isEmailValid(String email) {
        log.info("Проверка email: {}", email);
        final String regexPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

}