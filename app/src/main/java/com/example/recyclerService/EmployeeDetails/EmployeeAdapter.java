package com.example.recyclerService.EmployeeDetails;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerService.R;

import java.util.ArrayList;

public class EmployeeAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<EmployeeAdapter.CustomViewHolder> {
    private ArrayList<Employee> employees;
    private Context context;
    private AlertDialog alertDialog;


    public EmployeeAdapter(Context context, ArrayList<Employee> employees) {
        this.employees = employees;
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_view, parent, false);

        return new CustomViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(EmployeeAdapter.CustomViewHolder holder, final int position) {
        Employee employee = employees.get(position);
        holder.employeeId.setText(employee.getId());
        holder.employeeName.setText(employee.getName());
        holder.empAge.setText(employee.getAge());
        holder.salary.setText(employee.getSalary());

        holder.currentData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Employee employee = employees.get(position);
                String text = context.getResources().getString(R.string.id) + " : " + employee.getId() + "\n" + context.getResources().getString(R.string.name) + " : " + employee.getName() + "\n"
                        + context.getResources().getString(R.string.age) + " : " + employee.getAge() + "\n" + context.getResources().getString(R.string.salary) + " : " + employee.getSalary();
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
               /*new AlertDialog.Builder(context)
                       .setMessage(text)
                       .setPositiveButton("Ok",null)
                       .show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView employeeId;
        private TextView employeeName;
        private TextView empAge;
        private TextView salary;
        private CardView currentData;

        CustomViewHolder(View view) {
            super(view);
            employeeId = view.findViewById(R.id.id);
            employeeName = view.findViewById(R.id.name);
            empAge = view.findViewById(R.id.age);
            salary = view.findViewById(R.id.salary);
            currentData = view.findViewById(R.id.currentData);
        }

    }
}
