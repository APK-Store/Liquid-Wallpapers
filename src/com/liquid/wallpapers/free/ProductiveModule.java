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

import com.google.inject.AbstractModule;
import com.liquid.wallpapers.free.core.caching.FileSystemCachingProvider;
import com.liquid.wallpapers.free.core.caching.IPersistentCache;
import com.liquid.wallpapers.free.core.settings.ISettingsProvider;
import com.liquid.wallpapers.free.core.settings.SharedPreferencesSettingsProvider;
import com.liquid.wallpapers.free.core.wallpapers.IWallpaperUpdater;
import com.liquid.wallpapers.free.core.wallpapers.WallpaperManager;
import com.liquid.wallpapers.free.core.wallpapers.WallpaperUpdater;
import com.liquid.wallpapers.free.dao.communications.CommunicationDAO;
import com.liquid.wallpapers.free.dao.communications.ICommunicationDAO;
import com.liquid.wallpapers.free.dao.favourites.FavouriteDAO;
import com.liquid.wallpapers.free.dao.favourites.IFavouriteDAO;
import com.liquid.wallpapers.free.dao.wallpapers.IWallpaperDAO;
import com.liquid.wallpapers.free.dao.wallpapers.WallpaperDAO;
import com.liquid.wallpapers.free.dao.wallpapers.parsing.IWallpaperParser;
import com.liquid.wallpapers.free.dao.wallpapers.parsing.JsonWallpaperParser;

/**
 * @author Daniel Czerwonk
 * 
 */
final class ProductiveModule extends AbstractModule {

	private final Context context;

	/**
	 * Creates a new instance of ProductiveModule.
	 * 
	 * @param context
	 */
	public ProductiveModule(Context context) {
		super();

		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		this.bind(Context.class).toInstance(this.context);
		this.bind(WallpaperManager.class).asEagerSingleton();
		this.bind(ISettingsProvider.class)
				.to(SharedPreferencesSettingsProvider.class).asEagerSingleton();
		this.bind(IWallpaperDAO.class).to(WallpaperDAO.class)
				.asEagerSingleton();
		this.bind(IPersistentCache.class).to(FileSystemCachingProvider.class)
				.asEagerSingleton();
		this.bind(IWallpaperUpdater.class).to(WallpaperUpdater.class)
				.asEagerSingleton();
		this.bind(ICommunicationDAO.class).to(CommunicationDAO.class)
				.asEagerSingleton();
		this.bind(IFavouriteDAO.class).to(FavouriteDAO.class)
				.asEagerSingleton();
		this.bind(IWallpaperParser.class).to(JsonWallpaperParser.class)
				.asEagerSingleton();
	}
}