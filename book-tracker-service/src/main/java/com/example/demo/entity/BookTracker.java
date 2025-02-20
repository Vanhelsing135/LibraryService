package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "BookTracker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bookId", nullable = false, unique = true)
    private Integer bookId;

    @Enumerated(EnumType.STRING)
    private BookStatus status;
    private LocalDateTime takenAt;
    private LocalDateTime returnBy;
}
