package com.udemy.bookms.controllers;

import com.udemy.bookms.models.Book;
import com.udemy.bookms.proxy.CambioProxy;
import com.udemy.bookms.repositories.BookRepository;
import com.udemy.bookms.responses.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CambioProxy cambioProxy;

    @Operation(summary = "Find a price in an current currency by book id.")
    @GetMapping("/{id}/{currency}")
    public Book findBook(@PathVariable(value = "id") Long id,
                         @PathVariable(value = "currency") String currency) {
        var port = environment.getProperty("local.server.port");
        var dbBook = repository.getReferenceById(id);
        if(dbBook == null)
            throw new RuntimeException("Book not Found");

        var cambio = cambioProxy.getCambio(dbBook.getPrice(), "USD", currency);
        dbBook.setEnvironment("Book port: " + port + "  Cambio Port: " + cambio.getEnvironment());
        dbBook.setPrice(cambio.getConvertedValue());
        return dbBook;
    }

//    @GetMapping("/{id}/{currency}")
//    public Book findBook(@PathVariable(value = "id") Long id,
//                         @PathVariable(value = "currency") String currency) {
//        var port = environment.getProperty("local.server.port");
//        var dbBook = repository.getReferenceById(id);
//        if(dbBook == null)
//            throw new RuntimeException("Book not Found");
//
//        HashMap<String, String> params = new HashMap<>();
//        params.put("amount", dbBook.getPrice().toString());
//        params.put("from", "USD");
//        params.put("to", currency);
//
//        var response = new RestTemplate()
//                .getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}",
//                Cambio.class, params);
//
//        var cambio = response.getBody();
//        dbBook.setEnvironment(port);
//        dbBook.setPrice(cambio.getConvertedValue());
//        return dbBook;
//    }
}
