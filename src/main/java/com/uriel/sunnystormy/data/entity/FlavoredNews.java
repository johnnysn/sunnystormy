package com.uriel.sunnystormy.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "FLAVOREDNEWS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString
public class FlavoredNews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String flavoredTitle;

    @NotBlank(message = "Content must not be empty")
    @Column(length = 1000)
    private String flavoredContent;

    @NotNull(message = "Flavor must not be empty")
    private Flavor flavor;

    public enum Flavor {
        SUNNY, STORMY
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private News originalNews;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "prompt_id")
    private Prompt prompt;

}
