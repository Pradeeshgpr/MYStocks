package com.myowncountry.mystocks.google.signin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

public class SignInService  {

    public static SignInService signinService;

    private GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();

    public Intent getCredentials(Context context) {
        return GoogleSignIn.getClient(context, gso).getSignInIntent();
    }

    public GoogleSignInAccount getCredentials(Intent in) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(in);
            return task.getResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void logout(Context context) {
        GoogleSignIn.getClient(context, gso).signOut().addOnSuccessListener((a) ->{
            PackageManager packageManager = context.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
            ComponentName componentName = intent.getComponent();
            Intent mainIntent = Intent.makeRestartActivityTask(componentName);
            context.startActivity(mainIntent);
            Runtime.getRuntime().exit(0);
        });
    }

    public static SignInService getInstant() {
        if (signinService == null)
            signinService = new SignInService();
        return signinService;
    }
}
