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
    if (icol == ivec3(13, 14, 15)) {
        vec2 scaledScreenSize = 2 / vec2(ProjMat[0][0], -ProjMat[1][1]);
        ivec3 textColor = ivec3(Color.rgb * 255.5);
        uint alignment = textColor.x >> 4;
        uint verticalAlignment = floor(alignment / 3.);
        uint horizontalAlignment = mod(alignment, 3);
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
            case 1: { yOffset += 18; pos.x /= 2; break; }
            case 2: { yOffset += 18; break; }
        }

        int yPosition;
        switch (verticalAlignment) {

        }

        yOffset += Color.y * scaledScreenSize.y;

        pos.y = yOffset;

        if (abs(Color.x - 59./255.5) < 0.01) {
            custom = Color.z * 255.5;
        }
    } else {
        custom = 0.;
    }

    gl_Position = ProjMat * ModelViewMat * vec4(pos, 1.0);
}