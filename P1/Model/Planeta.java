package Model;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author NikolaiGiovanni
 * @author AmandaFernandezPiedra
 */

public class Planeta extends BranchGroup
{
    // Atributos de relación
    //private BranchGroup planeta;
    private RotationInterpolator rotator;
    private TransformGroup inclinacion;
    private boolean estadoRotacion = true;

    // ******* Constructor
    public Planeta (float radio, String textura, long rotacion, double inclinar, float traslacion, long orbital) 
    { 
        this.setCapability(Node.ENABLE_PICK_REPORTING);
        this.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        this.setPickable(true);
        
        //transformaciones
        TransformGroup rotaNormal = rotarNormal(rotacion);
        inclinacion = inclinar(inclinar);
        TransformGroup trasla = trasladar(traslacion);
        TransformGroup rotarAlrede = rotarAlrededor(orbital);

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
        fig.addChild(new Sphere (radio, 
            Primitive.GENERATE_NORMALS | 
            Primitive.GENERATE_TEXTURE_COORDS |
            Primitive.ENABLE_APPEARANCE_MODIFY, 64, 
            ap));
        //addChild necesarios
        rotaNormal.addChild(fig);
        inclinacion.addChild(rotaNormal);
        trasla.addChild(inclinacion);
        rotarAlrede.addChild(trasla);
        this.addChild(rotarAlrede);
        
    }
    public BranchGroup getPlaneta()
    {
        return this;
    }
    private TransformGroup rotarNormal(long rotacion)
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
    
    public TransformGroup rotarAlrededor(long orbital)
    {
        //BranchGroup bgr = new BranchGroup();
        // Se crea el grupo que contendrá la transformación de rotación
        // Todo lo que cuelgue de él rotará
        TransformGroup transform = new TransformGroup ();
        // Se le permite que se cambie en tiempo de ejecución
        transform.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        // Se crea la matriz de rotación
        Transform3D yAxis = new Transform3D ();
        // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
        Alpha value = new Alpha (-1, Alpha.INCREASING_ENABLE, 0, 0, 
                orbital, 0, 0, 0, 0, 0);
        // Se crea el interpolador de rotación, las figuras iran rotando
        rotator = new RotationInterpolator (value, transform, yAxis, 0.0f, (float) Math.PI*2.0f);
        // Se le pone el entorno de activación y se activa
        rotator.setSchedulingBounds(new BoundingSphere (new Point3d (0.0, 0.0, 0.0 ), 100.0));
        rotator.setEnable(true);
        // Se cuelga del grupo de transformación y este se devuelve
        transform.addChild(rotator);
        return transform;
    }
    
    public TransformGroup trasladar(float traslacion)
    {
        //BranchGroup bgt = new BranchGroup();
        TransformGroup trans = new TransformGroup();
        
        Transform3D trans3D = new Transform3D(); //Se crea la matriz de traslación
        trans3D.setTranslation(new Vector3f(traslacion,0,0)); //se define la traslacion sobre le eje x
        trans.setTransform(trans3D);
        return trans;
    }
    
    public TransformGroup inclinar(double inclinacion)
    {
        BranchGroup bgt = new BranchGroup();
        TransformGroup incli = new TransformGroup();
        
        Transform3D incli3D = new Transform3D(); //Se crea la matriz de traslación
        incli3D.rotZ(inclinacion); //se define la traslacion sobre le eje x
        incli.setTransform(incli3D);
        return incli;
    }
    
    public void addSatelite(Satelite sate)
    {
        inclinacion.addChild(sate);
    }
    
    public void addAnillo(Anillo anillo)
    {
        inclinacion.addChild(anillo);
    }
    
    public void cambiarEstadoRotacion(){
        estadoRotacion = !estadoRotacion;
        rotator.setEnable(estadoRotacion);
    }
}
