/*
 * Copyright 2022-2023 Yury Kharchenko
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

package com.j_phone.amuse.j3d;

import ru.woesss.j2me.micro3d.MathUtil;

/** @noinspection unused*/
public class Util3D {

	public static int sqrt(int p) {
		return MathUtil.iSqrt(p);
	}

	public static int sin(int p) {
		return MathUtil.iSin(p);
	}

	public static int cos(int p) {
		return MathUtil.iCos(p);
	}
}