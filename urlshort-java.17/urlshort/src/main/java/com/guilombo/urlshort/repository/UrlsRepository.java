package com.guilombo.urlshort.repository;

import com.guilombo.urlshort.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlsRepository extends JpaRepository<Url, Short> {

    Optional<Url> findByShortUrl(String shortUrl);
}
