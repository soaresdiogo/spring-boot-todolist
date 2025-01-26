# TodoList Spring Boot Project
## 🚀 Technologies
- [Spring Boot](https://spring.io/projects/spring-boot) - Java Framework
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) - Programming Language
- [Lombok](https://projectlombok.org/) - Boilerplate Reduction
- [JPA](https://spring.io/projects/spring-data-jpa) - Persistence Framework
- [H2 Database](https://www.h2database.com/html/main.html) - In-Memory Database
- [BCrypt](https://en.wikipedia.org/wiki/Bcrypt) - Password Hashing
- [Swagger](https://swagger.io/) - API Documentation

## 🏗️ Project Structure
```
todolist-project/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
├── pom.xml
└── README.md
```

## 📋 Prerequisites
- Java Development Kit (JDK) 17
- Maven
- Git

## ⚙️ Project Setup

### 1. Clone the Repository
```bash
git clone [your-repository-url]
cd todolist-project
```

### 2. Build the Project
```bash
# Build without running tests
mvn clean package -DskipTests

# Build with tests
mvn clean package
```

### 3. Run the Application
```bash
# Using Maven
mvn spring-boot:run

# Or using the generated JAR
java -jar target/todolist-1.0.0.jar
```

## 🗄️ Database Configuration
The project uses H2 in-memory database. Configuration is in `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:todolistdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=admin
spring.datasource.password=admin
spring.h2.console.enabled=true
```

### Accessing H2 Console
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:todolistdb`
- Username: `admin`
- Password: `admin`

## 📚 API Documentation
Swagger UI is available at:
- `http://localhost:8080/swagger-ui.html`

## 🔒 Authentication
The application uses BCrypt for password hashing, ensuring secure user authentication.

## 🚀 Deployment
### Docker (Optional)
```bash
# Build Docker image
docker build -t todolist-app .

# Run Docker container
docker run -p 8080:8080 todolist-app
```

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


## 🛠️ Troubleshooting
- Ensure JDK 17 is correctly installed
- Check Maven settings
- Verify database connectivity

Made with ♥ by [Diogo Soares]
