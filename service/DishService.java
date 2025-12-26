package service;

import db.DBConnectionProvider;
import model.Category;
import model.Dish;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//
public class DishService {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void addDish(Dish dish) {
        String sql = "INSERT INTO dish(name,category,price) VALUES (?,?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2,dish.getCategory().name());
            preparedStatement.setBigDecimal(3,dish.getPrice());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()){
                dish.setId(generatedKeys.getInt(1));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void deleteDish(int id){
        String sql = "DELETE FROM dish WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void changeDish(Dish dish){
        String sql = "UPDATE dish SET name = ?, category = ?, price = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, dish.getName());
            preparedStatement.setString(2,dish.getCategory().name());
            preparedStatement.setBigDecimal(3,dish.getPrice());
            preparedStatement.setInt(4,dish.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Dish getDIshById(int id) {
        String sql = "SELECT * FROM Dish WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setCategory(Category.valueOf(resultSet.getString("category")));
                dish.setPrice(resultSet.getBigDecimal("price"));
                return dish;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Dish> getDish(){
        String sql = "SELECT * FROM dish";
        List<Dish> dishList = new ArrayList<>();
        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setCategory(Category.valueOf(resultSet.getString("category")));
                dish.setPrice(resultSet.getBigDecimal("price"));
                dishList.add(dish);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return dishList;
    }

    public List<Dish> getDishByCategory(Category category){
        String sql = "SELECT * FROM dish Where category = ?";
        List<Dish> dishList = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1,category.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setCategory(Category.valueOf(resultSet.getString("category")));
                dish.setAvailable(resultSet.getBoolean("available"));
                dishList.add(dish);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dishList;
    }
}
