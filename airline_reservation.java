package pog;
import java.util.Scanner;

public class airline_reservation {
   static int seats[] = new int[11];
   static Scanner sc = new Scanner(System.in);

public static void main(String[] args) 
{
    System.out.println("** Welcome to XYZ Airlines. **");
    while (true)
    {
        System.out.println("1. View available seats");
        System.out.println("2. Reserve a seat");
        System.out.println("3. Exit");
        System.out.println("Please select an option: ");
        int choice = sc.nextInt();
        switch (choice) 
        {
            case 1:
                displaySeats();
                break;
            case 2:
                reserveSeat();
                break;
            case 3:
                System.out.println("Thank you for using XYZ Airlines.");
                System.exit(0);
        } 
    }
}

public static void displaySeats() 
{
    System.out.println("Seats:");
    for (int i = 1; i <= 10; i++) 
    {
        System.out.println("Seat " + i + ": " + (seats[i] == 0 ? "Available" : "Reserved"));
    }
} 
public static void reserveSeat() 
{
    System.out.println("Enter seat number to reserve: ");
    int seat = sc.nextInt();
    
    if (seats[seat] == 0) {
        seats[seat] = 1;
        System.out.println("Seat " + seat + " reserved successfully.");
    } else {
        System.out.println("Seat " + seat + " is already reserved");
    }
   }
}