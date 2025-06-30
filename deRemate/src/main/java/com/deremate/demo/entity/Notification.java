package com.deremate.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Notificacion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "subtitle", nullable = false)
    private String subtitle;

    @Builder.Default
    @Column(name = "read")
    private Boolean read = false;
}
