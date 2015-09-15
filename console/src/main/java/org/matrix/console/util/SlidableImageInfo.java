/* 
 * Copyright 2014 OpenMarket Ltd
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.matrix.console.util;


import java.io.Serializable;

public class SlidableImageInfo implements Serializable {
    public String midentifier;
    public String mImageUrl = null;
    public int mRotationAngle = 0;
    public int mOrientation = 0;
    public String mMimeType = null;

    // default constructor
    public SlidableImageInfo() {

    }
}
