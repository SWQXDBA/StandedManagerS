package Demo2_MarketSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Purchase_order {
    public  Map<Good,Integer> goods = new HashMap<>();
    public  Customer customer;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("客户:").append(customer.userName).append("\n");
        for(var i :goods.entrySet()){
            stringBuilder.append("货物:").append(i.getKey().name).append("数量:").append(i.getValue()).append("\n");
        }
        return stringBuilder.toString();
    }
}
