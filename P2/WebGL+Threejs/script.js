var scene = new THREE.Scene();
var camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 1000);
var renderer = new THREE.WebGLRenderer();
var tierra, luna, sol,cloudMesh;
var step=0;

main();

function renderScene() {
step+=0.1;
//cube.rotation.x+=0.01;
//sphere.position.x=Math.sin(step)*20;
tierra.rotation.y+=0.004;
requestAnimationFrame(renderScene);
renderer.render(scene, camera);
}

function main() {


renderer.setClearColor(0xFFFFFF,1.0);
renderer.setSize(window.innerWidth, window.innerHeight);
renderer.shadowMapEnabled = true;
renderer.setSize(window.innerWidth, window.innerHeight);



//Sol 
var texturaSol = THREE.ImageUtils.loadTexture('sun.jpg');
var sphereGeometrySol = new THREE.SphereGeometry(7,80,80);
var sphereMaterialSol = new THREE.MeshPhongMaterial({
	map: texturaSol,
	ambient: 0xFFD700
});
sol = new THREE.Mesh(sphereGeometrySol,sphereMaterialSol);
sol.position.x = 0;
sol.position.y = 0;
sol.position.z = 0;
scene.add(sol);

//Tierra

var sphereGeometryTierra = new THREE.SphereGeometry(9,80,80);
var tierra_textura = THREE.ImageUtils.loadTexture('earth1.jpg');
var bumpTierra = THREE.ImageUtils.loadTexture('earth2.jpg');
var specularTierra = THREE.ImageUtils.loadTexture('earth3.jpg');
var canvasCloud = THREE.ImageUtils.loadTexture('earth4.jpg');
var geometryCloud   = new THREE.SphereGeometry(9, 80, 80);
var materialCloud  = new THREE.MeshPhongMaterial({
  alphaMap : canvasCloud,
  transparent : true,
  side : THREE.DoubleSide,
  opacity : 0.9,
  depthWrite : false,
});
cloudMesh = new THREE.Mesh(geometryCloud, materialCloud);

bumpTierra.anisotropy = 16;
tierra_textura.anisotropy = 16;
var sphereMaterialTierra = new THREE.MeshPhongMaterial({
	map: tierra_textura,
	bumpMap: bumpTierra,
	bumpScale: 0.8,
	specularMap: specularTierra,
	specular: new THREE.Color('grey')
});
tierra = new THREE.Mesh(sphereGeometryTierra,sphereMaterialTierra);
tierra.position.x = 40;
tierra.position.y = 0;
tierra.position.z = 0;
tierra.castShadow=true;
tierra.receiveShadow = true;
tierra.add(cloudMesh);
sol.add(tierra);

//FONDO
geometry = new THREE.SphereGeometry(100, 20, 20);
material = new THREE.MeshBasicMaterial();
material.map = THREE.ImageUtils.loadTexture("bg.png");
material.side = THREE.BackSide;
var mesh = new THREE.Mesh(geometry, material);
scene.add(mesh);

//Luna
var texturaLuna = THREE.ImageUtils.loadTexture('moon1.jpg');
var texturaLunaBump = THREE.ImageUtils.loadTexture('moon2.jpg');
texturaLuna.anisotropy = 16;
texturaLunaBump.anisotropy = 16;
var sphereGeometryLuna = new THREE.SphereGeometry(3,80,80);
var sphereMaterialLuna = new THREE.MeshPhongMaterial({
	map: texturaLuna,
	bumpMap:texturaLunaBump,
	bumpScale: 0.05,
});
luna = new THREE.Mesh(sphereGeometryLuna,sphereMaterialLuna);
luna.position.x = 20;
luna.position.y = 6;
luna.position.z = 0;
luna.castShadow=true;
luna.receiveShadow = true;
tierra.add(luna);
//scene.add(luna);

var pointLight = new THREE.PointLight(0xffffff,2,100);
pointLight.position.set(0, 0, 0 );
pointLight.castShadow=true;
pointLight.shadowCameraVisible = true;


scene.add(pointLight);


// Añadir cámara
camera.position.x = -30;
camera.position.y = 40;
camera.position.z = 30;
camera.lookAt(scene.position);

$("#canvas").append(renderer.domElement);

renderScene();


}
