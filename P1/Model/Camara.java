package Model;


import com.sun.j3d.utils.behaviors.mouse.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author NikolaiGiovanni
 * @author AmandaFernandezPiedra
 */

public class Camara extends BranchGroup{
    
    private boolean activa;
    private TransformGroup tg;
    private ViewPlatform vp;
    private View view;
    private Canvas3D canvas;
    private double anguloEscala;
    private double planoDelantero;
    private double planoTrasero;
    
    public Camara(boolean movimiento, boolean perspectiva, Canvas3D canvas, Point3d posicion, Point3d lookat, Vector3d vUp, double anguloEscala, double planoDelantero, double planoTrasero){
       
       this.setPickable(false);
       
       this.activa = false;
       this.canvas = canvas;
       this.anguloEscala = anguloEscala;
       this.planoDelantero = planoDelantero;
       this.planoTrasero = planoTrasero;
       
       //nivel Transform3D
       Transform3D ts = new Transform3D();
       ts.lookAt(posicion, lookat, vUp);
       ts.invert();
       
       //nivel transform group
       tg = new TransformGroup(ts);
       //nivel view platform
       vp = new ViewPlatform();
              
       if(movimiento){
           tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
           MouseRotate myMouseRotate = new MouseRotate(MouseBehavior.INVERT_INPUT);
           myMouseRotate.setFactor(0.005);
           myMouseRotate.setTransformGroup(tg);
           myMouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(), 200));
           
           
           MouseTranslate myMouseTranslate = new MouseTranslate(MouseBehavior.INVERT_INPUT);
           myMouseTranslate.setFactor(0.1);
           myMouseTranslate.setTransformGroup(tg);
           myMouseTranslate.setSchedulingBounds(new BoundingSphere(new Point3d(), 200));
           
           MouseWheelZoom myMouseZoom = new MouseWheelZoom(MouseBehavior.INVERT_INPUT);
           myMouseZoom.setFactor(2.0);
           myMouseZoom.setTransformGroup(tg);
           myMouseZoom.setSchedulingBounds(new BoundingSphere(new Point3d(), 200));

           tg.addChild(myMouseRotate);
           tg.addChild(myMouseTranslate);
           tg.addChild(myMouseZoom);
           
           //tg.addChild(orbit);
       }
       
       tg.addChild(vp);
       
       view = new View();
       view.setPhysicalBody(new PhysicalBody());
       view.setPhysicalEnvironment(new PhysicalEnvironment());
       
       if(perspectiva){
        view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        view.setFieldOfView(Math.toRadians(anguloEscala));
        view.setFrontClipDistance(planoDelantero);
        view.setBackClipDistance(planoTrasero);
       }
       else{
           view.setProjectionPolicy(View.PARALLEL_PROJECTION);
           view.setScreenScalePolicy(View.SCALE_EXPLICIT);
           view.setScreenScale(this.anguloEscala);
           view.setFrontClipDistance(this.planoDelantero);
           view.setBackClipDistance(this.planoTrasero);
       }
       
       view.attachViewPlatform(vp);
       this.addChild(tg);
    }
    
    public void activar(){
        if(!activa){
            view.addCanvas3D(this.canvas);
            activa=true;
        }
    }
    
    public void desactivar(){
        if(activa){
            view.removeCanvas3D(this.canvas);
            activa=false;
        }
    }
    
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
}