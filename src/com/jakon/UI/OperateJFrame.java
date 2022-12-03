package com.jakon.UI;

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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

public class OperateJFrame extends JFrame implements WindowListener, MouseListener {

    Operator operator;

    JPanel showDocsJPanel = new JPanel();
    JPanel downloadDocsJPanel = new JPanel();
    JPanel uploadDocsJPanel = new JPanel();

    JTabbedPane jTabbedPane = new JTabbedPane();

    JTextField docID = new JTextField();

    JTextField id = new JTextField();
    JTextField description = new JTextField();
    JTextField filepath = new JTextField();

    JButton download = new JButton();
    JButton upload = new JButton();

    public OperateJFrame(Operator operator) {
        this.operator = operator;

        initJFrame();

        initView();

        this.setVisible(true);
    }

    public void initJFrame() {
        this.setTitle("档案管理系统 - 操作员 " + this.operator.getName());
        this.setSize(700, 460);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);

        this.addWindowListener(this);
    }

    public void initView() {
        initShowDocsJPanel();
        initDownloadDocsJPanel();
        initUploadDocsJPanel();

        jTabbedPane.add("档案列表", showDocsJPanel);
        jTabbedPane.add("档案下载", downloadDocsJPanel);
        jTabbedPane.add("档案上传", uploadDocsJPanel);

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

    public void initUploadDocsJPanel() {
        uploadDocsJPanel.setLayout(null);

        Font font = new Font("", Font.PLAIN, 16);

        // 添加档案编号文字
        JLabel idText = new JLabel("档案编号");
        idText.setBounds(50, 50, 70, 30);
        idText.setFont(font);
        uploadDocsJPanel.add(idText);

        // 添加档案号输入框
        id.setBounds(140, 50, 300, 30);
        id.setFont(font);
        uploadDocsJPanel.add(id);

        // 添加档案描述文字
        JLabel descriptionText = new JLabel("档案描述");
        descriptionText.setBounds(50, 100, 70, 30);
        descriptionText.setFont(font);
        uploadDocsJPanel.add(descriptionText);

        // 添加档案描述输入框
        description.setBounds(140, 100, 300, 30);
        description.setFont(font);
        uploadDocsJPanel.add(description);

        // 添加文件路径文字
        JLabel filepathText = new JLabel("文件路径");
        filepathText.setBounds(50, 150, 70, 30);
        filepathText.setFont(font);
        uploadDocsJPanel.add(filepathText);

        // 添加文件路径输入框
        filepath.setBounds(140, 150, 300, 30);
        filepath.setFont(font);
        uploadDocsJPanel.add(filepath);

        // 添加上传按钮
        upload.setBounds(140, 220, 150, 60);
        upload.setText("上传档案");
        upload.setFont(font);
        upload.addMouseListener(this);
        uploadDocsJPanel.add(upload);

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
//            DataProcessing.saveDataInfo();
//            System.out.println("程序数据存储到本地文件.");
            DataProcessing.savaDataInfoToDB();
        } catch (SQLException | ClassNotFoundException ex) {
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
                String result = operator.downloadFile(id);
                showJDialog(result);
                docID.setText("");
            }
        }

        if (e.getSource() == upload) {
            System.out.println("点击上传按钮.");

            String id = this.id.getText();
            String description = this.description.getText();
            String filepath = this.filepath.getText();

            if (id.length() == 0 || description.length() == 0 || filepath.length() == 0) {
                System.out.println("档案编号、描述或文件路径为空.");
                showJDialog("档案编号、描述或文件路径为空.");
            } else {
                String creator = this.operator.getName();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                String result = operator.uploadFile(id, creator, timestamp, description, filepath);
                System.out.println(result);
                showJDialog(result);

                initShowDocsJPanel();

                this.id.setText("");
                this.description.setText("");
                this.filepath.setText("");
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