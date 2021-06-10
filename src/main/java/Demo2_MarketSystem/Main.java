package Demo2_MarketSystem;

import ManagerHelperSystem.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int id=0;

    public static void main(String[] args) {
        ManagerS managerS = new ManagerS("超市订单管理系统","C:\\Users\\SWQXDBA2\\Desktop\\data.txt",10);
        Scanner scanner = new Scanner(System.in);
        managerS.setItems("客户","订单","商品");
        Option addUser = new Option() {
            @Override
            public void Run(ManagerS managerS, Scanner scanner) {
                Customer customer = new Customer();
                System.out.println("请输入用户名");
                customer.userName= scanner.nextLine();
                System.out.println("请输入密码");
                customer.passWord= scanner.nextLine();
                System.out.println("请输入账户金额");
                customer.money= scanner.nextDouble();
                scanner.nextLine();
                managerS.setData(String.valueOf(id++),"客户",customer);
            }
        };
        Option addGood = new Option() {
            @Override
            public void Run(ManagerS managerS, Scanner scanner) {
                Good good = new Good();
                System.out.println("请输入商品名称");
                good.name=scanner.nextLine();
                System.out.println("请输入商品数量");
                good.count=scanner.nextInt();
                scanner.nextLine();
                System.out.println("请输入商品价格");
                good.price=scanner.nextDouble();
                managerS.setData(String.valueOf(id++),"商品",good);
            }
        };
        Option showCustomer = new Option() {
            @Override
            public void Run(ManagerS managerS, Scanner scanner) {
              ArrayList<Object> customers = managerS.queryItemIf("客户", c->true);
              for(var i:customers){
                  System.out.println((Customer)i);
              }
                /*方法二
                ArrayList<String> customers2 = managerS.queryIdIf("客户", c->true);
                for(var i:customers2){
                    Customer customer =(Customer) managerS.queryItemById(i,"客户");
                    System.out.println(customer);
                }
                 */
            }


        };
        Option save = new Option() {
            @Override
            public void Run(ManagerS managerS, Scanner scanner) {
                managerS.save();
            }
        };
        while(true){
            System.out.println("请输入您的操作 1添加用户 2 添加商品 3 显示所有客户 4 保存");
            int option;
            option=scanner.nextInt();
            scanner.nextLine();
            switch (option){
                case 1:addUser.Run(managerS,scanner);break;
                case 2:addGood.Run(managerS,scanner);break;
                case 3:showCustomer.Run(managerS,scanner);break;
                case 4:save.Run(managerS,scanner);break;
                default:
                    System.out.println("请重新输入");break;
            }
        }
    }

}
