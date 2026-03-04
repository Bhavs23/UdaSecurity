# UdaSecurity - Multi-Module Java Application

A comprehensive Java application for security system deployment featuring modular architecture with separate services for security monitoring and image processing.

##  Project Structure

This is a **multi-module Maven project** with the following structure:

```
UdaSecurity (Root)
│
├──  security-service/              # Security Service Module
│   ├──  src/
│   │   ├──  main/
│   │   │   ├──  java/             # Java source code
│   │   │   └──  resources/        # Configuration files
│   │   └──  test/
│   │       └──  java/             # Unit tests
│   ├──  pom.xml                   # Security module Maven config
│   └──  dependency-reduced-pom.xml # Maven assembly helper
│
├──  image-service/                # Image Service Module
│   ├──  src/
│   │   ├── main/
│   │   │   ├── java/             # Java source code
│   │   │   └── resources/        # Configuration files
│   │   └── test/
│   │       └── java/             # Unit tests
│   ├── pom.xml                   # Image module Maven config
│   └── dependency-reduced-pom.xml # Maven assembly helper
│
├── .qodo/                        # Code quality/documentation (auto-generated)
│
├── pom.xml                       # Parent POM (root configuration)
├── .gitignore                    # Git ignore rules
├── README.md                     # This file
└── executable_jar.png            # Reference image (can delete)
```

##  Modules Overview

### Security Service (security-service)
**Purpose:** Core security system management

**Features:**
- Sensor event monitoring and processing
- Alert generation and management
- System state management
- Event logging and tracking
- Integration with image service

**Key Files:**
- `src/main/java/` - Security service implementation
- `src/test/java/` - Unit tests for security module
- `pom.xml` - Module-specific dependencies

---

### Image Service (image-service)
**Purpose:** Image capture and processing integration

**Features:**
- Image processing and analysis
- Integration with security events
- Image storage and retrieval
- Buffered image handling
- External API integration (if applicable)

**Key Files:**
- `src/main/java/` - Image service implementation
- `src/test/java/` - Unit tests for image module
- `pom.xml` - Module-specific dependencies

---

##  Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK) 8** or higher
  - Check: `java -version`
  
- **Maven 3.6** or higher
  - Check: `mvn -v`
  
- **IntelliJ IDEA** (Community or Professional Edition)
  - Download from: https://www.jetbrains.com/idea/
  
- **Git** for version control
  - Check: `git --version`

### Installation & Setup

#### 1. Clone the Repository

```bash
git clone https://github.com/YourUsername/UdaSecurity.git
cd UdaSecurity
```

#### 2. Open in IntelliJ IDEA

1. Open IntelliJ IDEA
2. Go to **File** → **Open**
3. Navigate to the `UdaSecurity` folder
4. Click **Open as Project**
5. Wait for IntelliJ to index the files

#### 3. Build the Project

From the root directory, run:

```bash
mvn clean install
```

This will:
- Clean previous builds
- Download all dependencies
- Compile all modules
- Run all unit tests
- Package the modules

#### 4. Verify the Build

```bash
mvn clean compile
```

Run tests:

```bash
mvn test
```

## Build Commands

### Clean and Build All Modules
```bash
mvn clean install
```

### Compile Only (No Tests)
```bash
mvn clean compile
```

### Run All Tests
```bash
mvn test
```

### Build Specific Module
```bash
# Build only security-service
mvn clean install -pl security-service

# Build only image-service
mvn clean install -pl image-service
```

### Skip Tests and Build Faster
```bash
mvn clean install -DskipTests
```

### Force Update Dependencies
```bash
mvn clean compile -U
```

## 📚 Dependencies

Key dependencies managed in parent pom.xml:

```xml
<!-- JUnit for Unit Testing -->
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
</dependency>

<!-- Logging Framework -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>

<!-- Miglayout for GUI Components -->
<dependency>
    <groupId>com.miglayout</groupId>
    <artifactId>miglayout-swing</artifactId>
    <version>11.0</version>
</dependency>
```

##  Testing

### Run All Tests
```bash
mvn test
```

### Run Tests for Specific Module
```bash
mvn test -pl security-service
mvn test -pl image-service
```

### Run Specific Test Class
```bash
mvn test -Dtest=SecurityServiceTest
```

## Development Progress

- [x] Step 0: Downloaded starter code and opened in IntelliJ
- [x] Step 1: Updated pom.xml with dependencies
- [x] Step 2: Split into multi-module structure
  - [x] Created parent POM
  - [x] Created security-service module
  - [x] Created image-service module
  - [x] Organized folder structure
- [x] Step 3: Built project successfully with `mvn clean install`
- [x] Step 4: Created module descriptors and configuration files
- [x] GitHub: Uploaded to GitHub repository

##  Technologies Used

- **Java 8+** - Core programming language
- **Maven 3.6+** - Build and dependency management
- **JUnit 4** - Unit testing framework
- **Log4j** - Logging framework
- **Miglayout** - GUI framework for Swing components

##  How to Contribute

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

##  License

This project is open source and available under the MIT License.

##  Author

Vunnam Bhavika

---

**Created as part of the UdaSecurity Java Application Deployment course**

For questions or issues, please open a GitHub issue in this repository.
