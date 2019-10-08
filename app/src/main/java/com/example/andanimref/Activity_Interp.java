package com.example.andanimref;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class Activity_Interp extends Activity {

    InputMethodManager imm;
    private Spinner intorp_spinner;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    final ArrayList<Button> intorp_btn_array = new ArrayList<Button>();
    private SquareRectView boxview;
    private SquareRectView boxview_dimmed;
    private SquareRectView boxview_origin;
    private SquareRectView boxview_dimmed_origin;
    private Button intorp_play;
    private Button intorp_btn1;
    private Button intorp_btn2;
    private Button intorp_btn3;
    private Boolean animateIng = false;
    private Handler mHandler;
    private Runnable mRunnable;
    private TextView durationIndex;
    private TextView factorIndex;
    private String animCategory = "Size";
    private String easingType = "decelerateInterpolator";
    private double factor = 0.5;
    private int Duration = 600;
    private float boxview_width= 0;
    private float boxview_xPos = 0;
    private float boxview_origin_width= 0;
    private float boxview_origin_xPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ease);

        arrayList = new ArrayList<>();
        arrayList.add("decelerateInterpolator");
        arrayList.add("accelerateInterpolator");

        arrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,arrayList
        );

        intorp_spinner = (Spinner)findViewById(R.id.intorp_spinner);

        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        boxview = (SquareRectView) findViewById(R.id.boxview);
        boxview_dimmed = (SquareRectView) findViewById(R.id.boxview_dimmed);

        boxview_origin = (SquareRectView) findViewById(R.id.boxview_origin);
        boxview_dimmed_origin = (SquareRectView) findViewById(R.id.boxview_dimmed_origin);

        durationIndex = (TextView) findViewById(R.id.durationIndex);
        factorIndex = (TextView) findViewById(R.id.factorIndex);

        durationIndex.setText(Integer.toString(Duration));
        factorIndex.setText(Double.toString(factor));

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                boxview_width = boxview.getWidth();
                boxview_xPos = boxview.getX();

                boxview_origin_width = boxview_origin.getWidth();
                boxview_origin_xPos = boxview_origin.getX();
            }
        }, 100);

        durationIndex.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboard();
//                    clickAnimFunction();
                    return true;
                }
                return false;
            }
        });
        factorIndex.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    hideKeyboard();
