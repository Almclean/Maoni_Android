#version 330

layout(location = 0) in vec4 vVector;
layout(location = 1) in vec4 color;

smooth out vec4 theColor;

uniform mat4 mvpMatrix;

void main()
{
    gl_Position = mvpMatrix * vVector;
    theColor = color;
}