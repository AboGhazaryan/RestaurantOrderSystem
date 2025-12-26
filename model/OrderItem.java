package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private int id;
    private int orderId;
    private int dishId;
    private int quantity;
    private BigDecimal price;

}
