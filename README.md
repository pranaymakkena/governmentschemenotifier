# Government Scheme Notifier

This project is a Government Scheme Notifier that fetches the latest scheme notifications from a website and sends email notifications to subscribed users. It is implemented in Java.

## Features

- Scrapes scheme notifications from a website using Jsoup library.
- Sends email notifications to subscribed users using JavaMail API.
- Stores and compares notifications to notify users about new schemes.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

## Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/yourusername/govt-scheme-notification-system.git
2. Change into the project directory:

bash
3. Copy code
cd govt-scheme-notification-system
4. Build the project using Maven:

bash
5. Copy code
mvn clean install
# Configuration
6. Open the config.properties file in a text editor.
7. Update the configuration settings according to your requirements:

8. Set the CSV file path containing user email addresses.
9. Set the sender email and password for sending notifications.
10. Customize any other settings if needed.
# Usage
Run the main Java class to start the application.

bash
Copy code
java -jar target/govt-scheme-notification-system.jar
The application will fetch the latest scheme notifications, compare them with previously saved notifications, and send email notifications to subscribed users.

# Contributing
Contributions are welcome! If you encounter any issues or have suggestions for improvements, please open an issue or submit a pull request.
