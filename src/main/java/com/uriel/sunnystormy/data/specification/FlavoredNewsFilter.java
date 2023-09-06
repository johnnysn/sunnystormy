package com.uriel.sunnystormy.data.specification;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

@Builder
public record FlavoredNewsFilter(FlavoredNews.Flavor flavor) implements Specification<FlavoredNews> {

    @Override
    public Predicate toPredicate(Root<FlavoredNews> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (flavor != null) {
            return builder.equal(root.get("flavor"), flavor);
        }

        return null;
    }
}
