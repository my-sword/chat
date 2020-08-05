package View;

import Model.GetMessageCode;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import java.io.IOException;


/**
 * 账号注册界面
 *
 */
public class Register extends Window {
    private ToggleGroup group;
    public Register() throws IOException {
        root = FXMLLoader.load(getClass().getResource("Fxml/Register.fxml"));//载入注册界面布局
        Scene scene = new Scene(root,600,639);
        initStyle(StageStyle.TRANSPARENT);//窗口无边框  StageStyle.UTILITY窗口无任务栏
        setScene(scene);
        setTitle("Chat");
        group = new ToggleGroup();//按钮组
        RadioButton radioButton = ((RadioButton) $("man"));
        radioButton.setToggleGroup(group);
        ((RadioButton) $("women")).setToggleGroup(group);
        radioButton.setSelected(true);
        radioButton.requestFocus();//获取焦点
        move();     //可拖动
        setIcon();  //父类自方法设置窗体图标
    }


    public void setErrorTip(String id,String Text){
        ((Label) $(id)).setText(Text);
    }//错误提示
    public void resetErrorTip(){
        ((Label) $("accountError")).setText("");
        ((Label) $("nameError")).setText("");
        ((Label) $("passwordError")).setText("");
        ((Label) $("rePasswordError")).setText("");
        ((Label) $("birthError")).setText("");
        ((Label) $("phoneError")).setText("");
        ((Label)$("codeError")).setText("");
    }//重置错误提示
    public void clear(){
        ((TextField) $("account")).clear();
        ((TextField) $("name")).clear();
        ((PasswordField) $("password")).clear();
        ((PasswordField) $("rePassword")).clear();
        ((DatePicker) $("birthday")).setValue(null);
        ((TextField) $("phone")).clear();
        RadioButton radioButton = ((RadioButton) $("man"));
        radioButton.setSelected(true);
        radioButton.requestFocus();
    }//清除文本
    public void setHeadPortrait(Button button,String head){
        //设置风格：CSS的背景（头像）
        button.setStyle(String.format("-fx-background-image:url('View/Fxml/CSS/Image/%s.jpg')",head));
    }
}
