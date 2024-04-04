# Government Scheme Notifier

This project is a Government Scheme Notifier that fetches the latest scheme notifications through backend logic and lets users know their eligible schemes based on their input. It is implemented in Java.

## Features

- Stores and compares data to notify users about new schemes.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/govt-scheme-notification-system.git
2. Change into the project directory:

    ```bash
    cd govt-scheme-notification-system

3. Build the project using Maven:

   ```bash
   mvn clean install

# Configuration
4. Open the config.properties file in a text editor.
5. Update the configuration settings according to your requirements:

6. Set the CSV file path containing user email addresses.
7. Set the sender email and password for sending notifications.
8. Customize any other settings if needed.

# Usage
Run the main Java class to start the application.

```bash
java -jar target/govt-scheme-notification-system.jar
