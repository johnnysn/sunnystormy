package com.uriel.sunnystormy.data.repository;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlavoredNewsRepository extends JpaRepository<FlavoredNews, UUID> {
}
