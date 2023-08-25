package com.uriel.sunnystormy.remote;

import com.uriel.sunnystormy.data.entity.Prompt;
import com.uriel.sunnystormy.remote.dto.OpenAIMessageDTO;
import com.uriel.sunnystormy.remote.dto.OpenAIRequestDTO;
import com.uriel.sunnystormy.remote.dto.OpenAIResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

public class OpenAIRequestHandler implements ChatAPIRequestHandler {

    private final String apiKey;

    private final WebClient client;

    public OpenAIRequestHandler(String baseUrl, String apiKey) {
        this.apiKey = apiKey;
        client = WebClient.create(baseUrl);
    }

    @Override
    public Prompt prompt(String message) {
        var prompt = Prompt.builder()
                .content(message)
                .build();

        var requestBody = createRequestBody(message);
        var request = createRequest(requestBody);
        var response = request
                .retrieve()
                .onStatus(
                        status -> status != HttpStatus.OK,
                        clientResponse -> Mono.empty()
                ).bodyToMono(OpenAIResponseDTO.class)
                .block();

        if (response == null) return null;

        fillPromptInfo(prompt, response);
        return prompt;
    }

    private WebClient.RequestHeadersSpec<?> createRequest(OpenAIRequestDTO request) {
        return client.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/chat/completions")
                        .build())
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .accept(MediaType.APPLICATION_JSON);
    }

    private static OpenAIRequestDTO createRequestBody(String message) {
        return OpenAIRequestDTO.builder()
                .model("gtp-3.5-turbo")
                .messages(List.of(new OpenAIMessageDTO("user", message)))
                .build();
    }

    private void fillPromptInfo(Prompt prompt, OpenAIResponseDTO response) {
        prompt.setResponse(response.getChoices().get(0).getMessage().getContent());
        prompt.setTimestamp(LocalDateTime.now());
    }
}
