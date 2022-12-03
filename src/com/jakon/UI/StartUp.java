package com.jakon.UI;

import com.formdev.flatlaf.FlatIntelliJLaf;

// 用户图形化交互界面 从这里启动
public class StartUp {
    public static void main(String[] args) {
        // 设置UI主题
        FlatIntelliJLaf.setup();

        // 进入登录界面
        new LoginJFrame();

    }
}