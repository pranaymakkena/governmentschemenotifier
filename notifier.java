import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNotifier {

    public static List<String> getUsersEmails(String csvFile) {
        List<String> emails = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String email = parts[1].trim();
                emails.add(email);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emails;
    }

    public static void sendEmail(String senderEmail, String senderPassword, List<String> receiverEmails,
                                 String subject, String plainText, String htmlText) {
        String host = "smtp.gmail.com";
        int port = 587;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setSubject(subject);

            // Plain text
            message.setText(plainText);

            // HTML text
            message.setContent(htmlText, "text/html");

            for (String receiverEmail : receiverEmails) {
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
                Transport.send(message);
            }

            System.out.println("Emails sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static String notificationsToHTML(List<String[]> notifications) {
        StringBuilder htmlText = new StringBuilder();
        htmlText.append("<strong>Latest Schemes</strong><br><br>");

        for (String[] notification : notifications) {
            String link = notification[0];
            String name = notification[1];

            String formattedLink = String.format("<a href=\"%s\">%s</a><br>", link, name.capitalize());
            htmlText.append(formattedLink);
        }

        return htmlText.toString();
    }

    public static boolean notifyUsers(String csvFile, List<String[]> notifications, String senderEmail,
                                      String senderPassword) {
        List<String> emails = getUsersEmails(csvFile);

        if (emails.isEmpty()) {
            System.err.println("Emails file not found.");
            System.exit(2);
        }

        String htmlText = notificationsToHTML(notifications);
        String subject = "Govt. Scheme Notifier!!";

        sendEmail(senderEmail, senderPassword, emails, subject, "", htmlText);

        return true;
    }

    // Rest of the code...
}
