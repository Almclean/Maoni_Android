
uniform mat4 mvpMatrix;

attribute vec3 vVector;

void main()
{
    gl_Position = mvpMatrix * vec4(vVector, 1.0);
}