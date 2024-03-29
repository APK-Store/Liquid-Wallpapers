/* 
 * Android Scroid - Screen Android
 * 
 * Copyright (C) 2009  Daniel Czerwonk <d.czerwonk@googlemail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.liquid.wallpapers.free;

import android.content.Context;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Daniel Czerwonk
 * 
 */
public final class DependencyInjector {

	private static Injector injector;

	public static <T> T getInstance(Class<T> classObject) {
		if (injector == null) {
			throw new IllegalStateException(
					Messages.getString("DependencyInjector.0")); //$NON-NLS-1$
		}

		return injector.getInstance(classObject);
	}

	static void init(Context context) {
		injector = Guice.createInjector(new ProductiveModule(context));
	}

	static boolean isInitialized() {
		return (injector != null);
	}
}