package com.railway;

import com.project.dao.TrainDAO;

public class BookingThread extends Thread {

    private TrainDAO dao;
    private int trainId;
    private int seats;

    public BookingThread(TrainDAO dao, int trainId, int seats) {
        this.dao = dao;
        this.trainId = trainId;
        this.seats = seats;
    }

    @Override
    public void run() {

        boolean result = dao.bookTicket(trainId, seats);

        if (result) {
            System.out.println(Thread.currentThread().getName() +
                    " booked " + seats + " seats successfully.");
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " booking failed.");
        }
    }
}
