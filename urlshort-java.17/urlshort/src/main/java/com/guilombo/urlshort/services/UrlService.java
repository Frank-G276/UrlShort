package com.guilombo.urlshort.services;

import com.guilombo.urlshort.models.Url;
import com.guilombo.urlshort.models.UrlResponse;
import com.guilombo.urlshort.repository.UrlsRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlsRepository urlsRepository;

    String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private String randomNumber(){
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(7);
        for (int i = 0; i < 7; i++){
            int index = random.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }
    private Boolean verification(String urlRandom){
        List<Url> urls = urlsRepository.findAll();
        boolean response = false;
        for (Url url : urls) {
            if (url.equals(urlRandom)) response = true;
        }
        return response;
    }

    public Url createUrl(UrlResponse urlOriginal) {
        String urlRandom;
        do {
            urlRandom = randomNumber();
        }while (verification(urlRandom).equals(true));
        Url url = new Url();
        url.setOriginalUrl(urlOriginal.getOriginalUrl());
        url.setShortUrl(urlRandom);
        url.setCreatAt(LocalDateTime.now());
        url.setClickCount(0);

        return urlsRepository.save(url);
    }

    public void deleteUrl(@NonNull Short id) {
        urlsRepository.deleteById(id);
    }

    public Optional<String> searchUrl(@NonNull String shortUrl){
        Optional<Url> url = urlsRepository.findByShortUrl(shortUrl);
        url.ifPresent(
                url1 -> {
                    url1.setClickCount(url1.getClickCount() + 1);
                    urlsRepository.save(url1);
                }
        );
        return url.map(Url::getOriginalUrl);
    }

}
