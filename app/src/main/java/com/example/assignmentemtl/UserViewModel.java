package com.example.assignmentemtl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private List<User> allUsers = new ArrayList<>();

    public UserViewModel() {
        userRepository = new UserRepository();
        loadUsers();
    }

    public LiveData<List<User>> getUsers() {
        return usersLiveData;
    }

    public void loadUsers() {
        userRepository.fetchUsers(new MutableLiveData<List<User>>() {
            @Override
            public void setValue(List<User> users) {
                allUsers = users;
                usersLiveData.setValue(users);
            }
        });
    }

    public void filterByCity(String city) {
        if (city.equals("All")) {
            usersLiveData.setValue(allUsers);
        } else {
            List<User> filtered = new ArrayList<>();
            for (User user : allUsers) {
                if (user.address.city.equals(city)) {
                    filtered.add(user);
                }
            }
            usersLiveData.setValue(filtered);
        }
    }

    public void setAllUsers(List<User> users) {
        this.allUsers = users;
    }
}
