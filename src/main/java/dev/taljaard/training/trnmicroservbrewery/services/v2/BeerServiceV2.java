package dev.taljaard.training.trnmicroservbrewery.services.v2;

import java.util.UUID;

import dev.taljaard.training.trnmicroservbrewery.web.model.v2.BeerDtoV2;

public interface BeerServiceV2 {

    BeerDtoV2 getBeerById(UUID beerId);

    BeerDtoV2 saveBeer(BeerDtoV2 beer);

    void updateBeer(UUID beerId, BeerDtoV2 beerDto);

    void deleteBeerById(UUID beerId);
}
