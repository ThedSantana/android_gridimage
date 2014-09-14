package com.yahoo.ddosch.gridimagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.yahoo.ddosch.gridimagesearch.R;
import com.yahoo.ddosch.gridimagesearch.models.Settings;
import com.yahoo.ddosch.gridimagesearch.models.Settings.ImageColor;
import com.yahoo.ddosch.gridimagesearch.models.Settings.ImageSize;
import com.yahoo.ddosch.gridimagesearch.models.Settings.ImageType;

public class SettingsActivity extends Activity {

	private Settings settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		this.settings = Settings.getInstance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
	
	public void onSave(View v) {
		saveImageSize();
		saveImageColor();
		saveImageType();
		saveImageSite();
		finish();
	}
	
	private void saveImageSize() {
		final EditText et = (EditText)findViewById(R.id.etImageSz);
		final String value = et.getText().toString();
		if (value != null && !value.trim().isEmpty()) {
			try {
				final ImageSize e = ImageSize.valueOf(value);
				settings.setImageSize(e);
			} catch (IllegalArgumentException e) {
				// Do nothing
			}
		}
	}
	
	private void saveImageColor() {
		final EditText et = (EditText)findViewById(R.id.etColorFilter);
		final String value = et.getText().toString();
		if (value != null && !value.trim().isEmpty()) {
			try {
				final ImageColor e = ImageColor.valueOf(value);
				settings.setImageColor(e);
			} catch (IllegalArgumentException e) {
				// Do nothing
			}
		}
	}
	
	private void saveImageType() {
		final EditText et = (EditText)findViewById(R.id.etImageType);
		final String value = et.getText().toString();
		if (value != null && !value.trim().isEmpty()) {
			try {
				final ImageType e = ImageType.valueOf(value);
				settings.setImageType(e);
			} catch (IllegalArgumentException e) {
				// Do nothing
			}
		}
	}
	
	private void saveImageSite() {
		final EditText et = (EditText)findViewById(R.id.etSiteFilter);
		final String value = et.getText().toString();
		if (value != null && !value.trim().isEmpty()) {
			settings.setSite(value);
		}
	}
}
