package com.gsitm.bioauth;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.biometrics.BiometricPrompt;
import android.util.Log;
import android.widget.Toast;

@TargetApi(28)
public class MyAuthticationCallback extends BiometricPrompt.AuthenticationCallback {
    private static final String TAG = "지문";

    Context context;

    public MyAuthticationCallback() {
        super();
    }
    public MyAuthticationCallback(Context context) {
        super();
        this.context = context;
    }
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
    }

    @Override
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        Log.i(TAG, "onAuthenticationSucceeded");
        super.onAuthenticationSucceeded(result);

        Log.i(TAG, "Message: 인증 되었습니다.");

        Toast.makeText(context, "지문인증 완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
    }
}
