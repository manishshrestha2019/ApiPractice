package com.example.apipractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import api.EmployeeAPI;
import model.Employee;
import model.EmployeeCUD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEmployeeActivity extends AppCompatActivity {
    private final static String Base_URL="http://dummy.restapiexample.com/api/v1/";
    private EditText etName,etAge,etSalary;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        btnRegister=findViewById(R.id.btnRegister);

        etName=findViewById(R.id.etEmployeeName);
        etAge=findViewById(R.id.etEmployeeAge);
        etSalary=findViewById(R.id.etEmployeeSalary);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });
    }
    private void Register(){
        String name=etName.getText().toString();
        float salary= Float.parseFloat(etSalary.getText().toString());
        int age= Integer.parseInt(etAge.getText().toString());

        EmployeeCUD employeeCUD=new EmployeeCUD(name,salary,age);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeAPI employeeAPI=retrofit.create(EmployeeAPI.class);
        Call<Void> voidCall=employeeAPI.registerEmployee(employeeCUD);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AddEmployeeActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddEmployeeActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
