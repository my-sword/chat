package Main;

import Controller.Controller;
import View.addFriend;
import View.mainWindow;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage window) throws Exception{
        new Controller().exec();//实例化并调用exec方法
        //new addFriend().show();//可以在此验证

        //new mainWindow().show();//可以在此验证

    }

    public static void main(String[] args) {
        launch(args);//程序传参
    }
}
