package com.example.marriagehall.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class HallBookingDTO {
    @NotBlank @Size(max = 100)
    private String customerName;
    @NotBlank @Size(max = 100)
    private String hallName;
    @NotNull @Future
    private LocalDate eventDate;
    @NotNull @Min(51)
    private Integer hallCapacity;
    @NotNull @DecimalMin(value = "0.01")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal rentAmount;
    @NotBlank
    @Pattern(regexp = "(?i)PENDING|CONFIRMED|CANCELLED", message = "Status must be PENDING, CONFIRMED or CANCELLED")
    private String bookingStatus = "PENDING";

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
