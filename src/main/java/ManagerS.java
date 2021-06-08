import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ManagerS {

    String systemName;
    String loadPath;
    Map<String, Map<String,Object>> itemMap = new HashMap<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> itemNames = new ArrayList<>();
    int offset = 5;


    public ManagerS(String systemName, String loadPath, int offset) {
        this.systemName = systemName;
        this.loadPath = loadPath;
        this.offset = offset;
        load();
    }
    public void clear(){
        itemNames.clear();
        itemMap.clear();
        ids.clear();
    }
    public void standAdd(String message){
        Scanner scanner = new Scanner(System.in);
        for(String itemName:itemNames){
            System.out.println(message+itemName);
            String val = scanner.nextLine();
            setDate(itemName,val);
        }
        scanner.close();
    }
    public void load(){
        File file = new File(loadPath);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            while((str = bufferedReader.readLine())!=null){
                if(str.equals("EndOfID"))
                    break;
                ids.add(str);
            }

            while((str = bufferedReader.readLine())!=null){
                String[] strs = str.split("@###@");

                String id = strs[0];
                String itemName = strs[1];
                String val = strs[2];
                if(!ids.contains(id)){
                    ids.add(id);
                }
                if(!itemNames.contains(itemName)){
                    itemNames.add(itemName);
                }
                Map<String,Object> item = itemMap.get(itemName);
                if(item==null){
                    item = new HashMap<>();
                    itemMap.put(itemName,item);
                }
                item.put(id,val);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void save(){
        File file = new File(loadPath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for(String id:ids){
                bufferedWriter.write(id+"\n");
            }
            bufferedWriter.write("EndOfID\n");
            bufferedWriter.flush();
            for(Map.Entry<String, Map<String,Object>> entry : itemMap.entrySet()){
                String itemName = entry.getKey();
                Map<String,Object> items = entry.getValue();
                for(Map.Entry<String,Object> item : items.entrySet()){
                    String id = item.getKey();
                    Object val = item.getValue();
                    bufferedWriter.write(id+"@###@"+itemName+"@###@"+val+"\n");
                }
            }
            bufferedWriter.flush();

        bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Map<String,Object> getItem(String itemName){
        return itemMap.get(itemName);
    }
    public void setDate(String itemName,String itemId, Object itemVal){
        Map<String,Object> item = getItem(itemName);
        //如果没有这个字段 则新增一个
        if(item==null){
            item = new HashMap<>();
            itemMap.put(itemName,item);
        }

        if(!itemNames.contains(itemName))
        itemNames.add(itemName);
        item.put(itemId,itemVal);
        if(!ids.contains(itemId)){
            ids.add(itemId);
        }


    }
    public void setDate(String itemName, Object itemVal){
        Map<String,Object> item = getItem(itemName);
        if(item==null){
            item = new HashMap<>();
            itemMap.put(itemName,item);
        }
        if(!itemNames.contains(itemName))
            itemNames.add(itemName);
        if(!ids.contains(item.size()+1+"")){
            ids.add(item.size()+1+"");
        }
        item.put(item.size()+1+"",itemVal);

    }

    public void modify(String modifyItem,Object newValue,String apply,Object appliedValue){
        Map<String,Object> applyItem = itemMap.get(apply);

        for(Map.Entry<String,Object> entry:applyItem.entrySet()){
            if(entry.getValue().equals(appliedValue)){
                String id = entry.getKey();
                //找到要修改的条目
                Map<String,Object> moItem = itemMap.get(modifyItem);
                //设置为新的值
                moItem.put(id,newValue);
            }
        }


    }
    public void showMenu(Printable printable){
        printable.Show();
    }
    public void showDates(){
        for(String itemName:itemNames){
            System.out.print(itemName);
            for (int i = 0; i <offset- itemName.length(); i++) {
                System.out.print(" ");
            }
        }
        System.out.println();
        //id代表字段标签 应该是唯一的
        for (String id:ids){
            //获取每个字段
            for(Map.Entry<String, Map<String,Object>> entry : itemMap.entrySet()){
                //在字段中找到id对应的数据val
                Map<String,Object> values = entry.getValue();
                Object value = values.get(id);
                System.out.print(String.valueOf(value));
                //打印设置的空格
                for (int i = 0; i <offset- String.valueOf(value).length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
