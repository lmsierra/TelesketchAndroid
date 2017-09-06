package telesketch.lmsierra.com.telesketch.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import telesketch.lmsierra.com.telesketch.drawingUtils.DrawingSurface;
import telesketch.lmsierra.com.telesketch.R;
import telesketch.lmsierra.com.telesketch.sensors.ShakeSensorManager;

import static android.provider.MediaStore.Images;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.toString();
    private static final int REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION = 1;

    private ShakeSensorManager shakeSensorManager;

    private Button buttonUp;
    private Button buttonDown;
    private Button buttonLeft;
    private Button buttonRight;

    private DrawingSurface drawingSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        bindUI();

        setupButtons();

        shakeSensorManager = new ShakeSensorManager(this);
        shakeSensorManager.setShakeSensorListener(new ShakeSensorManager.ShakeSensorListener() {
            @Override
            public void onShake() {
                Log.e(TAG, "Shake your body");
                drawingSurface.reset();
                drawingSurface.changeColor();
            }
        });
    }

    private void bindUI() {
        buttonUp = (Button) findViewById(R.id.activity_main_button_up);
        buttonDown = (Button) findViewById(R.id.activity_main_button_down);
        buttonLeft = (Button) findViewById(R.id.activity_main_button_left);
        buttonRight = (Button) findViewById(R.id.activity_main_button_right);

        drawingSurface = (DrawingSurface) findViewById(R.id.activity_main_draw_surface);
    }

    private void setupButtons() {
        buttonUp.setOnTouchListener(new View.OnTouchListener() {

            private Handler handler;

            Runnable action = new Runnable() {
                @Override
                public void run() {
                    drawingSurface.drawUp();
                    handler.post(this);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (handler != null) {
                            return true;
                        } else {
                            handler = new Handler();
                            handler.post(action);
                        }

                        break;

                    case (MotionEvent.ACTION_UP):

                        if (handler == null) {
                            return true;
                        }

                        handler.removeCallbacks(action);
                        handler = null;
                        break;
                }
                return false;
            }
        });

        buttonDown.setOnTouchListener(new View.OnTouchListener() {

            private Handler handler;

            Runnable action = new Runnable() {
                @Override
                public void run() {
                    drawingSurface.drawDown();
                    handler.post(this);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (handler != null) {
                            return true;
                        } else {
                            handler = new Handler();
                            handler.post(action);
                        }

                        break;

                    case (MotionEvent.ACTION_UP):

                        if (handler == null) {
                            return true;
                        }

                        handler.removeCallbacks(action);
                        handler = null;
                        break;
                }
                return false;
            }
        });

        buttonLeft.setOnTouchListener(new View.OnTouchListener() {

            private Handler handler;

            Runnable action = new Runnable() {
                @Override
                public void run() {
                    drawingSurface.drawLeft();
                    handler.post(this);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (handler != null) {
                            return true;
                        } else {
                            handler = new Handler();
                            handler.post(action);
                        }

                        break;

                    case (MotionEvent.ACTION_UP):

                        if (handler == null) {
                            return true;
                        }

                        handler.removeCallbacks(action);
                        handler = null;
                        break;
                }
                return false;
            }
        });

        buttonRight.setOnTouchListener(new View.OnTouchListener() {

            private Handler handler;

            Runnable action = new Runnable() {
                @Override
                public void run() {
                    drawingSurface.drawRight();
                    handler.post(this);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (handler != null) {
                            return true;
                        } else {
                            handler = new Handler();
                            handler.post(action);
                        }

                        break;

                    case (MotionEvent.ACTION_UP):

                        if (handler == null) {
                            return true;
                        }

                        handler.removeCallbacks(action);
                        handler = null;
                        break;
                }
                return false;
            }
        });
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.action_settings:



                break;

            case R.id.action_save:


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                        requestPermissions(new String [] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION);
                        return false;
                    }
                }

                saveImage();

                break;

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case REQUEST_CODE_EXTERNAL_STORAGE_PERMISSION:
                if(grantResults.length > 0){
                    saveImage();
                }
                break;
        }
    }

    private void saveImage(){

        String url = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "Sketch" + File.separator;
        File folder = new File(url);

        if (!folder.exists()){
            folder.mkdirs();
        }

        OutputStream outputStream = null;
        File image = new File(url, String.format(getString(R.string.image_name), System.currentTimeMillis()));

        try{
            outputStream = new FileOutputStream(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(outputStream != null) {

            Bitmap bitmap = drawingSurface.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            saveImageToGallery(image);
        }
    }

    private void saveImageToGallery(File image){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
        contentValues.put(Images.Media.BUCKET_ID, image.toString().toLowerCase().hashCode());
        contentValues.put(Images.Media.BUCKET_DISPLAY_NAME, image.getName().toLowerCase());
        contentValues.put("_data", image.getAbsolutePath());

        ContentResolver contentResolver = getContentResolver();
        contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, contentValues);
    }
}
