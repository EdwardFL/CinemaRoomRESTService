package cinema.dtos;

import lombok.Data;

@Data
public class TicketResponseDTO {

    private String token;
    private TicketDTO ticket;

    public TicketResponseDTO(String token, TicketDTO ticketDTO) {
        this.ticket = ticketDTO;
        this.token = token;
    }

}
