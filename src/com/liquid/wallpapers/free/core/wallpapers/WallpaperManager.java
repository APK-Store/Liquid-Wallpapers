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
package com.liquid.wallpapers.free.core.wallpapers;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.inject.Inject;
import com.liquid.wallpapers.free.Wallpaper;
import com.liquid.wallpapers.free.core.caching.IPersistentCache;
import com.liquid.wallpapers.free.core.caching.WallpaperCache;
import com.liquid.wallpapers.free.dao.wallpapers.IWallpaperDAO;
import com.liquid.wallpapers.free.dao.wallpapers.WallpaperListReceivingException;

/**
 * @author Daniel Czerwonk
 * 
 */
public class WallpaperManager {

	private final IPersistentCache persistentWallpaperCache;
	private final IWallpaperDAO wallpaperDAO;
	private Wallpaper[] wallpapers;

	/**
	 * Creates a new instance of WallpaperManager.
	 * 
	 * @param wallpaperDAO
	 * @param persistentWallpaperCache
	 */
	@Inject
	public WallpaperManager(IWallpaperDAO wallpaperDAO,
			IPersistentCache persistentWallpaperCache) {
		super();

		this.wallpaperDAO = wallpaperDAO;
		this.persistentWallpaperCache = persistentWallpaperCache;
	}

	private Bitmap getImage(URI uri, boolean cachable, String fileNamePrefix)
			throws ClientProtocolException, IOException {
		if (!cachable) {
			return this.wallpaperDAO.downloadImage(uri);
		}

		if (WallpaperCache.isInCache(uri)) {
			return WallpaperCache.get(uri);
		} else {
			Bitmap bitmap = null;

			synchronized (this.persistentWallpaperCache) {
				if (this.persistentWallpaperCache
						.isInCache(uri, fileNamePrefix)) {
					bitmap = this.persistentWallpaperCache.get(uri,
							fileNamePrefix);
				}

				if (bitmap == null) {
					bitmap = this.wallpaperDAO.downloadImage(uri);
					this.persistentWallpaperCache.put(bitmap, uri,
							fileNamePrefix);
				}
			}

			WallpaperCache.put(uri, bitmap);

			return bitmap;
		}
	}

	/**
	 * Gets preivew image for wallpaper.
	 * 
	 * @param wallpaper
	 *            Wallpaper containing URI of image
	 * @return Image (read out from cache or downloaded)
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Bitmap getPreviewImage(Wallpaper wallpaper)
			throws ClientProtocolException, IOException {
		return this.getImage(wallpaper.getPreviewUrl(), true,
				Messages.getString("WallpaperManager.0")); //$NON-NLS-1$
	}

	/**
	 * Gets thumb image for wallpaper.
	 * 
	 * @param wallpaper
	 *            Wallpaper containing URI of image
	 * @return Image (read out from cache or downloaded)
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Bitmap getThumbImage(Wallpaper wallpaper)
			throws ClientProtocolException, IOException {
		return this.getImage(wallpaper.getThumbUrl(), true,
				Messages.getString("WallpaperManager.1")); //$NON-NLS-1$
	}

	/**
	 * Gets wallpaper by id. Note: loadAvailableWallpapers(Context) must be
	 * called before invoking this method.
	 * 
	 * @param id
	 *            Id of Wallpaper
	 * @return
	 */
	public Wallpaper getWallpaperById(String id) {
		if (this.wallpapers == null) {
			throw new IllegalStateException(
					Messages.getString("WallpaperManager.2")); //$NON-NLS-1$
		}

		for (Wallpaper wallpaper : this.wallpapers) {
			if (wallpaper.getId().equals(id)) {
				return wallpaper;
			}
		}

		return null;
	}

	/**
	 * Gets image for wallpaper.
	 * 
	 * @param wallpaper
	 *            Wallpaper containing URI of image
	 * @return Image (read out from cache or downloaded)
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public Bitmap getWallpaperImage(Wallpaper wallpaper)
			throws ClientProtocolException, IOException {
		return this.getImage(wallpaper.getWallpaperUrl(), false, null);
	}

	/**
	 * Gets wallpaper list retrieved by loadAvailableWallpapers(Context). Note:
	 * loadAvailableWallpapers(Context) must be called before invoking this
	 * method.
	 * 
	 * @return the wallpapers
	 */
	public Wallpaper[] getWallpapers() {
		if (this.wallpapers == null) {
			throw new IllegalStateException(
					Messages.getString("WallpaperManager.3")); //$NON-NLS-1$
		}

		return this.wallpapers;
	}

	/**
	 * Loads list of all available wallpapers from service.
	 * 
	 * @param context
	 * @return List of available wallpapers
	 * @throws WallpaperListReceivingException
	 */
	public synchronized void loadAvailableWallpapers(Context context)
			throws WallpaperListReceivingException {
		Wallpaper[] retrievedWallpapers = this.wallpaperDAO
				.getAvailableWallpapers(context);

		if (this.wallpapers != null) {
			// if wallpapers were retrieved before, wallpapers field must be
			// locked
			synchronized (this.wallpapers) {
				this.wallpapers = retrievedWallpapers;
			}
		} else {
			this.wallpapers = retrievedWallpapers;
		}
	}
}