package com.uriel.sunnystormy.remote;

import com.uriel.sunnystormy.data.entity.Prompt;

public interface ChatAPIRequestHandler {

    Prompt prompt(String message);

}
