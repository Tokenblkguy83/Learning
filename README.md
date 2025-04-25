# Learning Project

A Kotlin-based project for learning and demonstrating various programming concepts and security scanning techniques.

## Features

- ADB-based malicious package detection
- Configurable logging system
- Environment variable-based configuration

## Prerequisites

- JDK 17 or higher
- Gradle 7.6 or higher
- Android Debug Bridge (ADB) for Android device scanning features

## Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/Tokenblkguy83/Learning.git
   cd Learning
   ```

2. Build the project:
   ```
   ./gradlew build
   ```

3. Run the application:
   ```
   ./gradlew run
   ```

4. Run with ADB scanning:
   ```
   ./gradlew run --args="--scan-adb"
   ```

## Configuration

The application can be configured using environment variables or by modifying the `config.properties` file located in `src/main/resources`.

### Important Environment Variables

- `DB_USERNAME` - Database username
- `DB_PASSWORD` - Database password
- `SECURITY_ENCRYPTION_KEY` - Encryption key for sensitive data
- `EMAIL_SMTP_HOST` - SMTP server for email notifications
- `EMAIL_SMTP_USERNAME` - Email username
- `EMAIL_SMTP_PASSWORD` - Email password

## Project Structure

- `src/main/kotlin/com/example` - Main application code
- `src/main/kotlin/Src/ADB` - ADB-related functionality
- `src/main/kotlin/Src/Utils` - Utility classes including logging
- `src/main/resources` - Configuration files

## Security Considerations

- Sensitive configuration values should be provided via environment variables
- Do not commit real credentials to the repository
- The ADB scanning feature requires proper permissions

## License

This project is licensed under the MIT License - see the LICENSE file for details.
