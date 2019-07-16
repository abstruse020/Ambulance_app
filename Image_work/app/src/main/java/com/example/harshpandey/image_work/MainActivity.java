package com.example.harshpandey.image_work;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String path;
    Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyPermissions();
    }
    public void select(View view)
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("file/*");
        startActivityForResult(intent,1);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==1 || resultCode==Activity.RESULT_OK)
        {

            Uri uri = data.getData();
            path=uri.getPath();
            show_path(path);
            getImageBitmap();
        }
    }
    public void getImageBitmap()
    {
        bm = BitmapFactory.decodeFile(path);
    }
    public void show(View view)
    {
        int[] pixels = new int[bm.getHeight()*bm.getWidth()];
        bm.getPixels(pixels,0,bm.getWidth(),0,0,bm.getWidth(),bm.getHeight());
        Log.v("main activity", "pixels are"+pixels[0]+" "+pixels[1]);
        String imageContent="";
        for(int i=0;i<(bm.getWidth()*bm.getHeight()); i++)
        {
            int tem= Color.blue(pixels[i]);//or save it in byte
            if(tem==255)
            {
                continue;
            }
            char ch = (char)tem;
            imageContent+= ch;
        }
        show_Image_content(imageContent);
    }
   // public void showOther(View view) {

     //   for(int i=0; i<bm.getWidth();i++)
       // {
         //   for(int j=0; j<bm.getHeight();j++)
           //     mutbitmap.setPixel(i,j,Color.rgb(0,0,255));
        //}
        //bm.setPixels(pixels,0,bm.getWidth(),0,0,bm.getWidth(),bm.getHeight());
    //showImage(mutbitmap);
    //}
    public void show_path(String str)
    {
        TextView tv = (TextView)findViewById(R.id.show_path);
        tv.setText(str);
    }
    public void show_Image_content(String str)
    {
        TextView tv = (TextView)findViewById(R.id.show_image_content);
        tv.setText(str);
    }
    public void verifyPermissions()
    {
        Log.d("TAG","verify permissions : asking user for the permissions");
        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0])==PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1])==PackageManager.PERMISSION_GRANTED)
        {
            show_Image_content("verified permissions ");
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,1);
        }
    }
}
