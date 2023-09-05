package com.uriel.sunnystormy.data.repository;

import com.uriel.sunnystormy.data.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {

    @Query("""
        select count(n) from News n where timestamp >= :from 
    """)
    int countNewsStartingFrom(LocalDateTime from);

    Optional<News> findFirstByFlavoredNewsIsNullOrderByTimestampDesc();

    boolean existsByTitleLikeIgnoreCaseAndTimestamp(String title, LocalDateTime timestamp);
}
