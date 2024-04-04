import java.util.Scanner;

public class DataInputPage {
    public static void collectUserData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your age:");
        int age = scanner.nextInt();

        System.out.println("Enter your income:");
        double income = scanner.nextDouble();

        // Create a UserData object and pass it to DataStorage for storage
        UserData userData = new UserData(age, income);
        DataStorage.addUserInput(userData);

        scanner.close(); 
    }
}
