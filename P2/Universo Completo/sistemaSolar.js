var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 10000000);
var renderer = new THREE.WebGLRenderer();
var sol, mercurio, venus, tierra, tierraNubes, tierraNodo, luna, marte, marteNodo, phobos, deimos, jupiter, jupiterGas, jupiterNodo, io, europa, ganimedes, calisto, saturno, saturnoNodo1, saturnoNodo2, anillo1, anillo2, anillo3, urano, uranoNodo, miranda, ariel, umbriel, titania, oberon, neptuno, neptunoNodo, triton, container, controls, stats;
var t = 0;
var l = 0;
var rotarLuna = true;

main();

function animate() {
    // Rotacion del Sol
    sol.rotation.y += 0.001;
    //Rotacion de la capa solar del Sol
    capaSolar.rotation.y += 0.001;
    
    // Rotacion de Mercurio
    mercurio.rotation.y += 0.06;
    // Traslacion de Mercurio
    mercurio.position.x = Math.sin(t*0.8)*60;
    mercurio.position.z = Math.cos(t*0.8)*60;
    
    // Rotacion de Venus
    venus.rotation.y += 0.04;
    // Inclinacion de Venus
    venus.rotation.z = degToRad(177.36);
    // Traslacion de Venus
    venus.position.x = Math.sin(t*0.5)*100;
    venus.position.z = Math.cos(t*0.5)*100;
    
    // Rotacion de la Tierra
    tierra.rotation.y += 0.02;
    // Rotacion de las nubes de la Tierra
    tierraNubes.rotation.y += 0.01;
    // Inclinacion de la Tierra
    tierraNodo.rotation.z = degToRad(23.4);
    // Traslacion de la Tierra
    tierraNodo.position.x = Math.sin(t*0.3)*180;
    tierraNodo.position.z = Math.cos(t*0.3)*180;
    
    // Traslacion de la Luna (al rededor de la Tierra)
    luna.position.x = Math.sin(l*2)*20;
    luna.position.z = Math.cos(l*2)*20;
    
    // Rotacion de Marte
    marte.rotation.y += 0.015;
    // Inclinacion de Marte
    marteNodo.rotation.z = degToRad(25.19);
    // Traslacion de Marte
    marteNodo.position.x = Math.sin(t*0.33)*350;
    marteNodo.position.z = Math.cos(t*0.33)*350;
    
    // Traslacion de Phobos (al rededor de Marte)
    phobos.position.x = Math.sin(t*1.5)*13.5;
    phobos.position.y = Math.sin(t*1.5)*6;
    phobos.position.z = Math.cos(t*1.5)*13.5;
    
    // Traslacion de Deimos (al rededor de Marte)
    deimos.position.x = -Math.sin(t*1.9)*18;
    deimos.position.y = Math.cos(t*1.9)*8;
    deimos.position.z = Math.cos(t*1.9)*18;
    
    // Rotacion de Jupiter
    jupiter.rotation.y += 0.018;
    // Rotacion de la atmosfera gaseosa de Jupiter
    jupiterGas.rotation.y += 0.1;
    // Inclinacion de Jupiter
    jupiterNodo.rotation.z = degToRad(3.12);
    // Traslacion de Jupiter
    jupiterNodo.position.x = Math.sin(t*0.15)*550;
    jupiterNodo.position.z = Math.cos(t*0.15)*550;
    
    // Traslacion de Io (al rededor de Jupiter)
    io.position.x = Math.sin(t*0.5)*25;
    io.position.y = Math.cos(t*0.5)*10;
    io.position.z = Math.cos(t*0.5)*25;
   
    // Traslacion de Europa (al rededor de Jupiter)
    europa.position.x = -Math.sin(t*0.8)*35;
    europa.position.y = Math.sin(t*0.8)*15;
    europa.position.z = Math.cos(t*0.8)*35;
    
    // Traslacion de Ganimedes (al rededor de Jupiter)
    ganimedes.position.x = Math.sin(t*0.6)*45;
    ganimedes.position.y = Math.cos(t*0.6)*20;
    ganimedes.position.z = Math.cos(t*0.6)*45;
    
    // Traslacion de Calisto (al rededor de Jupiter)
    calisto.position.x = Math.sin(t*0.4)*55;
    calisto.position.y = Math.cos(t*0.4)*25;
    calisto.position.z = Math.cos(t*0.4)*55;

    // Rotacion de Saturno
    saturno.rotation.y += 0.01;
    // Inclinacion de los anillos para estar perpendicularmente con Saturno, ya que se crean perpendiculares al plano x-z
    saturnoNodo1.rotation.x = degToRad(90);
    // Inclinacion de Saturno
    saturnoNodo2.rotation.z = degToRad(26.73);
    // Traslacion de Saturno
    saturnoNodo2.position.x = Math.sin(t*0.1)*950;
    saturnoNodo2.position.z = Math.cos(t*0.1)*950;
    
    // Rotacion de Urano
    urano.rotation.y += 0.014;
    // Inclinacion de Urano
    uranoNodo.rotation.z = degToRad(97.77);
    // Traslacion de Urano
    uranoNodo.position.x = Math.sin(t*0.09)*1750;
    uranoNodo.position.z = Math.cos(t*0.09)*1750;
    
    // Traslacion de Mirada (al rededor de Urano)
    miranda.position.x = Math.sin(t*0.3)*6;
    miranda.position.z = Math.cos(t*0.3)*6;
    
    // Traslacion de Ariel (al rededor de Urano)
    ariel.position.x = Math.sin(t*0.5)*8.8;
    ariel.position.y = Math.sin(t*0.5)*1.3;
    ariel.position.z = Math.cos(t*0.5)*8.8;
    
    // Traslacion de Umbriel (al rededor de Urano)
    umbriel.position.x = -Math.sin(t*0.2)*11.1;
    umbriel.position.y = Math.cos(t*0.2)*2.5;
    umbriel.position.z = Math.cos(t*0.2)*11.1;
    
    // Traslacion de Titania (al rededor de Urano)
    titania.position.x = Math.sin(t*0.6)*12.5;
    titania.position.y = Math.cos(t*0.6)*6.5;
    titania.position.z = Math.cos(t*0.6)*12.5;
    
    // Traslacion de Oberon (al rededor de Urano)
    oberon.position.x = -Math.sin(t*0.67)*13.5;
    oberon.position.y = Math.sin(t*0.67)*9;
    oberon.position.z = Math.cos(t*0.67)*13.5;
    
    // Rotacion de Neptuno
    neptuno.rotation.y += 0.016;
    // Inclinacion de Neptuno
    neptunoNodo.rotation.z = degToRad(28.32);
    // Traslacion de Neptuno
    neptunoNodo.position.x = Math.sin(t*0.08)*2450;
    neptunoNodo.position.z = Math.cos(t*0.08)*2450;
    
    // Traslacion de Triton (al rededor de Neptuno)
    triton.position.x = Math.sin(t*0.5)*7;
    triton.position.y = Math.sin(t*0.5)*7;
    triton.position.z = Math.cos(t*0.5)*7;
    
    t += Math.PI/180*2;
    
    // Para contolar el movimiento de la Luna con el click del raton
    if(rotarLuna)
    {
        l += Math.PI/180*2;
    }
    
    requestAnimationFrame(animate);
    renderer.render( scene, camera );
    controls.update();
}

