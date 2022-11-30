package com.jakon.Utils;

import com.jakon.Models.Administrator;
import com.jakon.Models.Browser;
import com.jakon.Models.Operator;
import com.jakon.Models.User;
import com.jakon.Utils.DataProcessing;

import java.io.IOException;
import java.util.Scanner;

public class APP {
    public static void main(String[] args)  {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("-----------------档案系统登录界面-----------------");
            System.out.println("1.登录");
            System.out.println("2.退出");
            System.out.print("请输入您的选择：");
            String choose = sc.next();
            switch (choose) {
                case "1" -> login();
                case "2" -> {
                    try {
                        DataProcessing.saveDataInfo();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("再见，祝您生活愉快.");
                    System.exit(0);
                }
                default -> System.out.println("没有这个选项，请重新输入.");
            }
        }
    }

    public static void login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入用户名：");
        String name = sc.next();
        System.out.print("请输入口令：");
        String password = sc.next();

        User u = DataProcessing.searchUser(name, password);
        if (u == null) {
            System.out.println("用户名或口令有误，登陆失败.");
            return;
        }
        System.out.println("欢迎使用档案系统.");
        if (u.getRole().equalsIgnoreCase("Administrator")) {
            Administrator admin = new Administrator(u.getName(), u.getPassword(), u.getRole());
            admin.showMenu();
        } else if (u.getRole().equalsIgnoreCase("Operator")) {
            Operator op = new Operator(u.getName(), u.getPassword(), u.getRole());
            op.showMenu();
        } else {
            Browser bro = new Browser(u.getName(), u.getPassword(), u.getRole());
            bro.showMenu();
        }
    }
}