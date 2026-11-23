package com.example.marriagehall.service;

import com.example.marriagehall.dto.HallBookingDTO;
import com.example.marriagehall.entity.MarriageHallBooking;
import java.time.LocalDate;
import java.util.List;

public interface MarriageHallService {
    MarriageHallBooking create(HallBookingDTO dto);
    List<MarriageHallBooking> getAll();
    MarriageHallBooking getById(Integer id);
    MarriageHallBooking update(Integer id, HallBookingDTO dto);
    void cancel(Integer id);
    List<MarriageHallBooking> byDates(LocalDate start, LocalDate end);
    List<MarriageHallBooking> byCapacity(Integer capacity);
    List<MarriageHallBooking> byStatus(String status);
}
