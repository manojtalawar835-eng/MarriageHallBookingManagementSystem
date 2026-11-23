package com.example.marriagehall.repository;

import com.example.marriagehall.entity.MarriageHallBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface MarriageHallRepository extends JpaRepository<MarriageHallBooking, Integer> {
    List<MarriageHallBooking> findByEventDateBetween(LocalDate start, LocalDate end);
    List<MarriageHallBooking> findByHallCapacityGreaterThanEqual(Integer capacity);
    List<MarriageHallBooking> findByBookingStatusIgnoreCase(String status);
    boolean existsByHallNameIgnoreCaseAndEventDateAndBookingStatusNotIgnoreCase(String hallName, LocalDate eventDate, String excludedStatus);
    boolean existsByHallNameIgnoreCaseAndEventDateAndBookingStatusNotIgnoreCaseAndBookingIdNot(
            String hallName, LocalDate eventDate, String excludedStatus, Integer bookingId);
}
