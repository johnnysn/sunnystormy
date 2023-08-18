package com.uriel.sunnystormy.data.repository;

import com.uriel.sunnystormy.data.entity.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PromptRepository extends JpaRepository<Prompt, UUID> {
}
