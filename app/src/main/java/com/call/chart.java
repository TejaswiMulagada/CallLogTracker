package com.call;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class chart extends Activity {
	ArrayList<String> al=new ArrayList<String>();
	ArrayList<String> nooftimes=new ArrayList<String>();
 List<PieDetailsItem> piedata = new ArrayList<PieDetailsItem>(0);
 int k=0,j=0,a=0;
 int inc=0;int out=0,mis=0;
 public void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.piechart);
  Bundle extras=getIntent().getExtras();
  al=extras.getStringArrayList("values");
  nooftimes=extras.getStringArrayList("noof");
  
  int sum=0;
  
  for(int j=0;j<nooftimes.size();j++)
  {
	String s2[]=nooftimes.get(j).split("~");
	 if(s2[0].equals("incoming call"))
	  {
	  inc=Integer.parseInt(s2[1]);
	  }
	 if(s2[0].equals("outgoing call"))
	  {
		 out=Integer.parseInt(s2[1]);
	  }
	 if(s2[0].equals("missed call"))
	  {
		 mis=Integer.parseInt(s2[1]);
	  }
  }
  for(int i=0;i<al.size();i++)
  {
	  String s[]=al.get(i).split("~");
	 
	 
	  if(s[0].equals("incoming call"))
	  {
		  TextView tv1=(TextView)findViewById(R.id.income);
		  tv1.setText("Incoming Calls "+" "+"("+inc+"/"+s[1]+")");
		  k=Integer.parseInt(s[1]);
		  sum=sum+k;
	  }
	  if(s[0].equals("outgoing call"))
	  {
		  TextView tv1=(TextView)findViewById(R.id.out);
		  tv1.setText("Outgoing Calls "+" "+"("+out+"/"+s[1]+")");
		  j=Integer.parseInt(s[1]);
		  sum=sum+j;
	  }
	  if(s[0].equals("missed call"))
	  {
		  TextView tv1=(TextView)findViewById(R.id.missed);
		  tv1.setText("Missed Calls "+" "+"("+mis+"/"+s[1]+")");
		  a=Integer.parseInt(s[1]);
		  sum=sum+a;
	  }
  }
  TextView tv1=(TextView)findViewById(R.id.tot);
  tv1.setText("All Calls "+" "+"("+sum+")");
  
  PieDetailsItem item;
  int maxCount = 0;
  int itemCount = 0;
 
  int items[] = { k, j ,a};
  int colors[] = { 0xff0099FF,  0xff00FF99,  0xffFF3333, -12303292, -7829368 };
  String itemslabel[] = { " vauesr ur 100", " vauesr ur 200",
    " vauesr ur 300", " vauesr ur 400", " vauesr ur 500" };
  for (int i = 0; i < items.length; i++) {
   itemCount = items[i];
   item = new PieDetailsItem();
   item.count = itemCount;
   item.label = itemslabel[i];
   item.color = colors[i];
   piedata.add(item);
  maxCount = maxCount + itemCount;
  }
  int size = 250;
  int BgColor = 0xffa11b1;
  Bitmap mBaggroundImage = Bitmap.createBitmap(size, size,
    Bitmap.Config.ARGB_8888);
  View_PieChart piechart = new View_PieChart(this);
  piechart.setLayoutParams(new LayoutParams(size, size));
  piechart.setGeometry(size, size, 2, 2, 2, 2, 2130837504);
  piechart.setSkinparams(BgColor);
  piechart.setData(piedata, maxCount);
  piechart.invalidate();
  piechart.draw(new Canvas(mBaggroundImage));
  piechart = null;
  ImageView mImageView = new ImageView(this);
  mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
    LayoutParams.WRAP_CONTENT));
  mImageView.setBackgroundColor(BgColor);
  mImageView.setImageBitmap(mBaggroundImage);
  LinearLayout finalLayout = (LinearLayout) findViewById(R.id.pie_container);
  finalLayout.addView(mImageView);
 }
}
