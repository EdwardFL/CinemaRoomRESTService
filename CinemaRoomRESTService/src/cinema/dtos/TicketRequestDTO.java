package cinema.dtos;

import lombok.Data;

@Data
public class TicketRequestDTO {
    private int row;
    private int column;
    private String token;

}
