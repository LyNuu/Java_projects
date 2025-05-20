package dataBase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Logging(
        OperationType operationType,
        LocalDateTime timestamp,
        BigDecimal amount
) {
}
