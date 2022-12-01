package com.jakon.Models;

import com.jakon.Utils.DataProcessing;

import java.io.*;
import java.sql.Timestamp;
import java.util.Scanner;

public class Operator extends User implements Serializable {
    public Operator() {
    }

    public Operator(String name, String password, String role) {
        super(name, password, role);
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);

        loop:
        while (true) {
            System.out.println("---------------------档案系统---------------------");
            System.out.println("1.上传文件");
            System.out.println("2.下载文件");
            System.out.println("3.显示文件列表");
            System.out.println("4.退出");
            System.out.print("请输入您的选择：");
            String choose = sc.next();
            switch (choose) {
                case "1" -> uploadFile();
                case "2" -> downloadFile();
                case "3" -> showFileList();
                case "4" -> {
                    System.out.println("退出.");
                    break loop;
                }
                default -> System.out.println("没有这个选项，请重新输入.");
            }
        }
    }

    public void uploadFile() {
        // 读入档案信息
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入档案号：");
        String id = sc.next();
        System.out.print("请输入档案描述：");
        String description = sc.next();
        System.out.print("请输入文件名：");
        String filename = sc.next();
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()));

        // 判断档案号唯一
        if (DataProcessing.searchDoc(id) != null) {
            System.out.println("档案号已存在，上传失败.");
            return;
        }

        // 创建 upload 目录
        File dir = new File("upload");
        dir.mkdirs();

        // 将文件拷贝到 upload 目录下
        String pathname = "upload";
        FileInputStream fis;
        FileOutputStream fos;

        try {
            fis = new FileInputStream(filename);
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("文件不存在，上传失败.");
                return;
            }
            filename = file.getName();
            fos = new FileOutputStream(pathname + "\\" + filename);
        } catch (IOException e) {
            System.out.println("IO异常，上传失败.");
            return;
        }

        // 自动释放资源
        try (fis; fos) {
            byte[] bytes = new byte[1024 * 1024 * 5];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            System.out.println("IO异常，上传失败.");
            return;
        }

        // 在数据库中记录档案信息
        Doc doc = new Doc(id, this.getName(), timestamp, description, filename);
        boolean result = DataProcessing.insertDoc(doc);
        if (result) {
            System.out.println("上传成功.");
        } else {
            System.out.println("档案号已存在，上传失败.");
        }

    }

    public String uploadFile(String id, String creator, Timestamp timestamp, String description, String filepath) {

        // 判断档案号唯一
        if (DataProcessing.searchDoc(id) != null) {
            return "档案号已存在，上传失败.";
        }

        // 创建 upload 目录
        File dir = new File("upload");
        if (!dir.exists()) {
            if (!dir.mkdirs()) return "找不到上传路径，上传失败.";
        }

        // 将文件拷贝到 upload 目录下
        String pathname = "upload";
        String filename;
        FileInputStream fis;
        FileOutputStream fos;

        try {
            fis = new FileInputStream(filepath);
            File file = new File(filepath);
            if (!file.exists()) {
                return "文件不存在，上传失败.";
            }
            filename = file.getName();
            fos = new FileOutputStream(pathname + "\\" + filename);
        } catch (IOException e) {
            return "IO异常，上传失败.";
        }

        // 自动释放资源
        try (fis; fos) {
            byte[] bytes = new byte[1024 * 1024 * 5];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            return "IO异常，上传失败.";
        }

        // 在数据库中记录档案信息
        Doc doc = new Doc(id, this.getName(), timestamp, description, filename);
        boolean result = DataProcessing.insertDoc(doc);
        return result ? "上传成功." : "上传失败.";
    }

}