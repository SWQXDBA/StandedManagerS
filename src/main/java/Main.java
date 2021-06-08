import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ManagerS managerS = new ManagerS("学生管理系统", "C:\\Users\\SWQXDBA\\Desktop\\cs.txt", 10);
        managerS.setData("姓名", "1", "小明");
        managerS.setData("姓名", "2", "小明2");
        managerS.setData("姓名", "3","小明3");


        managerS.setData("年龄", "1", 25);
        managerS.setData("年龄", "2", 21);
        managerS.setData("年龄", "3",26);


        managerS.showDates(managerS.sortBy("年龄",(o1, o2) -> (Integer) o1- (Integer)o2));
        managerS.deleteData(managerS.queryItemIf("年龄",o -> (Integer)o<22));
        managerS.showDates(managerS.sortBy("年龄",(o1, o2) -> (Integer) o1- (Integer)o2));
        managerS.clear();
        managerS.save();

    }
}
