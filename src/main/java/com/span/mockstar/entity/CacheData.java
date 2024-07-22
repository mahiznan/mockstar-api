package com.span.mockstar.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "cache_data", indexes = {
        @Index(name = "hash_index", columnList = "hash"),
        @Index(name = "source_index", columnList = "source"),
        @Index(name = "expires_at_index", columnList = "expiresAt"),
})
public class CacheData {
    protected CacheData() {

    }

    public CacheData(String source, String hash, String value, Instant createdAt, Instant expiresAt) {
        this.id = UUID.randomUUID();
        this.source = source;
        this.hash = hash;
        this.value = value;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(nullable = false)
    private UUID id;
    private String source;
    private String hash;
    @Column(columnDefinition = "LONGTEXT")
    private String value;
    private Instant createdAt;
    private Instant expiresAt;

    public UUID getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getHash() {
        return hash;
    }

    public String getValue() {
        return value;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}