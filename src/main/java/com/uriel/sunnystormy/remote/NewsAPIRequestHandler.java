package com.uriel.sunnystormy.remote;

import com.uriel.sunnystormy.data.entity.News;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NewsAPIRequestHandler {

    List<News> fetch(int pageSize);

}
