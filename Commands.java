public interface Commands {
    String EXIT = "0";
    String ADD_DISH = "1";
    String CHANGE_DISH = "2";
    String REMOVE_DISH = "3";
    String ADD_CUSTOMER = "4";
    String SHOW_CUSTOMERS = "5";
    String CREATE_NEW_ORDERS = "6";
    String ADD_DISHES_THE_ORDER = "7";
    String SHOW_ALL_ORDERS = "8";
    String SHOW_CUSTOMER_ORDER_HISTORY = "9";
    String SHOW_ORDER_DETAILS = "10";
    String CHANGE_ORDER_STATUS = "11";
    String SHOW_MENU_BY_CATEGORY = "12";


    static void printCommands() {
        System.out.println("Please input " + EXIT + " for EXIT");
        System.out.println("Please input " + ADD_DISH + " for add dish");
        System.out.println("Please input " + CHANGE_DISH + " for change dish");
        System.out.println("Please input " + REMOVE_DISH + " for delete dish");
        System.out.println("Please input " + ADD_CUSTOMER + " for add customer");
        System.out.println("Please input " + SHOW_CUSTOMERS + " for show customers");
        System.out.println("Please input " + CREATE_NEW_ORDERS + " for create new orders");
        System.out.println("Please input " + ADD_DISHES_THE_ORDER + " for add dishes the order");
        System.out.println("Please input " + SHOW_ALL_ORDERS + " show all orders");
        System.out.println("Please input " + SHOW_CUSTOMER_ORDER_HISTORY + " show customer order history");
        System.out.println("Please input " + SHOW_ORDER_DETAILS + " show order details");
        System.out.println("Please input " + CHANGE_ORDER_STATUS + " change order status");
        System.out.println("Please input " + SHOW_MENU_BY_CATEGORY + " show menu by category");
    }
}
