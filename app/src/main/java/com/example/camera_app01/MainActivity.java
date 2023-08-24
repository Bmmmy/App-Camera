package com.example.camera_app01;

import androidx.activity.result.ActivityResult;
//import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
//import android.graphics.Bitmap;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //activityresultlauncher -> 來處理相機啟動後的結果 (暫時用下面來簡單顯示)
    //用 final去確保ActivityResultLauncher 在整個生命週期裡面不會被更改
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getExtras() != null) {
                        Toast.makeText(MainActivity.this, "完成拍照", Toast.LENGTH_SHORT).show();
                        //Bundle extras = data.getExtras();Bitmap photo = (Bitmap) extras.get("data");
                    }else{
                        Toast.makeText(MainActivity.this, "無法完成拍照", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "取消拍照", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //設置畫面布局
    }

    // 點擊按鈕後，呼叫相機
    @SuppressLint("QueryPermissionsNeeded")
    public void openCamera(View view){
        // 創建一個打開拍照介面的頁面
        // Intent -> 用來啟用相機
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Check 是否有相機app可以處理這個Intent
        if (cameraIntent.resolveActivity(getPackageManager()) != null){
            //用ActivityResultLauncher 啟動相機
            //呼叫startActivityForResult，等拍照完後回傳結果 ，start不能用 用新的launcher
            cameraLauncher.launch(cameraIntent);//打開相機
        }
    }

    //接下來是拍照完後被呼叫
    //目的:處裡拍照結果

}

