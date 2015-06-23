package Model;

import static java.lang.Math.*;
import javax.media.j3d.*;
import javax.vecmath.*;

/**
 *
 * @author NikolaiGiovanni
 * @author AmandaFernandezPiedra
 */

public class Torus extends BranchGroup{

    public Torus(float rad1, float rad2, int res1, int res2, Appearance ap)
    {
        float[] v = new float[res1*res2*3];
        float[] vFinal;
        int[] c = new int[res1*res2*6];
        TexCoord2f[] vtex = new TexCoord2f[(res1+1)*(res2+1)];
        int[] ctex = new int[res1*res2*6];
        float[] vin = new float[3];
        float[] vp = new float[3*res2];
        float[] vt = new float[vp.length];
        float[] nv = new float[v.length];
        
        //Creo el punto inicial
        vin[0] = rad2;
        vin[1] = 0.0f;
        vin[2] = 0.0f;
        
        //Primero roto sobre el eje Z y obtengo los primeros puntos del torus, los ke hacen el anillo ke rotara para dibujar el torus
        revolucionZ(vin,vp,res2);
        
        //Segundo traslado dichos puntos a la posicion donde se rotará para obtener el torus
        trasladarArray(vp,vt,rad1,0.0f,0.0f); //trasladar a la posicion rad1
        
        //Tercero roto sobre el eje Y para obtener finalmente el torus
        revolucionY(vt,v,res1);
        
        //Ahora creo los triángulos de las caras
        crearCarasTriangulos(c,res1,res2);
        normales(res1,res2,rad1,nv);
        
        //Ahora creo el vector de coordenadas de la textura y sus indices
        texturas(vtex,ctex,res1,res2);
        
        //Por ultimo extiendo el vector de coordenadas de la geometria
        vFinal = extenderVector(v,vtex.length);
        
        //Metodo 1        
        //Ahora ya tengo la estructura de la geometria y creo la nueva geometria
        IndexedGeometryArray geometria = new IndexedTriangleArray(vFinal.length/3,
                GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.TEXTURE_COORDINATE_2,
                c.length); 
        
        //Meto las coordenadas de la geometria
        geometria.setCoordinates(0, vFinal);
        geometria.setCoordinateIndices(0, c);
        
        //Meto las normales
        geometria.setNormals(0, nv);
        geometria.setNormalIndices(0, c);
 
        //Meto las coordenadas de textura
        geometria.setTextureCoordinates(0,0,vtex);
        geometria.setTextureCoordinateIndices(0,0,ctex);
        
        //Creo la nueva figura como un Shape3D
        Shape3D torusShape = new Shape3D(geometria, ap);
        
        //añado la nueva figura Shape3D como hijo al BranchGroup de la clase, con lo que podre engancharlo a cualquier nodo
        this.addChild(torusShape);   
    }
    
    //Función para obtener una figura por revolución sobre el eje Z
    private void revolucionZ(float[] vin, float[]vp, int res2)
    {//valor de los angulos en la rotacion
        double n; 
        
    	//Generacion de los vertices con tecnica de revolucion (rotacion sobre el eje Z)
    	for(int j=0;j<res2;j++)
    	{ 
            n = j*(2*PI/(res2));
            for(int i=0;i<=vin.length-3;i+=3)
	    {
                vp[(j*vin.length)+i]=(float)(cos(n)*vin[i] - sin(n)*vin[i+1]);
                vp[(j*vin.length)+i+1]=(float)(sin(n)*vin[i] + cos(n)*vin[i+1]); 
                vp[(j*vin.length)+i+2]=vin[i+2];
            } 
        }
    }
    
    //Función para obtener una figura por revolución sobre el eje Y
    private void revolucionY(float[]vt,float[]v,int res1)
    {
        //valor de los angulos en la rotacion
        double n;   

    	//Generacion de los vertices con tecnica de revolucion (rotacion sobre el eje Y)
    	for(int j=0;j<res1;j++)
    	{ 
            n = j*(2*PI/(res1));
            for(int i=0; i<=vt.length-3;i+=3)
            {
                v[(j*vt.length)+i]=((float)(cos(n)*vt[i] + sin(n)*vt[i+2])); 
                v[(j*vt.length)+i+1]=(vt[i+1]); 
                v[(j*vt.length)+i+2]=((float)(-sin(n)*vt[i] + cos(n)*vt[i+2]));
            }
        }
    }
    
