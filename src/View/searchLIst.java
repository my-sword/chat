package View;

import Controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import Model.Data.*;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * 搜索类
 */
public class searchLIst {
    private Button head;
    private Alert alert;
    private Label information;
    private Button friend_add;
    private Pane pane;
    private Apply apply;
    private String friendHead;

    /**
     * 构造函数（传入头像和搜索账号）
     * @param ihead 好友头像
     * @param nickname 好友昵称
     */
    public  searchLIst(String ihead,String nickname) throws IOException{
        alert = new Alert();//调用Alert自类（弹出信息窗口）
        //创建面板
        head = new Button();
        friend_add = new Button();
        information = new Label();
        pane = new Pane();
        pane.getChildren().add(head);//添加组件到面板
        pane.getChildren().add(information);
        pane.setPrefSize(470, 60);//设置宽高
        head.setPrefSize(56, 56);
        friend_add.setPrefSize(32, 32);
        friend_add.getStyleClass().add("add");//为按钮设置id
        friend_add.setLayoutX(400);
        friend_add.setLayoutY(14);
        friend_add.setTooltip(new Tooltip("添加好友"));
        pane.getChildren().add(friend_add);
        head.setLayoutX(2);
        head.setLayoutY(2);
        information.setPrefSize(200, 32);
        information.setLayoutX(65);
        information.setLayoutY(5);
        head.getStyleClass().add("head");
        information.getStyleClass().add("information");
        pane.getStyleClass().add("ListItem");

        setText(nickname);//自方法,将账号显示在标签上
        setHead(ihead);   //自方法,按钮设置图标
        try{//返回所有nickname（账号名）的结果集
            ResultSet ret = Controller.database.execResult("select * from user where nickname =?",nickname);
            if(ret.next()){//指针指向第一个值 并计数加1
                //调用Apply自类（好友申请窗体，Controller类调用cacuAge自类根据 结果集的生日 计算年龄）
                apply = new Apply(ihead,nickname,Controller.CAGE.getAge(ret.getString("birthday")) + "岁");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * 设置头像的方法
     * @param button
     * @param head
     */
    public void setHeadPorTrait(Button button, String head) {
        button.setStyle(String.format("-fx-background-image:url('View/Fxml/CSS/Image/head/%s.jpg')", head));
    }
    public void setHead(String head){
        setHeadPorTrait(this.head,head);
        friendHead = head;
    }

    /**
     * 设置与获取好友列表上的备注
     */
    public void setText(String Text){
        information.setText(Text);
    }
    public String getText(){ return information.getText();}
    public Pane getPane(){ return pane;}

//由addFriend--Controller调用
    /**
     * 发送好友申请
     */
    public void sendAdd(String Account) throws IOException {
        friend_add.setOnAction(event -> {
            ResultSet resultSet1 = null;
            boolean flag = false;
            try {
                resultSet1 = Controller.database.execResult("select * from companion where I_account = ?",Controller.userdata.getAccount());
                while (resultSet1.next()){
                    if(resultSet1.getString("Y_account").equals(Account)){
                        alert.setText("该账号已经是你的好友！");
                        alert.exec();//执行消息框
                        flag = true;
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            if (!flag){//如果不是已有好友账号，显示请求界面
                apply.show();
            }
        });
    }
}
