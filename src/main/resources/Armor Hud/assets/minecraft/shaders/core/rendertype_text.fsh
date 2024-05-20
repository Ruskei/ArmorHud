#version 330

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in float custom;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (color.a < 0.1) {
        discard;
    }

    if (custom > 0.5) {
        //health bar colors 20, 39, 59, 79, 98, 118, 138, 158, 177, 197, 217, 236, 255
        vec4 pixelColor = texture(Sampler0, texCoord0);
        ivec3 icol = ivec3(pixelColor.rgb * 255.5);
        if (icol == ivec3(13, 14, 15)) {
            discard;
        }

        //figure out if drawing health bar
        if (custom > 1.) {
            float interval = 255.5/13.;
            if (icol.x != 0 && icol.x == icol.y && icol.y == icol.z && mod(icol.x, interval) < 1 || mod(icol.x, interval) >= interval - 1.001) {
                if (icol.x <= custom) {
                    color = vec4(1 - custom / 255.5, custom / 255.5, 0., 1.);
                } else {
                    color = vec4(0., 0., 0., 1.);
                }
            }
        }
    }
    
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
