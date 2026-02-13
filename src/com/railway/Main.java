package com.railway;

import com.project.dao.TrainDAO;
import com.project.dao.TrainDAOimpl;
import com.project.model.Train;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TrainDAO dao = new TrainDAOimpl();

        while (true) {

            System.out.println("\n===== ONLINE TICKET BOOKING SYSTEM =====");
            System.out.println("1. View Trains");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    List<Train> trains = dao.getAllTrains();
                    for (Train t : trains) {
                        System.out.println(
                                t.getTrainId() + " | " +
                                        t.getTrainName() + " | " +
                                        t.getSource() + " -> " +
                                        t.getDestination() + " | Seats: " +
                                        t.getTotalSeats()
                        );
                    }
                    break;

                case 2:
                    System.out.print("Enter Train ID: ");
                    int trainId = sc.nextInt();
                    System.out.print("Enter Seats: ");
                    int seats = sc.nextInt();

                    if (dao.bookTicket(trainId, seats))
                        System.out.println("Booking Successful!");
                    else
                        System.out.println("Booking Failed!");
                    break;

                case 3:
                    dao.viewBookings();
                    break;

                case 4:
                    System.out.print("Enter Booking ID: ");
                    int bookingId = sc.nextInt();

                    if (dao.cancelBooking(bookingId))
                        System.out.println("Cancelled Successfully!");
                    else
                        System.out.println("Cancellation Failed!");
                    break;

                case 5:
                    System.out.println("Thank you!");
                    System.exit(0);
            }
        }
    }
}
