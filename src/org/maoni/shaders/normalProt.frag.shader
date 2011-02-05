#version 330

smooth in vec4 theColor;

out vec4 outputColor;

void main()
{
    float lerpValue = gl_FragCoord.y / 600.0f;
    
    outputColor = theColor;
}