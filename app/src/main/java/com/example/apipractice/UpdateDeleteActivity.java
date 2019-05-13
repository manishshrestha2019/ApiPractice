package com.example.apipractice;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

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
                if(!inputSearchValidation()){
                    return;
                }
                loadData();

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                if(!inputValidation()){
                                    return;
                                }
                                updateEmployee();
                                etName.setText("");
                                etAge.setText("");
                                etSalary.setText("");
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteActivity.this);
                builder.setMessage("Are you sure want to update?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                deleteEmployee();
                                etId.setText("");
                                etName.setText("");
                                etAge.setText("");
                                etSalary.setText("");


                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };


                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDeleteActivity.this);
                builder.setMessage("Are you sure want to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


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
    public boolean inputValidation(){
        boolean isValid=true;
        if(TextUtils.isEmpty(etName.getText().toString())){
            etName.setError("Please Enter Employee Name");
            etName.requestFocus();
            isValid=false;
        }else if(TextUtils.isEmpty(etAge.getText().toString())){
            etAge.setError("Please Enter Employee Age");
            etAge.requestFocus();
            isValid=false;
        }else if(TextUtils.isEmpty(etSalary.getText().toString())){
            etSalary.setError("Please Enter Employee Salary");
            etSalary.requestFocus();
            isValid=false;
        }
        return isValid;
    }
    public boolean inputSearchValidation(){
        boolean isValid=true;
        if(TextUtils.isEmpty(etId.getText().toString())){
            etId.setError("Please Enter Employee ID to Search");
            etId.requestFocus();
            isValid=false;
        }
        return isValid;
    }


}
