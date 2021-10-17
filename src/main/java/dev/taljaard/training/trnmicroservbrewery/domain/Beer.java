package dev.taljaard.training.trnmicroservbrewery.domain;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class Beer {

    private UUID id;
    private String beerName;
    private String beerStyle;
    private Long upc;

    private Timestamp createdDate;
    private Timestamp lastUpdateDate;
}
