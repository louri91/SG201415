package Model;

import java.io.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author NikolaiGiovanni
 * @author AmandaFernandezPiedra
 */

public class Sonido {
    private String ruta;
    private Player player;
    public Sonido(String file)
    {
        ruta = file;
    }

    public void ReproducirMP3() 
    {
        try
        {
            InputStream fis = this.getClass().getResourceAsStream(ruta);
            BufferedInputStream bis = new BufferedInputStream(fis);
            player = new Player(bis);
        }
        catch (JavaLayerException e) 
        {
            e.printStackTrace();
        } 
        new Thread()
        {
            public void run()
            {
                try
                {
                    player.play();
                }
                catch(JavaLayerException e){}
            }
        }.start();
    }
    
    public Player getPlayer()
    {
        return player;
    }
}

