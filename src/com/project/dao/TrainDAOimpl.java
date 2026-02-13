package com.project.dao;

import com.project.model.Train;
import com.project.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAOimpl implements TrainDAO {

    @Override
    public List<Train> getAllTrains() {

        List<Train> trains = new ArrayList<>();
        String query = "SELECT * FROM trains";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Train t = new Train();
                t.setTrainId(rs.getInt("train_id"));
                t.setTrainName(rs.getString("train_name"));
                t.setSource(rs.getString("source"));
                t.setDestination(rs.getString("destination"));
                t.setTotalSeats(rs.getInt("total_seats"));
                trains.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return trains;
    }

    @Override
    public synchronized boolean bookTicket(int trainId, int seats) {

        String checkQuery = "SELECT total_seats FROM trains WHERE train_id=?";
        String updateQuery = "UPDATE trains SET total_seats=? WHERE train_id=?";
        String insertQuery =
                "INSERT INTO bookings (user_id, train_id, seats, booking_date) VALUES (?, ?, ?, ?)";

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(checkQuery);
            ps1.setInt(1, trainId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {

                int available = rs.getInt("total_seats");

                if (available >= seats) {

                    PreparedStatement ps2 = con.prepareStatement(updateQuery);
                    ps2.setInt(1, available - seats);
                    ps2.setInt(2, trainId);
                    ps2.executeUpdate();

                    PreparedStatement ps3 = con.prepareStatement(insertQuery);
                    ps3.setInt(1, 1);
                    ps3.setInt(2, trainId);
                    ps3.setInt(3, seats);
                    ps3.setDate(4, new Date(System.currentTimeMillis()));
                    ps3.executeUpdate();

                    con.commit();
                    return true;
                }
            }

            con.rollback();

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void viewBookings() {

        String query = "SELECT * FROM bookings";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n---- BOOKINGS ----");

            while (rs.next()) {
                System.out.println(
                        "Booking ID: " + rs.getInt("booking_id") +
                                " | Train ID: " + rs.getInt("train_id") +
                                " | Seats: " + rs.getInt("seats") +
                                " | Date: " + rs.getDate("booking_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized boolean cancelBooking(int bookingId) {

        String getQuery =
                "SELECT train_id, seats FROM bookings WHERE booking_id=?";
        String deleteQuery =
                "DELETE FROM bookings WHERE booking_id=?";
        String updateQuery =
                "UPDATE trains SET total_seats = total_seats + ? WHERE train_id=?";

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(getQuery);
            ps1.setInt(1, bookingId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {

                int trainId = rs.getInt("train_id");
                int seats = rs.getInt("seats");

                PreparedStatement ps2 = con.prepareStatement(updateQuery);
                ps2.setInt(1, seats);
                ps2.setInt(2, trainId);
                ps2.executeUpdate();

                PreparedStatement ps3 = con.prepareStatement(deleteQuery);
                ps3.setInt(1, bookingId);
                ps3.executeUpdate();

                con.commit();
                return true;
            }

            con.rollback();

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        return false;
    }
}
