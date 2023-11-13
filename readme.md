# SunnyStormy API

![GitHub](https://img.shields.io/github/license/johnnysn/sunnystormy)

SunnyStormy is a Spring Boot is a fun conceptual project that offers a 
unique twist on news consumption. 
It provides short overviews of the latest news articles 
flavored from either an optimistic or a pessimistic point of view, 
adding a touch of playfulness to your news feed. 
This project leverages the [NewsAPI](https://newsapi.org/) 
to fetch news fragments and the [Chat GPT API](https://openai.com/) 
to generate the flavored news content.

## Table of Contents

- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

Before you start using SunnyStormy, make sure you have the following prerequisites installed on your system:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) - Version 17 or higher.
- [Gradle](https://gradle.org/install/) - Build automation and dependency management tool.
- [Git](https://git-scm.com/downloads) - Version control system.

Also, make sure you have access to the required external resources:

- [NewsAPI API Key](https://newsapi.org/docs/get-started) - Sign up for a free API key to access news data.
- [OpenAI API Key](https://openai.com/) - Create your account and API key to access AI text completions.

### Installation

Follow these steps to install and run SunnyStormy in development mode:

1. Clone the SunnyStormy repository:

   ```bash
   git clone https://github.com/johnnysn/sunnystormy.git
   ```

2. Navigate to the project directory:

   ```bash
   cd sunnystormy
   ```
   
3. Set the environment variables APP_API_KEY (API key for protected endpoints of the application), 
CHAT_API_KEY and NEWS_API_KEY. If the ``prod`` profile is not
active, CHAT_API_KEY should be just a dummy key, since no real communication with OpenAI will occur.

4. Build and run the project using Gradle:

   ```bash
   gradle bootRun 
   ```

    You can also pass environment variables along with the execution command:

    ```bash
    APP_API_KEY=mykey NEWS_API_KEY=newsorgkey CHAT_API_KEY=dummy gradle bootRun 
    ```
   
    In order to run the project in production mode and actyally consume ChatGPT AI resources, you should add
``--args='--spring.profiles.active=prod'`` to the execution command.

SunnyStormy should now be up and running on `http://localhost:8080`.

## API Endpoints

Endpoints that consume external resources are protected by a simple API key authorization, which 
is defined by the environment variable ``APP_API_KEY``. The key must be provided in a header
entry identified by ``ApiKey``. You can visualize the API documentation by running the app
and accessing the Swagger UI: `http://localhost:8080/swagger-ui/index.html`.

### 1) Fetch Latest News

#### Endpoint
- **POST** `/fetching/news`

#### Description
Retrieves the latest news from an external API with the batch size configured in the `application.properties` file 
(`application.news.max-batch-size`) and saves them into the database.

#### Request
- No request body is required.

#### Response
- The response will be a JSON array containing news articles with the following structure:

```json
[
    {
        "id": "08da0a53-cedd-4b7c-9a8c-91308be0ea6e",
        "timestamp": "2023-11-12 09:22:43",
        "title": "Iceland declares state of emergency over escalating earthquakes, and volcano eruption fears - Euronews",
        "content": "Iceland has 33 active volcanic systems, the highest number in Europe, and thousands of tremors have been recorded since the end of October.",
        "imgUrl": "https://static.euronews.com/articles/stories/08/03/27/68/1000x563_cmsv2_fb6b8b7a-7bb3-539f-ae74-c31b10b96c6e-8032768.jpg"
    }
]
```

#### Authorization
- This endpoint is protected by API key authorization.

### 2) Generate Flavored News Overview for a specific news fragment

#### Endpoint
- **POST** `/prompting/flavored-news`

#### Description
Calls the ChatGPT API to generate an optimistic or a pessimistic overview of a 
news article identified by its `news_id` and saves it into the database.

#### Request
- The request body should be a JSON object with the following format:

```json
{
    "news_id": "0f0b120c-5bde-4cd3-ae49-641df99fc2bc",
    "flavor": "SUNNY"
}
```

- `news_id`: The unique identifier of the news article.
- `flavor`: The flavor or tone for the generated news overview (e.g., "SUNNY").

#### Response
- The response will be a JSON object with the following structure:

```json
{
    "id": "712dcecf-0bbf-49ac-9d68-71f329163d23",
    "flavoredTitle": "Iceland's Resilience Shines as State of Emergency Declared Amidst Earthquakes and Volcano Eruption Fears",
    "flavoredContent": "Iceland, home to Europe's highest number of volcanic systems, showcases its strength and preparedness as it declares a state of emergency amidst escalating earthquakes and fears of a volcano eruption. Thousands of tremors have been recorded since October, highlighting the country's resilience and dedication to ensuring public safety.",
    "flavor": "SUNNY",
    "originalNews": {
        "id": "08da0a53-cedd-4b7c-9a8c-91308be0ea6e",
        "timestamp": "2023-11-12 09:22:43",
        "title": "Iceland declares state of emergency over escalating earthquakes, and volcano eruption fears - Euronews",
        "content": "Iceland has 33 active volcanic systems, the highest number in Europe, and thousands of tremors have been recorded since the end of October.",
        "imgUrl": "https://static.euronews.com/articles/stories/08/03/27/68/1000x563_cmsv2_fb6b8b7a-7bb3-539f-ae74-c31b10b96c6e-8032768.jpg"
    }
}
```

### 3) Generate Flavored News Overview for the latest news fragment

#### Endpoint
- **POST** `/prompting/flavored-news/for-latest`

#### Description
Does the same thing of the previous endpoint, but for the most recent news fragment
that has not been flavored yet. Both sunny and stormy flavored news overviews will
be generated.

#### Request
- No request body is required.

#### Response
- An array containing a maximum of two objects, 
each with the same structure as the previous endpoint.

#### Authorization
- This endpoint is protected by API key authorization.

### 4) Retrieve flavored news overviews

#### Endpoint
- **GET** `/public/flavored-news`

#### Description
Retrieves a page of flavored news overviews, sorted in descending order by the timestamp of the original news fragment.

#### Request
- Accepts two optional query params:
  - page: Number of the page, starting from 0
  - size: Page size, with maximum value configured in the application property `application.news.max-page-size`

#### Response
- A JSON with the structure of `org.springframework.data.domain.Page` containing flavored news descriptors as the previous
endpoint.

#### Authorization
- No authorization is required.

## Contributing

We welcome contributions to SunnyStormy! If you'd like to add features, fix bugs, or improve documentation, 
please fork this repository and submit a pull request. 
For major changes, please open an issue first to discuss the proposed changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.