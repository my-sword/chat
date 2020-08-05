package Model;

/**
 * 消息提示音的播放
 *
 */

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class playSound {
    Player player;
    public playSound(){

    }
    public void play() throws FileNotFoundException, JavaLayerException {
        BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(new File("qq.mp3")));//E:\javaLastWork\chat\src\Model\
        player = new Player(buffer);//调用jar包
        player.play();
    }
}


