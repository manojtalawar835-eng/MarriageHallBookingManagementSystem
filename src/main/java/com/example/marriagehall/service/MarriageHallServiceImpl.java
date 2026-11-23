package com.example.marriagehall.service;

import com.example.marriagehall.dto.HallBookingDTO;
import com.example.marriagehall.entity.MarriageHallBooking;
import com.example.marriagehall.exception.*;
import com.example.marriagehall.repository.MarriageHallRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MarriageHallServiceImpl implements MarriageHallService {
    private static final Set<String> STATUSES = Set.of("PENDING", "CONFIRMED", "CANCELLED");
    private final MarriageHallRepository repository;
    public MarriageHallServiceImpl(MarriageHallRepository repository) { this.repository = repository; }

    private void validate(HallBookingDTO dto, Integer currentId) {
        if (dto.getCustomerName() == null || dto.getCustomerName().isBlank()) throw new IllegalArgumentException("Customer name is required.");
        if (dto.getHallName() == null || dto.getHallName().isBlank()) throw new IllegalArgumentException("Hall name is required.");
        if (dto.getEventDate() == null || !dto.getEventDate().isAfter(LocalDate.now())) throw new IllegalArgumentException("Event date must be in the future.");
        if (dto.getHallCapacity() == null || dto.getHallCapacity() <= 50) throw new IllegalArgumentException("Hall capacity must be greater than 50.");
        if (dto.getRentAmount() == null || dto.getRentAmount().signum() <= 0) throw new IllegalArgumentException("Rent amount must be greater than zero.");
        String status = dto.getBookingStatus() == null ? "" : dto.getBookingStatus().toUpperCase();
        if (!STATUSES.contains(status)) throw new IllegalArgumentException("Status must be PENDING, CONFIRMED or CANCELLED.");
        if (!"CANCELLED".equals(status)) {
            boolean conflict = currentId == null
                ? repository.existsByHallNameIgnoreCaseAndEventDateAndBookingStatusNotIgnoreCase(dto.getHallName().trim(), dto.getEventDate(), "CANCELLED")
                : repository.existsByHallNameIgnoreCaseAndEventDateAndBookingStatusNotIgnoreCaseAndBookingIdNot(dto.getHallName().trim(), dto.getEventDate(), "CANCELLED", currentId);
            if (conflict) throw new BookingConflictException("This hall already has an active booking on the selected date.");
        }
    }
    private void copy(HallBookingDTO dto, MarriageHallBooking entity) {
        entity.setCustomerName(dto.getCustomerName().trim());
        entity.setHallName(dto.getHallName().trim());
        entity.setEventDate(dto.getEventDate());
        entity.setHallCapacity(dto.getHallCapacity());
        entity.setRentAmount(dto.getRentAmount());
        entity.setBookingStatus(dto.getBookingStatus().toUpperCase());
    }
    public MarriageHallBooking create(HallBookingDTO dto) {
        validate(dto, null); MarriageHallBooking entity = new MarriageHallBooking(); copy(dto, entity);
        return repository.save(entity);
    }
    @Transactional(readOnly = true) public List<MarriageHallBooking> getAll() { return repository.findAll(); }
    @Transactional(readOnly = true) public MarriageHallBooking getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Booking " + id + " was not found."));
    }
    public MarriageHallBooking update(Integer id, HallBookingDTO dto) {
        MarriageHallBooking entity = getById(id); validate(dto, id); copy(dto, entity); return repository.save(entity);
    }
    public void cancel(Integer id) {
        MarriageHallBooking entity = getById(id); entity.setBookingStatus("CANCELLED"); repository.save(entity);
    }
    @Transactional(readOnly = true) public List<MarriageHallBooking> byDates(LocalDate start, LocalDate end) {
        if (start == null || end == null || end.isBefore(start)) throw new IllegalArgumentException("Provide valid start and end dates.");
        return repository.findByEventDateBetween(start, end);
    }
    @Transactional(readOnly = true) public List<MarriageHallBooking> byCapacity(Integer capacity) {
        if (capacity == null || capacity <= 50) throw new IllegalArgumentException("Minimum capacity must be greater than 50.");
        return repository.findByHallCapacityGreaterThanEqual(capacity);
    }
    @Transactional(readOnly = true) public List<MarriageHallBooking> byStatus(String status) {
        if (status == null || !STATUSES.contains(status.toUpperCase())) throw new IllegalArgumentException("Status must be PENDING, CONFIRMED or CANCELLED.");
        return repository.findByBookingStatusIgnoreCase(status.toUpperCase());
    }
}
