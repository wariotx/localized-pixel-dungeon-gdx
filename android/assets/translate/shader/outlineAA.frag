#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoord;

const float smoothing = 1.0/16.0;

void main()
{
    float alpha = texture2D(u_texture, v_texCoord).a; 
    //If somewhere between complete transparent and completely opaque
    if (alpha > 0.0 && alpha < 1.0)
    {
        gl_FragColor = vec4(0.0, 0.0, 0.0, abs(0.5 - alpha));
    }
    else
    {
        gl_FragColor = vec4(v_color.rgb, alpha);
    }
        
}