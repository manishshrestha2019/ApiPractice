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

public class UpdateDeleteActivity extends AppCompatActivity {
    private final static String Base_URL="http://dummy.restapiexample.com/api/v1/";
    private Button btnSearch,btnUpdate,btnDelete;
    private EditText etId,etName,etAge,etSalary;
    Retrofit retrofit;
    EmployeeAPI employeeAPI;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        btnSearch=findViewById(R.id.btnSearchUD);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
        etId=findViewById(R.id.etIdUD);
        etAge=findViewById(R.id.etEmployeeAgeUD);
        etSalary=findViewById(R.id.etEmployeeSalaryUD);
        etName=findViewById(R.id.etEmployeeNameUD);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();

            }
        });
    }
    private void CreateInstance(){
        retrofit =new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        employeeAPI=retrofit.create(EmployeeAPI.class);
    }

    private void loadData(){
        CreateInstance();
        Call<Employee> listCall=employeeAPI.getEmployeeById(Integer.parseInt(etId.getText().toString()));
        listCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                etName.setText(response.body().getEmployee_name());
                etAge.setText(Integer.toString((response.body().getEmployee_age())));
                etSalary.setText(Float.toString((response.body().getEmployee_salary())));


            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateEmployee(){
        CreateInstance();
        EmployeeCUD employeeCUD =new EmployeeCUD(
                etName.getText().toString(),Float.parseFloat(etSalary.getText().toString())
                ,Integer.parseInt(etAge.getText().toString())
        );

        Call<Void> voidCall =employeeAPI.updateEmployee(Integer.parseInt(etId.getText().toString()),employeeCUD);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UpdateDeleteActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteEmployee(){
        CreateInstance();
        Call<Void> voidCall=employeeAPI.deleteEmployee(Integer.parseInt(etId.getText().toString()));

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UpdateDeleteActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


}
