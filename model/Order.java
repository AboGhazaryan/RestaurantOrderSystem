package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private int customerId;
    private BigDecimal totalPrice;
    private Date orderDate;
    private Status status;
   // private List<OrderItem> items = new ArrayList<>();
}
