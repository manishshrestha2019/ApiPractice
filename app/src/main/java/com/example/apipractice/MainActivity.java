package com.example.apipractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import api.EmployeeAPI;
import model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private final static String Base_URL="http://dummy.restapiexample.com/api/v1/";
    private TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData=findViewById(R.id.tvData);

        loadData();
    }

    private void loadData(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(Base_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        EmployeeAPI employeeAPI=retrofit.create(EmployeeAPI.class);

        Call<List<Employee>> listCall=employeeAPI.getAllEmployees();

        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                List<Employee> employeeList=response.body();
                for(Employee emp: employeeList){
                    String content="";
                    content +="ID : "+ emp.getId()+"\n";
                    content +="Name : "+ emp.getEmployee_name()+"\n";
                    content +="Age : "+ emp.getEmployee_age()+"\n";
                    content +="Salary : "+ emp.getEmployee_salary()+"\n";
                    content+="--------------------------\n";
                    tvData.append(content);



                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}
