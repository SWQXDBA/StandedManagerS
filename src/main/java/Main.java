import java.util.*;

public class Main {
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
        if(managerS.queryId("userName",userName).size()!=0){
            System.out.println("用户名已存在！");
            createCount(managerS,scanner);
            return;
        }
        System.out.println("请输入密码");
        String password = scanner.nextLine();
        String id =String.valueOf(managerS.ID);
        managerS.setData(id,"userName",userName);
        managerS.setData(id,"passWord",password);
        managerS.setData(id,"money",0);
    }
    static void saveMoney(ManagerS managerS,Scanner scanner){
        System.out.println("请输入账户");
        String userName = scanner.nextLine();

        ArrayList<String> userId= managerS.queryId("userName",userName);

        if(userId.size()==0){
            System.out.println("账户错误!");
            return;
        }
        System.out.println("请输入密码");
        String userPassword = scanner.nextLine();
        if(managerS.queryItemById(userId.get(0),"passWord").equals(userPassword)){
            Integer oldMoney =(Integer) managerS.queryItemById(userId.get(0),"money");
            System.out.println("登陆成功，请输入要存储的钱数量");
            Integer money = scanner.nextInt();
            scanner.nextLine();
            managerS.modify("money",(Integer)(oldMoney+money),userId.get(0));
        }else{
            System.out.println("密码错误！");
        }


    }
    static void getMoney(ManagerS managerS,Scanner scanner){
        System.out.println("请输入账户");
        String userName = scanner.nextLine();

        ArrayList<String> userId= managerS.queryId("userName",userName);

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

        ArrayList<String> userId= managerS.queryId("userName",userName);

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
            ArrayList<String> toUserId = managerS.queryId("userName",toUserName);
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
