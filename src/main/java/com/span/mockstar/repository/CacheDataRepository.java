package com.span.mockstar.repository;

import com.span.mockstar.entity.CacheData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface CacheDataRepository extends JpaRepository<CacheData, UUID> {
    Optional<CacheData> findByHashAndExpiresAtAfter(String hash, Instant time);

    @Modifying
    @Query(value = "DELETE from CacheData c where c.expiresAt < ?1")
    void deleteCacheBefore(Instant time);

}