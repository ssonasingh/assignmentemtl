package com.example.assignmentemtl;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private UserViewModel viewModel;
    private UserAdapter adapter;
    private Spinner spinnerCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCity = findViewById(R.id.spinnerCity);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        viewModel.getUsers().observe(this, users -> {
            if (users != null) {
                adapter.setUsers(users);
                viewModel.setAllUsers(users);
                setupCitySpinner(users);
            }
        });

        spinnerCity.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            public void onItemSelected(android.widget.AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedCity = parent.getItemAtPosition(position).toString();
                viewModel.filterByCity(selectedCity);
            }
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });
    }

    private void setupCitySpinner(List<User> users) {
        Set<String> citySet = new HashSet<>();
        citySet.add("All");
        for (User user : users) {
            citySet.add(user.address.city);
        }
        List<String> cities = new ArrayList<>(citySet);
        Collections.sort(cities);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);
        spinnerCity.setSelection(cities.indexOf("All"));
    }


    }
