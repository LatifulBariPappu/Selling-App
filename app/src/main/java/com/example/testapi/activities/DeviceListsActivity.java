package com.example.testapi.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.testapi.controller.ApiController;
import com.example.testapi.databinding.ActivityDeviceListsBinding;
import com.example.testapi.models.DeviceAdapter;
import com.example.testapi.models.DeviceListModel;
import com.example.testapi.models.Devices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceListsActivity extends AppCompatActivity {
    ActivityDeviceListsBinding binding;
    DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceListsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sp = getSharedPreferences("saved_login",MODE_PRIVATE);
        int plaza_id =sp.getInt("retailerId",0);

        binding.deviceListsRecView.setLayoutManager(new LinearLayoutManager(this));
        getApi(plaza_id);
        
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
                deviceAdapter.filter(query);
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
                binding.searchEdt.setHint("search . . .");
            }
        });
    }

    private void getApi(int plazaId) {
        Call<DeviceListModel> call = ApiController
                .getInstance()
                .getapi()
                .getDeviceLists(plazaId);

        call.enqueue(new Callback<DeviceListModel>() {
            @Override
            public void onResponse(Call<DeviceListModel> call, Response<DeviceListModel> response) {
                if(response.isSuccessful() && response.body()!=null){
                    String msg = response.body().getMessage();
                    if("Request Successful".equals(msg)){
                        List<Devices> devices = response.body().getDevicesList();
                        deviceAdapter = new DeviceAdapter(devices);
                        binding.deviceListsRecView.setAdapter(deviceAdapter);
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeviceListModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
                    binding.searchEdt.setHint("search by name");
                    break;
                case 2:
                    binding.searchEdt.setHint("search by mobile");
                    break;
                case 3:
                    binding.searchEdt.setHint("search by IMEI1");
                    break;
                case 4:
                    binding.searchEdt.setHint("search by IMEI2");
                    break;
                default:
                    binding.searchEdt.setHint("search . . .");
            }
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        });

        popupMenu.show();
    }
}