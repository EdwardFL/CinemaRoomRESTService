package cinema.dtos;

import lombok.Data;

@Data
public class ReturnedTicketResponseDTO {
    private TicketDTO returned_ticket;
    public ReturnedTicketResponseDTO(TicketDTO returned_ticket) {
        this.returned_ticket = returned_ticket;
    }
}