    //Función para trasladar puntos a cualquier parte del espacio 3D
    private void trasladarArray(float[] vp, float[] vt, float x, float y, float z)
    {   
        for(int i=0; i<=vp.length-3;i+=3)
	{
		vt[i]=(x+vp[i]);
		vt[i+1]=(y+vp[i+1]);
		vt[i+2]=(z+vp[i+2]);
	}
    }
    
    //Función para obtener los triangulos de las caras del torus
    private void crearCarasTriangulos(int[]c,int res1,int res2)
    {    
        int a=0, b=0, e=0;
        //Generacion de las malla de triangulos
        for(int i=0;i<res2-1;i++)
    	{
            for(int j=0;j<res1-1;j++)
            {
                //Primer triangulo sentido antihorario
                c[((res1-1)*6)*i+(6*j)]=(i+(res2*j)); //0
                c[((res1-1)*6)*i+(6*j)+1]=((i+1)+(res2*(j+1))); //4
                c[((res1-1)*6)*i+(6*j)+2]=((i+1)+(res2*j)); //1                 
                
                //Segundo triangulo sentido horario
                c[((res1-1)*6)*i+(6*j)+3]=(i+(res2*j)); //0
                c[((res1-1)*6)*i+(6*j)+4]=(i+(res2*(j+1))); //3
                c[((res1-1)*6)*i+(6*j)+5]=((i+1)+(res2*(j+1))); //4
            }
        }
        //Por ahora hemos recorrido el vector c hasta la posicion (res2-1)*(res1-1)*6
        a=(res2-1)*(res1-1)*6;
	//Ahora cierro la malla por el lateral
	for(int i=0;i<res2-1;i++)
	{
		//Primer triangulo sentido antihorario
                c[a+(6*i)]=((res2)*(res1-1)+i);
                c[a+(6*i)+1]=(i+1);
                c[a+(6*i)+2]=((res2)*(res1-1)+i+1);
                
                //Segundo triangulo sentido horario
                c[a+(6*i)+3]=((res2)*(res1-1)+i);
                c[a+(6*i)+4]=(i);
                c[a+(6*i)+5]=(i+1);
	}
        //Por ahora hemos recorrido el vector c hasta la posicion a+(res2-1)*6
        b=a+(res2-1)*6;
        //Ahora cierro la malla por arriba
        for(int i=0;i<res1-1;i++)
        {
            //Primer triangulo sentido antihorario
            c[b+(6*i)]=((i+1)*(res2-1)+i);
            c[b+(6*i)+1]=((i+1)*(res2));
            c[b+(6*i)+2]=(i*res2);
                
            //Segundo triangulo sentido horario
            c[b+(6*i)+3]=((i+1)*(res2-1)+i);
            c[b+(6*i)+4]=((i+2)*(res2-1)+i+1);
            c[b+(6*i)+5]=((i+1)*(res2));
    	}
        //Por ahora hemos recorrido el vector c hasta la posicion b+((res1-1)*6)
        e=b+((res1-1)*6);
        
	//Ahora cierro la malla del todo con los ultimos tirangulos
	//Primer triangulo sentido antihorario
        c[e]=(res2*(res1-1)+res2-1);
        c[e+1]=(0);
        c[e+2]=(res2*(res1-1));
                
        //Segundo triangulo sentido horario
        c[e+3]=(res2*(res1-1)+res2-1);
        c[e+4]=(res2-1);
        c[e+5]=(0);
        
        //y hemos recorrido el vector c res1*res2*6 veces, su tamaño completo
    }
    
    private void normales(int res1,int res2,float rad1,float[]nv)
    {
        float[] normal = new float[3];
        //Se que la primera normal del primer vertice segun hemos creado el torus es: (1,0,0)
        //Asi que la creo y le hago las mismas transformaciones que al crear el torus
        normal[0] = 1.0f;
        normal[1] = 0.0f;
        normal[2] = 0.0f;
        
        float[] vnor = new float[res2*3];
        float[] vnort = new float[vnor.length];
        
        //Primero roto sobre el eje Z y obtengo los primeros puntos del torus, los ke hacen el anillo ke rotara para dibujar las normales
        revolucionZ(normal,vnor,res2);
        
        //Segundo traslado dichos puntos a la posicion donde se rotará para obtener las normales
        trasladarArray(vnor,vnort,rad1,0.0f,0.0f); //trasladar a la posicion rad1
        
        //Tercero roto sobre el eje Y para obtener finalmente todas las normales
        revolucionY(vnort,nv,res1);
    }
    
