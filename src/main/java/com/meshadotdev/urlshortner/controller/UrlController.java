package com.meshadotdev.urlshortner.controller;

import com.meshadotdev.urlshortner.service.ShortenUrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UrlController {
    private final ShortenUrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody String url, HttpServletRequest request) {
        String shortenUrl = urlService.createShortenUrl(url);

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path(request.getContextPath()).build().toUriString();

        String fullUrl = baseUrl + "/" + shortenUrl;
        return ResponseEntity.ok(fullUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> getUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.getUrl(shortUrl);
        return ResponseEntity.ok().location(URI.create(originalUrl)).build();
    }

}
