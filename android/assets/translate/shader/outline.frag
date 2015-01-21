#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoord;

void main()
{
    float alpha = texture2D(u_texture, v_texCoord).a; 

    if (alpha < 0.5 && alpha > 0.0)
        gl_FragColor = vec4(0, 0, 0, 1 - alpha);
    else
        gl_FragColor = texture2D(u_texture, v_texCoord);
}