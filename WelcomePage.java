public class WelcomePage {
    public static void main(String[] args) {
        System.out.println("Welcome to the Government Scheme Notifier!");

        // Direct users to input their data
        DataInputPage.collectUserData();
        
        // Generate recommendations after data input
        RecommendationPage.generateRecommendations();
    }
}
