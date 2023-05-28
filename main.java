import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.Gson;

public class Main {
    private static List<String[]> allNotifications = new ArrayList<>();

    public static Map<String, Object> getLinks(String file) {
        File jsonFile = new File(file);
        if (!jsonFile.exists()) {
            System.out.println("Links File not found!");
            System.exit(2);
        } else {
            System.out.println("Using " + file + " file for links.");
        }
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(jsonFile);
            Map<String, Object> data = gson.fromJson(reader, Map.class);
            return data;
        } catch (IOException e) {
            System.out.println("Json file is in Invalid format.");
            System.exit(2);
        }
        return null;
    }

    public static void createDir(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            System.out.println(path + " directory created.");
            directory.mkdirs();
        } else {
            System.out.println(path + " Already Exists.");
        }
    }

    public static void dumpDictData(String path, Map<String, Object> data, int indent) {
        File file = new File(path);
        if (file.exists()) {
            System.out.println("Overwriting " + path + " file with new data.");
        } else {
            System.out.println("Writing data to " + path);
        }
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.out.println("Invalid Json Formatted data.");
            System.exit(2);
        }
    }

    public static String urlToJsonFileName(String url) {
        return url.replace("http://", "").replace("https://", "").replace("/", "-") + ".json";
    }

    public static int getLastPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element lastPageLink = doc.selectFirst("li.last a");
        String lastPageUrl = lastPageLink.attr("href");
        String[] splitUrl = lastPageUrl.split("=");
        return Integer.parseInt(splitUrl[splitUrl.length - 1]);
    }

    public static void getLatestNotifications(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element contentBlock = doc.selectFirst("div#content");
        Elements notifications = contentBlock.select("div.item-list li");
        for (Element notification : notifications) {
            Element aNotification = notification.selectFirst("a");
            String[] notificationData = {aNotification.absUrl("href"), aNotification.text()};
            allNotifications.add(notificationData);
        }
    }

    public static Map<String, Object> getSavedNotifications(String filePath) {
        File jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            System.out.println(filePath + " saved notifications file not found.");
            return null;
        }
        System.out.println(filePath + " saved notifications found.");
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(jsonFile);
            Map<String, Object> data = gson.fromJson(reader, Map.class);
            return data;
        } catch (IOException e) {
            System.out.println(filePath + " json data is in invalid format.");
            return null;
        }
    }

    public static void main(String[] args) {
        String csvFile = "users.csv";
        String senderMail = "yourmail@gmail.com";
        String senderPasswd = "your_app_password";
        String savedFilePath = "notification_data/www.india.gov.in-my-government-schemes.json";
        String dataDir = System.getProperty("user.dir") + File.separator + "notification_data";
        String baseLink = "https://www.india.gov.in/my-government/schemes";

        createDir(dataDir);

        Map<String, Object> savedNotifications = getSavedNotifications(savedFilePath);

        int lastPage;
        try {
            lastPage = getLastPage(baseLink);
        } catch (IOException e) {
            System.out.println("Error retrieving the last page: " + e.getMessage());
            return;
        }

        for (int pageNo = 0; pageNo <= lastPage; pageNo++) {
            String pageLink = baseLink + "?page=" + pageNo;
            try {
                getLatestNotifications(pageLink);
                System.out.println(pageLink + " notifications loaded.");
            } catch (IOException e) {
                System.out.println("Error retrieving notifications from " + pageLink + ": " + e.getMessage());
            }
        }

        if (savedNotifications != null) {
            List<String[]> newNotifications = new ArrayList<>();
            List<String[]> savedNotificationList = (List<String[]>) savedNotifications.get("urls");
            for (String[] notification : allNotifications) {
                if (!savedNotificationList.contains(notification)) {
                    newNotifications.add(notification);
                }
            }

            Map<String, Object> newNotificationsData = new HashMap<>();
            newNotificationsData.put("urls", newNotifications);
            dumpDictData(dataDir + File.separator + "new_notifications.json", newNotificationsData, 4);

            if (newNotifications.size() > 0) {
                System.out.println("Informing users about the new schemes.");
                notifyUsers(csvFile, newNotifications, senderMail, senderPasswd);
            } else {
                System.out.println("No new schemes were published. Informing them about all the schemes available.");
                notifyUsers(csvFile, allNotifications, senderMail, senderPasswd);
            }
        } else {
            System.out.println("Scraping data for the first time. Informing Users about all currently available schemes.");
            notifyUsers(csvFile, allNotifications, senderMail, senderPasswd);
        }

        Map<String, Object> allNotificationsData = new HashMap<>();
        allNotificationsData.put("urls", allNotifications);
        dumpDictData(dataDir + File.separator + urlToJsonFileName(baseLink), allNotificationsData, 4);
    }

    public static void notifyUsers(String csvFile, List<String[]> notifications, String senderEmail, String senderPasswd) {
        // TODO: Implement the notification logic
    }
}
