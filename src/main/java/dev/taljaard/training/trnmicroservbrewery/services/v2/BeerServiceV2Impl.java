package dev.taljaard.training.trnmicroservbrewery.services.v2;

import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.taljaard.training.trnmicroservbrewery.enums.BeerStyleEnum;
import dev.taljaard.training.trnmicroservbrewery.web.model.v2.BeerDtoV2;

@Service
public class BeerServiceV2Impl implements BeerServiceV2 {

    @Override
    public BeerDtoV2 getBeerById(UUID beerId) {
        return BeerDtoV2.builder().id(beerId).beerName("Free For All").beerStyle(BeerStyleEnum.Lager).build();
    }

    @Override
    public BeerDtoV2 saveBeer(BeerDtoV2 beer) {
        return BeerDtoV2.builder().id(UUID.randomUUID()).beerName(beer.getBeerName()).beerStyle(beer.getBeerStyle())
                .build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDtoV2 beerDto) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteBeerById(UUID beerId) {
        // TODO Auto-generated method stub

    }

}
