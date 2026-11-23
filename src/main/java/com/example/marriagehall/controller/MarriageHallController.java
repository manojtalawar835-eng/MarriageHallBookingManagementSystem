package com.example.marriagehall.controller;

import com.example.marriagehall.dto.HallBookingDTO;
import com.example.marriagehall.entity.MarriageHallBooking;
import com.example.marriagehall.service.MarriageHallService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hall-bookings")
public class MarriageHallController {
    private final MarriageHallService service;
    public MarriageHallController(MarriageHallService service) { this.service = service; }

    @PostMapping public ResponseEntity<MarriageHallBooking> create(@Valid @RequestBody HallBookingDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
    @GetMapping public List<MarriageHallBooking> getAll() { return service.getAll(); }
    @GetMapping("/{id}") public MarriageHallBooking getById(@PathVariable Integer id) { return service.getById(id); }
    @PutMapping("/{id}") public MarriageHallBooking update(@PathVariable Integer id, @Valid @RequestBody HallBookingDTO dto) {
        return service.update(id, dto);
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> cancel(@PathVariable Integer id) {
        service.cancel(id); return ResponseEntity.noContent().build();
    }
    @GetMapping("/reports/dates") public List<MarriageHallBooking> dates(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) { return service.byDates(start, end); }
    @GetMapping("/reports/capacity") public List<MarriageHallBooking> capacity(@RequestParam Integer minCapacity) {
        return service.byCapacity(minCapacity);
    }
    @GetMapping("/reports/status") public List<MarriageHallBooking> status(@RequestParam String status) {
        return service.byStatus(status);
    }
}
