import java.text.NumberFormat;
import java.util.*;

public class Cinema {
    enum Seats {
        S,
        B
    }

    enum TicketsType {
        DEFAULT,
        CHEAP,
    }

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<TicketsType, Integer> ticketPrice = new HashMap<>() {{
        put(TicketsType.DEFAULT, 10);
        put(TicketsType.CHEAP, 8);
    }};
    private static final ArrayList<ArrayList<Seats>> hall = new ArrayList<>();

    private static int lines;
    private static int seatsInRow;
    private static int numberOfSeats;
    private static int numberOfPurchasedTickets = 0;
    private static int currentIncome = 0;
    private static int totalIncome = 0;

    public static void main(String[] args) {
        createHall();
        menu();
        //calculateProfit();
    }

    private static void menu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        int action = scanner.nextInt();

        switch (action) {
            case 1:
                printCinemaHall();
                break;
            case 2:
                buyTicket();
                break;
            case 3:
                getStatistics();
                break;
            case 0:
                System.exit(0);
        }
    }

    private static void buyTicket() {
        System.out.println("\nEnter a row number:");
        int row = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = scanner.nextInt();

        if (row < 0 || row > lines || seat < 0 || seat > seatsInRow ) {
            System.out.println("Wrong input!");
            buyTicket();
        }

        if (hall.get(row - 1).get(seat - 1) == Seats.B) {
            System.out.println("That ticket has already been purchased!");
            buyTicket();
        }

        int income = getPrice(row);
        System.out.printf("\nTicket price: $%s\n\n", income);
        hall.get(row - 1).set(seat - 1, Seats.B);
        currentIncome += income;
        numberOfPurchasedTickets++;
        menu();
    }

    private static int getPrice(int row) {
        if (numberOfSeats <= 60) {
            return ticketPrice.get(TicketsType.DEFAULT);
        }

        return ticketPrice.get(row > lines / 2 ? TicketsType.CHEAP : TicketsType.DEFAULT);
    }

    private static void createHall() {
        System.out.println("Enter the number of rows:");
        lines = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsInRow = scanner.nextInt();
        numberOfSeats = lines * seatsInRow;

        for (int i = 0; i < lines; i++) {
            ArrayList<Seats> line = new ArrayList<>();
            for (int j = 0; j < seatsInRow; j++) {
                line.add(Seats.S);
            }
            hall.add(line);
        }

        calculateProfit();
    }

    private static void printCinemaHall() {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 0; i < seatsInRow; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();
        for (int i = 0; i < lines; i++) {
            System.out.printf("%d ", i + 1);
            for (int j = 0; j < seatsInRow; j++) {
                System.out.printf("%s ", hall.get(i).get(j));
            }
            System.out.println();
        }
        menu();
    }

    private static void getStatistics() {
        NumberFormat percentageFormat = NumberFormat.getPercentInstance();
        percentageFormat.setMinimumFractionDigits(2);
        System.out.printf("Number of purchased tickets: %s\n", numberOfPurchasedTickets);
        System.out.printf("Percentage: %s%n", percentageFormat.format(currentIncome / totalIncome * 100));
        System.out.printf("Current income: $%s\n", currentIncome);
        System.out.printf("Total income: $%s\n", totalIncome);
        menu();
    }

    private static void calculateProfit() {
        if (numberOfSeats <= 60) {
            totalIncome = numberOfSeats * ticketPrice.get(TicketsType.DEFAULT);
        } else {
            int rowsDefault = lines / 2;
            int rowsCheap = lines - rowsDefault;
            int amountDefault = rowsDefault * ticketPrice.get(TicketsType.DEFAULT);
            int amountCheap = rowsCheap * ticketPrice.get(TicketsType.CHEAP);
            totalIncome = seatsInRow * (amountDefault + amountCheap);
        }
    }
}