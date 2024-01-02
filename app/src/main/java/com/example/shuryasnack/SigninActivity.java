package com.example.shuryasnack;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

public class SigninActivity extends AppCompatActivity {
    // variabel penyimpan xml
    private LinearLayout signinButton;
    private EditText edEmail, edPassword;
    private TextView toSignup;
    private ImageView backtoLandingPage, toggleButton;

    // variabel biasa
    public static String currentUserId;
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(".info/connected");

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // Memindahkan pengguna ke halaman dashboard
            String uid = currentUser.getUid();
            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);

                if (connected) {
                    Log.d("FirebaseConnection", "Koneksi ke Firebase: Terhubung");
                } else {
                    Log.d("FirebaseConnection", "Koneksi ke Firebase: Tidak Terhubung");
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                Log.e("FirebaseConnection", "Error: " + error.getMessage());
            }
        });




        // Inisialisasi elemen-elemen dengan ID
        // tombol landing page
        backtoLandingPage = findViewById(R.id.backtoLandingPage);
        // tombol login
        signinButton = findViewById(R.id.signinButton);
        // input email
        edEmail = findViewById(R.id.emailSignin);
        // input password
        edPassword = findViewById(R.id.passwordSignin);
        // toggle visibility
        toggleButton = findViewById(R.id.toggleButton);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Set initial state
        edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        toggleButton.setImageResource(R.drawable.ic_visibility_on);

        // Toggle password visibility on button click
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = edEmail.getText().toString();
                password = edPassword.getText().toString();

                Log.d(TAG,"email : " + email);
                Log.d(TAG,"password : " + password);

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(SigninActivity.this, "Masukkan Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(SigninActivity.this, "Format Email tidak valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(SigninActivity.this, "Masukkan Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Login Berhasil!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            currentUserId = user.getUid();
                            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SigninActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void togglePasswordVisibility() {
        int inputType = edPassword.getInputType();
        if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            toggleButton.setImageResource(R.drawable.ic_visibility_off);
        } else {
            edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            toggleButton.setImageResource(R.drawable.ic_visibility_on);
        }

        // Move the cursor to the end of the text
        edPassword.setSelection(edPassword.getText().length());
    }
}