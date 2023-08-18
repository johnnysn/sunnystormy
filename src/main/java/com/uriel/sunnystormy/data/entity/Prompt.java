package com.uriel.sunnystormy.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PROMPT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class Prompt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Timestamp should not be null")
    private LocalDateTime timestamp;

    @NotBlank(message = "Content should not be empty")
    @Size(max = 4000, message = "Size should not be greater than 4000")
    @Column(length = 4000)
    private String content;

    @Size(max = 4000, message = "Size should not be greater than 4000")
    @Column(length = 4000)
    private String response;

}
