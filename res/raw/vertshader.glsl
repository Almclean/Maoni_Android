
uniform mat4 mvpMatrix;

attribute vec4 vVector;
attribute vec4 a_color;

varying vec4 v_color;

void main()
{
    gl_Position = mvpMatrix * vVector;
    v_color = a_color;
}