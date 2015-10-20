package edu.uco.shvosi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.uco.shvosi.MyGdxGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
                              
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.useGL30 = false;
                config.title = "Get Out of My Lair!";
                config.width = 1024;
                config.height = 576;
                config.x = 50;
                config.y = 50;
                new LwjglApplication(new MyGdxGame(), config);         
         
        }
}
