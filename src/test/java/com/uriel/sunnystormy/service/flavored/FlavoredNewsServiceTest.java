package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import com.uriel.sunnystormy.data.repository.FlavoredNewsRepository;
import com.uriel.sunnystormy.data.specification.FlavoredNewsFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlavoredNewsServiceTest {

    @Mock
    private FlavoredNewsRepository repository;

    @InjectMocks
    private FlavoredNewsService flavoredNewsService;

    @ParameterizedTest
    @NullSource
    @EnumSource(FlavoredNews.Flavor.class)
    void mustFindAllCorrectly_WithFlavorSpecified(FlavoredNews.Flavor flavor) {
        // arrange
        var news = FlavoredNews.builder()
                .flavoredTitle("Spider Test")
                .flavoredContent("Spider test description")
                .flavor(FlavoredNews.Flavor.SUNNY)
                .build();
        var news2 = FlavoredNews.builder()
                .flavoredTitle("Scorpion Test")
                .flavoredContent("Scorpion test description")
                .flavor(FlavoredNews.Flavor.STORMY)
                .build();
        var pageable = PageRequest.of(0, 5);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(
                new PageImpl<>(List.of(news, news2), pageable, 2)
        );
        // act
        var page = flavoredNewsService.findAll(flavor, pageable);
        // assert
        Assertions.assertTrue(page.getContent().contains(news));
        Assertions.assertTrue(page.getContent().contains(news2));
        Assertions.assertEquals(2, page.getTotalElements());
        ArgumentCaptor<FlavoredNewsFilter> argumentCaptor = ArgumentCaptor.forClass(FlavoredNewsFilter.class);
        verify(repository).findAll(argumentCaptor.capture(), eq(pageable));
        Assertions.assertEquals(flavor, argumentCaptor.getValue().flavor());
    }
}
