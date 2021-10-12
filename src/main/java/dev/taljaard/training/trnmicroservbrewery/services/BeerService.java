package dev.taljaard.training.trnmicroservbrewery.services;

import java.util.UUID;

import dev.taljaard.training.trnmicroservbrewery.web.model.BeerDto;

public interface BeerService {

    BeerDto getBeerById(UUID beerId);

    BeerDto saveBeer(BeerDto beer);

    void updateBeer(UUID beerId, BeerDto beerDto);

    void deleteBeerById(UUID beerId);
}
