# SunnyStormy API

SunnyStormy is a Spring Boot project that offers a unique twist on news consumption. 
It provides short overviews of the latest news articles 
flavored from either an optimistic or a pessimistic point of view, 
adding a touch of personality to your news feed. This project leverages the [NewsAPI](https://newsapi.org/) 
to fetch news fragments and the [Chat GPT API](https://openai.com/) to generate the flavored news content.

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
    NEWS_API_KEY=newsorgkey APP_API_KEY=mykey CHAT_API_KEY=dummy gradle bootRun 
    ```

SunnyStormy should now be up and running on [http://localhost:8080](http://localhost:8080).

## Contributing

We welcome contributions to SunnyStormy! If you'd like to add features, fix bugs, or improve documentation, 
please fork this repository and submit a pull request. 
For major changes, please open an issue first to discuss the proposed changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.