package com.uriel.sunnystormy.data.repository;

import com.uriel.sunnystormy.data.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsRepository extends JpaRepository<News, UUID> {
}
