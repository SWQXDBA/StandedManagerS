import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class ManagerS {

    String systemName;
    String loadPath;
    /***
     * Map<itemName,Map<id,value>>
     */
    Map<String, Map<String, Object>> itemMap = new HashMap<>();
    ArrayList<String> ids = new ArrayList<>();//储存所有有效的id
    ArrayList<String> itemNames = new ArrayList<>();//字段属性的名字
    int offset = 5;
    int ID=1;//主键

    /***
     *
     * @param systemName
     * @param loadPath 加载的储存文件路径
     * @param offset 标准打印时，两个属性间隔的距离
     */
    public ManagerS(String systemName, String loadPath, int offset) {
        this.systemName = systemName;
        this.loadPath = loadPath;
        this.offset = offset;
        if(loadPath!=null)
        load();
    }

    /***
     * 用于把属性名们加入设定 这个顺序会影响标准打印的顺序
     * @param items
     */
    public void setItems(String... items){
        for(String s:items){
            itemMap.put(s,new HashMap<String, Object>());
            itemNames.add(s);
        }

    }
    public void clear() {
        itemNames.clear();
        itemMap.clear();
        ids.clear();
        ID=1;
    }

    /***
     * 标准数据输入， 用于输入一条数据
     * @param message
     */
    public void standAdd(String message) {
        Scanner scanner = new Scanner(System.in);
        String newId = String.valueOf(ID++);
        for (String itemName : itemNames) {
            System.out.println(message + itemName);
            String val = scanner.nextLine();
            setData(itemName,newId, val);
        }
        ids.add(newId);
        scanner.close();
    }

    /***
     * 用于从文件中加载数据
     */
    public void load() {
        File file = new File(loadPath);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.equals("EndOfID"))
                    break;
                ids.add(str);
            }

            while ((str = bufferedReader.readLine()) != null) {
                String[] strs = str.split("@###@");

                String id = strs[0];
                String itemName = strs[1];
                String val = strs[2];
                if (!ids.contains(id)) {
                    ids.add(id);
                    ID++;
                }
                if (!itemNames.contains(itemName)) {
                    itemNames.add(itemName);
                }
                Map<String, Object> item = itemMap.get(itemName);
                if (item == null) {
                    item = new HashMap<>();
                    itemMap.put(itemName, item);
                }
                item.put(id, val);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /***
     * 保存数据到文件中
     */
    public void save() {
        File file = new File(loadPath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            for (String id : ids) {
                bufferedWriter.write(id + "\n");
            }
            bufferedWriter.write("EndOfID\n");
            bufferedWriter.flush();
            for (Map.Entry<String, Map<String, Object>> entry : itemMap.entrySet()) {
                String itemName = entry.getKey();
                Map<String, Object> items = entry.getValue();
                for (Map.Entry<String, Object> item : items.entrySet()) {
                    String id = item.getKey();
                    Object val = item.getValue();
                    bufferedWriter.write(id + "@###@" + itemName + "@###@" + val + "\n");
                }
            }
            bufferedWriter.flush();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Map<String, Object> getItem(String itemName) {
        return itemMap.get(itemName);
    }

    /***
     * 用于增加一个字段
     * @param dataId 数据的id，代表数据的索引
     * @param itemName 字段名
     * @param itemVal 数据的值
     */
    public void setData( String dataId,String itemName, Object itemVal) {
        Map<String, Object> item = getItem(itemName);
        //如果没有这个字段 则新增一个
        if (item == null) {
            item = new HashMap<>();
            itemMap.put(itemName, item);
        }

        if (!itemNames.contains(itemName))
            itemNames.add(itemName);
           item.put(dataId, itemVal);
        if (!ids.contains(dataId)) {
            ids.add(dataId);
            ID++;
        }

    }


    /***
     * 根据id删除一条数据
     * @param id
     */
    public void deleteData(String id) {

        for (Map.Entry<String, Map<String, Object>> entry : itemMap.entrySet()) {
            entry.getValue().remove(id);
        }
        ids.remove(id);

    }
    /***
     * 根据id删除数据
     * @param idList
     */
    public void deleteData(ArrayList<String> idList) {
        for (String s : idList) {
            deleteData(s);
        }
    }

    /***
     * 比如 ：queryId("name","小明")
     * @param itemName
     * @param itemVal
     * @return 返回itemName字段.equals(itemVal)的id;
     */
    public ArrayList<String> queryId(String itemName, Object itemVal) {
        Map<String, Object> item = getItem(itemName);
        if (item == null)
            return null;
        ArrayList<String> id = new ArrayList<>();
        for (Map.Entry<String, Object> entry : item.entrySet()) {
            if (entry.getValue().equals(itemVal))
                id.add(entry.getKey());
        }
        return id;
    }

    /***
     * 获取数据的一个字段属性
     * @param id
     * @param itemName
     * @return
     */
    public Object queryItemById(String id, String itemName) {
        Map<String, Object> item = getItem(itemName);
        if (item == null)
            return null;
        return item.get(id);
    }

    /***
     * 返回属性itemName满足Predicate<Object> o的数据的id
     * @param itemName
     * @param o
     * @return
     */
    public ArrayList<String> queryItemIf(String itemName, Predicate<Object> o) {
        Map<String, Object> item = getItem(itemName);
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, Object> i : item.entrySet()) {
            if (o.test(i.getValue())) {
                list.add(i.getKey());
            }
        }
        return list;

    }

    /***
     * 把所有 ifItem属性等于ifValue的数据的modifiedItem属性改为newValue
     * 例如 modify("评分","A+","成绩","100");
     * @param modifiedItem
     * @param newValue
     * @param ifItem 条件字段
     * @param ifValue 条件的值
     */
    public void modify(String modifiedItem, Object newValue, String ifItem, Object ifValue) {
        Map<String, Object> applyItem = itemMap.get(ifItem);

        for (Map.Entry<String, Object> entry : applyItem.entrySet()) {
            if (entry.getValue().equals(ifValue)) {
                String id = entry.getKey();
                //找到要修改的条目
                Map<String, Object> moItem = itemMap.get(modifiedItem);
                //设置为新的值
                moItem.put(id, newValue);
            }
        }
    }

    /***
     * 同上
     * @param modifiedItem
     * @param newValue
     * @param id 要修改的数据的id
     */
    public void modify(String modifiedItem, Object newValue, String id) {
                //找到要修改的条目
                Map<String, Object> moItem = itemMap.get(modifiedItem);
                //设置为新的值
                moItem.put(id, newValue);
    }

    /***
     * GroupBy itemName.
     * 根据itemName属性进行排序
     * @param itemName
     * @param comparator
     * @return
     */
    public ArrayList<String> sortBy(String itemName, Comparator<Object> comparator) {
        Map<String, Object> item = getItem(itemName);
        if (item == null)
            return null;
        ArrayList<String> id = new ArrayList<>(ids);
        //其实是根据id获取属性。然后调用传入的比较器进行比较
        id.sort((o1, o2) -> comparator.compare(item.get(o1), item.get(o2)));
        return id;
    }

    public void showMenu(Printable printable) {
        printable.Show();
    }

    /***
     * 标准输出
     */
    public void showDates() {
        for (String itemName : itemNames) {
            System.out.print(itemName);
            for (int i = 0; i < offset - itemName.length(); i++) {
                System.out.print(" ");
            }
        }
        System.out.println();
        //id代表字段标签 应该是唯一的
        for (String id : ids) {
            //获取每个字段
            for (String itemName : itemNames) {
                //在字段中找到id对应的数据val
                Map<String, Object> values = itemMap.get(itemName);
                Object value = values.get(id);
                System.out.print(String.valueOf(value));
                //打印设置的空格
                for (int i = 0; i < offset - String.valueOf(value).length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /***
     * 标准输出，按照 tpid 的顺序
     * @param tpid
     */
    public void showDates(ArrayList<String> tpid) {
        for (String itemName : itemNames) {
            System.out.print(itemName);
            for (int i = 0; i < offset - itemName.length(); i++) {
                System.out.print(" ");
            }
        }
        System.out.println();
        //id代表字段标签 应该是唯一的
        for (String id : tpid) {
            //获取每个字段
            for (String itemName : itemNames) {
                //在字段中找到id对应的数据val
                Map<String, Object> values = itemMap.get(itemName);
                Object value = values.get(id);
                System.out.print(String.valueOf(value));
                //打印设置的空格
                for (int i = 0; i < offset - String.valueOf(value).length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