//                    clickAnimFunction();
                    return true;
                }
                return false;
            }
        });

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        intorp_btn1 = (Button) findViewById(R.id.intorp_btn1);
        intorp_btn2 = (Button) findViewById(R.id.intorp_btn2);
        intorp_btn3 = (Button) findViewById(R.id.intorp_btn3);

        intorp_btn_array.add(intorp_btn1);
        intorp_btn_array.add(intorp_btn2);
        intorp_btn_array.add(intorp_btn3);

        for (int i = 0; i < intorp_btn_array.size(); i++) {
            //// 버튼 클릭
            final int finalI = i;
            intorp_btn_array.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (finalI) {
                        case 0:
                            animCategory = "Size";
                            break;
                        case 1:
                            animCategory = "Positon";
                            break;
                        case 2:
                            animCategory = "Transparencies";
                            break;
                        default:
                            break;
                    }
                    hideKeyboard();
                    clickAnimFunction();
                }
            });
        }

        intorp_play = (Button) findViewById(R.id.intorp_play);

        intorp_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAnimFunction();
            }
        });

        intorp_spinner.setAdapter(arrayAdapter);
        intorp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                easingType = arrayList.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    void TransAnim_Custom(View view, float startX, float endX, float startY, float endY, int duration, double factor) {
        TranslateAnimation anim = new TranslateAnimation(
                startX, endX,
                startY, endY );
        anim.setFillAfter(true);
        if (easingType == "decelerateInterpolator"){
            anim.setInterpolator(new DecelerateInterpolator((float) factor));
        }
        if (easingType == "accelerateInterpolator"){
            anim.setInterpolator(new AccelerateInterpolator((float) factor));
        }
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    void SclaeAnim_Custom(View view, float startScaleX, float endScaleX, float startScaleY, float endScaleY, float originX, float originY, int duration, double factor) {
        ScaleAnimation anim = new ScaleAnimation( startScaleX, endScaleX, startScaleY, endScaleY, Animation.RELATIVE_TO_SELF, originX, Animation.RELATIVE_TO_SELF, originY  );
        anim.setFillAfter(true);
        if (easingType == "decelerateInterpolator"){
            anim.setInterpolator(new DecelerateInterpolator((float) factor));
        }
        if (easingType == "accelerateInterpolator"){
            anim.setInterpolator(new AccelerateInterpolator((float) factor));
        }
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    void AlphaAnim_Custom(View view, float startAlpha, float endAlpha, int duration, double factor) {
        Animation anim = new AlphaAnimation( startAlpha, endAlpha );
        anim.setFillAfter(true);
        if (easingType == "decelerateInterpolator"){
            anim.setInterpolator(new DecelerateInterpolator((float) factor));
        }
        if (easingType == "accelerateInterpolator"){
            anim.setInterpolator(new AccelerateInterpolator((float) factor));
        }
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    void TransAnim_Nor(View view, float startX, float endX, float startY, float endY, int duration) {
        TranslateAnimation anim = new TranslateAnimation(
                startX, endX,
                startY, endY );
        anim.setFillAfter(true);
        if (easingType == "decelerateInterpolator"){
            anim.setInterpolator(new DecelerateInterpolator());
        }
        if (easingType == "accelerateInterpolator"){
            anim.setInterpolator(new AccelerateInterpolator());
        }
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    void SclaeAnim_Nor(View view, float startScaleX, float endScaleX, float startScaleY, float endScaleY, float originX, float originY, int duration) {
        ScaleAnimation anim = new ScaleAnimation( startScaleX, endScaleX, startScaleY, endScaleY, Animation.RELATIVE_TO_SELF, originX, Animation.RELATIVE_TO_SELF, originY  );
        anim.setFillAfter(true);
        if (easingType == "decelerateInterpolator"){
            anim.setInterpolator(new DecelerateInterpolator());
        }
        if (easingType == "accelerateInterpolator"){
            anim.setInterpolator(new AccelerateInterpolator());
        }
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    void AlphaAnim_Nor(View view, float startAlpha, float endAlpha, int duration) {
        Animation anim = new AlphaAnimation( startAlpha, endAlpha );
        anim.setFillAfter(true);
        if (easingType == "decelerateInterpolator"){
            anim.setInterpolator(new DecelerateInterpolator());
        }
        if (easingType == "accelerateInterpolator"){
            anim.setInterpolator(new AccelerateInterpolator());
        }
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    void clickAnimFunction() {
        String DurationStiring = durationIndex.getText().toString();
        Duration = Integer.parseInt(DurationStiring);

        String FactorStiring = factorIndex.getText().toString();
        factor = Float.parseFloat(FactorStiring);

        if (!animateIng){
            animateIng = true;
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    animateIng = false;
                }
            };
            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, Duration);
            //// Size Animation
            if ( animCategory == "Size"){
                boxview_dimmed.setImageAlpha(255);
                boxview_dimmed.setX(boxview_xPos);

                boxview_dimmed_origin.setImageAlpha(255);
                boxview_dimmed_origin.setX(boxview_origin_xPos);

                AlphaAnim_Custom(boxview_dimmed, 1, 1, 0, factor );
                TransAnim_Custom(boxview_dimmed, 0, 0, 0,0, 0, factor);

                AlphaAnim_Nor(boxview_dimmed_origin, 1, 1, 0 );
                TransAnim_Nor(boxview_dimmed_origin, 0, 0, 0,0, 0 );

                if (boxview_dimmed.getScaleX() == 1){
                    SclaeAnim_Custom(boxview_dimmed, 1, 0, 1, 0, 0.5f, 0.5f, Duration, factor);
                    SclaeAnim_Nor(boxview_dimmed_origin, 1, 0, 1, 0, 0.5f, 0.5f, Duration );
                    mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            boxview_dimmed.setScaleX(0);
                            boxview_dimmed.setScaleY(0);

                            boxview_dimmed_origin.setScaleX(0);
                            boxview_dimmed_origin.setScaleY(0);
                        }
                    };
                    mHandler = new Handler();
                    mHandler.postDelayed(mRunnable, Duration);
                }
                if (boxview_dimmed.getScaleX() == 0){
                    boxview_dimmed.setScaleX(1);
                    boxview_dimmed.setScaleY(1);
                    SclaeAnim_Custom(boxview_dimmed, 0, 1, 0, 1, 0.5f, 0.5f, Duration, factor);
                }

                if (boxview_dimmed_origin.getScaleX() == 0){
                    boxview_dimmed_origin.setScaleX(1);
                    boxview_dimmed_origin.setScaleY(1);
                    SclaeAnim_Nor(boxview_dimmed_origin, 0, 1, 0, 1, 0.5f, 0.5f, Duration );
                }
            }

            //// Positon Animation
            if ( animCategory == "Positon"){
                boxview_dimmed.setImageAlpha(255);
                boxview_dimmed.setScaleX(1);
                boxview_dimmed.setScaleY(1);

                boxview_dimmed_origin.setImageAlpha(255);
                boxview_dimmed_origin.setScaleX(1);
                boxview_dimmed_origin.setScaleY(1);

                AlphaAnim_Custom(boxview_dimmed, 1, 1, 0, factor );
                SclaeAnim_Custom(boxview_dimmed, 1, 1, 1, 1, 0.5f, 0.5f, 0, factor);

                AlphaAnim_Nor(boxview_dimmed_origin, 1, 1, 0 );
                SclaeAnim_Nor(boxview_dimmed_origin, 1, 1, 1, 1, 0.5f, 0.5f, 0 );

                if (boxview_dimmed.getX() == boxview_xPos){
                    TransAnim_Custom(boxview_dimmed, 0, -boxview_width, 0,0, Duration, factor);
                    TransAnim_Nor(boxview_dimmed_origin, 0, -boxview_origin_width, 0,0, Duration );
                    mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            TransAnim_Custom(boxview_dimmed, 0, 0, 0,0, 0, factor);
                            boxview_dimmed.setX(boxview_xPos-boxview_width);

                            TransAnim_Nor(boxview_dimmed_origin, 0, 0, 0,0, 0 );
                            boxview_dimmed_origin.setX(boxview_origin_xPos-boxview_origin_width);
                        }
                    };
                    mHandler = new Handler();
                    mHandler.postDelayed(mRunnable, Duration);
                }
                if (boxview_dimmed.getX() == boxview_xPos-boxview_width){
                    boxview_dimmed.setX(boxview_xPos);
                    TransAnim_Custom(boxview_dimmed, -boxview_width, 0, 0,0, Duration, factor);
                }
                if (boxview_dimmed_origin.getX() == boxview_origin_xPos-boxview_origin_width){
                    boxview_dimmed_origin.setX(boxview_origin_xPos);
                    TransAnim_Nor(boxview_dimmed_origin, -boxview_origin_width, 0, 0,0, Duration );
                }
            }

            //// Alpha Animation
            if ( animCategory == "Transparencies"){

                boxview_dimmed.setX(boxview_xPos);
                boxview_dimmed.setScaleX(1);
                boxview_dimmed.setScaleY(1);

                boxview_dimmed_origin.setX(boxview_origin_xPos);
                boxview_dimmed_origin.setScaleX(1);
                boxview_dimmed_origin.setScaleY(1);

                SclaeAnim_Custom(boxview_dimmed, 1, 1, 1, 1, 0.5f, 0.5f, 0, factor);
                TransAnim_Custom(boxview_dimmed, 0, 0, 0,0, 0, factor);

                SclaeAnim_Nor(boxview_dimmed_origin, 1, 1, 1, 1, 0.5f, 0.5f, 0 );
                TransAnim_Nor(boxview_dimmed_origin, 0, 0, 0,0, 0 );

                if (boxview_dimmed.getImageAlpha() == 255){
                    AlphaAnim_Custom(boxview_dimmed, 1, 0, Duration, factor );
                    AlphaAnim_Nor(boxview_dimmed_origin, 1, 0, Duration );
                    mRunnable = new Runnable() {
                        @Override
                        public void run() {
                            AlphaAnim_Custom(boxview_dimmed, 0, 0, 0, factor );
                            boxview_dimmed.setImageAlpha(0);

                            AlphaAnim_Nor(boxview_dimmed_origin, 0, 0, 0 );
                            boxview_dimmed_origin.setImageAlpha(0);
                        }
                    };
                    mHandler = new Handler();
                    mHandler.postDelayed(mRunnable, Duration);
                }
                if (boxview_dimmed.getImageAlpha() == 0){
                    boxview_dimmed.setImageAlpha(255);
                    AlphaAnim_Custom(boxview_dimmed, 0, 1, Duration, factor );
                }

                if (boxview_dimmed_origin.getImageAlpha() == 0){
                    boxview_dimmed_origin.setImageAlpha(255);
                    AlphaAnim_Nor(boxview_dimmed_origin, 0, 1, Duration );
                }
            }
        }
    }

    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(durationIndex.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(factorIndex.getWindowToken(), 0);
    }

}


