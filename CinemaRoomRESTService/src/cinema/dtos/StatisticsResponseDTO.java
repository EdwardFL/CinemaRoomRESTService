package cinema.dtos;

import lombok.Data;

@Data
public class StatisticsResponseDTO {
    private int current_income;
    private int number_of_available_seats;
    private int number_of_purchased_tickets;

    public StatisticsResponseDTO(int current_income, int number_of_available_seats, int number_of_purchased_tickets) {
        this.current_income = current_income;
        this.number_of_available_seats = number_of_available_seats;
        this.number_of_purchased_tickets = number_of_purchased_tickets;
    }
}
