//
// Camera.java
//
// Darren Kessner
//


package io.github.dkessner;


public class Camera
{
    public Camera(PApplet p)
    {
        this.p = p;

        final float cameraZ = p.height/2.0f / p.tan(PConstants.PI/6);
        position.z = cameraZ;

        final float fov = PConstants.PI/3;
        final float aspect = (float)p.width / p.height;
        final float near = cameraZ/100.0f;
        final float far = cameraZ*10.0f;

        p.perspective(fov, aspect, near, far);
    }

    public static final float speed = 5.0f;
    public static final float angularSpeed = PApplet.radians(1.2f);

    private PVector position = new PVector();
    private PVector velocity = new PVector();

    private float yaw;
    private float yawVelocity;
    private float pitch;
    private float pitchVelocity;

    void moveX(float speed) {velocity.x = speed;}
    void moveY(float speed) {velocity.y = speed;}
    void moveZ(float speed) {velocity.z = speed;}
    void moveYaw(float speed) {yawVelocity = speed;}
    void movePitch(float speed) {pitchVelocity = speed;}

    public PMatrix3D getCameraMatrix() 
    {
        PMatrix3D transformation = new PMatrix3D();
        transformation.rotateY(-yaw);
        transformation.rotateX(-pitch);
        return transformation;
    }

    public void update()
    {
        PMatrix3D transformation = getCameraMatrix();
        PVector step = transformation.mult(velocity, null);
        position.add(step); 

        yaw += yawVelocity;
        pitch += pitchVelocity;
    }

    public void transform()
    {
        p.resetMatrix();
        p.rotateX(pitch);
        p.rotateY(yaw);
        p.translate(-position.x, -position.y, -position.z);
    }

    private PApplet p;
}


