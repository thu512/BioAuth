package com.gsitm.bioauth;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "지문";

    private Button btn;
    private BiometricPrompt mBiometricPrompt;
    private FingerprintAuthenticationDialogFragment mFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);


        btn.setOnClickListener((view) -> {

            //28이상 버전
            if (Build.VERSION.SDK_INT >= 28) {

                //지문을 사용할수 있는경우
                if (isSupportBiometricPrompt()) {

                    // Create biometricPrompt
                    mBiometricPrompt = new BiometricPrompt.Builder(this)
                            .setDescription("Description")
                            .setTitle("Title")
                            .setSubtitle("Subtitle")
                            .setNegativeButton("Cancel", getMainExecutor(), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.i(TAG, "Cancel button clicked");
                                }
                            })
                            .build();
                    CancellationSignal cancellationSignal = getCancellationSignal();
                    BiometricPrompt.AuthenticationCallback authenticationCallback = new MyAuthticationCallback(this);


                    Log.i(TAG, "Show biometric prompt");
                    mBiometricPrompt.authenticate( cancellationSignal, getMainExecutor(), authenticationCallback);

                }

            } else { //FingerPrint사용
                Log.i("지문", "API 27");

                mFragment = new FingerprintAuthenticationDialogFragment();
                mFragment.setCallback(new FingerprintAuthenticationDialogFragment.SecretAuthorize(){
                    @Override
                    public void success() {
                        Toast.makeText(getApplicationContext(), "인증 성공", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail() {
                        Toast.makeText(getApplicationContext(), "인증 실패", Toast.LENGTH_SHORT).show();
                    }
                });

                mFragment.show(this.getFragmentManager(), "my_fragment");

            }
        });

    }


    private boolean isSupportBiometricPrompt() {
        PackageManager packageManager = this.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        }
        return false;
    }

    //지문 인증 취소 이벤트
    private CancellationSignal getCancellationSignal() {
        // With this cancel signal, we can cancel biometric prompt operation
        CancellationSignal cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                //handle cancel result
                Log.i(TAG, "Canceled");
            }
        });
        return cancellationSignal;
    }


}
