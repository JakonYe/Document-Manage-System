package com.jakon.Utils;

import com.jakon.Models.*;

import java.io.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

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
            initDataInfo();
        } catch (IOException | ClassNotFoundException e) {
//            throw new RuntimeException(e);
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