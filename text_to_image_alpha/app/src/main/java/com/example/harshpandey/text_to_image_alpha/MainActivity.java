package com.example.harshpandey.text_to_image_alpha;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.*;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    Uri uri;
    String path;
    String fileContents="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyPermissions();
    }
    public void add(View view)
    {
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("file/*");
        //intent.putExtra("file",)
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==1)
            if(resultCode== Activity.RESULT_OK)
            {
                uri = data.getData();
                //Uri is stored in uri
                try
                {
                    path=uri.getPath();//file path

                    // fin = (FileInputStream) getContentResolver().openInputStream(uri);
                }//fileContents2 was an object of parcel fileDescriptor local to on activity request
                catch (NullPointerException e)//removed file not found exception
                {
                    e.printStackTrace();
                    Log.e("Main Activity", "\n\n\n\nFile Not Found no \n null pointer.");
                    return;
                }
               // FileDescriptor fd = fileContents2.getFileDescriptor();//now fd could be used to read file

                show_path(path);
            }
    }

    public void encrypt(View view)
    {
        Log.v("main activity encrypt","encrypt called");
        try
        {
            readContent();
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }

    }
    public void readContent()throws IOException
    {//getContentResolver().
        int i;
        InputStream fin = getContentResolver().openInputStream(uri);
        while((i=fin.read())!=-1){
            char ch= (char) i;
            fileContents += ch;
        }
        Log.v("main activity read", "file read");
        int size=fileContents.length();
        Log.v("main activity", "length of content in file = "+size);
        int[] arr = new int[size];
        char[] fileCon_char= fileContents.toCharArray();
        for(int j=0; j<size; j++)
        {
            arr[j]=(int)fileCon_char[j];
        }
        //making an array for holding the image image_array
        int size_img=(int)Math.sqrt(size)+1;
        Log.v("main activity","length X breadth = "+size_img+" X "+size_img);
        int[] image_array= new int[size_img*size_img];
        int i1;
        for(i1=0;i1<size;i1++)
            image_array[i1]=arr[i1];
        for(;i1<size_img*size_img;i1++)
            image_array[i1]=255;
        String byt="";
        for(i=0;i<size;i++)
        {
            byt = byt +" "+ image_array[i];//remove the spaces before saving it to an image
        }
//
//        Color [] image_rgb= new Color[size_img*size_img];
//        for(i=0;i<size;i++)
//            image_rgb[i]
        Log.v("main activity","before making bitmap");//ALPHA_8,ARGB_4444,ARGB_8888,etc
        Bitmap bm= Bitmap.createBitmap(image_array,size_img,size_img,Bitmap.Config.ARGB_8888);
        Bitmap mutbm=bm.copy(Bitmap.Config.ARGB_8888,true);
        i=0;
        for(int k=0; k<size_img;k++)
        {
            for(int j=0; j<size_img;j++)
            {
                mutbm.setPixel(j,k,Color.rgb(255,255,image_array[i]));
                i++;
            }
        }
        show_image(mutbm);
        Log.v("main activity","after making bitmap");
        int []a=new int[3];
        a[0]=mutbm.getPixel(0,0);
        a[0]=Color.blue(a[0]);
        a[1]=mutbm.getPixel(0,1);
        a[1]=Color.blue(a[1]);
        Log.v("main activity "," \n"+"\n\nthe first Pixel value is t="+((char)a[0])+" h="+((char)a[1])+" \nthe new char array is = "+byt);
        saveImage(mutbm,"first");

        show_file_content(fileContents);//remove it | for checking
    }
    private void saveImage(Bitmap finalBitmap, String image_name) {

        String root = Environment.getExternalStorageDirectory().toString()+"/alpha/";
        File myDir = new File(root);
        myDir.mkdirs();
        String fname = "Image-" + image_name+ ".png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void show_file_content(String fileContents)
    {
        TextView textview = (TextView)findViewById(R.id.show_content);
        textview.setText(fileContents);
    }
    public void show_path(String showPath)
    {
        TextView textview = (TextView)findViewById(R.id.show_path);
        textview.setText(showPath);
    }
    public void show_image(Bitmap bm)
    {
        ImageView iv = (ImageView)findViewById(R.id.image);
        iv.setImageBitmap(bm);
    }
    `public void verifyPermissions()
    {
        Log.d("TAG","verify permissions : asking user for the permissions");
        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0])==PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1])==PackageManager.PERMISSION_GRANTED)
        {
            show_file_content("verified permissions ");
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,1);
        }
    }

}
