package dataBase;

import java.time.LocalDateTime;
/**
 * Класс-запись (record) для хранения информации о логах операций.
 * <p>
 * Используется для хранения данных о выполненных операциях, таких как:
 * - Тип операции
 * - Временная метка
 * - Сумма операции
 * - Дополнительные детали
 * </p>
 *
 * <p><b>Пример использования:</b>
 * <pre>{@code
 * logging logEntry = new logging("WITHDRAWAL", LocalDateTime.now(), 100.0, "Снятие наличных");
 * System.out.println(logEntry);
 * }</pre>
 *
 * @param operationType тип операции (например, "WITHDRAWAL", "REPLENISHMENT")
 * @param timestamp     временная метка операции
 * @param amount        сумма операции (может быть null, если операция не связана с деньгами)
 * @param details       дополнительные детали операции
 */
public record logging(
        String operationType,
        LocalDateTime timestamp,
        Double amount,
        String details
) {
}
