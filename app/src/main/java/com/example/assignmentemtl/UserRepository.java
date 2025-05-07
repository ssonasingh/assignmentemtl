package com.example.assignmentemtl;

import androidx.lifecycle.MutableLiveData;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class UserRepository {
    private ApiService apiService;

    public UserRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void fetchUsers(MutableLiveData<List<User>> userListLiveData) {
        apiService.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    userListLiveData.setValue(response.body());
                } else {
                    userListLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                userListLiveData.setValue(null);
            }
        });
    }
}
