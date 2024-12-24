package com.example.testapi.activities;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.example.testapi.databinding.ActivityDeviceListsBinding;
import com.example.testapi.models.DefaulterAdapter;
import com.example.testapi.models.DeviceAdapter;
import com.example.testapi.models.DeviceListModel;
import com.example.testapi.models.Devices;
import com.example.testapi.models.DefaulterResponse;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceListsActivity extends AppCompatActivity {
    ActivityDeviceListsBinding binding;
    DeviceAdapter deviceAdapter;
    DefaulterAdapter defaulterAdapter;
    Boolean allIsClicked = false;
    Boolean defaulterIsClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceListsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deviceListsRecView.setLayoutManager(new LinearLayoutManager(this));

        allBtnClicked(true);

        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBtnClicked(true);
            }
        });

        binding.defaulterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaulterBtnClicked(true);
            }
        });

        
        binding.searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().toLowerCase();
                if (allIsClicked && deviceAdapter != null) {
                    deviceAdapter.filter(query);
                } else if (defaulterIsClicked && defaulterAdapter != null) {
                    defaulterAdapter.filter(query);
                } else {
                    Log.e("FilterError", "No adapter initialized or adapter is null.");
                }

                binding.ivClear.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });
        binding.ivDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDropDownMenu();
            }
        });
        binding.ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.searchEdt.setText("");
                binding.searchEdt.setHint("search here");
            }
        });
    }
    private void getDefaulterList(int retailId){
        Call<DefaulterResponse> call = ApiController
                .getInstance()
                .getapi()
                .getDefaulterList(retailId);

        call.enqueue(new Callback<DefaulterResponse>() {
            @Override
            public void onResponse(Call<DefaulterResponse> call, Response<DefaulterResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    String msg = response.body().getMessage();
                    if("Defaulter list retrieved successfully.".equals(msg)){
                        Toast.makeText(getApplicationContext(),"Defaulter list retrieved successfully.",Toast.LENGTH_SHORT).show();
                        List<DefaulterResponse.Defaulter> defaulterDevices = response.body().getDefaultersList();
                        defaulterAdapter= new DefaulterAdapter(DeviceListsActivity.this,defaulterDevices);
                        binding.deviceListsRecView.setAdapter(defaulterAdapter);
                    }else if ("No defaulters with outstanding amounts for the provided retailer.".equals(msg)){
                        Toast.makeText(getApplicationContext(),"No defaulters with outstanding amounts for the provided retailer.",Toast.LENGTH_SHORT).show();
                    }else if ("No device found for the provided retailer.".equals(msg)){
                        Toast.makeText(getApplicationContext(),"No device found for the provided retailer.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "response body is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaulterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDeiceListApi(int retailId) {
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
                        deviceAdapter = new DeviceAdapter(DeviceListsActivity.this,devices);
                        binding.deviceListsRecView.setAdapter(deviceAdapter);
                    } else if ("No devices found for the provided retailer.".equals(msg)) {
                        Toast.makeText(getApplicationContext(),"No devices found for the provided retailer.",Toast.LENGTH_SHORT).show();
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
    private void allBtnClicked(Boolean isClicked){
        if(isClicked){
            allIsClicked = true;
            defaulterIsClicked = false;
            binding.allBtn.setCardBackgroundColor(Color.parseColor("#8692f7"));
            binding.defaulterBtn.setCardBackgroundColor(Color.parseColor("#ffffff"));
            binding.lockedBtn.setCardBackgroundColor(Color.parseColor("#ffffff"));
            SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
            int retailId =sp.getInt("retailerId",0);
            defaulterAdapter=null;
            getDeiceListApi(retailId);
        }
    }
    private void defaulterBtnClicked(Boolean isClicked){
        if(isClicked){
            defaulterIsClicked = true;
            allIsClicked = false;
            binding.allBtn.setCardBackgroundColor(Color.parseColor("#ffffff"));
            binding.defaulterBtn.setCardBackgroundColor(Color.parseColor("#8692f7"));
            binding.lockedBtn.setCardBackgroundColor(Color.parseColor("#ffffff"));
            SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
            int retailId =sp.getInt("retailerId",0);
            deviceAdapter = null;
            getDefaulterList(retailId);
        }else{
            allBtnClicked(true);
        }
    }

    private void lockBtnClicked(Boolean isClicked){
        if(isClicked){
            binding.allBtn.setCardBackgroundColor(Color.parseColor("#ffffff"));
            binding.defaulterBtn.setCardBackgroundColor(Color.parseColor("#ffffff"));
            binding.lockedBtn.setCardBackgroundColor(Color.parseColor("#8692f7"));
        }else{
            allBtnClicked(true);
        }
    }
    private void showDropDownMenu() {
        PopupMenu popupMenu = new PopupMenu(this, binding.ivDropdown);
        popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "name");
        popupMenu.getMenu().add(Menu.NONE, 2, Menu.NONE, "mobile");
        popupMenu.getMenu().add(Menu.NONE, 3, Menu.NONE, "imei1");
        popupMenu.getMenu().add(Menu.NONE, 4, Menu.NONE, "imei2");

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 1:
                    binding.dropDownTV.setText("Name");
                    break;
                case 2:
                    binding.dropDownTV.setText("Mobile");
                    break;
                case 3:
                    binding.dropDownTV.setText("IMEI1");
                    break;
                case 4:
                    binding.dropDownTV.setText("IMEI2");
                    break;
                default:
                    binding.searchEdt.setHint("search here");
            }
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        });

        popupMenu.show();
    }
}