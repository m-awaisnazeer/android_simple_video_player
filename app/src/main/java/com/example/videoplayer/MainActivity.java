package com.example.videoplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    MyAdapter myAdapter;
    public static int REQUEST_PERMISSION = 1;
    File directory;
    boolean aBoolean_permission;
    public static ArrayList<File> fileArrayList= new ArrayList<>();
    private boolean bolean_permission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRecyclerView = findViewById(R.id.listVideoRecyclerView);
        myRecyclerView.setHasFixedSize(true);


        directory = new File("/mnt/"); //phone and SD card



        //GridLayoutManager manager = new GridLayoutManager(this,2);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(manager);

        PermissionForVideo();


    }

    private void PermissionForVideo()
    {
     if (ContextCompat.checkSelfPermission(getApplicationContext(),
             Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
     {
         if ((ActivityCompat.shouldShowRequestPermissionRationale(this,
                 Manifest.permission.READ_EXTERNAL_STORAGE))){

         }
         else {
             ActivityCompat.requestPermissions(MainActivity.this, new String[] {
                     Manifest.permission.READ_EXTERNAL_STORAGE
             },REQUEST_PERMISSION);
         }
     }
     else {
         aBoolean_permission = true;
         getFile(directory);
         myAdapter = new MyAdapter(getApplicationContext(),fileArrayList);
         myRecyclerView.setAdapter(myAdapter);
     }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION)
        {
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                aBoolean_permission = true;
                getFile(directory);
                myAdapter = new MyAdapter(getApplicationContext(),fileArrayList);
                myRecyclerView.setAdapter(myAdapter);
            }
            else {
                Toast.makeText(this, "Need Permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public ArrayList<File> getFile(File directory){

        File listFile[] = directory.listFiles();
        if(listFile!=null && listFile.length>0){

            for(int i = 0;i<listFile.length;i++){

                if(listFile[i].isDirectory()){

                    getFile(listFile[i]);

                }

                else{

                    bolean_permission = false;
                    if(listFile[i].getName().endsWith(".mp4")){

                        for(int j=0;j<fileArrayList.size();j++){

                            if(fileArrayList.get(j).getName().equals(listFile[i].getName())){

                                bolean_permission = true;


                            }else{

                            }

                        }

                        if(bolean_permission){
                            bolean_permission =false;
                        }
                        else{
                            fileArrayList.add(listFile[i]);
                        }

                    }


                }


            }


        }
        return fileArrayList;
    }
}
