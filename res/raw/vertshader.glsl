attribute vec4 vVector;
uniform mat4 mvpMatrix;

void main()
{
    gl_Position = mvpMatrix * vVector;
}