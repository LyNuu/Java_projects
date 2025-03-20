package models;

/**
 * Класс, представляющий пользователя системы.
 * @param id уникальный идентификатор пользователя
 * @param password пароль для аутентификации
 * @param name имя пользователя
 * @param countMoney баланс на счету
 */
public record User(int id, String name, String password, double countMoney) { }
