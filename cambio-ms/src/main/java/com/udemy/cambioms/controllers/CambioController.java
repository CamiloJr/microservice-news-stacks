package com.udemy.cambioms.controllers;

import com.udemy.cambioms.models.Cambio;
import com.udemy.cambioms.repositories.CambioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Tag(name = "Cambio Service API")
@RestController
@RequestMapping("/cambio-service")
public class CambioController {

    private Logger LOGGER = LoggerFactory.getLogger(CambioController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository repository;

    @Operation(description = "Get cambio drom currency!")
    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(
                            @PathVariable("amount") BigDecimal amount,
                            @PathVariable("from") String from,
                            @PathVariable("to") String to) {
        LOGGER.info("getCambio is called with -> {}, {} and {}", amount, from, to);
        var port = environment.getProperty("local.server.port");
        var dbCambio = repository.findByFromAndTo(from, to);

        if(dbCambio == null)
            throw new RuntimeException("Currency Unsupported");

        var conversionFactory = dbCambio.getConversionFactor();
        var convertValue = conversionFactory.multiply(amount);
        dbCambio.setConvertedValue(convertValue.setScale(2, RoundingMode.CEILING));
        dbCambio.setEnvironment(port);
        return dbCambio;
    }
}
