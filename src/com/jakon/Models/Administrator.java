package com.jakon.Models;

import com.jakon.Utils.DataProcessing;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Scanner;

public class Administrator extends User {
    public Administrator() {
    }

    public Administrator(String name, String password, String role) {
        super(name, password, role);
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);

        loop:
        while (true) {
            System.out.println("---------------------档案系统---------------------");
            System.out.println("1.添加用户");
            System.out.println("2.删除用户");
            System.out.println("3.修改用户信息");
            System.out.println("4.显示用户列表");
            System.out.println("5.退出");
            System.out.print("请输入您的选择：");
            String choose = sc.next();
            switch (choose) {
                case "1" -> {
                    try {
                        addUser();
                    } catch (SQLException exp) {
                        System.out.println("数据库异常，添加失败，请重试.");
                    }
                }
                case "2" -> {
                    try {
                        deleteUser();
                    } catch (SQLException exp) {
                        System.out.println("数据库异常，删除失败，请重试.");
                    }
                }
                case "3" -> {
                    try {
                        updateUserInfo();
                    } catch (SQLException exp) {
                        System.out.println("数据库异常，修改失败，请重试.");
                    }
                }
                case "4" -> {
                    try {
                        showUserList();
                    } catch (SQLException exp) {
                        System.out.println("数据库异常，显示列表失败，请重试.");
                    }
                }
                case "5" -> {
                    System.out.println("退出.");
                    break loop;
                }
                default -> System.out.println("没有这个选项，请重新输入.");
            }
        }
    }

    public void addUser() throws SQLException {
        Scanner sc = new Scanner(System.in);

        String name;
        while (true) {
            System.out.print("请输入用户名：");
            name = sc.next();
            if (DataProcessing.searchUser(name) != null) {
                System.out.println("用户名已存在，请重新输入.");
                continue;
            }
            break;
        }

        System.out.print("请输入口令：");
        String password = sc.next();

        String role;
        while (true) {
            System.out.print("请输入身份：");
            role = sc.next();
            if (checkRoleType(role)) break;
            System.out.println("没有这种身份，请重新输入：");
        }

        DataProcessing.insertUser(name, password, role);
        System.out.println("添加成功.");
    }

    public void deleteUser() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String name = sc.next();
        if (DataProcessing.delete(name)) System.out.println("删除成功.");
        else System.out.println("该用户名不存在，删除失败.");
    }

    public void updateUserInfo() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String name = sc.next();
        if (!DataProcessing.delete(name)) {
            System.out.println("该用户名不存在，修改失败.");
            return;
        }

        System.out.print("请输入新口令：");
        String password = sc.next();

        while (true) {
            System.out.print("请输入新身份：");
            String role = sc.next();
            if (checkRoleType(role)) {
                DataProcessing.insertUser(name, password, role);
                System.out.println("修改成功.");
                break;
            } else {
                System.out.println("没有这种身份，请重新输入.");
            }
        }
    }

    public void showUserList() throws SQLException {
        Enumeration<User> e = DataProcessing.getAllUser();
        System.out.println("用户列表如下：");
        while (e.hasMoreElements()) {
            User u = e.nextElement();
            System.out.println("name:" + u.getName() + "  password:" + u.getPassword() + "  role:" + u.getRole());
        }
    }
}