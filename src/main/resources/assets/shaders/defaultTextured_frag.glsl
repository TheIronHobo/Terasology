/*
 * Copyright 2012 Benjamin Glatzel <benjamin.glatzel@me.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

uniform sampler2D texture;

void main(){
    vec4 diffColor = texture2D(texture, gl_TexCoord[0].xy);

    if (diffColor.a < 0.1)
        discard;

    gl_FragData[0].rgba = diffColor * gl_Color;
    gl_FragData[1].rgba = vec4(0.5, 1.0, 0.5, 0.0);
    gl_FragData[2].rgba = vec4(1.0, 1.0, 1.0, 0.0);
}
