package Demo2_MarketSystem;

public class Customer {
    String userName;
    String passWord;
    Double money;

    @Override
    public String toString() {
        return "Customer{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", money=" + money +
                '}';
    }
}
