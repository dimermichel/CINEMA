package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int numberOfRows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numberOfSeats = scanner.nextInt();
        char[][] cinema = new char[numberOfRows][numberOfSeats];
        for (char[] chars : cinema) {
            Arrays.fill(chars, 'S');
        }

        int menuSelection = selectMenu(scanner);

        while (menuSelection != 0) {
            switch (menuSelection) {
                case 1:
                    printCinema(cinema);
                    break;
                case 2:
                    buyTicket(scanner, cinema, numberOfRows, numberOfSeats);
                    break;
                case 3:
                    statistics(cinema, numberOfRows, numberOfSeats);
                    break;
                default:
                    System.out.println("Invalid selection");
                    break;
            }
            menuSelection = selectMenu(scanner);
        }
        scanner.close();
    }

    public static int selectMenu(Scanner scannerMenu) {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        return scannerMenu.nextInt();
    }

    public static void buyTicket( Scanner scanner, char[][] cinema, int numberOfRows, int numberOfSeats){
        int[] userInput = getTicketsUserInput(scanner, cinema, numberOfRows, numberOfSeats);
        System.out.println();
        System.out.println("Ticket price: $" + calculateTicketPrice(numberOfRows, numberOfSeats, userInput[0]));
        cinema[userInput[0] - 1][userInput[1] - 1] = 'B';
    }

    public static int[] getTicketsUserInput(Scanner scanner, char[][] cinema, int numberOfRows, int numberOfSeats) {
        boolean validInput = false;
        int rowNumber = 0, seatNumber = 0;
        System.out.println();
        while (!validInput) {
            System.out.println("Enter a row number:");
            rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatNumber = scanner.nextInt();
            if ((rowNumber < 0 || rowNumber > numberOfRows) || (seatNumber < 0 || seatNumber > numberOfSeats)) {
                System.out.println();
                System.out.println("Wrong input!");
                System.out.println();
            } else {
                if (isSeatFree(cinema, rowNumber, seatNumber)) validInput = true;
            }
        }
        return new int[]{rowNumber, seatNumber};
    }

    public static boolean isSeatFree(char[][] cinema, int rowNumber, int seatNumber){
        if (cinema[rowNumber -1][seatNumber -1] == 'B') {
            System.out.println();
            System.out.println("That ticket has already been purchased!");
            System.out.println();
            return false;
        }
        return true;
    }

    public static void printCinema(char[][] cinema) {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int i = 1; i <= cinema[0].length; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        for (int i = 0; i < cinema.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(" " + cinema[i][j]);
            }
            System.out.println();
        }
    }

    public static int calculateTicketPrice(int numberOfRows, int numberOfSeats, int row) {
        int totalSeats = numberOfRows * numberOfSeats;
        if (totalSeats <= 60) {
            return  10;
        } else {
            if (row <= numberOfRows / 2) {
                return 10;
            } else {
                return 8;
            }
        }
    }

    public static void statistics(char[][] cinema, int numberOfRows, int numberOfSeats) {
        int purchasedTickets = 0;
        int currentIncome = 0;
        int totalIncome = 0;
        int totalSeats = numberOfRows * numberOfSeats;
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                if (cinema[i][j] == 'B') {
                    purchasedTickets++;
                    currentIncome += calculateTicketPrice(numberOfRows, numberOfSeats, i + 1);
                }
            }
        }
        if (totalSeats <= 60) {
            totalIncome = totalSeats * 10;
        } else {
            totalIncome = (numberOfRows / 2) * numberOfSeats * 10 + (numberOfRows - numberOfRows / 2) * numberOfSeats * 8;
        }
        double percentage = (double) purchasedTickets / totalSeats * 100;
        System.out.println();
        System.out.println("Number of purchased tickets: " + purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

}