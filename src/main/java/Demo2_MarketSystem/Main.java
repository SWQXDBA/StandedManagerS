package Demo2_MarketSystem;

import ManagerHelperSystem.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int id=0;

    public static void main(String[] args) {

        ManagerS managerS = new ManagerS("���ж�������ϵͳ","C:\\Users\\SWQXDBA\\Desktop\\cs.txt",10);
        Scanner scanner = new Scanner(System.in);
        managerS.setItems("�ͻ�","����","��Ʒ");
        Option addUser = new Option() {
            @Override
            public void Run(ManagerS managerS, Scanner scanner) {
                Customer customer = new Customer();
                System.out.println("�������û���");
                customer.userName= scanner.nextLine();
                System.out.println("����������");
                customer.passWord= scanner.nextLine();
                System.out.println("�������˻����");
                customer.money= scanner.nextDouble();
                scanner.nextLine();
                managerS.setData(String.valueOf(id++),"�ͻ�",customer);
            }
        };
        Option addGood = new Option() {
            @Override
            public void Run(ManagerS managerS, Scanner scanner) {
                Good good = new Good();
                System.out.println("��������Ʒ����");
                good.name=scanner.nextLine();
                System.out.println("��������Ʒ����");
                good.count=scanner.nextInt();
                scanner.nextLine();
                System.out.println("��������Ʒ�۸�");
                good.price=scanner.nextDouble();
                managerS.setData(String.valueOf(id++),"��Ʒ",good);
            }
        };
        Option showCustomer = new Option() {
            @Override
            public void Run(ManagerS managerS, Scanner scanner) {
              ArrayList<Object> customers = managerS.queryItemIf("�ͻ�", c->true);
              for(var i:customers){
                  System.out.println((Customer)i);
              }
                /*������
                ArrayList<String> customers2 = managerS.queryIdIf("�ͻ�", c->true);
                for(var i:customers2){
                    Customer customer =(Customer) managerS.queryItemById(i,"�ͻ�");
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
            System.out.println("���������Ĳ��� 1����û� 2 �����Ʒ 3 ��ʾ���пͻ� 4 ����");
            int option;
            option=scanner.nextInt();
            scanner.nextLine();
            switch (option){
                case 1:addUser.Run(managerS,scanner);break;
                case 2:addGood.Run(managerS,scanner);break;
                case 3:showCustomer.Run(managerS,scanner);break;
                case 4:save.Run(managerS,scanner);break;
                default:
                    System.out.println("����������");break;
            }
        }
    }

}
