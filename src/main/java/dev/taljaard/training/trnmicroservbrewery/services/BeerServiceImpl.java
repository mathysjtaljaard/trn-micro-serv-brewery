package dev.taljaard.training.trnmicroservbrewery.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.taljaard.training.trnmicroservbrewery.web.model.BeerDto;

@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(beerId).beerName("Free For All").beerStyle("Lager").build();
    }

    @Override
    public BeerDto saveBeer(BeerDto beer) {
        return BeerDto.builder().id(UUID.randomUUID()).beerName(beer.getBeerName()).beerStyle(beer.getBeerStyle())
                .build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteBeerById(UUID beerId) {
        // TODO Auto-generated method stub

    }

}
