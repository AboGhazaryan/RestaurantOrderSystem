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
    private Order order;
    private Dish dish;
    private int quantity;
    private BigDecimal price;

}
