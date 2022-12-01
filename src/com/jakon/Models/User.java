package com.jakon.Models;

import com.jakon.Utils.DataProcessing;

import java.io.*;
import java.util.Enumeration;
import java.util.Scanner;

public abstract class User implements Serializable {
    private String name;
    private String password;
    private String role;

    public User() {
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public static boolean checkRoleType(String role) {
        if (role.equalsIgnoreCase("Administrator")) return true;
        if (role.equalsIgnoreCase("Operator")) return true;
        return role.equalsIgnoreCase("Browser");
    }

    public abstract void showMenu();

    public void downloadFile() {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入档案号：");
        String id = sc.next();
        Doc doc = DataProcessing.searchDoc(id);
        if (doc == null) {
            System.out.println("指定档案不存在，下载失败.");
        } else {
            String filename = doc.getFilename();
            String uploadPathname = "upload";
            String downloadPathname = "download" + "\\" + this.getRole() + "\\" + this.getName();
            FileInputStream fis;
            FileOutputStream fos;

            File file = new File(downloadPathname);
            if (!file.exists()) {
                file.mkdirs();
            }

            try {
                fis = new FileInputStream(uploadPathname + "\\" + filename);
                fos = new FileOutputStream(downloadPathname + "\\" + filename);
            } catch (FileNotFoundException e) {
                System.out.println("IO异常，下载失败.");
                return;
            }

            try (fis; fos) {
                byte[] bytes = new byte[1024];
                int len;
                while ((len = fis.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            } catch (IOException e) {
                System.out.println("IO异常，下载失败.");
            }
        }
        System.out.println("下载成功.");
    }

    public String downloadFile(String id) {

        Doc doc = DataProcessing.searchDoc(id);

        if (doc == null) {
            return "指定档案号不存在，下载失败.";
        }

        String filename = doc.getFilename();
        String uploadPathname = "upload";
        String downloadPathname = "download" + "\\" + this.getRole() + "\\" + this.getName();
        FileInputStream fis;
        FileOutputStream fos;

        File file = new File(downloadPathname);
        if (!file.exists()) {
            boolean result = file.mkdirs();
            if (!result) return "无法找到指定档案，下载失败.";
        }

        try {
            fis = new FileInputStream(uploadPathname + "\\" + filename);
            fos = new FileOutputStream(downloadPathname + "\\" + filename);
        } catch (FileNotFoundException e) {
            return "IO异常，下载失败.";
        }

        try (fis; fos) {
            byte[] bytes = new byte[1024 * 1024 * 5];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            return "IO异常，下载失败.";
        }

        return "下载成功.";
    }

    public void showFileList() {
        if (DataProcessing.docs.size() == 0) {
            System.out.println("暂无可下载档案.");
            return;
        }

        Enumeration<Doc> e = DataProcessing.getAllDoc();
        System.out.println("档案列表如下：");
        while (e.hasMoreElements()) {
            Doc d = e.nextElement();
            System.out.println(d);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}