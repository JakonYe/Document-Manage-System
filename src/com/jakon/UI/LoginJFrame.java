package com.jakon.UI;

import com.jakon.Models.Administrator;
import com.jakon.Models.Browser;
import com.jakon.Models.Operator;
import com.jakon.Models.User;
import com.jakon.Utils.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class LoginJFrame extends JFrame implements MouseListener, WindowListener {

    JButton login = new JButton();

    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();

    public LoginJFrame() {
        initJFrame();

        initView();

        this.setVisible(true);
    }

    public void initJFrame() {
        this.setTitle("档案管理系统");
        this.setSize(430, 260);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        this.addWindowListener(this);
    }

    public void initView() {
        Font font = new Font("", Font.PLAIN, 14);

        // 添加用户名文字
        JLabel usernameText = new JLabel("用户");
        usernameText.setBounds(50, 35, 50, 26);
        usernameText.setFont(font);
        this.getContentPane().add(usernameText);

        // 添加用户名输入框
        username.setBounds(110, 35, 200, 26);
        username.setFont(font);
        this.getContentPane().add(username);

        // 添加密码文字
        JLabel passwordText = new JLabel("密码");
        passwordText.setBounds(50, 95, 50, 26);
        passwordText.setFont(font);
        this.getContentPane().add(passwordText);

        // 密码输入框
        password.setBounds(110, 95, 200, 26);
        password.setFont(font);
        this.getContentPane().add(password);

        // 添加登录按钮
        login.setBounds(150, 150, 70, 40);
        login.setText("登录");
        login.setFont(font);
        login.addMouseListener(this);
        this.getContentPane().add(login);
    }

    public void showJDialog(String content) {
        // 创建一个弹框
        JDialog jDialog = new JDialog();
        jDialog.setTitle("提示");
        jDialog.setSize(200, 150);
        jDialog.setAlwaysOnTop(true);
        jDialog.setLocationRelativeTo(null);
        jDialog.setModal(true);

        // 创建JLabel对象管理文字并添加到弹框当中
        JLabel warning = new JLabel(content);
        warning.setBounds(20, 20, 200, 150);
        warning.setFont(new Font("", Font.PLAIN, 12));
        jDialog.getContentPane().add(warning);

        // 让弹框展示出来
        jDialog.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == login) {
            System.out.println("点击登录按钮.");

            // 获取两个文本输入框中的内容
            String usernameInput = username.getText();
            String passwordInput = password.getText();

            System.out.println("用户输入的用户名为：" + usernameInput);
            System.out.println("用户输入的密码为：" + passwordInput);

            if (usernameInput.length() == 0 || passwordInput.length() == 0) {
                System.out.println("用户名或者密码为空");
                showJDialog("用户名或者密码为空");
            } else {
                User user = DataProcessing.searchUser(usernameInput, passwordInput);
                if (user != null) {
                    System.out.println("用户名和密码正确，登录成功.");
                    // 关闭当前登录界面
                    this.setVisible(false);
                    // 打开身份相对应的界面
                    String name = user.getName();
                    String password = user.getPassword();
                    String role = user.getRole();
                    if (user.getRole().equalsIgnoreCase("Administrator")) {
                        new AdminJFrame(new Administrator(name, password, "Administrator"));
                    } else if (user.getRole().equalsIgnoreCase("Operator")) {
                        new OperateJFrame(new Operator(name, password, "Operator"));
                    } else if (user.getRole().equalsIgnoreCase("Browser")) {
                        new BrowseJFrame(new Browser(name, password, "Browser"));
                    }
                } else {
                    System.out.println("用户名或密码错误.");
                    showJDialog("用户名或密码错误.");
                    // 清空输入框
                    username.setText("");
                    password.setText("");
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}