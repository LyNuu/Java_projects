package dataBase;
/**
 * Класс-запись (enum) для хранения информации о логах операций.
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
 * System.out.println(Logging.CREATE);
 * }</pre>
 */
public enum OperationType {

    CREATE,
    VIEW,
    DEPOSIT,
    WITHDRAW;

}
