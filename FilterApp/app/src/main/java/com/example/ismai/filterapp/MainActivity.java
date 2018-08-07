package com.example.ismai.filterapp;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Button btnCamera, btnFromPhone;
    ImageView imageView;
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
    }

    public void Init() {
        btnCamera = findViewById(R.id.button_camera);
        btnFromPhone = findViewById(R.id.button_from_phone);
        imageView = findViewById(R.id.imageView);
        btnFromPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Bir Fotoðraf Seçin"), IMAGE_PICK);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, IMAGE_CAPTURE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case IMAGE_PICK:
                    this.imageFromGallery(resultCode, data);
                    break;
                case IMAGE_CAPTURE:
                    this.imageFromCamera(resultCode, data);
                    break;
                default:
                    break;
            }

        }
    }

    public void imageFromGallery(int resultCode, Intent data) {
        Uri targetUri = data.getData();
        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        i.setData(targetUri);
        startActivity(i);
    }

    public void imageFromCamera(int resultCode, Intent data) {
        Bitmap image = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        image.recycle();

        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("resim", byteArray);
        startActivity(intent);


    }


}
