package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 登录界面
 *
 */
public class Dialog extends Window{

    public Dialog() throws IOException{
        root = FXMLLoader.load(getClass().getResource( "Fxml/Dialog.fxml"));//调用javafx的CSS布局
        Scene scene = new Scene(root,448,397);//设置大小场景
        initStyle(StageStyle.TRANSPARENT);//去除边框
        setScene(scene);
        setTitle("Chat");
        ((Button) $("close")).setTooltip(new Tooltip("关闭"));    //调用父类模板方法 (Button)root.lookup("#close")
        ((Button) $("mini")).setTooltip(new Tooltip("最小化"));
        move();         //调用父类方法实现窗口拖动
        close1();       //关闭窗口
        ((Button) $("I_login")).setVisible(false);//默认打钩的自动登录单选框
        ((Button) $("I_login")).setManaged(false);
        setLogin();     //设置打钩事件
        setSavePass();  //设置自动登录打钩事件
        mini();         //最小化
        setIcon();      //调用父类添加窗口图标
        System.out.println("登录窗口");
    }




    /**
     * 设置自动登录和记住密码的方框选择
     */
    public void setLogin(){
        ((Button) $("I_login1")).setOnAction(event -> {
            ((Button) $("I_login1")).setVisible(false); //非打钩按钮
            ((Button) $("I_login1")).setManaged(false);
            ((Button) $("I_login")).setVisible(true);   //打钩按钮
            ((Button) $("I_login")).setManaged(true);
        });
        ((Button) $("I_login")).setOnAction(event -> {
            ((Button) $("I_login")).setVisible(false);
            ((Button) $("I_login")).setManaged(false);
            ((Button) $("I_login1")).setVisible(true);
            ((Button) $("I_login1")).setManaged(true);
        });
    }
    public void setSavePass(){
        ((Button) $("save_pass1")).setOnAction(event -> {
            ((Button) $("save_pass1")).setVisible(false);
            ((Button) $("save_pass1")).setManaged(false);
            ((Button) $("save_pass")).setVisible(true);
            ((Button) $("save_pass")).setManaged(true);
        });
        ((Button) $("save_pass")).setOnAction(event -> {
            ((Button) $("save_pass")).setVisible(false);
            ((Button) $("save_pass")).setManaged(false);
            ((Button) $("save_pass1")).setVisible(true);
            ((Button) $("save_pass1")).setManaged(true);
        });
    }

    /**
     * 关闭和最小化
     */
    public void close1(){
        ((Button) $("close")).setOnAction(event -> {
            this.close();
            this.clear();
        });
    }
    public void mini(){
        ((Button) $("mini")).setOnAction(event -> {
            setIconified(true);//最小化
        });
    }
    /**
     * 设置错误提示
     * @param id
     * @param Text
     */
    public void setErrorTip(String id,String Text){
        ((Label) $(id)).setText(Text);
    }
    /**
     * 重置错误提醒
     */
    public void resetErrorTip(){
        setErrorTip("accountError","");
        setErrorTip("passwordError","");
    }
    /**
     *清除各种输入框
     */
    public void clear(){
        ((TextField) $("UserName")).clear();
        ((PasswordField) $("Password")).clear();
    }
    public void clear(String id){
        ((TextField) $(id)).clear();
    }

}
