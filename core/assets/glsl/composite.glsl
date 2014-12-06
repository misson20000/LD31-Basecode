struct {
  vec2 coords;
  float radius;
  vec3 color;
} pointLight;

struct {
  vec2 coordA;
  vec2 coordB;
  float radius;
  vec3 color;
} lineLight;

vec4 doLight(pointLight light) {
  float l = length(light.coords - gl_FragCoord.xy)/light.radius;
  return vec4(light.color, max(0.0, 1.0-l));
}

float dist2line(vec2 segA, vec2 segB, vec2 p) {
  vec2 p2 = vec2(segB.x - segA.x,segB.y - segA.y);
  float num = p2.x * p2.x + p2.y * p2.y;
  float u = ((p.x - segA.x) * p2.x + (p.y - segA.y) * p2.y) / num;

  u = clamp(u, 0.0, 1.0);

  float x = segA.x + u * p2.x;
  float y = segA.y + u * p2.y;

  return length(vec2(x-p.x, y-p.y));
}

vec4 doLineLight(lineLight light) {
  float l = dist2line(light.coordA, light.coordB, gl_FragCoord.xy)/light.radius;
  return vec4(light.color, max(0.0, 1.0-l));
}

vec4 composite(vec4 a, vec4 b) {
  return vec4((a.rgb * a.a) + (b.rgb * b.a), 1.-(1.-a.a)*(1.-b.a));
}

void main(void) {
  vec2 uv = gl_FragCoord.xy/iResolution.xy;
  gl_FragColor = vec4((texture2D(iChannel0, uv) * composite(
						      doLight(iMouse.xy, 300.0, vec3(1.0, 0.0, 1.0)),
						      doLineLight(vec2(100), vec2(200, 100), 200.0, vec3(0.0, 1.0, 0.0)))).xyz,1.);
}
