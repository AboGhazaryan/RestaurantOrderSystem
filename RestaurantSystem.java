import model.*;
import service.CustomerService;
import service.DishService;
import service.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class RestaurantSystem implements Commands {

    public static Scanner scanner = new Scanner(System.in);
    public static DishService dishService = new DishService();
    public static CustomerService customerService = new CustomerService();
    public static OrderService orderService = new OrderService();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printCommands();
            String command = scanner.nextLine();

            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_DISH:
                    addDish();
                    break;
                case CHANGE_DISH:
                    changeDish();
                    break;
                case REMOVE_DISH:
                    printDish();
                    System.out.println("Please input id to remove the dish");
                    int id = Integer.parseInt(scanner.nextLine());
                    dishService.deleteDish(id);
                    System.out.println("This dish  id=" + id + " has been removed");
                    break;
                case ADD_CUSTOMER:
                    addCostumer();
                    break;
                case SHOW_CUSTOMERS:
                    printCustomer();
                    break;
                case CREATE_NEW_ORDERS:
                    createOrderFlow();
                    break;
                case ADD_DISHES_THE_ORDER:
                    addDishToOrder();
                    break;
                case SHOW_ALL_ORDERS:
                    printOrders();
                    break;
                case SHOW_CUSTOMER_ORDER_HISTORY:
                    showCustomerOrdersHistory();
                    break;
                case SHOW_ORDER_DETAILS:
                    showOrderItems();
                    break;
                case CHANGE_ORDER_STATUS:
                   changeOrderStatus();
                    break;
                case SHOW_MENU_BY_CATEGORY:
                    showMenubyCategory();
                    break;

                default:
                    System.out.println("Invalid command");
            }

        }

    }

    private static void changeOrderStatus() {
        printOrders();
        System.out.println("Enter order Id");
        int orderId = Integer.parseInt(scanner.nextLine());
        System.out.println("Choose new status:");
        System.out.println("1-> PREPARING,2-> READY,3 -> DELIVERED");
        int  choice = Integer.parseInt(scanner.nextLine());
        Status newStatus;
        switch (choice){
            case 1: newStatus = Status.PREPARING;break;
            case 2: newStatus = Status.READY;break;
            case 3: newStatus = Status.DELIVERED;break;
            default:
                System.out.println("Invalid choice");
                return;
        }
        orderService.updateOrderStatus(orderId,newStatus);
    }

    private static void showOrderItems() {
        printOrders();
        System.out.println("Enter order ID to se details:");
        int orderId = Integer.parseInt(scanner.nextLine());
        List<OrderItem> orderItemByOrderId = orderService.getOrderItemByOrderId(orderId);
        for (OrderItem item : orderItemByOrderId) {
            System.out.println(item);
        }
    }

    private static void showMenubyCategory() {
        System.out.println("Enter category to viw menu");
        System.out.println("Category: ");
        Category[] values = Category.values();
        for (Category value : values) {
            System.out.println(value + ",");
        }
        String categryInput = scanner.nextLine();

        try {
            Category category = Category.valueOf(categryInput.toUpperCase());
            List<Dish> dishes = dishService.getDishByCategory(category);

            if (dishes.isEmpty()) {
                System.out.println("No dishes found for category " + category);
                return;
            }
            System.out.println("Menu for catgory: " + category);
            for (Dish dish : dishes) {
                System.out.println(dish);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid category");
        }
    }



    private static void printOrders() {
        List<Order> orderList = orderService.getAllOrders();
        for (Order order : orderList) {
            System.out.println(order);
        }
    }

    private static void addDishToOrder() {
        printOrders();
        System.out.println("Enter order id");
        int orderId = Integer.parseInt(scanner.nextLine());
        printDish();
        System.out.println("Enter dish id to add:");
        int dishId = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter qunatity:");
        int quantity = Integer.parseInt(scanner.nextLine());
        orderService.addDishOrder(orderId, dishId, quantity);
        System.out.println("Dish added to order successfully");

    }

    private static void showCustomerOrdersHistory() {
        System.out.println("Enter customer id to view order history");
        printCustomer();
        int customerid = Integer.parseInt(scanner.nextLine());
        List<Order> orders = orderService.getOrderByCustomerId(customerid);
        if (orders.isEmpty()) {
            System.out.println("No orders found for this customer");
            return;
        }
        System.out.println("Order history:");
        for (Order order : orders) {
            System.out.println(order);
        }


    }

    private static void createOrderFlow() {
        printCustomer();
        System.out.println("Enter customer id: ");
        int customerId = Integer.parseInt(scanner.nextLine());
        int orderId = orderService.createOrder(customerId);
        System.out.println("Order created successfully. Order ID = " + orderId);

    }

    private static void printCustomer() {
        List<Customer> customer = customerService.getCustomer();
        if (customer.isEmpty()) {
            System.out.println("No customers found");
            return;
        }

        for (Customer customer1 : customer) {
            System.out.println(customer1);
        }
    }

    private static void addCostumer() {
        System.out.println("Please input name,phone,email");
        String s = scanner.nextLine();
        String[] custmerData = s.split(",");
        Customer customer = new Customer();
        customer.setName(custmerData[0]);
        customer.setPhone(custmerData[1]);
        customer.setEmail(custmerData[2]);
        customerService.addCustomer(customer);
        System.out.println("Customer added successfully");
    }

    private static void printDish() {
        List<Dish> dish = dishService.getDish();
        for (Dish dish1 : dish) {
            System.out.println(dish1);
        }
    }

    private static void changeDish() {

        List<Dish> dishelist = dishService.getDish();
        if (dishelist.isEmpty()) {
            System.out.println("No dish available.");
            return;
        }
        System.out.println("Available dishes: ");
        for (Dish dish : dishelist) {
            System.out.println(dish);
        }
        System.out.println("Please input dish id of the dish you want to update:");
        int id = Integer.parseInt(scanner.nextLine());

        Dish dish = dishService.getDIshById(id);
        if (dish == null) {
            System.out.println("Dish with id " + id + " not found");
            return;
        }
        System.out.println("Please input name,category,price");
        System.out.println("Category: ");
        Category[] values = Category.values();
        for (Category value : values) {
            System.out.println(value + ",");
        }
        String name = scanner.nextLine();
        String[] dishData = name.split(",");

        dish.setName(dishData[0]);
        dish.setCategory(Category.valueOf(dishData[1].toUpperCase()));
        dish.setPrice(new BigDecimal(dishData[2]));
        dishService.changeDish(dish);
        System.out.println("Dish changed successfully");
    }

    private static void addDish() {
        System.out.println("Please input name,category,price");
        System.out.println("Category: ");
        Category[] values = Category.values();
        for (Category value : values) {
            System.out.println(value + ",");
        }


        String s = scanner.nextLine();
        String[] dishData = s.split(",");
        Dish dish = new Dish();
        dish.setName(dishData[0]);
        dish.setCategory(Category.valueOf(dishData[1].toUpperCase()));
        dish.setPrice(new BigDecimal(dishData[2]));
        dishService.addDish(dish);
        System.out.println("Dish added successfully");
    }
}
