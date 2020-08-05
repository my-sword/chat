package Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import Controller.Controller;
import Model.Data.*;
import View.ApplyAdd;
import View.mainWindow;
import javazoom.jl.decoder.JavaLayerException;

/**
 * 接收服务端的信息和发消息到服务端，利用多线程的方式
 *
 */
public class chatManager {
    private String  ip;
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private playSound play;
    private mainWindow mainwindow;
    public chatManager(){
        play = new playSound();
    }//好友请求提示音播放


    private static final chatManager instance = new chatManager();
    public static chatManager getInstance(){
        return instance;
    }
    public void setMainwindow(mainWindow mainwindow){
        this.mainwindow = mainwindow;
    }
//Controller p470调用
    /**
     * 连接服务端
     * @param ip 服务端ip
     * @param account 连接的账号
     */
    public void connect(String ip,String account) {
        this.ip = ip;
        new Thread(){
            @Override
            public void run(){
                try{
                    socket = new Socket(ip,12333);//地址 端口
                    in = new DataInputStream(socket.getInputStream());   //获取输入流
                    out = new DataOutputStream(socket.getOutputStream());//获取输出流
                    out.writeUTF(account);//将文件内容以UTF8的形式传入管道
                    String line;
                    while ((line = in.readUTF()) != null){      //将in.readUTF()赋值line 再循环判断line不为空
                        System.out.println(line);
                        String []str = line.split(" ");  //空格分割信息(个人账号、好友账号、上下线)
                        String Y_account = str[0];
                        String I_account = str[1];
                        String msg = "";
                        for(int i = 2;i<str.length;i++){
                            if (str[i].indexOf("add") != -1){   //如果 查找add出现的位置  不为空
                                msg = msg + "";
                            }else {
                                msg = msg + str[i] + " ";
                            }
                        }

                        //获取信息的格式
                        try {
                            //有用户上线的信息
                            if(msg.equals("#@@@ ")){
                                int index = messageData.account.indexOf(Y_account);
                                if(index != -1){
                                    mainwindow.getFriendVector().get(index).setOnline();//通过管道信息设置上下线提醒
                                }
                            }
                            //用户下线的信息
                            else if (msg.equals("@@@@ ")){
                                int index = messageData.account.indexOf(Y_account);
                                if(index != -1){
                                    mainwindow.getFriendVector().get(index).setOutline();
                                }
                            }
                            //有添加好友的申请信息
                            else if(str[2].indexOf("add") != -1){
                                ResultSet ret = Controller.database.execResult("select * from user where account =?",Y_account);
                                if(ret.next()){
                                    String Age = Controller.CAGE.getAge(ret.getString("birthday"));
                                    try{
                                        play.play();  //添加消息提示音
                                    }catch (JavaLayerException e){
                                        e.printStackTrace();
                                    }
                                    //显示  申请好友同意接界面（三个基本参数，头像昵称年龄、申请语句为默认值（增加将语句存入sql再读取功能））
                                    new ApplyAdd(ret.getString("head"),ret.getString("nickname"),Age,"你好,交个朋友吧").show();
                                }
                            }
                            else {
                                //如果当前窗口就是发消息好友的窗口
                                if (messageData.account.get(mainwindow.getFriendlist().getSelectionModel().getSelectedIndex()).equals(Y_account)){
                                    ResultSet resultSet = Controller.database.execResult("select head from user where account =?",Y_account);
                                    if(resultSet.next()){
                                        try{
                                            play.play();  //添加消息提示音
                                        }catch (JavaLayerException e){
                                            e.printStackTrace();
                                        }//chat类添加
                                        Controller.ch.addLeft(resultSet.getString("head"),msg);
                                    }
                                    int i = messageData.account.indexOf(Y_account);
                                    if(i != -1){
                                        messageData.msg.get(i).add(Y_account + " " + msg);
                                    }
                                }else {
                                    /*如果不在当前窗口，消息存入记录，并设置消息未读*/
                                    int i = messageData.account.indexOf(Y_account);
                                    if(i != -1){
                                        try{
                                            play.play();
                                        }catch (JavaLayerException e){
                                            e.printStackTrace();
                                        }
                                        messageData.msg.get(i).add(Y_account + " " + msg);
                                        int count = messageData.msgTip.get(Y_account);
                                        count ++;
                                        messageData.msgTip.put(Y_account,count);
                                        mainwindow.getFriendVector().get(i).addMsgTip(1);
                                    }
                                }
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                    in.close();
                    out.close();
                    in = null;
                    out = null;
                }catch (IOException e){
                    System.out.println("没有连接上哦！");
                    e.printStackTrace();
                }
            }
        }.start();
    }
//Controller p620调用
    /**
     * 向服务端发送消息
     * @param msg 要发送的消息
     */
    public void sendServer(String msg) throws IOException{
        if(out != null){
            out.writeUTF(msg);
            out.flush();
        }
    }
}
