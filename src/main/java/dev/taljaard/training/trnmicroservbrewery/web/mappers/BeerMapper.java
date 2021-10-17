package dev.taljaard.training.trnmicroservbrewery.web.mappers;

import org.mapstruct.Mapper;

import dev.taljaard.training.trnmicroservbrewery.domain.Beer;
import dev.taljaard.training.trnmicroservbrewery.domain.DateMapper;
import dev.taljaard.training.trnmicroservbrewery.web.model.BeerDto;

@Mapper(uses = { DateMapper.class })
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
