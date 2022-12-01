package com.jakon.UI;

import com.jakon.Models.Browser;
import com.jakon.Models.Doc;
import com.jakon.Models.Operator;
import com.jakon.Utils.DataProcessing;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;

public class BrowseJFrame extends JFrame implements WindowListener, MouseListener {
    Browser browser;

    JPanel showDocsJPanel = new JPanel();
    JPanel downloadDocsJPanel = new JPanel();

    JTabbedPane jTabbedPane = new JTabbedPane();

    JTextField docID = new JTextField();

    JButton download = new JButton();

    public BrowseJFrame(com.jakon.Models.Browser browser) {
        this.browser = browser;

        initJFrame();

        initView();

        this.setVisible(true);
    }

    public void initJFrame() {
        this.setTitle("档案管理系统 - 浏览者 " + this.browser.getName());
        this.setSize(700, 460);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);

        this.addWindowListener(this);
    }

    public void initView() {
        initShowDocsJPanel();
        initDownloadDocsJPanel();

        jTabbedPane.add("档案列表", showDocsJPanel);
        jTabbedPane.add("档案下载", downloadDocsJPanel);

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
        download.setBounds(50, 170, 150, 60);
        download.setText("下载档案");
        download.setFont(font);
        download.addMouseListener(this);
        downloadDocsJPanel.add(download);
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

    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() == download) {
            System.out.println("点击下载按钮.");

            String id = docID.getText();
            if (id.length() == 0) {
                System.out.println("档案号为空.");
                showJDialog("档案号为空.");
            } else {
                String result = browser.downloadFile(id);
                showJDialog(result);
                docID.setText("");
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
}