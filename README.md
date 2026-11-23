# Marriage Hall Booking Management System

A full-stack mini-project built with Java 17, Spring Boot 3.3.5, Spring Data JPA, MySQL, Bootstrap 5 and Vanilla JavaScript.

## Requirements
- JDK 17+
- Maven 3.8+
- MySQL Server 8+
- Browser with internet access for Bootstrap/Icon CDN assets

## 1. Create the database
Run in MySQL Workbench:
```sql
CREATE DATABASE marriage_hall_db;
```
The application uses `spring.jpa.hibernate.ddl-auto=update` and creates the `MarriageHallBookings` table on startup.

## 2. Configure database credentials
Edit `src/main/resources/application.properties`, or set environment variables:
- `DB_URL` (default: `jdbc:mysql://localhost:3306/marriage_hall_db?...`)
- `DB_USERNAME` (default: `root`)
- `DB_PASSWORD` (default: empty)

Never commit real database passwords to a public repository.

## 3. Run
From the project root:
```bash
mvn clean spring-boot:run
```
Open: http://localhost:8080/

The UI is served by Spring Boot from `src/main/resources/static/`; do not open the HTML files directly from the filesystem.

## 4. API endpoints
Base URL: `/api/v1/hall-bookings`

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/v1/hall-bookings` | Create booking (201) |
| GET | `/api/v1/hall-bookings` | List bookings (200) |
| GET | `/api/v1/hall-bookings/{id}` | Get booking (200/404) |
| PUT | `/api/v1/hall-bookings/{id}` | Update booking (200) |
| DELETE | `/api/v1/hall-bookings/{id}` | Soft cancel (204) |
| GET | `/api/v1/hall-bookings/reports/dates?start=2027-01-01&end=2027-12-31` | Date report |
| GET | `/api/v1/hall-bookings/reports/capacity?minCapacity=200` | Capacity report |
| GET | `/api/v1/hall-bookings/reports/status?status=CONFIRMED` | Status report |

Cancellation is a soft cancellation: the record remains in MySQL with `BookingStatus=CANCELLED`, and cancelled records do not block a new reservation.

## 5. Sample JSON
Use Postman → POST → Body → raw → JSON:
```json
{
  "customerName": "Ananya Sharma",
  "hallName": "Evergreen Grand Hall",
  "eventDate": "2027-12-20",
  "hallCapacity": 500,
  "rentAmount": 85000.00,
  "bookingStatus": "PENDING"
}
```
Choose a future date when testing. The same hall cannot have more than one non-cancelled booking on the same date. Valid statuses: `PENDING`, `CONFIRMED`, `CANCELLED`.

## 6. Postman test sequence
1. POST the sample JSON. Expect 201 and a generated `bookingId`.
2. GET `/api/v1/hall-bookings`. Expect an array containing the new record.
3. GET `/api/v1/hall-bookings/1` (replace `1` with returned ID).
4. PUT `/api/v1/hall-bookings/{id}` with the full JSON body.
5. Test conflict: POST same hall and date with a different customer. Expect 409.
6. Test invalid data: capacity 50 or rent 0. Expect 400.
7. GET each report URL above. Use a valid future date range and capacity > 50.
8. DELETE `/api/v1/hall-bookings/{id}`. Expect 204; verify the row is retained with status `CANCELLED`.

## 7. Architecture
- `controller`: REST request/response layer
- `service`: business rules and transactional operations
- `repository`: Spring Data JPA persistence
- `entity`: MySQL entity mapping
- `dto`: validated API input model
- `exception`: centralized error responses

## Notes
- Dashboard revenue is calculated from CONFIRMED booking rent amounts. This is a booking-value total, not a payment ledger or proof of collected payments.
- Dashboard and report data are fetched from the REST API; there are no hardcoded booking arrays.
- This package includes source code and setup instructions. Runtime screenshots and live endpoint verification must be produced after connecting it to your own MySQL installation; no local database credentials or server were available during package creation.
