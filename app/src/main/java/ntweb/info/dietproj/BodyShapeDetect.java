package ntweb.info.dietproj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

import ntweb.info.dietproj.Classes.ShapeImageView;

public class BodyShapeDetect extends AppCompatActivity {

    private ImageView ivDesc,ivCamera,ivGallery;
    private ShapeImageView ivPhoto;
    private TextView tvDesc;

    private int step = 1;
    private int current_point = 1;
    private Point p1,p2, p_move;
    private Button btnSetPoint;
    //used for take photo from camera intent
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_PHOTO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_shape_detect);
       // getSupportActionBar().setTitle(getString(R.string.shape_detect));
        Log.v("BodyShapeDetect","Warning the activiy start!");
        // get refrence to design component
        ivDesc = (ImageView) findViewById(R.id.ivDesc);
        tvDesc = (TextView) findViewById(R.id.tvStepDesc);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivPhoto = (ShapeImageView) findViewById(R.id.ivPhoto);
        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        btnSetPoint = (Button) findViewById(R.id.btnSetPoint);
        p1 = new Point(0,0);
        p2 = new Point(0,0);
        p_move = new Point(0,0);



        // when user click on camera icon ,, open camera intent
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start camera intent
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });


        // when user click on gallry open camera for select galary
        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK,  MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });



        ivPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                /*
                Log.wtf("DEBUG","Some thing happend in TouchListiner");


                return false;
                */
                String TAG="DEBUG";;

                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN) {

                    Log.e(TAG,"Down");
                    return true;
                }

                if (motionEvent.getAction()==MotionEvent.ACTION_MOVE){


                    p_move.set((int)motionEvent.getX(), (int)motionEvent.getY());
                    ivPhoto.drawMovePoint(p_move);
                    return true;

                }
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){

                    if (step == 2 || step == 3 || step == 4) {
                        if (current_point == 1) {
                            p1.set((int) motionEvent.getX(), (int) motionEvent.getY());
                            btnSetPoint.setText(getString(R.string.set_point_1));
                            btnSetPoint.setVisibility(View.VISIBLE);

                        } else if (current_point == 2) {
                            p2.set((int) motionEvent.getX(), (int) motionEvent.getY());
                            btnSetPoint.setText(getString(R.string.set_point_2));
                            btnSetPoint.setVisibility(View.VISIBLE);
                        }
                    }


                    return true;
                }


                return false;
            }
        });

        tvDesc.setText(getString(R.string.step1));


    }




    public void setPoint(View v)
    {
        btnSetPoint.setVisibility(View.INVISIBLE);
        if (current_point == 1 && step == 2)
        {
            ivPhoto.resetPoint();
            ivPhoto.drawP1(p1);
            current_point = 2;
        }
        else if (current_point == 2 && step == 2) { // bust
            current_point = 1;
            ivPhoto.drawP2(p2);
            ivPhoto.setupBust(p1,p2);
            btnSetPoint.setVisibility(View.INVISIBLE);
            step = 3;
            tvDesc.setText(getString(R.string.step3));
            ivDesc.setImageResource(R.drawable.waist);
        }
        else if (current_point == 1 && step == 3) { // waist
            ivPhoto.resetWaistline();
            ivPhoto.resetPoint();
            ivPhoto.drawP1(p1);
            current_point = 2;
        }

        else if (current_point == 2 && step == 3) { //waist
            current_point = 1;
            ivPhoto.drawP2(p2);
            ivPhoto.setupWaist(p1,p2);
            btnSetPoint.setVisibility(View.INVISIBLE);
            step = 4;
            tvDesc.setText(getString(R.string.step4));
            ivDesc.setImageResource(R.drawable.hip);
        }


        else if (current_point == 1 && step == 4) { //hip
            ivPhoto.resetHipline();
            ivPhoto.resetPoint();
            ivPhoto.drawP1(p1);
            current_point = 2;
        }

        else if (current_point == 2 && step == 4) { //hip
            current_point = 1;
            ivPhoto.setupHip(p1,p2);
            ivPhoto.drawP2(p2);
            btnSetPoint.setVisibility(View.INVISIBLE);
            step = -1;
            CalculateShape(ivPhoto.getBustlength(), ivPhoto.getWaistlength(), ivPhoto.getHiplength());

        }

    }

    private void CalculateShape(int bust, int waist, int hip)
    {
        int body = 1;
        Log.wtf("DEBUG", "In CalculateShape b="+bust + " ,w="+ waist + " ,h=" + hip);
        // Calcualte Apple Body shape, [ This body shape describes a person who has broader shoulders and bust than they do hips ]
        if (bust > hip && DiffInPercentage(bust,hip) > 4)
        {
            // apple
            body = 2;
            Toast.makeText(getBaseContext(),"Apple",Toast.LENGTH_SHORT).show();
        }
        // maybe have banana or peach or hourglass?
        else
        {
            if (DiffInPercentage(bust,hip) < 10 && DiffInPercentage(waist, hip ) < 27)
            {
                // banana
                body = 1;
                Toast.makeText(getBaseContext(),"Banana, w,h: " + DiffInPercentage(waist, hip ),Toast.LENGTH_SHORT).show();
            }
            else if (DiffInPercentage(bust,waist) < 18 )
            {
                // pear
                body = 3;
                Toast.makeText(getBaseContext(),"pear",Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Hourglass
                body = 4;
                Toast.makeText(getBaseContext(),"Hourglass",Toast.LENGTH_SHORT).show();
            }

        }

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("body", body);
        editor.commit();
        // Go to BodyShapeDetector Activity
        Intent userInfoActivity = new Intent(getBaseContext(), ShowCurrentBodyType.class);
        startActivity(userInfoActivity);
        finish();


    }

    private int DiffInPercentage(float x, float y)
    {
        float dif = 0;
        if (x>y)
             dif =  (((x - y)/ y) * 100);
        else
             dif =  (((y - x)/ x) * 100);

        Log.wtf("DEBUG", "In DiffInPercentage x="+x + " ,y="+ y + " ,diffrent=" + dif);
        return (int)dif;
    }

    // when the user take a photo ,,, show it in ibPhoto and go to the next step
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivPhoto.setImageBitmap(imageBitmap);
            Log.wtf("DEBUG", "Inside Camera");
            gotoStep2();
        }
        else if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK)
        {
            Log.wtf("DEBUG", "Inside Photo");
            Uri selectedImage = data.getData();
            ivPhoto.setImageURI(selectedImage);
            gotoStep2();
        }
        else
        {
            Log.wtf("DEBUG", "Inside nothing, requestCode: " + requestCode + " ,  " );
        }
    }

    void gotoStep2()
    {
        step = 2;
        tvDesc.setText(getString(R.string.step2));
        ivDesc.setImageResource(R.drawable.bust);
        ivCamera.setVisibility(View.GONE);
        ivGallery.setVisibility(View.GONE);
    }
}
