package com.example.marriagehall.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "MarriageHallBookings")
public class MarriageHallBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID")
    private Integer bookingId;

    @Column(name = "CustomerName", nullable = false, length = 100)
    private String customerName;

    @Column(name = "HallName", nullable = false, length = 100)
    private String hallName;

    @Column(name = "EventDate", nullable = false)
    private LocalDate eventDate;

    @Column(name = "HallCapacity", nullable = false)
    private Integer hallCapacity;

    @Column(name = "RentAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal rentAmount;

    @Column(name = "BookingStatus", nullable = false, length = 20)
    private String bookingStatus;

    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getHallName() { return hallName; }
    public void setHallName(String hallName) { this.hallName = hallName; }
    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
    public Integer getHallCapacity() { return hallCapacity; }
    public void setHallCapacity(Integer hallCapacity) { this.hallCapacity = hallCapacity; }
    public BigDecimal getRentAmount() { return rentAmount; }
    public void setRentAmount(BigDecimal rentAmount) { this.rentAmount = rentAmount; }
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
}
