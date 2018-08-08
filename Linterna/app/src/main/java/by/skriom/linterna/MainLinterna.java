package by.skriom.linterna;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.R.*;
import android.R.array;


public class MainLinterna extends AppCompatActivity {
    ImageView boton;
    Camera camara;
    Camera.Parameters parametros;
    boolean isFlash= false, isOn=false;

    @Override
    protected void onStop() {
        super.onStop();
        if(camara!=null){
            camara.release();
            camara=null;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_linterna);

        boton= (ImageView)findViewById(R.id.boton);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,},1000);
        }
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            camara=Camera.open();
            parametros=camara.getParameters();
            isFlash=true;
        }
        boton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                 if(isFlash)
                 {
                    if (!isOn)
                    { //Encender
                         boton.setImageResource(R.drawable.enc);
                         parametros.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                         camara.setParameters(parametros);
                         camara.startPreview();
                         isOn=true;
                     }else
                         { //apagar
                            boton.setImageResource(R.drawable.foco);
                            parametros.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            camara.setParameters(parametros);
                            camara.stopPreview();
                            isOn=false;
                         }
                 }
            }
        });
    }
}
