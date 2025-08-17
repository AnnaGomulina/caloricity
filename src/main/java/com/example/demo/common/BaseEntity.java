package com.example.demo.common;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Comment("Дата и время создания")
    @CreationTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(updatable = false, nullable = false, columnDefinition = "TEXT")
    protected LocalDateTime createdAt;

    @Comment("Дата и время редактирования")
    @UpdateTimestamp
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(nullable = false, columnDefinition = "TEXT")
    protected LocalDateTime updatedAt;

    @Version
    @Setter(AccessLevel.NONE)
    @Comment("Номер версии сущности")
    protected long version = 0;
}
