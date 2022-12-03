package com.jakon.Utils;

import com.jakon.Models.*;

import java.io.*;
import java.sql.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringJoiner;

public class DataProcessing {
    public static Hashtable<String, User> users = new Hashtable<>();
    public static Hashtable<String, Doc> docs = new Hashtable<>();

    static {
        users.put("jack", new Operator("jack", "123", "Operator"));
        users.put("rose", new Browser("rose", "123", "Browser"));
        users.put("kate", new Administrator("kate", "123", "Administrator"));
        init();
    }

    public static void init() {
        try {
            initDataInfoFromDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveDataInfo() throws IOException {
        String filePath1 = "userData.dat";
        ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(filePath1));
        Collection<User> values1 = users.values();
        oos1.writeInt(values1.size());
        for (User user : values1) {
            oos1.writeObject(user);
        }
        oos1.close();

        String filePath2 = "docData.dat";
        ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(filePath2));
        Collection<Doc> values2 = docs.values();
        oos2.writeInt(values2.size());
        for (Doc doc : values2) {
            oos2.writeObject(doc);
        }
        oos2.close();
    }

    /**
     *
     * 程序关闭时，将用户数据与档案数据存储到数据库中
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void savaDataInfoToDB() throws ClassNotFoundException, SQLException {
        // 1. 注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2. 获取连接：如果连接的是本机mysql并且端口是默认的 3306 可以简化书写
        String url = "jdbc:mysql://127.0.0.1:3306/document?useSSL=false";
        String username = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, username, password);

        System.out.println("连接本机数据库成功！");

        // 3. 定义sql：删除用户表
        String sql = "TRUNCATE TABLE table_user";
        // 4. 获取statement对象
        Statement statement = connection.createStatement();
        // 5. 执行sql
        statement.executeUpdate(sql);

        System.out.println("删除用户列表原数据成功.");

        // 3. 定义sql：新增用户表数据
        StringBuilder sb1 =  new StringBuilder("INSERT INTO table_user (username, password, role) VALUES ");
        Collection<User> values1 = users.values();
        boolean first1 = true;
        for (User user : values1) {
            StringJoiner sj = new StringJoiner(",", "(", ")");
            sj.add("'" + user.getName() + "'");
            sj.add("'" + user.getPassword() + "'");
            sj.add("'" + user.getRole() + "'");
            if(first1) first1 = false;
            else sb1.append(",");
            sb1.append(" ").append(sj);
        }
        sql = sb1.toString();
        // 4. 获取statement对象
        statement = connection.createStatement();
        // 5. 执行sql
        int count1 = statement.executeUpdate(sql);
        // 6. 处理结果
        if(count1 == users.size()) {
            System.out.println("用户数据成功存进数据库中！");
        } else {
            System.out.println("用户数据存储失败.");
        }

        if(docs.size() > 0) {
            // 3. 定义sql：删除档案表
            sql = "TRUNCATE TABLE table_file";
            // 4. 获取statement对象
            statement = connection.createStatement();
            // 5. 执行sql
            statement.executeUpdate(sql);

            System.out.println("删除档案列表原数据成功.");

            // 3. 定义sql：新增档案表数据
            StringBuilder sb2 =  new StringBuilder("INSERT INTO table_file (id, creator, timestamp, description, filename) VALUES ");
            Collection<Doc> values2 = docs.values();
            boolean first2 = true;
            for (Doc doc : values2) {
                StringJoiner sj = new StringJoiner(",", "(", ")");
                sj.add("'" + doc.getID() + "'");
                sj.add("'" + doc.getCreator() + "'");
                String timestampStr = doc.getTimestamp().toString();
                sj.add("'" + timestampStr.substring(0, timestampStr.indexOf('.')) + "'");   // 去掉秒的小数点部分
                sj.add("'" + doc.getDescription() + "'");
                sj.add("'" + doc.getFilename() + "'");
                if (first2) first2 = false;
                else sb2.append(",");
                sb2.append(" ").append(sj);
            }
            sql = sb2.toString();
            // 4. 获取statement对象
            statement = connection.createStatement();
            // 5. 执行sql
            int count2 = statement.executeUpdate(sql);
            // 6. 处理结果
            if(count2 == docs.size()) {
                System.out.println("档案数据成功存进数据库中！");
            } else {
                System.out.println("档案数据存储失败.");
            }
        }

        // 7. 释放资源
        statement.close();
        connection.close();
    }

    public static void initDataInfo() throws IOException, ClassNotFoundException {
        String filePath1 = "userData.dat";
        File file1 = new File(filePath1);
        if (!file1.exists() || file1.length() == 0) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath1));
            oos.writeInt(0);
            oos.close();
        }
        ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream(filePath1));
        User user;
        int nums1 = ois1.readInt();
        for (int i = 0; i < nums1; i++) {
            user = (User) ois1.readObject();
            String name = user.getName();
            users.put(name, user);
        }
        ois1.close();

        String filePath2 = "docData.dat";
        File file2 = new File(filePath2);
        if (!file2.exists() || file2.length() == 0) {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath2));
            oos.writeInt(0);
            oos.close();
        }
        ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(filePath2));
        Doc doc;
        int nums2 = ois2.readInt();
        for (int i = 0; i < nums2; i++) {
            doc = (Doc) ois2.readObject();
            String id = doc.getID();
            docs.put(id, doc);
        }
        ois2.close();
    }

    /**
     *
     * 程序启动时，读取数据库中的用户列表和档案列表，以获取用户数据和档案数据
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void initDataInfoFromDB() throws ClassNotFoundException, SQLException {
        // 1. 注册驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 2. 获取连接：如果连接的是本机mysql并且端口是默认的 3306 可以简化书写
        String url = "jdbc:mysql://127.0.0.1:3306/document?useSSL=false";
        String username = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, username, password);

        System.out.println("连接本机数据库成功！");

        // 3. 定义sql
        String sql = "SELECT * FROM table_user";
        // 4. 获取statement对象
        Statement statement = connection.createStatement();
        // 5. 执行sql
        ResultSet resultSet = statement.executeQuery(sql);
        // 6. 处理结果
        while (resultSet.next()) {
            String name = resultSet.getString("username");
            String pwd = resultSet.getString("password");
            String role = resultSet.getString("role");
            if(role.equalsIgnoreCase("Administrator")) {
                users.put(name, new Administrator(name, pwd, role));
            } else if(role.equalsIgnoreCase("Operator")) {
                users.put(name, new Operator(name, pwd, role));
            } else if(role.equalsIgnoreCase("Browser")) {
                users.put(name, new Browser(name, pwd, role));
            }
        }
        // 7. 释放资源
        resultSet.close();

        System.out.println("读取用户列表成功！");

        // 3. 定义sql
        sql = "SELECT * FROM table_file";
        // 4. 获取statement对象
        statement = connection.createStatement();
        // 5. 执行sql
        resultSet = statement.executeQuery(sql);
        // 6. 处理结果
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String creator = resultSet.getString("creator");
            Timestamp timestamp = resultSet.getTimestamp("timestamp");
            String description = resultSet.getString("description");
            String filename = resultSet.getString("filename");
            docs.put(id, new Doc(id, creator, timestamp, description, filename));
        }
        // 7. 释放资源
        resultSet.close();
        statement.close();
        connection.close();

        System.out.println("读取档案列表成功！");
    }

    public static User searchUser(String name) {
        if (!users.containsKey(name)) return null;
        return users.get(name);
    }

    public static User searchUser(String name, String password) {
        if (!users.containsKey(name)) return null;
        User u = users.get(name);
        if (!u.getPassword().equals(password)) return null;
        return u;
    }

    public static Doc searchDoc(String id) {
        if (!docs.containsKey(id)) return null;
        return docs.get(id);
    }

    public static Enumeration<User> getAllUser() {
        return users.elements();
    }

    public static Enumeration<Doc> getAllDoc() {
        return docs.elements();
    }

    public static boolean insertUser(String name, String password, String role) {
        if (users.containsKey(name)) return false;
        User user = null;
        if (role.equalsIgnoreCase("Administrator")) {
            user = new Administrator(name, password, role);
        } else if (role.equalsIgnoreCase("Operator")) {
            user = new Operator(name, password, role);
        } else if (role.equalsIgnoreCase("Browser")) {
            user = new Browser(name, password, role);
        }
        if (user == null) return false;
        users.put(name, user);
        return true;
    }

    public static boolean insertDoc(Doc doc) {
        if (docs.containsKey(doc.getID())) return false;
        docs.put(doc.getID(), doc);
        return true;
    }

    public static boolean delete(String name) {
        if (!users.containsKey(name)) return false;
        users.remove(name);
        return true;
    }
}