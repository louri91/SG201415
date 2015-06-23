var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 100000);
var renderer = new THREE.WebGLRenderer();
var sol, tierra, tierraNubes, tierraNodo, luna, container, controls, stats;
var t = 0;
var l = 0;
var rotarLuna = true;

main();

function animate() {
    //Rotacion Sol
    sol.rotation.y += 0.001;
    //Rotacion capa solar
    capaSolar.rotation.y += 0.001;
    
    //Rotacion Tierra
    tierra.rotation.y += 0.02;
    //Rotacion nubes de la Tierra
    tierraNubes.rotation.y += 0.01;
    //Traslacion Tierra
    tierraNodo.position.x = Math.sin(t*0.3)*100;
    tierraNodo.position.z = Math.cos(t*0.3)*100;
    
    //Traslacion luna (movimiento al rededor de la Tierra)
    luna.position.x = (Math.sin(l*2)*20);
    luna.position.y = 3;
    luna.position.z = (Math.cos(l*2)*20);
    
    //Para controlar el movimiento de la luna con el click del raton
    if(rotarLuna)
    {
        l += Math.PI/180*2;
    }
    
    t += Math.PI/180*2;
    requestAnimationFrame(animate);
    renderer.render( scene, camera );
    controls.update();
}

//Función para cuando se hace click con el raton
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
    
    container = document.getElementById( 'canvas' );
    container.appendChild( renderer.domElement );
    
    // Controles del raton en la camara
    controls = new THREE.OrbitControls( camera );
    controls.damping = 0.2;
    controls.addEventListener( 'change', render );

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
    
    //Añadir nubes independientes al sol para poder rotarlas con mas o menos velocidad ke la del sol
    var canvasResultSol	= document.createElement('canvas')
	canvasResultSol.width = 720
	canvasResultSol.height = 360
	var contextResultSol = canvasResultSol.getContext('2d')	

	// Cargar el solMap.jpg
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
    
    // Añadir Tierra
    tierraNodo = new THREE.Group();
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
    
    //Añadir nubes independientes a la tierra para poder rotarlas con mas o menos velocidad ke la de la tierra
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
			// Crear dataTrans ImageData para tierraCloudMapTrans.pjg
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
    
    // Añadir luna
    var lunaGeometry = new THREE.SphereGeometry(4,64,64);
    var lunaTextura = THREE
    var lunaMaterial = new THREE.MeshPhongMaterial({
		map		    : THREE.ImageUtils.loadTexture('luna.jpg'),
		bumpMap		: THREE.ImageUtils.loadTexture('lunaBump.jpg'),
		bumpScale	: 0.8,
	});
    luna = new THREE.Mesh(lunaGeometry,lunaMaterial);
    luna.castShadow = true;
    luna.receiveShadow = true;
    tierraNodo.add(luna);
        
    //Luz Ambiente
    var luzAmbiente = new THREE.AmbientLight({color: 0x000000});
    scene.add(luzAmbiente);
    
    // Añadir camara
    camera.position.y = 300;
    camera.position.z = 500;
    camera.lookAt(scene.position);

    $("#canvas").append(renderer.domElement);
    //Llamada para que este atento al click del raton
    document.addEventListener("click", mouseClick, false);

    animate();
}
