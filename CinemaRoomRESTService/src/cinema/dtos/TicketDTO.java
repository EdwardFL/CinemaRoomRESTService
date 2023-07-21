package cinema.dtos;

import lombok.Data;

@Data
public class TicketDTO {

    private int row;
    private int column;
    private int price;

    public TicketDTO(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }
}
