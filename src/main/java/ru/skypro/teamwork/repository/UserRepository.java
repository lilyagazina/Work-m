package ru.skypro.teamwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.teamwork.entity.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Поиск пользователя по ID чата
     *
     * @param chatId ID чата в telegram
     * @return {@link User} пользователь чата
     */
    Optional<User> findFirstByChatId(Long chatId);
}