// Funcion para pasar grados a radianes
function degToRad (degrees) {
        return degrees * Math.PI / 180;
};

// Función para cuando se hace click con el raton
function mouseClick(e) {
    if(rotarLuna)
        rotarLuna = false;
    else
        rotarLuna = true;
        
    e.preventDefault();
    return false;
}

function render() {
    renderer.render( scene, camera );
}

function main() {
    // Fondo negro
    renderer.setClearColor(0x000000,1.0);
    renderer.setSize(window.innerWidth, window.innerHeight);
    renderer.shadowMapEnabled = true;
    renderer.setSize(window.innerWidth, window.innerHeight);
    
    container = document.getElementById('canvas');
    container.appendChild(renderer.domElement);
    
    // Controles del raton en la camara
    controls = new THREE.OrbitControls(camera);
    controls.damping = 0.2;
    controls.addEventListener('change', render);

    // Fondo estrellado
    var estrellas_geometria = new THREE.Geometry();
    var estrellas_material = new THREE.ParticleBasicMaterial({
        color:0xbbbbbb, 
        size:1, 
        sizeAttenuation:false
    });
    var estrellas;
            
    for(var i=0;i<5000;i++)
    {
        var vertices = new THREE.Vector3();
        vertices.set(Math.random()*2-1,Math.random()*2-1,Math.random()*2-1);
        vertices.multiplyScalar(6000);
        estrellas_geometria.vertices.push(vertices);
    }
    estrellas = new THREE.ParticleSystem(estrellas_geometria, estrellas_material);
    estrellas.scale.set(1.5,1.5,1.5);
    scene.add(estrellas);
    
    //Foco de luz
    var pointLight = new THREE.PointLight( 0xfbdf5a,1,100000 );
    pointLight.position.set( 0, 0, 0 );
    pointLight.scale.set(55,55,55);
    pointLight.castShadow = true;
    scene.add( pointLight );
    
    // Añadir Sol
    var solGeometry	= new THREE.SphereGeometry(50, 64, 64); 
	var solMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('sol.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('solBump.jpg'),
		bumpScale : 8,
        emissive  : 0x333333,
	});
    sol = new THREE.Mesh(solGeometry,solMaterial);
    sol.castShadow = false;
    sol.receiveShadow = false;
    scene.add(sol);
    
    // Añadir nubes independientes al sol para poder rotarlas con mas o menos velocidad ke el sol
    var canvasResultSol	= document.createElement('canvas')
	canvasResultSol.width = 720
	canvasResultSol.height = 360
	var contextResultSol = canvasResultSol.getContext('2d')		

	// Cargar el soldMap.jpg
	var imageMapSol = new Image();
	imageMapSol.addEventListener("load", function() {
		
		// Crear el dataMap ImageData para el solMap.jpg
		var canvasMap = document.createElement('canvas')
		canvasMap.width	= imageMapSol.width
		canvasMap.height = imageMapSol.height
		var contextMap = canvasMap.getContext('2d')
		contextMap.drawImage(imageMapSol, 0, 0)
		var dataMap	= contextMap.getImageData(0, 0, canvasMap.width, canvasMap.height)

		// Cargar solMapTrans.jpg
		var imageTrans = new Image();
		imageTrans.addEventListener("load", function(){
			// Crear dataTrans ImageData para solMapTrans.pjg
			var canvasTrans = document.createElement('canvas')
			canvasTrans.width = imageTrans.width
			canvasTrans.height = imageTrans.height
			var contextTrans = canvasTrans.getContext('2d')
			contextTrans.drawImage(imageTrans, 0, 0)
			var dataTrans = contextTrans.getImageData(0, 0, canvasTrans.width, canvasTrans.height)
			// dataMap + dataTrans dentro de dataResult
			var dataResult = contextMap.createImageData(canvasMap.width, canvasMap.height)
			for(var y = 0, offset = 0; y < imageMapSol.height; y++){
				for(var x = 0; x < imageMapSol.width; x++, offset += 4){
					dataResult.data[offset+0] = dataMap.data[offset+0]
					dataResult.data[offset+1] = dataMap.data[offset+1]
					dataResult.data[offset+2] = dataMap.data[offset+2]
					dataResult.data[offset+3] = 255 - dataTrans.data[offset+0]
				}
			}
			// Actualizar la textura 'capaSolarMaterial' con el resultado
			contextResultSol.putImageData(dataResult,0,0)	
			capaSolarMaterial.map.needsUpdate = true;
		})
		imageTrans.src = 'solMapTrans.jpg';
	}, false);
	imageMapSol.src	= 'solMap.jpg';
	
	var capaSolarGeometry = new THREE.SphereGeometry(50.5, 64, 64)
	var capaSolarMaterial = new THREE.MeshPhongMaterial({
		map		    : new THREE.Texture(canvasResultSol),
		side		: THREE.DoubleSide,
		transparent	: true,
		opacity		: 0.8,
        depthWrite  : false,
	});
	capaSolar = new THREE.Mesh(capaSolarGeometry, capaSolarMaterial);
    capaSolar.castShadow = false;
    capaSolar.receiveShadow = false;
    sol.add(capaSolar);
    
    // Añadir Mercurio
    var mercurioGeometry	= new THREE.SphereGeometry(4, 64, 64); 
	var mercurioMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('mercurio.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('mercurioBump.jpg'),
		bumpScale : 8,
	});
    mercurio = new THREE.Mesh(mercurioGeometry,mercurioMaterial);
    mercurio.castShadow = true;
    mercurio.receiveShadow = true;
    scene.add(mercurio);

    // Añadir Venus
    var venusGeometry	= new THREE.SphereGeometry(5, 64, 64); 
	var venusMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('venus.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('venusBump.jpg'),
		bumpScale : 8,
	});
    venus = new THREE.Mesh(venusGeometry,venusMaterial);
    venus.castShadow = true;
    venus.receiveShadow = true;
    scene.add(venus);
    
    tierraNodo = new THREE.Group();
    // Añadir Tierra
    var tierraGeometry = new THREE.SphereGeometry(15, 64, 64)
	var tierraMaterial = new THREE.MeshPhongMaterial({
		map		    : THREE.ImageUtils.loadTexture('tierra.jpg'),
		bumpMap		: THREE.ImageUtils.loadTexture('tierraBump.jpg'),
		bumpScale	: 0.8,
		specularMap	: THREE.ImageUtils.loadTexture('tierraSpecular.jpg'),
		specular	: new THREE.Color('grey'),
	});
	tierra = new THREE.Mesh(tierraGeometry, tierraMaterial);
    tierra.castShadow = true;
    tierra.receiveShadow = true;
    tierraNodo.add(tierra);
    scene.add(tierraNodo);
    
    // Añadir nubes independientes a la tierra para poder rotarlas con mas o menos velocidad ke la tierra
    var canvasResult = document.createElement('canvas')
	canvasResult.width = 1024
	canvasResult.height	= 512
	var contextResult = canvasResult.getContext('2d')		

	// Cargar el tierraCloudMap.jpg
	var imageMap = new Image();
	imageMap.addEventListener("load", function() {
		
		// Crear el dataMap ImageData para el tierraCloudMap.jpg
		var canvasMap = document.createElement('canvas')
		canvasMap.width	= imageMap.width
		canvasMap.height = imageMap.height
		var contextMap = canvasMap.getContext('2d')
		contextMap.drawImage(imageMap, 0, 0)
		var dataMap	= contextMap.getImageData(0, 0, canvasMap.width, canvasMap.height)

		// Cargar tierraCloudMapTrans.jpg
		var imageTrans = new Image();
		imageTrans.addEventListener("load", function(){
			// Crear dataTrans ImageData para etierraCloudMapTrans.pjg
			var canvasTrans = document.createElement('canvas')
			canvasTrans.width = imageTrans.width
			canvasTrans.height = imageTrans.height
			var contextTrans = canvasTrans.getContext('2d')
			contextTrans.drawImage(imageTrans, 0, 0)
			var dataTrans = contextTrans.getImageData(0, 0, canvasTrans.width, canvasTrans.height)
			// dataMap + dataTrans dentro de dataResult
			var dataResult = contextMap.createImageData(canvasMap.width, canvasMap.height)
			for(var y = 0, offset = 0; y < imageMap.height; y++){
				for(var x = 0; x < imageMap.width; x++, offset += 4){
					dataResult.data[offset+0] = dataMap.data[offset+0]
					dataResult.data[offset+1] = dataMap.data[offset+1]
					dataResult.data[offset+2] = dataMap.data[offset+2]
					dataResult.data[offset+3] = 255 - dataTrans.data[offset+0]
				}
			}
			// Actualizar la textura 'tierraNubesMaterial' con el resultado
			contextResult.putImageData(dataResult,0,0)	
			tierraNubesMaterial.map.needsUpdate = true;
		})
		imageTrans.src = 'tierraCloudMapTrans.jpg';
	}, false);
	imageMap.src = 'tierraCloudMap.jpg';
	
	var tierraNubesGeometry	= new THREE.SphereGeometry(15.5, 64, 64)
	var tierraNubesMaterial	= new THREE.MeshPhongMaterial({
		map		    : new THREE.Texture(canvasResult),
		side		: THREE.DoubleSide,
		transparent	: true,
		opacity		: 0.8,
        depthWrite  : false,
	});
	tierraNubes	= new THREE.Mesh(tierraNubesGeometry, tierraNubesMaterial)
    tierraNubes.castShadow = true;
    tierraNubes.receiveShadow = true;
    tierra.add(tierraNubes);
    
    // Añadir Luna
    var lunaGeometry = new THREE.SphereGeometry(3,64,64);
    var lunaTextura = THREE
    var lunaMaterial = new THREE.MeshPhongMaterial({
		map		    : THREE.ImageUtils.loadTexture('luna.jpg'),
		bumpMap		: THREE.ImageUtils.loadTexture('lunaBump.jpg'),
		bumpScale	: 0.8,
	});
    luna = new THREE.Mesh(lunaGeometry,lunaMaterial);
    luna.position.x = (Math.sin(0)*20);
    luna.position.z = (Math.cos(0)*20);
    luna.position.y = 3;
    luna.castShadow = true;
    luna.receiveShadow = true;
    tierraNodo.add(luna);

    // Añadir Marte
    marteNodo = new THREE.Group();
    var marteGeometry	= new THREE.SphereGeometry(11, 64, 64); 
	var marteMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('marte.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('marteBump.jpg'),
		bumpScale : 5,
	});
    marte = new THREE.Mesh(marteGeometry,marteMaterial);
    marte.castShadow = true;
    marte.receiveShadow = true;
    marteNodo.add(marte);
    scene.add(marteNodo);
    
    // Añadir Phobos
    var phobosGeometry	= new THREE.SphereGeometry(2, 64, 64); 
	var phobosMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('phobos.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('phobosBump.jpg'),
		bumpScale : 8,
	});
    phobos = new THREE.Mesh(phobosGeometry,phobosMaterial);
    phobos.castShadow = true;
    phobos.receiveShadow = true;
    marteNodo.add(phobos);

    // Añadir Deimos
    var deimosGeometry	= new THREE.SphereGeometry(2, 64, 64); 
	var deimosMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('deimos.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('deimosBump.jpg'),
		bumpScale : 8,
	});
    deimos = new THREE.Mesh(deimosGeometry,deimosMaterial);
    deimos.castShadow = true;
    deimos.receiveShadow = true;
    marteNodo.add(deimos);
    
    // Añadir Jupiter
    jupiterNodo = new THREE.Group();
    var jupiterGeometry	= new THREE.SphereGeometry(20, 64, 64); 
	var jupiterMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('jupiter.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('jupiterBump.jpg'),
		bumpScale : 2,
	});
    jupiter = new THREE.Mesh(jupiterGeometry,jupiterMaterial);
    jupiter.castShadow = true;
    jupiter.receiveShadow = true;
    jupiterNodo.add(jupiter);
    scene.add(jupiterNodo);
    
    // Añadir atmosfera gaseosa a Jupiter
    var canvasResultJupiter	= document.createElement('canvas')
	canvasResultJupiter.width = 720
	canvasResultJupiter.height = 360
	var contextResultJupiter = canvasResultJupiter.getContext('2d')		

	// Cargar el jupiterMap.jpg
	var imageMapJupiter = new Image();
	imageMapJupiter.addEventListener("load", function() {
		
		// Crear el dataMap ImageData para el jupiterMap.jpg
		var canvasMap = document.createElement('canvas')
		canvasMap.width	= imageMapJupiter.width
		canvasMap.height = imageMapJupiter.height
		var contextMap = canvasMap.getContext('2d')
		contextMap.drawImage(imageMapJupiter, 0, 0)
		var dataMap	= contextMap.getImageData(0, 0, canvasMap.width, canvasMap.height)

		// Cargar jupiterMapTrans.jpg
		var imageTrans = new Image();
		imageTrans.addEventListener("load", function(){
			// Crear dataTrans ImageData para jupiterMapTrans.pjg
			var canvasTrans = document.createElement('canvas')
			canvasTrans.width = imageTrans.width
			canvasTrans.height = imageTrans.height
			var contextTrans = canvasTrans.getContext('2d')
			contextTrans.drawImage(imageTrans, 0, 0)
			var dataTrans = contextTrans.getImageData(0, 0, canvasTrans.width, canvasTrans.height)
			// dataMap + dataTrans dentro de dataResult
			var dataResult = contextMap.createImageData(canvasMap.width, canvasMap.height)
			for(var y = 0, offset = 0; y < imageMapJupiter.height; y++){
				for(var x = 0; x < imageMapJupiter.width; x++, offset += 4){
					dataResult.data[offset+0] = dataMap.data[offset+0]
					dataResult.data[offset+1] = dataMap.data[offset+1]
					dataResult.data[offset+2] = dataMap.data[offset+2]
					dataResult.data[offset+3] = 255 - dataTrans.data[offset+0]
				}
			}
			// Actualizar la textura 'saturnoGasMaterial' con el resultado
			contextResultSaturno.putImageData(dataResult,0,0)	
			jupiterGasMaterial.map.needsUpdate = true;
		})
		imageTrans.src = 'jupiterMapTrans.jpg';
	}, false);
	imageMapJupiter.src	= 'jupiterMap.jpg';
	
	var jupiterGasGeometry = new THREE.SphereGeometry(20.1, 64, 64)
	var jupiterGasMaterial = new THREE.MeshPhongMaterial({
		map		    : new THREE.Texture(canvasResultJupiter),
		side		: THREE.DoubleSide,
		transparent	: true,
		opacity		: 0.26,
        depthWrite  : true,
	});
	jupiterGas = new THREE.Mesh(jupiterGasGeometry, jupiterGasMaterial);
    jupiterGas.castShadow = true;
    jupiterGas.receiveShadow = true;
    jupiter.add(jupiterGas);
    
    // Añadir Io
    var ioGeometry	= new THREE.SphereGeometry(4, 64, 64); 
	var ioMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('io.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('ioBump.jpg'),
		bumpScale : 3,
	});
    io = new THREE.Mesh(ioGeometry,ioMaterial);
    io.castShadow = true;
    io.receiveShadow = true;
    jupiterNodo.add(io);
    
    // Añadir Europa
    var europaGeometry	= new THREE.SphereGeometry(4.1, 64, 64); 
	var europaMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('europa.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('europaBump.jpg'),
		bumpScale : 3,
	});
    europa = new THREE.Mesh(europaGeometry,europaMaterial);
    europa.castShadow = true;
    europa.receiveShadow = true;
    jupiterNodo.add(europa);
    
    // Añadir Ganimedes
    var ganimedesGeometry	= new THREE.SphereGeometry(3.7, 64, 64); 
	var ganimedesMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('ganimedes.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('ganimedesBump.jpg'),
		bumpScale : 3,
	});
    ganimedes = new THREE.Mesh(ganimedesGeometry,ganimedesMaterial);
    ganimedes.castShadow = true;
    ganimedes.receiveShadow = true;
    jupiterNodo.add(ganimedes);
    
    // Añadir Calisto
    var calistoGeometry	= new THREE.SphereGeometry(3.2, 64, 64); 
	var calistoMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('calisto.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('calistoBump.jpg'),
		bumpScale : 3,
	});
    calisto = new THREE.Mesh(calistoGeometry,calistoMaterial);
    calisto.castShadow = true;
    calisto.receiveShadow = true;
    jupiterNodo.add(calisto);

    // Añadir Saturno
    saturnoNodo1 = new THREE.Group();
    saturnoNodo2 = new THREE.Group();
    var saturnoGeometry	= new THREE.SphereGeometry(30, 64, 64); 
	var saturnoMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('saturno.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('saturnoBump.jpg'),
		bumpScale : 3,
	});
    saturno = new THREE.Mesh(saturnoGeometry,saturnoMaterial);
    saturno.castShadow = true;
    saturno.receiveShadow = true;
    saturnoNodo2.add(saturno);
    saturnoNodo2.add(saturnoNodo1);
    scene.add(saturnoNodo2);
    
    //Añadir Anillo 1
    var anillo1Geometry = new THREE.TorusGeometry(36, 4, 2, 64, Math.PI*2);
    var anillo1Material = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('anillo1.jpg'),
	});
    anillo1 = new THREE.Mesh(anillo1Geometry, anillo1Material);
    saturnoNodo1.add(anillo1);
    
    //Añadir Anillo 2
    var anillo2Geometry = new THREE.TorusGeometry(46, 5, 2, 64, Math.PI*2);
    var anillo2Material = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('anillo2.jpg'),
	});
    anillo2 = new THREE.Mesh(anillo2Geometry, anillo2Material);
    saturnoNodo1.add(anillo2);
    
    //Añadir Anillo 3
    var anillo3Geometry = new THREE.TorusGeometry(58, 6, 2, 64, Math.PI*2);
    var anillo3Material = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('anillo3.jpg'),
	});
    anillo3 = new THREE.Mesh(anillo3Geometry, anillo3Material);
    saturnoNodo1.add(anillo3);
    
    // Añadir Urano
    uranoNodo = new THREE.Group();
    var uranoGeometry	= new THREE.SphereGeometry(4.3, 64, 64); 
	var uranoMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('urano.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('uranoBump.jpg'),
		bumpScale : 3,
	});
    urano = new THREE.Mesh(uranoGeometry,uranoMaterial);
    urano.castShadow = true;
    uranoreceiveShadow = true;
    uranoNodo.add(urano);
    scene.add(uranoNodo);
    
    // Añadir Miranda
    var mirandaGeometry	= new THREE.SphereGeometry(1.4, 64, 64); 
	var mirandaMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('miranda.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('mirandaBump.jpg'),
		bumpScale : 3,
	});
    miranda = new THREE.Mesh(mirandaGeometry,mirandaMaterial);
    miranda.castShadow = true;
    miranda.receiveShadow = true;
    uranoNodo.add(miranda);
    
    // Añadir Ariel
    var arielGeometry	= new THREE.SphereGeometry(1, 64, 64); 
	var arielMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('ariel.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('arielBump.jpg'),
		bumpScale : 3,
	});
    ariel = new THREE.Mesh(arielGeometry,arielMaterial);
    ariel.castShadow = true;
    ariel.receiveShadow = true;
    uranoNodo.add(ariel);
    
    // Añadir Umbriel
    var umbrielGeometry	= new THREE.SphereGeometry(0.8, 64, 64); 
	var umbrielMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('umbriel.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('umbrielumbrielBump.jpg'),
		bumpScale : 3,
	});
    umbriel = new THREE.Mesh(umbrielGeometry,umbrielMaterial);
    umbriel.castShadow = true;
    umbriel.receiveShadow = true;
    uranoNodo.add(umbriel);
    
    // Añadir Titania
    var titaniaGeometry	= new THREE.SphereGeometry(0.5, 64, 64); 
	var titaniaMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('titania.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('titaniaBump.jpg'),
		bumpScale : 3,
	});
    titania = new THREE.Mesh(titaniaGeometry,titaniaMaterial);
    titania.castShadow = true;
    titania.receiveShadow = true;
    uranoNodo.add(titania);
    
    // Añadir Oberon
    var oberonGeometry	= new THREE.SphereGeometry(0.3, 64, 64); 
	var oberonMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('oberon.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('oberonBump.jpg'),
		bumpScale : 3,
	});
    oberon = new THREE.Mesh(oberonGeometry,oberonMaterial);
    oberon.castShadow = true;
    oberon.receiveShadow = true;
    uranoNodo.add(oberon);
    
    // Añadir Neptuno
    neptunoNodo = new THREE.Group();
    var neptunoGeometry	= new THREE.SphereGeometry(4.3, 64, 64); 
	var neptunoMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('neptuno.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('neptunoBump.jpg'),
		bumpScale : 3,
	});
    neptuno = new THREE.Mesh(neptunoGeometry,neptunoMaterial);
    neptuno.castShadow = true;
    neptuno.receiveShadow = true;
    neptunoNodo.add(neptuno);
    scene.add(neptunoNodo);
    
    // Añadir Triton
    var tritonGeometry	= new THREE.SphereGeometry(1.2, 64, 64); 
	var tritonMaterial = new THREE.MeshPhongMaterial({
		map       : THREE.ImageUtils.loadTexture('triton.jpg'),
		bumpMap   : THREE.ImageUtils.loadTexture('tritonBump.jpg'),
		bumpScale : 3,
	});
    triton = new THREE.Mesh(tritonGeometry,tritonMaterial);
    triton.castShadow = true;
    triton.receiveShadow = true;
    neptunoNodo.add(triton);
    
    // Luz Ambiente
    var luzAmbiente = new THREE.AmbientLight({color: 0x000000});
    scene.add(luzAmbiente);
    
    // Añadir camara
    camera.position.y = 300;
    camera.position.z = 500;
    camera.lookAt(scene.position);

    $("#canvas").append(renderer.domElement);
    // Para que escuche el evento del doble click del raton
    document.addEventListener("dblclick", mouseClick, false);

    animate();
}
