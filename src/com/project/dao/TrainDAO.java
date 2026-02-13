package com.project.dao;

import com.project.model.Train;
import java.util.List;

public interface TrainDAO {

    List<Train> getAllTrains();

    boolean bookTicket(int trainId, int seats);

    void viewBookings();

    boolean cancelBooking(int bookingId);
}
