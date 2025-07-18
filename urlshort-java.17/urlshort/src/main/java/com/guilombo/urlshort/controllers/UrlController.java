package com.guilombo.urlshort.controllers;

import com.guilombo.urlshort.models.Url;
import com.guilombo.urlshort.models.UrlResponse;
import com.guilombo.urlshort.services.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/short/{shortUrl}")
    public ResponseEntity<Optional<String>> getUrl(@PathVariable String shortUrl){
        Optional<String> response = urlService.searchUrl(shortUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Url> createUrl (@RequestBody UrlResponse urlResponse){
        Url url = urlService.createUrl(urlResponse);
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUrl(@PathVariable Short Id){
        urlService.deleteUrl(Id);
        return ResponseEntity.noContent().build();
    }
}
