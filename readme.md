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

SunnyStormy should now be up and running on [http://localhost:8080](http://localhost:8080).

## API Endpoints

Endpoints that consume external resources are protected by a simple API key authorization, which 
is defined by the environment variable ``APP_API_KEY``. The key must be provided in a header
entry identified by ``ApiKey``. 

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
        "id": "967d4956-4389-4a72-bc8e-e01bc294371d",
        "timestamp": "2023-09-05 10:30:00",
        "title": "Texas Republicans put Trump ally Attorney General Ken Paxton on trial - NBC News",
        "content": "Texas will have its third-ever impeachment trial, when Attorney General Ken Paxton stands trial on charges of corruption, abuse of public trust and more.",
        "imgUrl": "https://media-cldnry.s-nbcnews.com/image/upload/t_nbcnews-fp-1200-630,f_auto,q_auto:best/rockcms/2023-09/230901-ken-paxton-se-1137a-1c130c.jpg"
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
    "id": "3bd60098-fd4b-4947-9d93-4d9d96bf7882",
    "flavoredTitle": "Texas Republicans stand up against corruption and abuse of public trust, putting Trump ally Attorney General Ken Paxton on trial",
    "flavoredContent": "In a historic move, Texas Republicans are taking a stand for integrity and justice as they commence the impeachment trial of Attorney General Ken Paxton. The trial will address charges of corruption and abuse of public trust, showcasing the state's commitment to upholding the highest standards of governance.",
    "flavor": "SUNNY",
    "originalNews": {
        "id": "967d4956-4389-4a72-bc8e-e01bc294371d",
        "timestamp": "2023-09-05 10:30:00",
        "title": "Texas Republicans put Trump ally Attorney General Ken Paxton on trial - NBC News",
        "content": "Texas will have its third-ever impeachment trial, when Attorney General Ken Paxton stands trial on charges of corruption, abuse of public trust and more.",
        "imgUrl": "https://media-cldnry.s-nbcnews.com/image/upload/t_nbcnews-fp-1200-630,f_auto,q_auto:best/rockcms/2023-09/230901-ken-paxton-se-1137a-1c130c.jpg"
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