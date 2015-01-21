#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;

varying vec4 v_color;
varying vec2 v_texCoord;

void main()
{
    vec2 size = vec2(1.0, 1.0) / vec2(512.0, 512.0);

    float alpha = texture2D(u_texture, v_texCoord).a; 
    float alpha_top = texture2D(u_texture, v_texCoord + vec2(0, size.y)).a;
    float alpha_right = texture2D(u_texture, v_texCoord + vec2(size.x, 0)).a;
    float alpha_bottom = texture2D(u_texture, v_texCoord + vec2(0, -size.y)).a;
    float alpha_left = texture2D(u_texture, v_texCoord + vec2(-size.x, 0)).a;

    float alpha_top_left = texture2D(u_texture, v_texCoord + vec2(size.x, size.y)).a;
    float alpha_top_right = texture2D(u_texture, v_texCoord + vec2(-size.x, size.y)).a;
    float alpha_bottom_left = texture2D(u_texture, v_texCoord + vec2(size.x, -size.y)).a;
    float alpha_bottom_right = texture2D(u_texture, v_texCoord + vec2(-size.x, -size.y)).a;

    if (alpha == 0.0 && (alpha_top == 1.0 || alpha_right == 1.0 || alpha_bottom == 1.0 || alpha_left == 1.0 ||
        alpha_top_left == 1.0 || alpha_top_right == 1.0 || alpha_bottom_left == 1.0 || alpha_bottom_right == 1.0))
        gl_FragColor = vec4(0, 0, 0, 1);
    else
        gl_FragColor = vec4(v_color.rgb, alpha);
}