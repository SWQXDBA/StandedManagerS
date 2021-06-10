package Demo2_MarketSystem;

public class Customer {
   public String userName;
    public String passWord;
    public Double money;

    @Override
    public String toString() {
        return "Customer{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", money=" + money +
                '}';
    }
}
