precision mediump float;

varying vec4 v_color;

void main()
{
    float odd = floor(mod(gl_FragCoord.y, 2.0));
    gl_FragColor = vec4(v_color.x, v_color.y, v_color.z, odd);
}