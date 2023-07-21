package cinema.dtos;

import java.util.List;

public class SeatResponseDTO {
    private int total_rows;
    private int total_columns;
    private List<SeatDTO> available_seats;

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

    public List<SeatDTO> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats(List<SeatDTO> available_seats) {
        this.available_seats = available_seats;
    }

    public SeatResponseDTO(int total_rows, int total_columns, List<SeatDTO> available_seats) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.available_seats = available_seats;
    }
}
