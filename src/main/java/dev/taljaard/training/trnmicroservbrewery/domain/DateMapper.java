package dev.taljaard.training.trnmicroservbrewery.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {

    public OffsetDateTime asOffsetDateTime(Timestamp ts) {
        if (ts != null) {
            LocalDateTime localDT = ts.toLocalDateTime();
            return OffsetDateTime.of(localDT.getYear(), localDT.getMonthValue(), localDT.getDayOfMonth(),
                    localDT.getHour(), localDT.getMinute(), localDT.getSecond(), localDT.getNano(), ZoneOffset.UTC);
        } else {
            return null;
        }
    }

    public Timestamp asTimestamp(OffsetDateTime offsetDateTime) {
        if (offsetDateTime != null) {
            return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        } else {
            return null;
        }
    }
}
