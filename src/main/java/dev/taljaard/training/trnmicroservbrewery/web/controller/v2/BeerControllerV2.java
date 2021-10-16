package dev.taljaard.training.trnmicroservbrewery.web.controller.v2;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.taljaard.training.trnmicroservbrewery.services.v2.BeerServiceV2;
import dev.taljaard.training.trnmicroservbrewery.web.model.v2.BeerDtoV2;

@RestController
@RequestMapping(BeerControllerV2.PATH)
public class BeerControllerV2 {

    public static final String PATH = "/api/v2/beer";
    private final BeerServiceV2 beerService;

    public BeerControllerV2(BeerServiceV2 beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDtoV2> getBeers(@PathVariable UUID beerId) {
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createBeer(@RequestBody BeerDtoV2 beer) {
        System.out.println(beer);
        BeerDtoV2 savedBeer = beerService.saveBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, String.format("%s/%s", PATH, savedBeer.getId()));
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<String> updateBeer(@PathVariable UUID beerId, @RequestBody BeerDtoV2 BeerDtoV2) {
        System.out.println(BeerDtoV2);
        beerService.updateBeer(beerId, BeerDtoV2);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable UUID beerId) {
        beerService.deleteBeerById(beerId);
    }
}