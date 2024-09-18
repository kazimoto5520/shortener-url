package com.meshadotdev.urlshortner.service;

import com.meshadotdev.urlshortner.entity.ShortenUrl;
import com.meshadotdev.urlshortner.repository.ShortenUrlRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShortenUrlService {
    private final ShortenUrlRepository shortenUrlRepository;

    public List<ShortenUrl> findAllUrls() {
        return shortenUrlRepository.findAll();
    }

    public String getUrl(String shortUrl) {
        ShortenUrl shortenUrl =  shortenUrlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new NoSuchElementException("Url not found"));

        return shortenUrl.getOriginalUrl();
    }

    public String createShortenUrl(String url) {
        ShortenUrl shortenUrl = new ShortenUrl();
        shortenUrl.setOriginalUrl(url);

        String encodedUrl = Base64.getUrlEncoder().encodeToString(url.getBytes()).substring(0, 8);
        shortenUrl.setShortUrl(encodedUrl);

        shortenUrlRepository.save(shortenUrl);

        return encodedUrl;
    }
}
