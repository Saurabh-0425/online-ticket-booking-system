package com.project.dao;

import com.project.model.Booking;
import com.project.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookingDAOimpl implements BookingDAO {

    @Override
    public boolean insertBooking(Booking booking) {

        String query = "INSERT INTO bookings (user_id, train_id, seats_booked, booking_date) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getTrainId());
            ps.setInt(3, booking.getSeatsBooked());
            ps.setDate(4, booking.getBookingDate());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
