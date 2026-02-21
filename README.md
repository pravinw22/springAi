# Spring AI - Ollama Chatbot & Book Recommendations

A Spring Boot web application that integrates with Ollama (local LLM) to provide an AI chatbot interface and AI-powered book recommendations by genre.

## Features

- **AI Chatbot** (`/index.html`) - Interactive chat interface with Ollama-powered responses
- **Book Recommendations** (`/books.html`) - Genre-based book recommendations with AI-generated summaries
- **15+ Genres** - Fiction, Mystery, Sci-Fi, Fantasy, AI/ML, Business, Psychology, and more
- **Modern UI** - Clean, responsive design with gradient backgrounds and smooth animations

## Tech Stack

- **Backend**: Spring Boot 3.5.11, Spring AI 1.1.2
- **AI Integration**: Ollama (local LLM runner)
- **Frontend**: Vanilla HTML, CSS, JavaScript
- **Java Version**: 17+

## Prerequisites

Before running this application, ensure you have:

1. **Java 17+** installed
2. **Maven** installed (or use the provided `./mvnw` wrapper)
3. **Ollama** installed and running locally - [Install Ollama](https://ollama.com/download)
4. **Ollama Model** pulled (configured for `deepseek-v3.1:671b-cloud` by default)

## Quick Start

### 1. Start Ollama

First, make sure Ollama is running on your machine:

```bash
# Check if Ollama is running
ollama --version

# Start Ollama service
ollama serve

# Pull the required model (or change in application.properties)
ollama pull deepseek-v3.1:671b-cloud
```

> **Note**: You can use any Ollama model by updating `spring.ai.ollama.chat.model` in `application.properties`

### 2. Run the Application

#### Option A: Using Maven Wrapper (Recommended)

```bash
./mvnw spring-boot:run
```

#### Option B: Using Maven

```bash
mvn spring-boot:run
```

#### Option C: Build and Run JAR

```bash
./mvnw clean package
java -jar target/springAi-0.0.1-SNAPSHOT.jar
```

### 3. Access the Application

Once running, open your browser:

| Page | URL |
|------|-----|
| Chatbot | http://localhost:8080/index.html |
| Book Recommendations | http://localhost:8080/books.html |
| Health Check | http://localhost:8080/api/ollama/health |

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/ollama/chat` | POST | Send message to AI model |
| `/api/ollama/books?genre={genre}` | GET | Get book recommendations by genre |
| `/api/ollama/health` | GET | Health check status |
| `/api/ollama/model` | GET | Get current model name |

## Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Ollama server URL
spring.ai.ollama.base-url=http://localhost:11434

# Model to use
spring.ai.ollama.chat.model=deepseek-v3.1:671b-cloud

# Temperature (creativity level)
spring.ai.ollama.chat.options.temperature=0.7
```

## Project Structure

```
springAi/
├── src/
│   ├── main/
│   │   ├── java/com/example/springAi/
│   │   │   ├── controller/
│   │   │   │   └── OllamaController.java    # REST API endpoints
│   │   │   ├── service/
│   │   │   │   └── OllamaService.java       # AI service layer
│   │   │   └── SpringAiApplication.java     # Main app entry
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html                 # Chat UI
│   │       │   └── books.html                 # Book recommendations UI
│   │       └── application.properties         # Config
│   └── test/                                  # Test files
├── pom.xml                                    # Maven dependencies
├── mvnw / mvnw.cmd                            # Maven wrapper
└── README.md                                  # This file
```

## Troubleshooting

### Ollama Connection Error
```
Error: Connection refused to localhost:11434
```
**Fix**: Ensure Ollama is running: `ollama serve`

### Model Not Found
```
Error: model 'deepseek-v3.1:671b-cloud' not found
```
**Fix**: Pull the model: `ollama pull deepseek-v3.1:671b-cloud`

### Port Already in Use
```
Port 8080 is already in use
```
**Fix**: Change port in `application.properties`:
```properties
server.port=8081
```

## License

This is a demo project for Spring AI learning purposes.