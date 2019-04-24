package engine.rendering;

import org.lwjgl.glfw.GLFW;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;

public class Camera {
	
	private Vector3f position, rotation;
	public float oldMouseX = 0, oldMouseY = 0, newMouseX = 0, newMouseY = 0, mouseSensitivity = 0.1f, VERTICAL_ROT_LOCK = 89.0f;
	float moveSpeed = 0.03f, jumpHeight = 0.2f;
	
	public Camera() {
		position = new Vector3f(0, 0, 0);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}
	
	public Matrix4f getViewMatrix() {
		Matrix4f rotateX = new Matrix4f().rotateAround(rotation.getX(), new Vector3f(1, 0, 0));
		Matrix4f rotateY = new Matrix4f().rotateAround(rotation.getY(), new Vector3f(0, 1, 0));
		Matrix4f rotateZ = new Matrix4f().rotateAround(rotation.getZ(), new Vector3f(0, 0, 1));
		Matrix4f rotation = rotateX.mul(rotateZ.mul(rotateY));
		
		Vector3f negPosition = new Vector3f(-position.getX(), -position.getY(), -position.getZ());
		Matrix4f translation = new Matrix4f().translate(negPosition);
		return rotation.mul(translation);
	}
	
	public void addPosition(Vector3f value) {
		position = position.add(value);
	}
	
	public void addPosition(float x, float y, float z) {
		position = position.add(new Vector3f(x, y, z));
	}
	
	public void addRotation(Vector3f value) {
		rotation = rotation.add(value);
	}
	
	public void addRotation(float x, float y, float z) {
		rotation = rotation.add(new Vector3f(x, y, z));
	}
	
	public void setPosition(Vector3f value) {
		position = value;
	}
	
	public void setPosition(float x, float y, float z) {
		position = new Vector3f(x, y, z);
	}
	
	public void setRotation(Vector3f value) {
		rotation = value;
	}
	
	public void setRotation(float x, float y, float z) {
		rotation = new Vector3f(x, y, z);
	}
	
	public void update(Window window) {
		if (window.isKeyDown(GLFW.GLFW_KEY_W)) this.addPosition( (float) (Math.sin(Math.toRadians(rotation.getY()))) * -moveSpeed, 0, (float) (Math.cos(Math.toRadians(rotation.getY()))) * moveSpeed);
		if (window.isKeyDown(GLFW.GLFW_KEY_A)) this.addPosition( (float) (Math.sin(Math.toRadians(rotation.getY() - 90))) * moveSpeed, 0, (float) (Math.cos(Math.toRadians(rotation.getY() - 90))) * -moveSpeed);
		if (window.isKeyDown(GLFW.GLFW_KEY_S)) this.addPosition( (float) (Math.sin(Math.toRadians(rotation.getY()))) * moveSpeed, 0, (float) (Math.cos(Math.toRadians(rotation.getY()))) * -moveSpeed);
		if (window.isKeyDown(GLFW.GLFW_KEY_D)) this.addPosition( (float) (Math.sin(Math.toRadians(rotation.getY() - 90))) * -moveSpeed, 0, (float) (Math.cos(Math.toRadians(rotation.getY() - 90))) * moveSpeed);
		if (window.isKeyDown(GLFW.GLFW_KEY_SPACE)) this.addPosition(0, jumpHeight, 0);
		if (window.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) this.addPosition(0, -jumpHeight, 0);
		
		newMouseY = (float) window.getMouseY();
		newMouseX = (float) window.getMouseX();
		
		float dy = newMouseY - oldMouseY;
		float dx = newMouseX - oldMouseX;
		
		if (rotation.getX() + -dy * mouseSensitivity <= -VERTICAL_ROT_LOCK) {
			this.addRotation(0, -dx * mouseSensitivity, 0);
		} else if (rotation.getX() + -dy * mouseSensitivity >= VERTICAL_ROT_LOCK) {
			this.addRotation(0, -dx * mouseSensitivity, 0);
		} else {
		this.addRotation(-dy * mouseSensitivity, -dx * mouseSensitivity, 0);
		}
		
		System.out.println(rotation.getX() + dy * mouseSensitivity);
		
		oldMouseY = newMouseY;
		oldMouseX = newMouseX;
	}

}
