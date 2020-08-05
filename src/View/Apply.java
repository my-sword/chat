package View;

import Controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.StageStyle;
import Model.chatManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 好友申请发送界面
 */
public class Apply extends Window{
    private String  textArea;
    //请求头像账号名年龄
    public Apply(String head,String nickname,String age) throws IOException{
        root = FXMLLoader.load(getClass().getResource("Fxml/Apply.fxml"));
        setScene(new Scene(root,485,310));
        initStyle(StageStyle.TRANSPARENT);
        move();
        setIcon();
        back();
        setHeadPorTrait(((Button)$("head")),head);
        ((Label) $("nickname")).setText(nickname);
        ((Label) $("age")).setText(age);
        sendAdd();
    }
    /**
     * 设置头像的方法
     * @param button
     * @param head
     */
    public void setHeadPorTrait(Button button, String head) {
        button.setStyle(String.format("-fx-background-image:url('View/Fxml/CSS/Image/head/%s.jpg')", head));
    }
    public void back(){
        ((Button) $("cancel")).setOnAction(event -> {
            this.close();
        });
    }

    /**
     * 把好友申请发送到服务端
     */
    public void sendAdd(){
        ((Button) $("submit")).setOnAction(event -> {
            String nickname = ((Label) $("nickname")).getText();
            try{
                ResultSet ret = Controller.database.execResult("select * from user where nickname =?",nickname);
                if(ret.next()){
                    textArea = ((TextArea) $("message")).getText();//获取文本域信息
                    String line = Controller.userdata.getAccount() + " " + ret.getString("account") + " " + "add" + "你好";
                    try{
                        chatManager.getInstance().sendServer(line);//发送服务器请求（数据库端）  目的：播放提示音 playSound
                        this.close();

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        });
    }

}
