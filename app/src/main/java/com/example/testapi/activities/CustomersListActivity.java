package com.example.testapi.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityCustomersListBinding;
import com.example.testapi.models.CustomerListAdapter;
import com.example.testapi.models.DeviceAdapter;
import com.example.testapi.models.DeviceListModel;
import com.example.testapi.models.Devices;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomersListActivity extends AppCompatActivity {

    ActivityCustomersListBinding binding;
    CustomerListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustomersListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.backCustomersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.customersListRecView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sp = getSharedPreferences("saved_login", MODE_PRIVATE);
        int retailId = sp.getInt("retailerId", 0);

        getCustomersApi(retailId);


        binding.CustomersSearchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase();
                adapter.filter(query);
                binding.ivCustomersClear.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });
        binding.ivCustomersClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.CustomersSearchEdt.setText("");
                binding.CustomersSearchEdt.setHint("search here");
            }
        });
        binding.ivCustomersDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDownMenu();
            }
        });

    }private void showDropDownMenu() {
        PopupMenu popupMenu = new PopupMenu(this, binding.customerDropDownTV);
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "name");
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, "mobile");
        popupMenu.getMenu().add(Menu.NONE, 3, Menu.NONE, "imei");

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 1:
                    binding.customerDropDownTV.setText("Name");
                    break;
                case 2:
                    binding.customerDropDownTV.setText("Mobile");
                    break;
                case 3:
                    binding.customerDropDownTV.setText("IMEI");
                    break;
                default:
                    binding.CustomersSearchEdt.setHint("search here");
            }
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        });

        popupMenu.show();
    }
    private void getCustomersApi(int retailId) {
        Call<DeviceListModel> call = ApiController
                .getInstance()
                .getapi()
                .getDeviceLists(retailId);

        call.enqueue(new Callback<DeviceListModel>() {
            @Override
            public void onResponse(Call<DeviceListModel> call, Response<DeviceListModel> response) {
                if(response.isSuccessful() && response.body()!=null){
                    String msg = response.body().getMessage();
                    if("Request Successful".equals(msg)){
                        List<Devices> devices = response.body().getDevicesList();
                        adapter = new CustomerListAdapter(CustomersListActivity.this,devices);
                        binding.customersListRecView.setAdapter(adapter);
                    } else if ("No devices found for the provided retailer.".equals(msg)) {
                        Toast.makeText(getApplicationContext(),"No customers found for the provided retailer.",Toast.LENGTH_SHORT).show();
                    } else if ("Validation errors".equals(msg)) {
                        Toast.makeText(getApplicationContext(),"The retail is field is required.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "response body is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeviceListModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}