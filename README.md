
# Task Management System

This project is a Task Management System that helps users organize, manage, and track tasks effectively.

## Getting Started

### Prerequisites
- **Docker**: Ensure Docker is installed on your system.
- **IDE**: Any Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or VSCode can be used.

### Running the Application

You can run the application using Docker or directly from an IDE.

#### Option 1: Running with Docker Compose

1. **Build the Docker containers**:  
   Open your terminal in the project directory and run the following command:
   ```bash
   docker-compose build
   ```

2. **Start the application**:  
   After the build completes, start the application with:
   ```bash
   docker-compose up
   ```

3. **Access the Application**:  
   The application will be available on the default server port: **9999**.

#### Option 2: Running with an IDE

1. **Configure Datasource**:
   - Open the `application.yml` file located in the `src/main/resources` directory.
   - Ensure that the datasource settings are configured according to your environment.

2. **Run the Application**:  
   Start the application directly from your IDE using the main class.

3. **Access the Application**:  
   The application will be available on the default server port: **9999**.

### Swagger Documentation

The project includes Swagger for API documentation. After the application is running, you can access the Swagger UI at the following URL:

```
http://localhost:9999/api/swagger/effective/swagger-ui/index.html#
```

## Additional Notes

- **Port Configuration**: If you need to change the default server port, modify the `server-port` value in the `application.yml` file.
- **Environment Variables**: Customize environment variables as needed in the `docker-compose.yml` or `application.yml`.
