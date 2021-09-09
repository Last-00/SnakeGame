package com.example.snakegame.Activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.snakegame.R;

import java.io.ByteArrayOutputStream;

public class NewPlayerActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,surname;
    ImageView iv;
    Bitmap bitmap;
    Button saveBtn, camBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_new_player);
        name = findViewById(R.id.et1);
        surname = findViewById(R.id.et2);
        iv = findViewById(R.id.iv);
        MainActivity.sp1=getSharedPreferences("progressInfo",0);
        saveBtn = findViewById(R.id.SaveData);
        camBtn = findViewById(R.id.CamBtn);
        camBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
    }

    @Override
    public  void onClick(View v) {
        if(v == camBtn){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,0);}
        if(v == saveBtn){
            String nme = name.getText().toString(); String snme = surname.getText().toString();
            if( nme.length()<1 || snme.length()<1 || bitmap == null)
                Toast.makeText(this, "fill out all the fields", Toast.LENGTH_SHORT).show();
            else{
                SharedPreferences.Editor editor1=MainActivity.sp1.edit();
                editor1.putString("name",nme);
                editor1.putString("surname",snme);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String encoded = Base64.encodeToString(b, Base64.DEFAULT);
                editor1.putString("bitmap",encoded);
                editor1.commit();
            Intent intent = new Intent(this, ChooseLevelActivity.class);
            intent.putExtra("name",name.getText().toString());
            intent.putExtra("surname",surname.getText().toString());
            intent.putExtra("bitmap",bitmap);
            startActivity(intent);}
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                bitmap = (Bitmap) data.getExtras().get("data");
                iv.setImageBitmap(bitmap);
                Toast.makeText(this, "Looks nice!", Toast.LENGTH_LONG).show();
            }
            else
                Toast.makeText(this, "The photo was canceled", Toast.LENGTH_LONG).show();
        }
    }
}