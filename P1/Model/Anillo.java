package Model;

import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author NikolaiGiovanni
 * @author AmandaFernandezPiedra
 */

public class Anillo extends BranchGroup
{
    // Atributos de relación
    private RotationInterpolator rotator;
    
    // ******* Constructor
    public Anillo (String textura,float rad1, float rad2, int res1, int res2, long rotacion) 
    { 
        //transformaciones
        TransformGroup rotaNormal = rotarNormal(rotacion);
        BranchGroup fig = new BranchGroup();
        Appearance ap = new Appearance();
        Texture aTexture = new TextureLoader (textura, null).getTexture();
        Material aMaterial = new Material(new Color3f(0.2f, 0.2f, 0.2f),    //Color para la reaccion con la luz ambiental
                                          new Color3f(0.0f, 0.0f, 0.0f),    //Color para la emision de luz
                                          new Color3f(1.0f, 1.0f, 1.0f),    //Color para la reaccion con la luz directa
                                          new Color3f(0.0f, 0.0f, 0.0f),    //Color para el brillo creado por la luz
                                          2);  //Brillo
        TextureAttributes atriTextura = new TextureAttributes();
        atriTextura.setTextureMode(TextureAttributes.MODULATE);
        ap.setTextureAttributes(atriTextura);
        ap.setMaterial(aMaterial);
        ap.setTexture (aTexture);
        
        fig.addChild(new Torus(rad1,rad2,res1,res2,ap));
        //addChild necesarios
        rotaNormal.addChild(fig);
        this.addChild(rotaNormal);
    }
    public BranchGroup getAnillo()
    {
        return this;
    }
    public TransformGroup rotarNormal(long rotacion)
    {
        // Se crea el grupo que contendrá la transformación de rotación
        // Todo lo que cuelgue de él rotará
        TransformGroup transform = new TransformGroup ();
        // Se le permite que se cambie en tiempo de ejecución
        transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Se crea la matriz de rotación
        Transform3D yAxis = new Transform3D ();
        // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, 
                rotacion, 0, 0, 0, 0, 0);
        // Se crea el interpolador de rotación, las figuras iran rotando
        rotator = new RotationInterpolator (value, transform, yAxis, 0.0f, (float) Math.PI*2.0f);
        // Se le pone el entorno de activación y se activa
        rotator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 100.0));
        rotator.setEnable(true);
        // Se cuelga del grupo de transformación y este se devuelve
        transform.addChild(rotator);
        return transform;         
    }   
}
