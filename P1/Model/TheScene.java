package Model;

import javax.media.j3d.*;

/**
 *
 * @author fvelasco
 */

class TheScene extends BranchGroup 
{
    /// La rama de donde cuelga la figura que se cambia
    private BranchGroup figure;

    TheScene (Camara camLuna, Camara camNave) 
    { 
        // Se crea la rama con una figura
        figure = createScene (camLuna, camNave);

        // Se cuelga figura de la escena
        this.addChild(figure);
    }

    private BranchGroup createScene (Camara camLuna, Camara camNave) 
    {
        // Se crea la rama desde la que cuelga todo
        BranchGroup bg = new BranchGroup ();
        
        // Se le dan permisos para poder intercambiar las figuras
        bg.setCapability(Group.ALLOW_CHILDREN_EXTEND | Group.ALLOW_CHILDREN_WRITE);
        
        //Creo la nave y la traslado
        //Nave nave = new Nave("models/Smaug/smaug.obj");
        Nave nave = new Nave("src/models/KinjaDragern/KinjaDragern.obj",camNave);
        nave.trasladar(7.1f, 0.9f, 0.0f);
        nave.moverse();
        bg.addChild(nave.getNave());
        
        
        //Creamos el Sol y lo metemos a nuestro escenario del universo
        Sol sol = new Sol(6.0f,"src/imgs/sun.jpg",27000);
        bg.addChild(sol);
        //(float radio, String textura, long rotacion, long orbital, long traslacion, double inclinar) 
        //Creamos los planetas con sus Satélites y Anillos y los añadimos al escenario del universo
        Planeta mercurio = new Planeta(0.2f,"src/imgs/mercurio.jpg",58600,Math.toRadians(0.01),6.6f,1920);//Creo mercurio
        bg.addChild(mercurio);
        
        Planeta venus = new Planeta(0.47f,"src/imgs/venus.jpg",243000,Math.toRadians(177.36),7.8f,4920);
        bg.addChild(venus);
        
        Planeta tierra = new Planeta(0.5f,"src/imgs/tierra.jpg",1000,Math.toRadians(23.4),9.5f,8000); //Creo la tierra
        Satelite luna = new Satelite(0.13f,"src/imgs/Moon.jpg",0.7f,27000,Math.toRadians(5.1454),camLuna,true); //Creo la luna
        tierra.addSatelite(luna); //añado la luna a la tierra
        bg.addChild(tierra);
        
        
        Planeta marte = new Planeta(0.26f,"src/imgs/marte.jpg",1030,Math.toRadians(25.19),11.3f,15040);
        Satelite fobos = new Satelite(0.04f,"src/imgs/phobos.jpg",0.35f,334,Math.toRadians(1.093),camLuna,false);
        Satelite deimos = new Satelite(0.045f,"src/imgs/deimos.jpg",0.5f,1262,Math.toRadians(1.793),camLuna,false);
        marte.addSatelite(fobos);
        marte.addSatelite(deimos);
        bg.addChild(marte);
        
       
        Planeta jupiter = new Planeta(3.0f,"src/imgs/jupiter.jpg",414,Math.toRadians(3.12),18.6f,94880);
        Satelite io = new Satelite(0.1f,"src/imgs/io.jpg", 3.3f,1750,Math.toRadians(0.040),camLuna,false);
        Satelite europa = new Satelite(0.1f, "src/imgs/europa.jpg", 3.6f,3551,Math.toRadians(0.470),camLuna,false);
        Satelite ganimedes = new Satelite(0.17f,"src/imgs/ganimedes.jpg",4.0f,7125,Math.toRadians(0.20),camLuna,false);
        Satelite calisto = new Satelite(0.15f, "src/imgs/calisto.jpg",4.8f,16689,Math.toRadians(0.192),camLuna,false);
        jupiter.addSatelite(io);
        jupiter.addSatelite(europa);
        jupiter.addSatelite(ganimedes);
        jupiter.addSatelite(calisto);
        bg.addChild(jupiter);

        Planeta saturno = new Planeta (2.5f,"src/imgs/saturno.jpg",246,Math.toRadians(26.73),32.0f,235680);
        //(float rad1= grande, float rad2=chico, int res1=grande, int res2=chico, long rotacion) 
        Anillo pri = new Anillo("src/imgs/AnilloPeque.jpg",3.1f,0.3f,64,2,246);
        Anillo seg = new Anillo("src/imgs/AnilloMediano.jpg",4.1f,0.57f,64,2,246);
        Anillo ter = new Anillo("src/imgs/AnilloGrande.jpg",5.5f,0.7f,64,2,246);
        saturno.addAnillo(pri);
        saturno.addAnillo(seg);
        saturno.addAnillo(ter);
        bg.addChild(saturno);
     
        Planeta urano = new Planeta (1.7f,"src/imgs/urano.jpg",718,Math.toRadians(97.77),42.0f,672080);
        Satelite miranda = new Satelite(0.09f,"src/imgs/miranda.jpg",1.9f,1400,Math.toRadians(4.338),camLuna,false);
        Satelite ariel = new Satelite(0.1f,"src/imgs/ariel.jpg",2.13f,2500,Math.toRadians(0.260),camLuna,false);
        Satelite umbriel = new Satelite(0.1f, "src/imgs/umbriel.jpg",2.36f,4100,Math.toRadians(0.128),camLuna,false);
        Satelite titania = new Satelite(0.12f, "src/imgs/luna.jpg", 2.62f,8700,Math.toRadians(0.079),camLuna,false);
        Satelite oberon = new Satelite(0.12f,"src/imgs/oberon.jpg", 2.90f,13450,Math.toRadians(0.0068),camLuna,false);
        urano.addSatelite(miranda);
        urano.addSatelite(ariel);
        urano.addSatelite(umbriel);
        urano.addSatelite(titania);
        urano.addSatelite(oberon);
        bg.addChild(urano);
       
        Planeta neptuno = new Planeta(1.6f,"src/imgs/neptuno.jpg",674,Math.toRadians(28.32),53.0f,1318320);
        Satelite triton = new Satelite(0.12f,"src/imgs/triton.jpg", 1.8f,-5877,Math.toRadians(156.865),camLuna,false);
        neptuno.addSatelite(triton);
        bg.addChild(neptuno);
        
        return bg;
    }
    
    public BranchGroup getSceneGroup()
    {
        return figure;
    }
}
