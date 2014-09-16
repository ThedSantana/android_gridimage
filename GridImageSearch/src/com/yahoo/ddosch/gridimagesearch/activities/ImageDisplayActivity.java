package com.yahoo.ddosch.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yahoo.ddosch.gridimagesearch.R;

public class ImageDisplayActivity extends Activity {

	private String url;
	private String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		url = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");
		final ImageView ivImageResult = (ImageView)findViewById(R.id.ivImageResult);
		Picasso.with(this).load(url).into(ivImageResult);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
	    finish();
	}
	
	public void onEmail(MenuItem mi) {
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
		emailIntent.setType("text/html");
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title); 
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, url); 
		startActivity(Intent.createChooser(emailIntent, "Email this image"));
	}
}
