package service;

import db.DBConnectionProvider;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//
public class CustomerService {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void addCustomer(Customer customer){
        String sql = "INSERT INTO customer(name,phone,email) VALUES ( ?,?,?) ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,customer.getName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.executeUpdate();
            ResultSet resultkeys = preparedStatement.getGeneratedKeys();
            if (resultkeys.next()){
                customer.setId(resultkeys.getInt(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomer(){
        String sql = "SELECT * FROM customer";
        List<Customer> customerList = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setEmail(resultSet.getString("email"));
                customerList.add(customer);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }
}
