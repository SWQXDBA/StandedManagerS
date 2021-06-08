import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ManagerS managerS = new ManagerS("学生管理系统", "C:\\Users\\SWQXDBA\\Desktop\\cs.txt", 10);
        managerS.showMenu(() -> {
            System.out.println(managerS.systemName);
        });
        managerS.setDate("姓名", "1", "小明");
        managerS.setDate("姓名", "2", "小明2");
        managerS.setDate("姓名", "小明3");


        managerS.setDate("年龄", "1", 21);
        managerS.setDate("年龄", "2", 21);
        Map<String,String> a = new HashMap<>();
        managerS.setDate("年龄", 26);
        managerS.showDates();
        managerS.modify("姓名", "不是小明", "年龄", 21);
        managerS.showDates();
        managerS.standAdd("请键入");
        managerS.showDates();
        managerS.clear();
        managerS.save();
    }
}
