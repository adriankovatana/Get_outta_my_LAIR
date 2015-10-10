package edu.uco.shvosi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import edu.uco.shvosi.MyGdxGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
                              
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                config.useGL30 = false;
                config.title = "Get Out of My Lair!";
                config.width = 800;
                config.height = 450;
                new LwjglApplication(new MyGdxGame(), config);         
         
        }
}
