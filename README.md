# UdaSecurity - Multi-Module Java Application

A comprehensive Java application for security system deployment featuring modular architecture with separate services for security monitoring and image processing.

##  Project Structure

This is a **multi-module Maven project** with the following structure:
```
catpoint-parent/
├── security-service/        # Security service module
├── image-service/           # Image processing service module
└── pom.xml                  # Parent POM
```

## Getting Started

### Prerequisites
- **Java 8** or higher
- **Maven 3.6** or higher
- **IntelliJ IDEA** (recommended)

### Build Instructions

1. Clone the repository:
```bash
git clone https://github.com/YourUsername/UdaSecurity.git
cd UdaSecurity
```

2. Build the project:
```bash
mvn clean install
```

3. Run tests:
```bash
mvn test
```

## Project Modules

**Security Service (security-service)**
- Handles security system management
- Event monitoring and sensor handling
- Alert generation and management

**Image Service (image-service)**
- Image capture and processing
- Integration with security events
- Storage and retrieval of image data

## Build Commands

### Clean and Build
```bash
mvn clean install
```

### Compile Only
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

## Technologies Used

- **Java** - Core programming language
- **Maven** - Build and dependency management
- **JUnit** - Unit testing
- **Log4j** - Logging framework

## License

This project is open source and available under the MIT License.

## Author

Vunnam Bhavika