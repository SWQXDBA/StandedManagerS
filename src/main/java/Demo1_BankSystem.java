import ManagerHelperSystem.ManagerS;

import java.util.ArrayList;
import java.util.Scanner;

/***
 * 银行系统对本工具类的适配性不太好，因为银行系统的难度在于比较复杂的逻辑，所以代码仍然比较长。不过crud重复的代码量很多，实际上工作量也不大
 */
public class Demo1_BankSystem {
    public static void main(String[] args) {
        ManagerS managerS = new ManagerS("银行系统",null,10);
        managerS.setItems("userName","passWord","money");
        System.out.println("欢迎来到银行系统");
        menu();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option){
                case 1:saveMoney(managerS,scanner);break;
                case 2:getMoney(managerS,scanner);break;
                case 3:transMoney(managerS,scanner);break;
                case 4:createCount(managerS,scanner);break;
                default:
                    System.out.println("请重新输入");
                    break;
            }
            managerS.showDates();
            menu();
        }
        scanner.close();

    }
    static void menu(){
        System.out.println("请输入操作 1：存钱 2：取钱 3：转账 4：创建账户");
    }
    static void createCount(ManagerS managerS,Scanner scanner){
        System.out.println("请输入用户名");
        String userName = scanner.nextLine();
        if(managerS.queryIdByItem("userName",userName).size()!=0){
            System.out.println("用户名已存在！");
            createCount(managerS,scanner);
            return;
        }
        System.out.println("请输入密码");
        String password = scanner.nextLine();
        String id =String.valueOf(managerS.getID());
        managerS.setData(id,"userName",userName);
        managerS.setData(id,"passWord",password);
        managerS.setData(id,"money",0);
    }
    static void saveMoney(ManagerS managerS,Scanner scanner){
        System.out.println("请输入账户");
        String userName = scanner.nextLine();
        //本案例展示返回第一个id的需求 此id其实应该是唯一的 其他案例提供id可能不唯一的使用方法
        String userId= managerS.querySingleIdByItem("userName",userName);

        if(userId==null){
            System.out.println("账户错误!");
            return;
        }
        System.out.println("请输入密码");
        String userPassword = scanner.nextLine();
        if(managerS.queryItemById(userId,"passWord").equals(userPassword)){
            Integer oldMoney =(Integer) managerS.queryItemById(userId,"money");
            System.out.println("登陆成功，请输入要存储的钱数量");
            Integer money = scanner.nextInt();
            scanner.nextLine();
            managerS.modify("money",(Integer)(oldMoney+money),userId);
        }else{
            System.out.println("密码错误！");
        }


    }
    static void getMoney(ManagerS managerS,Scanner scanner){
        System.out.println("请输入账户");
        String userName = scanner.nextLine();

        ArrayList<String> userId= managerS.queryIdByItem("userName",userName);

        if(userId.size()==0){
            System.out.println("账户错误!");
            return;
        }
        System.out.println("请输入密码");
        String userPassword = scanner.nextLine();
        if(managerS.queryItemById(userId.get(0),"passWord").equals(userPassword)){
            Integer oldMoney =(Integer) managerS.queryItemById(userId.get(0),"money");
            System.out.println("登陆成功，请输入要取的金额");
            Integer money = scanner.nextInt();
            scanner.nextLine();
            if(oldMoney<money){
                System.out.println("您的余额不足");
                return;
            }
            managerS.modify("money",oldMoney-money,userId.get(0));
            System.out.println("取款成功，余额为"+(oldMoney-money));
        }else{
            System.out.println("密码错误！");
        }


    }
    static void transMoney(ManagerS managerS,Scanner scanner){
        System.out.println("请输入账户");
        String userName = scanner.nextLine();

        ArrayList<String> userId= managerS.queryIdByItem("userName",userName);

        if(userId.size()==0){
            System.out.println("账户错误!");
            return;
        }
        System.out.println("请输入密码");
        String userPassword = scanner.nextLine();
        if(managerS.queryItemById(userId.get(0),"passWord").equals(userPassword)){
            Integer oldMoney =(Integer) managerS.queryItemById(userId.get(0),"money");
            System.out.println("登陆成功，请输入要转账的金额");
            Integer money = scanner.nextInt();
            scanner.nextLine();
            if(oldMoney<money){
                System.out.println("您的余额不足");
                return;
            }
            if(money<=0){
                System.out.println("您只能转大于0的数额");
                return;
            }
            System.out.println("请输入目标账户");
            String toUserName = scanner.nextLine();
            ArrayList<String> toUserId = managerS.queryIdByItem("userName",toUserName);
            if(toUserId.size()==0){
                System.out.println("目标账户不存在！");
                return;
            }

            Integer oldToMoney =(Integer) managerS.queryItemById(toUserId.get(0),"money");
            managerS.modify("money",oldMoney-money,userId.get(0));
            managerS.modify("money",oldToMoney+money,toUserId.get(0));

            System.out.println("转账成功，余额为"+(oldMoney-money));
        }else{
            System.out.println("密码错误！");
        }
    }
}
