package com.example.travelplanner.ui;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.travelplanner.R;
import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1001;
    private GoogleSignInClient client;
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this, MainActivity.class)); finish(); return;
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestIdToken(getString(R.string.web_client_id)).build();
        client = GoogleSignIn.getClient(this, gso);
        Button btn = findViewById(R.id.btnGoogleSignIn);
        btn.setOnClickListener(v -> startActivityForResult(client.getSignInIntent(), RC_SIGN_IN));
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount acct = task.getResult(ApiException.class);
                AuthCredential cred = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(cred).addOnCompleteListener(t -> {
                    if (t.isSuccessful()) { startActivity(new Intent(LoginActivity.this, MainActivity.class)); finish(); }
                    else { Toast.makeText(LoginActivity.this, "ورود ناموفق", Toast.LENGTH_LONG).show(); }
                });
            } catch (Exception e) { Toast.makeText(this, "خطا: " + e.getMessage(), Toast.LENGTH_LONG).show(); }
        }
    }
}
