package com.uriel.sunnystormy.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "NEWS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Date must not be null")
    private LocalDateTime timestamp;

    @NotBlank(message = "Title must not be empty")
    private String title;

    @NotBlank(message = "Content must not be empty")
    @Column(length = 400)
    private String content;

    private String imgUrl;

}
