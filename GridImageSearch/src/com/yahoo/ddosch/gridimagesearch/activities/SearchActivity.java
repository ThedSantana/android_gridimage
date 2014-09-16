package com.yahoo.ddosch.gridimagesearch.activities;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.ddosch.gridimagesearch.R;
import com.yahoo.ddosch.gridimagesearch.adapters.ImageResultsAdapter;
import com.yahoo.ddosch.gridimagesearch.listeners.EndlessScrollListener;
import com.yahoo.ddosch.gridimagesearch.models.ImageResult;
import com.yahoo.ddosch.gridimagesearch.models.Settings;

public class SearchActivity extends Activity {
	
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	private Settings settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		settings = Settings.getInstance();
		settings.setStart(0);
		setupViews();
		imageResults = new ArrayList<ImageResult>();
		aImageResults = new ImageResultsAdapter(this, imageResults);
		gvResults.setAdapter(aImageResults);
		if (settings.getQuery() != null && !settings.getQuery().trim().isEmpty()) {
			onImageSearch(null);
		}
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	        	getMoreResults();
	        }
	    });
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple, menu);
        return true;
    }
	
	public void onSettings(MenuItem mi) {
		startActivityForResult(new Intent(this, SettingsActivity.class), 0);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		etQuery.setText(settings.getQuery());
        onImageSearch(null);
    }
	
	private void setupViews() {
		etQuery = (EditText)findViewById(R.id.etQuery);
		if (settings.getQuery() != null && !settings.getQuery().trim().isEmpty()) {
			etQuery.setText(settings.getQuery());
		}
		gvResults = (GridView)findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				final ImageResult imageResult = imageResults.get(position);
				intent.putExtra("url", imageResult.getFullUrl());
				intent.putExtra("title", imageResult.getTitle());
				startActivityForResult(intent, 0);
			}
		});
	}
	
	public void onImageSearch(View v) {
		if (v != null) {
			settings.setQuery(etQuery.getText().toString());
		}
		final AsyncHttpClient client = new AsyncHttpClient();
		client.get(getQuery(), new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					settings.setStart(0);
					imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
					aImageResults.notifyDataSetChanged();
					getMoreResults();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void getMoreResults() {
		if (settings.getQuery() != null && !settings.getQuery().trim().isEmpty() && settings.getStart() < 56) {
			settings.setStart(settings.getStart() + 8);
			final AsyncHttpClient client = new AsyncHttpClient();
			client.get(getQuery(), new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
					JSONArray imageResultsJson = null;
					try {
						imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
						imageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
						aImageResults.notifyDataSetChanged();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	private String getQuery() {
		final StringBuilder bldr = new StringBuilder("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8");
		bldr.append("&q=").append(settings.getQuery());
		if (settings.getStart() > 0) {
			bldr.append("&start=").append(settings.getStart());
		}
		if (settings.getImageSize() != null) {
			bldr.append("&imgsz=").append(settings.getImageSize());
		}
		if (settings.getImageColor() != null) {
			bldr.append("&imgcolor=").append(settings.getImageColor());
		}
		if (settings.getImageType() != null) {
			bldr.append("&imgtype=").append(settings.getImageType());
		}
		if (settings.getSite() != null) {
			bldr.append("&as_sitesearch=").append(settings.getSite());
		}
		return bldr.toString();
	}
}
