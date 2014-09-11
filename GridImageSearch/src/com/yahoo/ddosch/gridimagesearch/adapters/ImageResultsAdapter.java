package com.yahoo.ddosch.gridimagesearch.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yahoo.ddosch.gridimagesearch.R;
import com.yahoo.ddosch.gridimagesearch.models.ImageResult;

public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultsAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result, images);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ImageResult imageInfo = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
		}
		final ImageView ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
		final TextView tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
		ivImage.setImageResource(0);
		tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));
		Picasso.with(getContext()).load(imageInfo.getThumbUrl()).into(ivImage);
		return convertView;
	}
}
