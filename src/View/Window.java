package View;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * 所有窗口的父类
 *
 *
 */
public abstract class Window extends Stage {//继承窗口类 定义虚类统一属性分配
    Parent root;
    private double xOffset;
    private double yOffset;

    /**
     * 设置图标方法
     */
    public void setIcon(){
        //为窗体图标添加图像资源流动态图像
        getIcons().add(new Image(getClass().getResourceAsStream("/View/Fxml/CSS/Image/Icon/qq.gif")));

    }

    /**
     * 窗口移动方法
     */
    public void move() {
        root.setOnMousePressed(event -> {
            xOffset = getX() - event.getScreenX();//偏移量=getX()当前屏幕位置-event.getScreenX()事件发生时的绝对坐标
            yOffset = getY() - event.getScreenY();
            getRoot().setCursor(Cursor.CLOSED_HAND);//改变手型

        });
        root.setOnMouseDragged(event -> {//设置鼠标移动事件
            setX(event.getScreenX() + xOffset);
            setY(event.getScreenY() + yOffset);


        });
        root.setOnMouseReleased(event -> {//鼠标释放恢复手型
            root.setCursor(Cursor.DEFAULT);

        });
    }

    /**
     * 获取root
     *
     * @return
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * 选择界面元素
     *
     * @param id
     * @return
     *///向上转型（Object对象的引用）
    public Object $(String id) {
        return (Object) root.lookup("#" + id);
    }

}