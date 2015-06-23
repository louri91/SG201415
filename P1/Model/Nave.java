package Model;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import java.io.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author NikolaiGiovanni
 * @author AmandaFernandezPiedra
 */

public class Nave {
    // Atributos de relación
    private BranchGroup nave;
    private String nombre;
    
    // ******* Constructor
    public Nave (String name,Camara camNave) 
    { 
        nave = new BranchGroup();
        nave.addChild(camNave);
        nombre = name;
        
    }
    public BranchGroup getNave()
    {
        return nave;
    }
    
    public void trasladar(float x, float y, float z)
    {
        BranchGroup bgt = new BranchGroup();
        TransformGroup trans = new TransformGroup();
        
        Transform3D trans3D = new Transform3D(); //Se crea la matriz de traslación
        trans3D.rotY(Math.toRadians(180));
        trans3D.setTranslation(new Vector3f(x,y,z)); //se define la traslacion sobre le eje x
        trans.setTransform(trans3D);
        trans.addChild(importarOBJ());
        trans.addChild(nave);
        bgt.addChild(trans);
        nave=bgt;
    }
    
    public BranchGroup importarOBJ()
    {
        BranchGroup bg = new BranchGroup();
        File file = new File(nombre);
        String dir = file.getParentFile().getAbsolutePath();
        String path = file.getAbsolutePath();
        
        /*
        Para incluir un modelo .obj en una escena se puede usar el siguiente código. 
        Se asume que ya se tiene creado el nodo, de nombre "nodo", de donde se quiere colgar el modelo importado
      */
        
        Scene modelo = null; 
        ObjectFile archivo = new ObjectFile (ObjectFile.RESIZE | ObjectFile.STRIPIFY | ObjectFile.TRIANGULATE );
        try {
            archivo.setBasePath(dir);
            modelo = archivo.load(path);
        } catch (FileNotFoundException | ParsingErrorException | IncorrectFormatException e) {
            System.err.println (e);
            System.exit(1);
        }
        bg.addChild ( modelo.getSceneGroup());
        
        return bg;
    }
    
    public void moverse()
    {
        float[] alphas = {0.0f, 0.0625f, 0.125f, 0.1875f, 0.25f, 
                          0.3125f, 0.375f, 0.4375f, 0.5f, 0.5625f, 
                          0.625f, 0.6875f, 0.75f, 0.8125f, 0.875f, 
                          0.9375f, 1.0f};
        //punto de origen(7.1f, 0.9f, 0.0f) = (0,0,0)
        Point3f[] posiciones = {
            new Point3f(0.0f, 0.0f, 0.0f),
            new Point3f(4.75f, 3.2f, -3.97f),
            new Point3f(12.32f, 6.5f, -13.64f),
            new Point3f(7.51f, 4.3f, -23.87f),
            new Point3f(-2.38f, 0.0f, -47.99f),
            new Point3f(-34.03f, -3.8f, -49.42f),
            new Point3f(-43.5f, -3.1f, -31.14f),
            new Point3f(-32.12f, -4.8f, -2.77f),
            new Point3f(-15.21f, -6.5f, -10.32f),
            new Point3f(-28.3f, 0.0f, 13.22f),
            new Point3f(-33.02f, 4.8f, 8.14f),
            new Point3f(-18.23f, 3.5f, 57.16f),
            new Point3f(3.92f, 0.0f, 38.04f),
            new Point3f(17.5f, -6.5f, 5.62f),
            new Point3f(36.8f, -4.8f, 18.17f),
            new Point3f(5.42f, -3.6f, 2.99f),
            new Point3f(0.0f, 0.0f, 0.0f)
        };
        Quat4f[] rotaciones = new Quat4f[17];
        for(int i =0;i<17;i++)
        {
            rotaciones[i] = new Quat4f();
        }/*
        rotaciones[0].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(-50.11));
        rotaciones[1].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(8.2));
        rotaciones[2].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(24.61));
        rotaciones[3].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(14.61));
        rotaciones[4].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(58.32));
        rotaciones[5].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(358.96));
        rotaciones[6].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(30.62));
        rotaciones[7].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(330.82));
        rotaciones[8].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(-66.03));
        rotaciones[9].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(0.257));
        rotaciones[10].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(1.262));
        rotaciones[11].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(1.468));
        rotaciones[12].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(0.311));
        rotaciones[13].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(0.604));
        rotaciones[14].set(0.0f, 0.1f, 0.0f, (float)Math.toRadians(0.504));
        rotaciones[15].set(0.0f, 0.0f, 0.0f, (float)Math.toRadians(0));*/
        //rotaciones[16].set(0.0f, 0.0f, 0.0f, (float)Math.toRadians(0));
        
        BranchGroup bg = new BranchGroup();
        TransformGroup movimiento = new TransformGroup();
        movimiento.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Transform3D trans = new Transform3D();
        trans.set(new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f));
        Alpha alphaMov = new Alpha(-1,30000);
        
        RotPosPathInterpolator posi = new RotPosPathInterpolator(alphaMov, movimiento, trans, alphas,rotaciones, posiciones);
        posi.setSchedulingBounds(new BoundingSphere(new Point3d (0.0, 0.0, 0.0), 100.0));
        movimiento.addChild(nave);
        bg.addChild(movimiento);
        bg.addChild(posi);
        
        nave = bg;
    }
}
