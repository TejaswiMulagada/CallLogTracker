package com.call;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AlbumsActivity extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the AlbumsActivity tab");
        setContentView(textview);
    }

}
