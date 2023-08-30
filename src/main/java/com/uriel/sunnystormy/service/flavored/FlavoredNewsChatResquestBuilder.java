package com.uriel.sunnystormy.service.flavored;

import com.uriel.sunnystormy.data.entity.FlavoredNews;
import org.springframework.stereotype.Component;

@Component
class FlavoredNewsChatResquestBuilder {
    public String build(String title, String content, FlavoredNews.Flavor flavor) {
        return String.format("""
            Generate a title and a short description for the news article below from %s point of view. Return only title and description
            in a JSON format.
            
            %s
            
            %s
            """,
            flavor.equals(FlavoredNews.Flavor.SUNNY) ? "an optimistic/joyfull" : "a pessimistic/grumpy", title, content
        );
    }
}
