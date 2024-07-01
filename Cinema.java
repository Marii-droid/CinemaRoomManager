package cinema;

import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each rows:");
        int seatsPerRow = sc.nextInt();

        boolean[][] seats = new boolean[rows][seatsPerRow];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < seatsPerRow; column++) {
                seats[row][column] = false;
            }
        }

        boolean keepGoing = false;
        while (!keepGoing) {
            int choice = cinemaMenu(sc);
            switch (choice) {
                case 1:
                    printCinema(seats);
                    break;
                case 2:
                    buySeat(seats, sc);
                    break;
                case 3:
                    showStatistics(seats);
                    break;
                case 0:
                    keepGoing = true;
                    break;
            }
        }
    }

    static void showStatistics(boolean[][] seats) {
        int purchasedTickets = 0;
        int currentIncome = 0;
        int totalIncome = 0;
        for (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            boolean[] row = seats[rowIndex];
            for (int seatIndex = 0; seatIndex < row.length; seatIndex++) {
                boolean seat = row[seatIndex];
                int userRowIndex = rowIndex + 1;
                int price = getPrice(userRowIndex, seats.length, seats.length * row.length);
                totalIncome += price;
                if (seat) {
                    purchasedTickets++;
                    currentIncome += price;
                }
            }
        }
        float purchasedTicketsPercentage = 100.0f * purchasedTickets / (seats.length * seats[0].length);
        System.out.printf(
                """
                        Number of purchased tickets: %d
                        Percentage: %.2f%%
                        Current income: $%d
                        Total income: $%d""", purchasedTickets, purchasedTicketsPercentage, currentIncome, totalIncome);
    }

    static void buySeat(boolean[][] seats, Scanner sc) {
        int rows = seats.length;
        int seatsPerRow = seats[0].length;
        int userRow;
        int userColumn;
        int arrayRow;
        int arrayColumn;
        boolean validSeat = false;
        do {
            System.out.println("Enter a row number:");
            userRow = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            userColumn = sc.nextInt();

            arrayRow = userRow - 1;
            arrayColumn = userColumn - 1;
            if (arrayRow < 0 || arrayRow >= rows || arrayColumn < 0 || arrayColumn >= seatsPerRow) {
                System.out.println("Wrong input!");
            } else if (seats[arrayRow][arrayColumn]) {
                System.out.println("That ticket has already been purchased!");
            } else {
                validSeat = true;
            }
        } while (!validSeat);

        int totalSeats = rows * seatsPerRow;
        int price = getPrice(userRow, rows, totalSeats);
        System.out.println("Ticket price: $" + price);
        seats[arrayRow][arrayColumn] = true;
    }

    private static int getPrice(int userRowIndex, int rows, int totalSeats) {
        boolean isFront = userRowIndex <= rows / 2;
        boolean isCheapSeat = totalSeats > 60 && !isFront;
        return isCheapSeat ? 8 : 10;
    }

    static int cinemaMenu(Scanner sc) {
        System.out.println(
                """

                        1. Show the seats
                        2. Buy a ticket
                        3. Statistics
                        0. Exit
                        """
        );
        return sc.nextInt();
    }

    static void printCinema(boolean[][] seats) {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int seatNumber = 1; seatNumber <= seats[0].length; seatNumber++) {
            System.out.print(" " + seatNumber);
        }
        System.out.println();

        int rowNumber = 1;
        for (boolean[] row : seats) {
            System.out.print(rowNumber++);
            for (boolean seat : row) {
                System.out.print(" " + (seat ? "B" : "S"));
            }
            System.out.println();
        }
    }
}
