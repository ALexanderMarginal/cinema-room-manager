// Don't delete scanner import
import java.util.Scanner;

class Name {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(createFullName(scanner.next(), scanner.next()));
        System.out.println(createFullName(scanner.next(), scanner.next()));
        System.out.println(createFullName(scanner.next(), scanner.next()));

    }

    //implement your method here
    public static String createFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}