    private void texturas(TexCoord2f[] vtex, int[] ctex, int res1, int res2)
    {
        //Se crean los valores del vector de coordenadas de textura
        int a=0,b=0,e=0;
        float s=0.0f, t=0.0f;
        int n,m;
        for(int j=0; j<=res1; j++)
        {
            s=((float)j/(res1));
            for(int i=0; i<=res2;i++)
            {
                t=((float)i/(res2));
                vtex[(res2+1)*j+i] = new TexCoord2f(s, t);
            }
        }
        for(int i=0; i<res2-1;i++)
        {
            for(int j=0; j<res1-1;j++)
            {
                //Primer triangulo sentido antihorario
                ctex[((res1-1)*6)*i+(6*j)]=(i+((res2+1)*j));
                ctex[((res1-1)*6)*i+(6*j)+1]=((i+1)+((res2+1)*(j+1)));
                ctex[((res1-1)*6)*i+(6*j)+2]=((i+1)+((res2+1)*j));            
               
                //Segundo triangulo sentido horario
                ctex[((res1-1)*6)*i+(6*j)+3]=(i+((res2+1)*j));
                ctex[((res1-1)*6)*i+(6*j)+4]=(i+((res2+1)*(j+1)));
                ctex[((res1-1)*6)*i+(6*j)+5]=((i+1)+((res2+1)*(j+1)));
            }
        }
        //Por ahora hemos recorrido el vector c hasta la posicion (res2-1)*(res1-1)*6
        a=(res2-1)*(res1-1)*6;
	//Ahora cierro la malla por el lateral
	for(int i=0;i<res2-1;i++)
	{
		//Primer triangulo sentido antihorario
                ctex[a+(6*i)]=((res2+1)*(res1-1)+i);
                ctex[a+(6*i)+1]=((res2+1)*(res1)+i+1);
                ctex[a+(6*i)+2]=((res2+1)*(res1-1)+i+1);
                
                //Segundo triangulo sentido horario
                ctex[a+(6*i)+3]=((res2+1)*(res1-1)+i);
                ctex[a+(6*i)+4]=((res2+1)*(res1)+i);
                ctex[a+(6*i)+5]=((res2+1)*(res1)+i+1);
	}//Bien
        //Por ahora hemos recorrido el vector c hasta la posicion a+(res2-1)*6
        b=a+(res2-1)*6;
        //Ahora cierro la malla por arriba
        for(int i=0;i<res1-1;i++)
        {
            //Primer triangulo sentido antihorario
            ctex[b+(6*i)]=((res2-1)+((res2+1)*i));
            ctex[b+(6*i)+1]=((i+1)*(res2+1)+(res2));
            ctex[b+(6*i)+2]=(((res2+1)*i)+res2);
                
            //Segundo triangulo sentido horario
            ctex[b+(6*i)+3]=((res2-1)+((res2+1)*i));
            ctex[b+(6*i)+4]=((i+1)*(res2+1)+(res2-1));
            ctex[b+(6*i)+5]=((i+1)*(res2+1)+(res2));
    	}
        //Por ahora hemos recorrido el vector c hasta la posicion b+((res1-1)*6)
        e=b+((res1-1)*6);
        
	//Ahora cierro la malla del todo con los ultimos tirangulos
	//Primer triangulo sentido antihorario
        ctex[e]=((res2*res1)+res1-2);
        ctex[e+1]=(((res2+1)*(res1+1))-1);
        ctex[e+2]=((res2*res1)+res1-1);
                
        //Segundo triangulo sentido horario
        ctex[e+3]=((res2*res1)+res1-2);
        ctex[e+4]=(((res2+1)*(res1+1))-2);
        ctex[e+5]=(((res2+1)*(res1+1))-1);
        //y hemos recorrido el vector c res1*res2*6 veces, su tamaño completo
    }
    
    private float[] extenderVector(float[] v, int tam)
    {
        int nPoints = v.length/3;
        int nTexCoordinates = tam;
        
        if(nPoints >= nTexCoordinates)
        {
            return v;
        }
        else
        {
            int resultSize = nTexCoordinates*3;
            float[] result = new float[resultSize];
            int i,j;
            for(i=0; i<v.length; i++)
            {
                result[i] = v[i];
            }
            
            for(j=i; j<resultSize; j++)
            {
                result[j] = 0.0f;
            }
            
            return result;
        }
    }
    
    public BranchGroup getTorus()
    {
        return this;
    }   
}