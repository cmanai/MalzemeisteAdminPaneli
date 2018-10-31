package com.bilgiyazan.malzemeiste.adminpaneli.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bilgiyazan.malzemeiste.adminpaneli.R;
import com.bilgiyazan.malzemeiste.adminpaneli.Utils.ConnectionDetector;
import com.orhanobut.dialogplus.DialogPlus;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.EditText;
import com.rey.material.widget.ProgressView;

import mehdi.sakout.fancybuttons.FancyButton;


public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    SharedPreferences prefs;
    DialogPlus Loading_Dialog;
    MaterialDialog Internet_Dialog;
    MaterialEditText Username, Password;
    FancyButton Login;
    ImageView Logo_Text;
    TextView Copyright;
    Animation animation1;
    Animation animation2;
    Animation animation3;
    Animation animation4;
    Animation animation5;
    ImageView background_overlay;
    Boolean isInternetPresent;
    ConnectionDetector connectionDetector;
    CheckBox RememberMe;
    String Logged;
    String RememberMe_value;
    String Username_Value;
    String Password_Value;
    CardView cardView;
    ImageView application_Logo;
    ImageView background;
    RelativeLayout splashscreen;
    ProgressView loading_data_progress;
    RelativeLayout white_circle;
    MaterialDialog Login_Failure;
    private FirebaseAuth mAuth;

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.splash_screen_layout);
        prefs = getSharedPreferences("malzemeiste_admin_panel", Context.MODE_PRIVATE);
        Logged = prefs.getString("Logged", "false");

        splashscreen = (RelativeLayout) findViewById(R.id.splashscreen_layout_R);
        if (Logged.equals("false")) {
            setupUI(splashscreen);
        }


        RememberMe_value = prefs.getString("RememberMe_Value", "false");

        Log.e("Language_Clicked", prefs.getString("Language_Clicked", "false"));


        //Initialize Components

        Username = (MaterialEditText) findViewById(R.id.Username);
        Password = (MaterialEditText) findViewById(R.id.Password);
        Login = (FancyButton) findViewById(R.id.Login);
        background_overlay = (ImageView) findViewById(R.id.background_overlay);
        loading_data_progress = (ProgressView) findViewById(R.id.loading_data_splash);
        white_circle = (RelativeLayout) findViewById(R.id.white_circle);
        cardView = (CardView) findViewById(R.id.cardView);

        Copyright = (TextView) findViewById(R.id.Copyright);
        Logo_Text = (ImageView) findViewById(R.id.logo_text);
        application_Logo = (ImageView) findViewById(R.id.Logo);
        RememberMe = (CheckBox) findViewById(R.id.RememberMe);

        background = (ImageView) findViewById(R.id.background);


        Username.setFloatingLabelText("E-Posta / Kullanıcı Adı");
        Username.setHint("E-Posta / Kullanıcı Adı");


        RememberMe.setText("Beni Hatırla");
        Login.setText("Giriş");


        Password.setFloatingLabelText("Şıfre");
        Password.setHint("Şıfre");
        Username.setError(null);
        Password.setError(null);


        if (RememberMe_value.equals("true")) {
            RememberMe.setChecked(true);

            Username_Value = prefs.getString("Username_Value", "");
            Password_Value = prefs.getString("Password_Value", "");
            Username.setText(Username_Value);
            Password.setText(Password_Value);
        } else {
            RememberMe.setChecked(false);

            Username_Value = prefs.getString("Username_Value", "");
            Password_Value = prefs.getString("Password_Value", "");

            Username.setText("");
            Password.setText("");
        }


        connectionDetector = new ConnectionDetector(getApplicationContext());
        isInternetPresent = connectionDetector.isConnectingToInternet();


        animation1 = AnimationUtils.loadAnimation(this, R.anim.grow_animation);
        animation1.reset();
        animation2 = AnimationUtils.loadAnimation(this, R.anim.popup_animation);
        animation2.reset();
        animation3 = AnimationUtils.loadAnimation(this, R.anim.fade_in_animation);
        animation3.reset();
        animation5 = AnimationUtils.loadAnimation(this, R.anim.fade_out_animation);
        animation5.reset();
        animation4 = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.shrink_animation);
        animation4.reset();

        //Giving Actions to components
        white_circle.clearAnimation();
        Logo_Text.clearAnimation();
        Username.clearAnimation();
        Password.clearAnimation();
        Login.clearAnimation();
        Copyright.clearAnimation();
        application_Logo.clearAnimation();
        cardView.clearAnimation();
        Username.setErrorColor(getColor(SplashScreenActivity.this, R.color.md_red_500));
        Password.setErrorColor(getColor(SplashScreenActivity.this, R.color.md_red_500));


        if (!isInternetPresent) {


            Internet_Dialog = new MaterialDialog.Builder(SplashScreenActivity.this)
                    .title(getResources().getString(R.string.no_internet_title))

                    .content(getResources().getString(R.string.no_internet_message))
                    .positiveText(R.string.agree)

                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            finish();
                        }
                    })
                    .positiveColor(getColor(SplashScreenActivity.this, R.color.colorPrimary))
                    .cancelable(false)

                    .build();


            Internet_Dialog.show();


        } else {
            Logged = prefs.getString("Logged", "false");

            if (Logged.equals("true")) {


                /*cardView.setVisibility(View.INVISIBLE);

                Intent refresh = new Intent(SplashScreenActivity.this, ContainerActivity.class);
                refresh.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

                startActivity(refresh);

                finish();*/


            } else if (Logged.equals("false") && prefs.getString("logged_out", "false").equals("false")) {

                background.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        application_Logo.setVisibility(View.VISIBLE);
                        application_Logo.startAnimation(animation1);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Logo_Text.setVisibility(View.VISIBLE);
                                Logo_Text.startAnimation(animation3);


                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        cardView.setVisibility(View.VISIBLE);
                                        Username.setVisibility(View.VISIBLE);
                                        Password.setVisibility(View.VISIBLE);
                                        Login.setVisibility(View.VISIBLE);
                                        Copyright.setVisibility(View.VISIBLE);
                                        RememberMe.setVisibility(View.VISIBLE);
                                        cardView.setAnimation(animation3);
                                        Username.startAnimation(animation3);
                                        Password.startAnimation(animation3);
                                        Login.startAnimation(animation3);
                                        Copyright.startAnimation(animation3);


                                    }
                                }, 1);
                            }
                        }, 1);
                    }
                }, 200);


            } else if (Logged.equals("false") && prefs.getString("logged_out", "false").equals("true")) {
                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("logged_out", "false");

                editor.apply();
                background.setVisibility(View.VISIBLE);
                application_Logo.setVisibility(View.VISIBLE);
                Logo_Text.setVisibility(View.VISIBLE);
                cardView.setVisibility(View.VISIBLE);
                Username.setVisibility(View.VISIBLE);
                Password.setVisibility(View.VISIBLE);
                Login.setVisibility(View.VISIBLE);
                Copyright.setVisibility(View.VISIBLE);
                RememberMe.setVisibility(View.VISIBLE);


            }
        }


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Username.getText().length() == 0) {

                    Username.setError(getResources().getString(R.string.required_field));

                }

                if (Password.getText().length() == 0) {

                    Password.setError(getResources().getString(R.string.required_field));

                }

                if (Username.getText().length() != 0 && Password.getText().length() != 0) {

                    if (RememberMe.isChecked()) {
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putString("RememberMe_Value", "true");
                        editor.putString("Username_Value", Username.getText().toString());
                        editor.putString("Password_Value", Password.getText().toString());

                        editor.apply();

                    } else {
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putString("RememberMe_Value", "false");
                        editor.putString("Username_Value", "");
                        editor.putString("Password_Value", "");
                        editor.apply();
                    }

                    SharedPreferences.Editor editor = prefs.edit();

//                    editor.putString("Logged", "true");
                    editor.apply();

                    loading_data_progress.clearAnimation();
                    background_overlay.clearAnimation();
                    ViewCompat.setElevation(loading_data_progress, 5);
                    ViewCompat.setElevation(background_overlay, 3);
                    ViewCompat.setElevation(white_circle, 4);
                    loading_data_progress.setVisibility(View.VISIBLE);
                    background_overlay.setVisibility(View.VISIBLE);

                    white_circle.setVisibility(View.VISIBLE);
                    background_overlay.startAnimation(animation3);
                    white_circle.startAnimation(animation1);
                    loading_data_progress.startAnimation(animation3);
                    ViewCompat.setElevation(Copyright, 1);

                    ViewCompat.setElevation(Login, 1);
                    cardView.setCardElevation(0);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    signIn(Username.getText().toString().trim(), Password.getText().toString());


                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent refresh = new Intent(SplashScreenActivity.this, ContainerActivity.class);

            refresh.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

            startActivity(refresh);

            finish();
        }

    }

    private void signIn(String email, String password) {


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Intent refresh = new Intent(SplashScreenActivity.this, ContainerActivity.class);

                            refresh.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);

                            startActivity(refresh);

                            finish();

                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                     /*       Toast.makeText(SplashScreenActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*/
                            Login_Failure = new MaterialDialog.Builder(SplashScreenActivity.this)
                                    .title("giriş başarısız")
                                    .icon(getApplicationContext().getResources().getDrawable(R.drawable.icon_malzemeiste))
                                    .limitIconToDefaultSize()
                                    .content("Kullanıcı adı veya şifre hatalı")
                                    .backgroundColor(getColor(getApplicationContext(), R.color.md_white_1000))
                                    .titleColor(getColor(getApplicationContext(), R.color.md_black_1000))
                                    .contentColor(Color.parseColor("#80000000"))

                                    .positiveText(getResources().getString(R.string.agree))
                                    .cancelable(false)
                                    .positiveColor(getColor(getApplicationContext(), R.color.primary))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Login_Failure.setCancelable(true);
                                            Login_Failure.dismiss();


                                        }
                                    })

                                    .build();

                            Login_Failure.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {

                                }
                            });
                            Login_Failure.show();


                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            ViewCompat.setElevation(background_overlay, 3);
                            ViewCompat.setElevation(white_circle, -1);
                            ViewCompat.setElevation(Login, 5);
                            ViewCompat.setElevation(Copyright, 4);
                            cardView.setCardElevation(4);
                            loading_data_progress.setVisibility(View.GONE);
                            white_circle.setVisibility(View.GONE);
                            background.setVisibility(View.VISIBLE);
                            application_Logo.setVisibility(View.VISIBLE);
                            Logo_Text.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.VISIBLE);
                            Username.setVisibility(View.VISIBLE);
                            Password.setVisibility(View.VISIBLE);
                            Login.setVisibility(View.VISIBLE);
                            Copyright.setVisibility(View.VISIBLE);
                            RememberMe.setVisibility(View.VISIBLE);
                            // updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //  mStatusTextView.setText(R.string.auth_failed);
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(SplashScreenActivity.this);
                    return false;
                }
            });
        }

       /* //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
