package com.jakon.UI;

import com.jakon.Models.Administrator;
import com.jakon.Models.Doc;
import com.jakon.Models.User;
import com.jakon.Utils.DataProcessing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Collection;

public class AdminJFrame extends JFrame implements MouseListener, WindowListener {

    Administrator admin;

    JPanel showDocsJPanel = new JPanel();
    JPanel downloadDocsJPanel = new JPanel();
    JPanel showUsersJPanel = new JPanel();
    JPanel manageUserJPanel = new JPanel();

    JTabbedPane jTabbedPane = new JTabbedPane();

    JTextField docID = new JTextField();
    JTextField username = new JTextField();
    JTextField password = new JTextField();
    JTextField role = new JTextField();

    JButton download = new JButton();
    JButton add = new JButton();
    JButton delete = new JButton();
    JButton update = new JButton();

    public AdminJFrame(Administrator admin) {
        this.admin = admin;

        initJFrame();

        initView();

        this.setVisible(true);
    }

    private void initJFrame() {
        this.setTitle("档案管理系统 - 管理员");
        this.setSize(700, 460);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);

        this.addWindowListener(this);
    }

    private void initView() {
        initShowDocsJPanel();
        initDownloadDocsJPanel();
        initShowUserJPanel();
        initManageUserJPanel();

        jTabbedPane.add("档案列表", showDocsJPanel);
        jTabbedPane.add("档案下载", downloadDocsJPanel);
        jTabbedPane.add("用户列表", showUsersJPanel);
        jTabbedPane.add("用户管理", manageUserJPanel);

        this.add(jTabbedPane);
    }

    private void initShowDocsJPanel() {
        showDocsJPanel.removeAll();

        showDocsJPanel.setLayout(new BorderLayout());

        String[] columnNames = {"档案号", "上传者", "上传时间", "档案描述", "文件名"};
        Object[][] tableValues = new Object[DataProcessing.docs.size()][5];
        Collection<Doc> values = DataProcessing.docs.values();
        int i = 0;
        for (Doc value : values) {
            tableValues[i][0] = value.getID();
            tableValues[i][1] = value.getCreator();
            tableValues[i][2] = value.getTimestamp();
            tableValues[i][3] = value.getDescription();
            tableValues[i][4] = value.getFilename();
            i++;
        }

        JTable jTable = new JTable(tableValues, columnNames);
        jTable.setAutoCreateRowSorter(true);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, tcr);

        JScrollPane jScrollPane = new JScrollPane(jTable);

        showDocsJPanel.add(jScrollPane, BorderLayout.CENTER);

        showDocsJPanel.repaint();
    }

    private void initDownloadDocsJPanel() {
        downloadDocsJPanel.setLayout(null);

        Font font = new Font("", Font.PLAIN, 16);

        // 添加档案号文字
        JLabel docIDText = new JLabel("档案号");
        docIDText.setBounds(50, 70, 50, 30);
        docIDText.setFont(font);
        downloadDocsJPanel.add(docIDText);

        // 添加档案号输入框
        docID.setBounds(50, 110, 350, 30);
        docID.setFont(font);
        downloadDocsJPanel.add(docID);

        // 添加下载按钮
        download.setBounds(50, 170, 150, 40);
        download.setText("下载");
        download.setFont(font);
        download.addMouseListener(this);
        downloadDocsJPanel.add(download);
    }

    private void initShowUserJPanel() {
        showUsersJPanel.removeAll();

        showUsersJPanel.setLayout(new BorderLayout());

        String[] columnNames = {"用户名", "密码", "身份"};
        Object[][] tableValues = new Object[DataProcessing.users.size()][3];
        Collection<User> values = DataProcessing.users.values();
        int i = 0;
        for (User value : values) {
            tableValues[i][0] = value.getName();
            tableValues[i][1] = value.getPassword();
            tableValues[i][2] = value.getRole();
            i++;
        }

        JTable jTable = new JTable(tableValues, columnNames);
        jTable.setAutoCreateRowSorter(true);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(JLabel.CENTER);
        jTable.setDefaultRenderer(Object.class, tcr);

        JScrollPane jScrollPane = new JScrollPane(jTable);

        showUsersJPanel.add(jScrollPane, BorderLayout.CENTER);

        showUsersJPanel.repaint();
    }

    private void initManageUserJPanel() {
        manageUserJPanel.setLayout(null);

        Font font = new Font("", Font.PLAIN, 16);

        // 添加用户名文字
        JLabel usernameText = new JLabel("用户");
        usernameText.setBounds(50, 50, 50, 30);
        usernameText.setFont(font);
        manageUserJPanel.add(usernameText);

        // 添加用户名输入框
        username.setBounds(120, 50, 300, 30);
        username.setFont(font);
        manageUserJPanel.add(username);

        // 添加密码文字
        JLabel passwordText = new JLabel("密码");
        passwordText.setBounds(50, 100, 50, 30);
        passwordText.setFont(font);
        manageUserJPanel.add(passwordText);

        // 密码输入框
        password.setBounds(120, 100, 300, 30);
        password.setFont(font);
        manageUserJPanel.add(password);

        // 添加身份文字
        JLabel roleText = new JLabel("身份");
        roleText.setBounds(50, 150, 50, 30);
        roleText.setFont(font);
        manageUserJPanel.add(roleText);

        // 身份输入框
        role.setBounds(120, 150, 300, 30);
        role.setFont(font);
        manageUserJPanel.add(role);

        // 添加按钮
        add.setBounds(120, 230, 90, 50);
        add.setText("添加");
        add.setFont(new Font("", Font.PLAIN, 18));
        add.addMouseListener(this);
        manageUserJPanel.add(add);

        // 删除按钮
        delete.setBounds(225, 230, 90, 50);
        delete.setText("删除");
        delete.setFont(new Font("", Font.PLAIN, 18));
        delete.addMouseListener(this);
        manageUserJPanel.add(delete);

        // 修改按钮
        update.setBounds(330, 230, 90, 50);
        update.setText("修改");
        update.setFont(new Font("", Font.PLAIN, 18));
        update.addMouseListener(this);
        manageUserJPanel.add(update);

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

        if (e.getSource() == download) {
            System.out.println("点击下载按钮.");

            String id = docID.getText();
            if (id.length() == 0) {
                System.out.println("档案号为空.");
                showJDialog("档案号为空.");
            } else {
                String result = admin.downloadFile(id);
                showJDialog(result);
                docID.setText("");
            }
        }

        if (e.getSource() == add) {
            System.out.println("点击添加按钮.");

            String username = this.username.getText();
            String password = this.password.getText();
            String role = this.role.getText();

            if (username.length() == 0 || password.length() == 0 || role.length() == 0) {
                System.out.println("用户名、密码或身份为空.");
                showJDialog("用户名、密码或身份为空.");
            } else {
                String result = admin.addUser(username, password, role);
                showJDialog(result);

                initShowUserJPanel();

                this.username.setText("");
                this.password.setText("");
                this.role.setText("");
            }
        }

        if (e.getSource() == delete) {
            System.out.println("点击删除按钮.");

            String username = this.username.getText();
            String password = this.password.getText();
            String role = this.role.getText();

            if (username.length() == 0 || password.length() == 0 || role.length() == 0) {
                System.out.println("用户名、密码或身份为空.");
                showJDialog("用户名、密码或身份为空.");
            } else {
                User user = DataProcessing.searchUser(username, password);
                if (user == null || !user.getRole().equalsIgnoreCase(role)) {
                    System.out.println("用户不存在或输入信息有误.");
                    showJDialog("用户不存在或输入信息有误.");
                } else {
                    String result = admin.deleteUser(username);
                    showJDialog(result);

                    initShowUserJPanel();

                    this.username.setText("");
                    this.password.setText("");
                    this.role.setText("");
                }
            }
        }

        if (e.getSource() == update) {
            System.out.println("点击更新按钮.");

            String username = this.username.getText();
            String password = this.password.getText();
            String role = this.role.getText();

            if (username.length() == 0 || password.length() == 0 || role.length() == 0) {
                System.out.println("用户名、新密码或新身份为空.");
                showJDialog("用户名、新密码或新身份为空.");
            } else {
                User user = DataProcessing.searchUser(username);
                if (user == null) {
                    System.out.println("用户不存在.");
                    showJDialog("用户不存在.");
                } else {
                    String result = admin.updateUserInfo(username, password, role);
                    showJDialog(result);

                    initShowUserJPanel();

                    this.username.setText("");
                    this.password.setText("");
                    this.role.setText("");
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
        try {
            DataProcessing.saveDataInfo();
            System.out.println("程序数据存储到本地文件.");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
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