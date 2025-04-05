package com.example.newuserinterface;


import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class FormActivity extends AppCompatActivity {
    private EditText name, lastname, email, password, readData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form);

        name = findViewById(R.id.txtName);
        lastname = findViewById(R.id.txtLastName);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        Button btnCreate = findViewById(R.id.btnCreate);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnRead = findViewById(R.id.btnRead);
        readData = findViewById(R.id.txtSearchUser);

        btnCreate.setOnClickListener(v -> saveData());
        btnRead.setOnClickListener(v -> readData());
        btnUpdate.setOnClickListener(v -> UpdateData());
        btnDelete.setOnClickListener(v -> deleteData());


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void saveData() {
        String name1 = name.getText().toString().trim();
        String lastname1 = lastname.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        if (name1.isEmpty() || lastname1.isEmpty() || email1.isEmpty() || password1.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password1.matches("\\d+")) {
            Toast.makeText(this, "Password must be numeric", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password1.length() <= 4) {
            Toast.makeText(this, "Password must be more than 4 digits", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(name1, lastname1, email1, password1);
        user.save();

        Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();

        // Limpiar los campos
        name.setText("");
        lastname.setText("");
        email.setText("");
        password.setText("");
    }


    public void readData() {
        String searchEmail = readData.getText().toString().trim();
        if (searchEmail.isEmpty()) {
            Toast.makeText(this, "Please, enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        List<User> list = User.find(User.class, "email = ?", searchEmail);
        if (!list.isEmpty()) {
            User user = list.get(0);
            name.setText(user.getName());
            lastname.setText(user.getLastname());
            email.setText(user.getEmail());
            password.setText(user.getPassword());

            Toast.makeText(this, "User found", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateData() {
        String emailToUpdate = email.getText().toString().trim();
        List<User> list = User.find(User.class, "email = ?", emailToUpdate);

        if (!list.isEmpty()) {
            User user = list.get(0);
            user.setName(name.getText().toString().trim());
            user.setLastname(lastname.getText().toString().trim());
            user.setPassword(password.getText().toString().trim());
            user.save();

            Toast.makeText(this, "Account updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User not found to update", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteData() {
        String emailToDelete = email.getText().toString().trim();
        List<User> list = User.find(User.class, "email = ?", emailToDelete);

        if (!list.isEmpty()) {
            User user = list.get(0);
            user.delete();

            Toast.makeText(this, "Account deleted", Toast.LENGTH_SHORT).show();

            // Limpiar campos
            name.setText("");
            lastname.setText("");
            email.setText("");
            password.setText("");
            readData.setText("");
        } else {
            Toast.makeText(this, "User not found to delete", Toast.LENGTH_SHORT).show();
        }
    }
}