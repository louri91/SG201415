package Universo3D;

import GUI.ControlWindow;
import Model.TheUniverse;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;

/**
 *
 * @author fvelasco
 * 
 */

public class Universo3D 
{
    public static void main(String[] args) 
    {
        // Se obtiene la configuración gráfica del sistema y se crea el Canvas3D que va a mostrar la imagen
        Canvas3D canvas = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
        Canvas3D canvas2 = new Canvas3D (SimpleUniverse.getPreferredConfiguration());
        // Se le da el tamaño deseado
        canvas.setSize(800, 600);
        canvas2.setSize(200,200);

        // Se crea el Universo con dicho Canvas3D
        TheUniverse universe = new TheUniverse (canvas, canvas2);
        // Se crea la GUI a partir del Canvas3D y del Universo
       
    }
}
