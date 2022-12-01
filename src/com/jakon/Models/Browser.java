package com.jakon.Models;

import java.io.Serializable;
import java.util.Scanner;

public class Browser extends User implements Serializable {
    public Browser() {
    }

    public Browser(String name, String password, String role) {
        super(name, password, role);
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);

        loop:
        while (true) {
            System.out.println("---------------------档案系统---------------------");
            System.out.println("1.下载文件");
            System.out.println("2.显示文件列表");
            System.out.println("3.退出");
            System.out.print("请输入您的选择：");
            String choose = sc.next();
            switch (choose) {
                case "1" -> downloadFile();
                case "2" -> showFileList();
                case "3" -> {
                    System.out.println("退出.");
                    break loop;
                }
                default -> System.out.println("没有这个选项，请重新输入.");
            }
        }
    }
}