#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoord;

const float smoothing = 1.0/16.0;

void main()
{
    float distance = texture2D(u_texture, v_texCoord).a;
    float alpha = smoothstep(0.3 - smoothing, 0.3 + smoothing, distance);
    if (distance >= 0.45 && distance < 0.55)
        gl_FragColor = mix(vec4(0.0, 0.0, 0.0, alpha), vec4(v_color.rgb, alpha), (distance - 0.45) * 10.0);
    else if (distance < 0.45)
        gl_FragColor = vec4(0.0, 0.0, 0.0, alpha);
    else
        gl_FragColor = vec4(v_color.rgb, alpha);
}