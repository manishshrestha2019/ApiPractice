package api;

import java.util.List;

import model.Employee;
import model.EmployeeCUD;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmployeeAPI {
    @GET("employees")
    Call<List<Employee>> getAllEmployees();

    //Get Employee on basis of EMPid
    @GET("employee/{empID}")
    Call<Employee> getEmployeeById(@Path("empID")int empId);

    //Register Employee
    @POST("create")
    Call<Void> registerEmployee(@Body EmployeeCUD emp);

    //Update
    @PUT("update/{empID}")
    Call<Void> updateEmployee(@Path("empID") int empId,@Body EmployeeCUD emp);

    //Delete
    @DELETE("delete/{empID}")
    Call<Void> deleteEmployee(@Path("empID")int empId);
}
