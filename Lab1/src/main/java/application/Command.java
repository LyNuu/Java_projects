package application;

import java.sql.SQLException;
/**
 * Интерфейс для реализации паттерна "Команда".
 * <p>
 * Определяет контракт для выполнения операций, связанных с бизнес-логикой приложения.
 * Все конкретные команды должны реализовывать этот интерфейс.
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * public class MyCommand implements command {
 *     public void Execute() throws SQLException {
 *         // Реализация операции
 *     }
 * }
 * }</pre>
 *
 * @see SQLException
 */
public interface Command {

    void execute() throws Exception;
}
