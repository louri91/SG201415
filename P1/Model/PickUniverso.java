/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.pickfast.PickCanvas;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PickInfo;
import javax.media.j3d.SceneGraphPath;
import javax.media.j3d.WakeupOnAWTEvent;

/**
 *
 * @author NikolaiGiovanni
 * @author AmandaFernandezPiedra
 */
public class PickUniverso extends Behavior{

    private WakeupOnAWTEvent condicion;
    private PickCanvas pickCanvas;
    private Canvas3D canvas;
    
    public PickUniverso(Canvas3D canvas){
        this.canvas = canvas;
        condicion = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
        setEnable(true);
    }
    public void initSearch(BranchGroup bg){
        pickCanvas = new PickCanvas(canvas,bg);
        pickCanvas.setMode(PickInfo.PICK_GEOMETRY);
        pickCanvas.setFlags(PickInfo.SCENEGRAPHPATH | PickInfo.CLOSEST_INTERSECTION_POINT);
        pickCanvas.setTolerance((float) 0.0);
        setEnable(true);
    }
    
    @Override
    public void initialize() {
        setEnable(true);
        this.wakeupOn(condicion);
    }

    @Override
    public void processStimulus(Enumeration cond) {
        WakeupOnAWTEvent c = (WakeupOnAWTEvent)cond.nextElement();
        AWTEvent[] e = c.getAWTEvent();
        MouseEvent raton = (MouseEvent)e[0];
        pickCanvas.setShapeLocation(raton);
        PickInfo pick = pickCanvas.pickClosest();
        if(pick!=null){
            SceneGraphPath sgp = pick.getSceneGraphPath();
            System.out.println(sgp.toString());
            Planeta p = (Planeta) sgp.getNode(sgp.nodeCount()-2);
            p.cambiarEstadoRotacion();
        }                
        setEnable(true);
        wakeupOn(condicion);
        
        
    }

}
    
    
    

