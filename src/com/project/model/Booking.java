package com.project.model;

import java.sql.Date;

public class Booking {

    private int userId;
    private int trainId;
    private int seatsBooked;
    private Date bookingDate;

    public Booking(int userId, int trainId, int seatsBooked, Date bookingDate) {
        this.userId = userId;
        this.trainId = trainId;
        this.seatsBooked = seatsBooked;
        this.bookingDate = bookingDate;
    }

    public int getUserId() { return userId; }
    public int getTrainId() { return trainId; }
    public int getSeatsBooked() { return seatsBooked; }
    public Date getBookingDate() { return bookingDate; }
}
