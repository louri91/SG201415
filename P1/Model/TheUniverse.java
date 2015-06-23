package Model;

import GUI.ControlWindow;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author fvelasco
 */

public class TheUniverse 
{
    // Atributos de relación
    private TheBackground background;
    private TheLights lights;
    private TheScene scene;
    private RotationInterpolator rotator;
    private BranchGroup root = new BranchGroup();
   // public Canvas3D canvas;
    public Camara camGeneral;
    public Camara camLuna;
    public Camara camNave;
    public Camara camaraParalela;
    private VirtualUniverse univer;
    public Locale uni ;

    private PickUniverso p;

    

    // ******* Constructor
    public TheUniverse (Canvas3D canvas, Canvas3D canvas2) 
    {
        // Todo cuelga de un nodo raiz
        univer = new VirtualUniverse();
        uni = new Locale (univer);
        //Cámara general
        camGeneral = new Camara(true, true, canvas, new Point3d(20.0,20.0,20.0), new Point3d(0.0,0.0,0.0), new Vector3d (0.0,1.0,0.0), 45, 0.1, 100.0);
        
        camLuna = new Camara(false, true, canvas, new Point3d(0,0.13,0), new Point3d(-0.7,0.3,0), new Vector3d (0,1,0), 60, 0.005, 10.0);
        
        camNave = new Camara(true, true, canvas, new Point3d(0,0.2,0.55), new Point3d(0,0.2,30), new Vector3d (0,1,0), 20, 0.1, 100.0);
     
        camaraParalela = new Camara (false, false, canvas2, new Point3d(20.0,20.0,20.0), new Point3d(0.0,0.0,0.0), new Vector3d (0.0,1.0,0.0), 45, 0.1, 100.0);
        
        p = new PickUniverso(canvas);
    
        ControlWindow controlWindow = new ControlWindow(canvas, canvas2, camaraParalela, camGeneral, camLuna, camNave, this);
        // Se muestra la ventana principal de la aplicación
        controlWindow.showWindow ();
       
        // Se crea y se añade el fondo
        background = new TheBackground ();
        root.addChild(background);
        
        // Se crean las luces y se añaden
        lights = new TheLights ();
        root.addChild(lights);

        // Se crea y se añade la escena al universo
        scene = new TheScene (camLuna, camNave); 

        p.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,0.0,0.0), 200.0));
        scene.addChild(p);
        
        //root.addChild(p);
        
        
        uni.addBranchGraph(scene);
        p.initSearch(scene);
        // Se optimiza la escena y se cuelga del universo
        root.compile();
        camGeneral.compile();
        //camLuna.compile();
        //camNave.compile();
        uni.addBranchGraph(root); 
        uni.addBranchGraph(camGeneral);
        //uni.addBranchGraph(camLuna);
        //uni.addBranchGraph(camNave);
    }
    
    // ******* Private
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
    
    private TransformGroup rotarAlrededor(long rotacion)
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
    
    private TransformGroup trasladar(float x, float y, float z)
    {
        TransformGroup trans = new TransformGroup();
        
        Transform3D trans3D = new Transform3D(); //Se crea la matriz de traslación
        trans3D.setTranslation(new Vector3f(x,y,z)); //se define la traslacion sobre le eje x
        trans.setTransform(trans3D);
        return trans;
    }
    // ******* Public
    public void closeApp (int code) 
    {
        System.exit (code);
    }
    // Esta clase es la fachada del modelo. 
    // Recibe todas las solicitudes de actuación y las redirige a los objetos que corresponden
    
    public void setLightsOnOff (int lightIndex, boolean onOff) 
    {
        lights.setOnOff(lightIndex, onOff);
    }
}
