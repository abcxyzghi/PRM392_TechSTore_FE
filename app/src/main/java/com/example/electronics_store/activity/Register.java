package com.example.electronics_store.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.electronics_store.R;
import com.example.electronics_store.retrofit.ApiService;
import com.example.electronics_store.retrofit.RetrofitClient;
import com.example.electronics_store.retrofit.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final EditText yourName = findViewById(R.id.name);
        final EditText emailAddress = findViewById(R.id.email);
        final EditText phone = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);

        final EditText confirmPassword = findViewById(R.id.reenter_password);
        final Button registerBtn = findViewById(R.id.signup_button);

        registerBtn.setOnClickListener(v -> {
            final String nameText = yourName.getText().toString();
            final String emailText = emailAddress.getText().toString();
            final String phoneText = phone.getText().toString();
            final String passwordText = password.getText().toString();
            final String confirmPasswordText = confirmPassword.getText().toString();

            if (nameText.isEmpty() || emailText.isEmpty() || phoneText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
                Toast.makeText(Register.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!passwordText.equals(confirmPasswordText)) {
                Toast.makeText(Register.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(nameText, emailText, phoneText, passwordText, "user");

            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<Void> call = apiService.registerUser(user);

            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Register.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, "Lỗi: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(Register.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}