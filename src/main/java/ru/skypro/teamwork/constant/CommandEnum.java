package ru.skypro.teamwork.constant;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * Список кнопок для управления ботом
 *
 */
@Getter
@RequiredArgsConstructor
public enum CommandEnum {
    /**
     * Приветствие, переход к выбору приюта
     */
    START("Выбор приюта", "/start"),
    /**
     * Кнопка выбора приюта
     */
    DOG_SHELTER("Приют для собак", "/dog"),
    /**
     * Кнопка выбора приюта
     */
    CAT_SHELTER("Приют для кошек", "/cat"),
    INFO("Информация о приюте", "/info"),
    GET("Как взять животное из приюта", "/get"),
    REPORT ("Прислать отчет о питомце", "/report"),
    TEXT("Ввести текст отчета", "/text"),
    PICTURE("Отправить фото", "/picture"),
    /**
     * Кнопка помощи
     */
    HELP("Позвать волонтера", "/help");

    /**
     * Описание команды
     */
    private final String description;
    /**
     * Команда
     */
    private final String command;
    /**
     * Возвращает готовую кнопку с описанием и командой
     */
    public InlineKeyboardButton getButton() {
        return new InlineKeyboardButton(description).callbackData(command);
    }
}