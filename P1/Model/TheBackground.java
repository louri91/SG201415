package Model;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.Point3d;

/**
 *
 * @author fvelasco
 */

class TheBackground extends BranchGroup {
  TheBackground () {
    
    // Se crea el objeto para el fondo y 
    //     se le asigna un área de influencia
    Background background = new Background ();
    background.setApplicationBounds (new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 100.0));
    
    // Se crea un aspecto basado en la textura a mostrar
    Appearance app = new Appearance ();
    Texture texture = new TextureLoader ("src/imgs/universo001.jpg", null).getTexture();
    app.setTexture (texture);
    
    // Se hace la esfera con un determinado radio indicándole:
    //    - Que genere coordenadas de textura
    //    - Que genere las normales hacia adentro
    //    - Que tenga el aspecto creado
    Sphere sphere = new Sphere (1.0f, Sphere.GENERATE_TEXTURE_COORDS | Sphere.GENERATE_NORMALS_INWARD, app);
    
    // Se crea la rama para la geometría del fondo, 
    BranchGroup bgGeometry = new BranchGroup ();
    // Se le añade la esfera
    bgGeometry.addChild (sphere);
    // Y se establece como geometría del objeto background
    background.setGeometry (bgGeometry);
    
    // Finalmente, se cuelga el fondo creado
    this.addChild (background);  
  }
}
