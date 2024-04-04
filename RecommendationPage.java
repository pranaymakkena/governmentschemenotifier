import java.util.List;

public class RecommendationPage {
    public static void generateRecommendations() {
        List<UserData> userDataList = DataStorage.getUserDataList();

        for (UserData userData : userDataList) {
            // Check eligibility for Scholarship based on income
            if (userData.getIncome() < 100000) {
                System.out.println("User with Age: " + userData.getAge() +
                                   ", Income: $" + userData.getIncome() +
                                   " is eligible for Scholarship.");
            } else {
                System.out.println("User with Age: " + userData.getAge() +
                                   ", Income: $" + userData.getIncome() +
                                   " is not eligible for Scholarship.");
            }
        }
    }
}
