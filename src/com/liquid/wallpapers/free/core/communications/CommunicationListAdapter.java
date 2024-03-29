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
package com.liquid.wallpapers.free.core.communications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liquid.wallpapers.free.Communication;
import com.liquid.wallpapers.free.R;

/**
 * @author Daniel Czerwonk
 * 
 */
final class CommunicationListAdapter extends BaseAdapter {

	private final Communication[] communications;
	private final Context context;

	/**
	 * Creates a new instance of CommunicationListAdapter.
	 * 
	 * @param communications
	 * @param context
	 */
	public CommunicationListAdapter(Communication[] communications,
			Context context) {
		super();

		this.communications = communications;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return this.communications.length;
	}

	private int getImageResId(Communication communication) {
		if (communication.getType().equals(Communication.Type.Email)) {
			return android.R.drawable.sym_action_email;
		} else {
			return android.R.drawable.sym_action_chat;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return this.communications[position];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Communication communication = this.communications[position];

		if (convertView == null) {
			LayoutInflater layoutInflater = LayoutInflater.from(this.context);
			convertView = layoutInflater.inflate(R.layout.communication, null);
		}

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.communicationImageView);
		imageView.setImageResource(this.getImageResId(communication));

		TextView textView = (TextView) convertView
				.findViewById(R.id.communicationTextView);
		textView.setText(communication.getValue());

		return convertView;
	}
}