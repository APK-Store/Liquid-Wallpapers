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
package com.liquid.wallpapers.free.core.settings;

import com.liquid.wallpapers.free.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.inject.Inject;

/**
 * @author Daniel Czerwonk
 * 
 */
public final class SharedPreferencesSettingsProvider implements
		ISettingsProvider {

	private static final String CACHE_SIZE_KEY = Messages
			.getString("SharedPreferencesSettingsProvider.0"); //$NON-NLS-1$
	private final Context context;
	private SharedPreferences sharedPreferences;

	/**
	 * Creates a new instance of SharedPreferencesSettingsProvider.
	 * 
	 * @param context
	 */
	@Inject
	SharedPreferencesSettingsProvider(Context context) {
		super();

		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.dan_nrw.android.scroid.core.settings.ISettingsProvider#getCacheSize()
	 */
	@Override
	public synchronized long getCacheSize() {
		if (this.sharedPreferences == null) {
			this.loadPreferences();
		}

		return this.sharedPreferences.getLong(CACHE_SIZE_KEY, 2048);
	}

	private synchronized void loadPreferences() {
		if (this.sharedPreferences != null) {
			return;
		}

		this.sharedPreferences = this.context.getSharedPreferences(
				context.getString(R.string.applicationName), 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.dan_nrw.android.scroid.core.settings.ISettingsProvider#setCacheSize
	 * (long)
	 */
	@Override
	public synchronized void setCacheSize(long cacheSize) {
		if (this.sharedPreferences == null) {
			this.loadPreferences();
		}

		Editor editor = this.sharedPreferences.edit();
		editor.putLong(CACHE_SIZE_KEY, cacheSize);
		editor.commit();
	}
}