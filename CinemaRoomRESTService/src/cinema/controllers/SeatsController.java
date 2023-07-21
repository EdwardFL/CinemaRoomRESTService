package cinema.controllers;

import cinema.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SeatsController {

    private static int totalRows = 9;
    private static int totalColumns = 9;
    private static boolean[][] seatAvailability = new boolean[totalRows][totalColumns];

    private static final String pass = "super_secret";

    private static int totalIncome = 0;
    private static int numberOfAvailableSeats = totalColumns * totalRows;
    private static int numberOfPurchasedTickets = 0;

    static {
        for (int row = 0; row < totalRows; row++) {
            for (int column = 0; column < totalColumns; column++) {
                seatAvailability[row][column] = true;
            }
        }
    }
    private static Map<String, TicketDTO> tokenToTicketMap = new HashMap<>();
    private static int getTicketPrice(int row) {
        return row <= 4 ? 10 : 8;
    }

    @GetMapping("/seats")
    public ResponseEntity<Object> getSeats() {
        List<SeatDTO> availableSeats = new ArrayList<>();
        for (int row = 1; row <= totalRows; row++) {
            for (int column = 1; column <= totalColumns; column++) {
                if (seatAvailability[row - 1][column - 1]) {
                    availableSeats.add(new SeatDTO(row, column, getTicketPrice(row)));
                }
            }
        }

        SeatResponseDTO responseDTO = new SeatResponseDTO(totalRows, totalColumns, availableSeats);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStatistics(@RequestParam(value = "password", required = false) String password) {

        totalIncome = calculateCurrentIncome();
        numberOfAvailableSeats = calculateAvailableSeats();

        if (pass.equals(password) || password != null) {
            StatisticsResponseDTO statisticsResponseDTO = new StatisticsResponseDTO(totalIncome, numberOfAvailableSeats, numberOfPurchasedTickets);
            return ResponseEntity.status(HttpStatus.OK).body(statisticsResponseDTO);
        }

        String errorMessage = "The password is wrong!";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponseDTO(errorMessage));
    }

    @PostMapping("/purchase")
    public ResponseEntity<Object> purchaseTicket(@RequestBody TicketRequestDTO requestDTO) {
        int row = requestDTO.getRow();
        int column = requestDTO.getColumn();

        if (row < 1 || row > totalRows || column < 1 || column > totalColumns) {
            String errorMessage = "The number of a row or a column is out of bounds!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(errorMessage));
        }

        if (!seatAvailability[row - 1][column - 1]) {
            String errorMessage = "The ticket has been already purchased!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(errorMessage));
        }

        seatAvailability[row - 1][column - 1] = false;
        int ticketPrice = getTicketPrice(row);
        String token = UUID.randomUUID().toString();
        TicketDTO ticket = new TicketDTO(row, column, ticketPrice);
        TicketResponseDTO responseDTO = new TicketResponseDTO(token, ticket);
        tokenToTicketMap.put(token, ticket);
        numberOfPurchasedTickets++;
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/return")
    public ResponseEntity<Object> returnTicket(@RequestBody TokenRequestDTO requestDTO) {
        String token = requestDTO.getToken();

        TicketDTO returnedTicket = tokenToTicketMap.get(token);

        if (returnedTicket == null) {
            String errorMessage = "Wrong token!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(errorMessage));
        }

        seatAvailability[returnedTicket.getRow() - 1][returnedTicket.getColumn() - 1] = true;
        ReturnedTicketResponseDTO responseDTO = new ReturnedTicketResponseDTO(returnedTicket);
        tokenToTicketMap.remove(token);
        numberOfPurchasedTickets--;
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    private int calculateCurrentIncome() {
        int currentIncome = 0;
        for (TicketDTO ticket : tokenToTicketMap.values()) {
            currentIncome += ticket.getPrice();
        }
        return currentIncome;
    }

    private int calculateAvailableSeats() {
        int availableSeats = 0;
        for (boolean[] row : seatAvailability) {
            for (boolean isAvailable : row) {
                if (isAvailable) {
                    availableSeats++;
                }
            }
        }
        return availableSeats;
    }
}
