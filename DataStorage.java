import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static List<UserData> userDataList = new ArrayList<>();

    public static void addUserInput(UserData userData) {
        userDataList.add(userData);
    }

    public static List<UserData> getUserDataList() {
        return userDataList;
    }
}
