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
package com.liquid.wallpapers.free.dao.wallpapers.parsing;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.liquid.wallpapers.free.Wallpaper;

/**
 * @author Daniel Czerwonk
 * 
 */
final class XmlWallpaperParser implements IWallpaperParser {

	/**
	 * Creates a new instance of XmlWallpaperParser.
	 */
	XmlWallpaperParser() {
		super();
	}

	private String getTextValueByNode(Node node) {
		for (int i = 0; i < node.getChildNodes().getLength(); i++) {
			Node subNote = node.getChildNodes().item(0);

			if (subNote.getNodeType() == Node.TEXT_NODE) {
				return subNote.getNodeValue();
			}
		}

		return null;
	}

	private Wallpaper getWallpaperByElement(Node node) {
		NodeList subNodes = node.getChildNodes();

		String id = null;
		String title = null;
		URI thumbUrl = null;
		URI previewUrl = null;
		URI wallpaperUrl = null;
		String text = null;

		for (int i = 0; i < subNodes.getLength(); i++) {
			Node subNode = subNodes.item(i);

			if (subNode.getNodeName().equals(
					Messages.getString("XmlWallpaperParser.0"))) { //$NON-NLS-1$
				id = this.getTextValueByNode(subNode);
			} else if (subNode.getNodeName().equals(
					Messages.getString("XmlWallpaperParser.1"))) { //$NON-NLS-1$
				title = this.getTextValueByNode(subNode);
			} else if (subNode.getNodeName().equals(
					Messages.getString("XmlWallpaperParser.2"))) { //$NON-NLS-1$
				wallpaperUrl = URI.create(this.getTextValueByNode(subNode));
			} else if (subNode.getNodeName().equals(
					Messages.getString("XmlWallpaperParser.3"))) { //$NON-NLS-1$
				previewUrl = URI.create(this.getTextValueByNode(subNode));
			} else if (subNode.getNodeName().equals(
					Messages.getString("XmlWallpaperParser.4"))) { //$NON-NLS-1$
				thumbUrl = URI.create(this.getTextValueByNode(subNode));
			} else if (subNode.getNodeName().equals(
					Messages.getString("XmlWallpaperParser.5"))) { //$NON-NLS-1$
				text = this.getTextValueByNode(subNode);
			}
		}

		return new Wallpaper(id, title, thumbUrl, previewUrl, wallpaperUrl,
				text);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.dan_nrw.boobleftboobright.IWallpaperParser#parse(java.lang.String)
	 */
	@Override
	public List<Wallpaper> parse(String data) throws ParseException {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(
					data)));

			Element element = document.getDocumentElement();
			NodeList list = element.getElementsByTagName(Messages
					.getString("XmlWallpaperParser.6")); //$NON-NLS-1$
			List<Wallpaper> wallpapers = new ArrayList<Wallpaper>();

			for (int i = 0; i < list.getLength(); i++) {
				Wallpaper wallpaper = this.getWallpaperByElement(list.item(i));

				if (wallpaper != null) {
					wallpapers.add(wallpaper);
				}
			}

			return wallpapers;
		} catch (SAXException ex) {
			throw new ParseException(ex.getMessage(), 0);
		} catch (IOException ex) {
			throw new ParseException(ex.getMessage(), 0);
		} catch (ParserConfigurationException ex) {
			throw new ParseException(ex.getMessage(), 0);
		} catch (FactoryConfigurationError ex) {
			throw new ParseException(ex.getMessage(), 0);
		}
	}
}