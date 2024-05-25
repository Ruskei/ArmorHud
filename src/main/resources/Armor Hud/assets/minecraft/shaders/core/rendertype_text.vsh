#version 150

#moj_import <fog.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;

uniform sampler2D Sampler0;
uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;
uniform int FogShape;
uniform vec2 ScreenSize;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out float custom;

void main() {
    vec3 pos = Position;

    vertexDistance = fog_distance(ModelViewMat, IViewRotMat * Position, FogShape);
    vertexColor = Color * texelFetch(Sampler2, UV2 / 16, 0);

    texCoord0 = UV0;
    vec4 pixelColor = texture(Sampler0, texCoord0);
    ivec3 icol = ivec3(pixelColor.rgb * 255.5);
    if (icol.xy == ivec2(13, 14) && (icol.z == 15 || icol.z == 16)) {
        vec2 scaledScreenSize = 2 / vec2(ProjMat[0][0], -ProjMat[1][1]);
        ivec3 textColor = ivec3(Color.rgb * 255.5);
        uint alignment = uint(textColor.x >> 4);
        uint verticalAlignment = uint(floor(alignment / 3.));
        uint horizontalAlignment = uint(mod(alignment, 3));

        if (Position.z == 0.0) {
            custom = 0.0;
            gl_Position = vec4(0);
            return;
        }

        custom = 1.0;
        vertexColor = texelFetch(Sampler2, UV2 / 16, 0);
        float yOffset = 0;
        int vertexId = gl_VertexID % 4;
        switch (vertexId) {
            //+1 is a gui pixel
            case 1: { yOffset += 18; break; }
            case 2: { yOffset += 18; break; }
        }

        if (horizontalAlignment == uint(0)) {
            //bottom
            pos.x -= scaledScreenSize.x / 2;
        } else if (horizontalAlignment == uint(1)) {
            //centre
            //do nothing
        } else {
            //top
            pos.x = scaledScreenSize.x - (pos.x - scaledScreenSize.x / 2.);
            if (vertexId == 2 || vertexId == 3) {
                pos.x += 36;
            }
        }

        if (verticalAlignment == uint(0)) {
            //bottom
            yOffset += scaledScreenSize.y - Color.y * 256;
        } else if (verticalAlignment == uint(1)) {
            //centre
            yOffset += scaledScreenSize.y / 2. + Color.y * 256;
        } else {
            //top
            yOffset += Color.y * 256;
        }

//        yOffset += Color.y * scaledScreenSize.y;

        pos.y = yOffset;

        if (icol.z == 16) {
            custom = Color.z * 255.5;
        }
    } else {
        custom = 0.;
    }

    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);
}