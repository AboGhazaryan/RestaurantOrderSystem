package service;

import db.DBConnectionProvider;
import model.Order;
import model.OrderItem;
import model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    public int createOrder(int customerId) {
        String sql = "INSERT INTO `order` (customer_id, total_price) values (?,0)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.executeUpdate();
            ResultSet resultKeys = preparedStatement.getGeneratedKeys();
            if (resultKeys.next()) {
                return resultKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<Order> getOrderByCustomerId(int customerId) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM `order` WHERE customer_id = ? ORDER BY order_date desc";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, customerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setOrderDate(resultSet.getTimestamp("order_date"));
                order.setTotalPrice(resultSet.getBigDecimal("total_price"));
                order.setStatus(Status.valueOf(resultSet.getString("status")));
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public void addDishOrder(int orderId, int dishId, int quantity) {
        String sql = "INSERT INTO order_item(order_id,dish_id,quantity,price) " +
                "SELECT ?,?,?, price * ? FROM DISH WHERE id =?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, dishId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, quantity);
            preparedStatement.setInt(5, dishId);
            int affected = preparedStatement.executeUpdate();
            if (affected == 0) {
                System.out.println("Dish not found. Nothing added.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        updateOrdertotalPrice(orderId);
    }

    public void updateOrdertotalPrice(int orderId) {
        String sql = "UPDATE `order` " +
                "Set total_price = (SELECT COALESCE(SUM(PRICE),0) FROM order_item WHERE order_id = ?) " +
                "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM `order`";
        List<Order> orderList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt("id"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setOrderDate(resultSet.getTimestamp("order_date"));
                order.setTotalPrice(resultSet.getBigDecimal("total_price"));
                order.setStatus(Status.valueOf(resultSet.getString("status")));
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public List<OrderItem> getOrderItemByOrderId(int orderId){
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT oi.id, oi.order_id, oi.dish_id, oi.quantity, oi.price, d.name AS dish_name " +
                "FROM order_item oi " +
                "LEFT JOIN dish d on oi.dish_id = d.id " +
                "WHERE oi.order_id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setInt(1,orderId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                OrderItem item = new OrderItem();
                item.setId(resultSet.getInt("id"));
                item.setOrderId(resultSet.getInt("order_id"));
                item.setDishId(resultSet.getInt("dish_id"));
                item.setQuantity(resultSet.getInt("quantity"));
                item.setPrice(resultSet.getBigDecimal("price"));
                items.add(item);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;

    }

    public void updateOrderStatus(int orderId, Status newStatus){
        String updateSql = "UPDATE `order` SET status = ? WHERE id =?";

        try(PreparedStatement preparedStatement =connection.prepareStatement(updateSql)){
            preparedStatement.setString(1,newStatus.name());
            preparedStatement.setInt(2,orderId);
            preparedStatement.executeUpdate();

            System.out.println("Order status changed to " + newStatus);

        }catch (SQLException e){
            e.printStackTrace();

        }


    }


}
