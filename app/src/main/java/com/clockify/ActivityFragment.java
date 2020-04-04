package com.clockify;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clockify.Helper.MyButtonClickListener;
import com.clockify.Helper.Swipe;
//import com.clockify.Models.Item;
import com.clockify.Adapter.ActivityAdapter;
import com.clockify.Model.ActivityModel;
import com.clockify.service.ClocklifyService;
import com.clockify.service.DeleteActivity;
import com.clockify.service.ShowActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ActivityFragment extends Fragment {
    RecyclerView recyclerView;
    ActivityAdapter adapter;
    LinearLayoutManager layoutManager;
    EditText searchActivity;
    TextView shortBy;
    Context context;
    Swipe swipe;

    List<ActivityModel> timer = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_activity2, container, false);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_activity2, container, false);
        context = rootView.getContext();
        //apiActivity();
        initLayout(rootView);
        //apiActivity
        return rootView;
    }

    private void initLayout(ViewGroup viewGroup) {
        recyclerView = viewGroup.findViewById(R.id.list_stopwatch);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        searchActivity = viewGroup.findViewById(R.id.search_activity);
        shortBy = viewGroup.findViewById(R.id.shortby);
        swipe=new Swipe(context,recyclerView,200) {
            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<Swipe.MyButton> buffer) {
                buffer.add(new MyButton(context,
                        "Delete",
                        30,
                        0,
                        Color.parseColor("#FF3C30"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(context,"Delete click",Toast.LENGTH_SHORT);
                            }
                        }));
                buffer.add(new MyButton(context,
                        "Update",
                        30,
                        R.drawable.ic_edit_white_24dp,
                        Color.parseColor("#FF9502"),
                        new MyButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(context,"Update click",Toast.LENGTH_SHORT);
                            }
                        }));
            }
        };
        //generateItem();
//ini buat searchnya
//        adapter.getFilter().filter(searchActivity.getText().toString());
    }

//    private void seacrh(){
//        searchLoc.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//            }
//        });
//    }
//    private void filter(String text){
//        List<Item> itemList = new ArrayList<>();
//        List<Item> filteredList = new ArrayList<>();
//        for(Item item: itemList){
//            if(item.getActivity().toLowerCase().contains(text.toLowerCase())){
//                filteredList.add(item);
//            }
//        }
//        adapter.filterList(filteredList);
//    }

// ini apinya buat nampilin
    public  void apiActivity(){

        adapter = new ActivityAdapter(context,timer);

        recyclerView.setAdapter(adapter);

        ShowActivity showActivity = ClocklifyService.create(ShowActivity.class);
        showActivity.ActivityList().enqueue(new Callback<List<ActivityModel>>() {
            @Override
            public void onResponse(Call<List<ActivityModel>> call, Response<List<ActivityModel>> response) {
                if (response.isSuccessful() && response.body() != null){
                    timer = response.body();
                    adapter.notifyDataSetChanged();
//                    List<Item> itemList=new ArrayList<>();
//                    itemList.add(new Item(timer));
//                    adapter = new ActivityAdapter(context,itemList);
//                    recyclerView.setAdapter(adapter);
                    //listStopwatch.addAll(timer);

                }else {
                    FailResponeHandler.handleRespone(context,response.errorBody());
                    Toast.makeText(context, "Gagal mengambil data Activity", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ActivityModel>> call, Throwable t) {
                if(!call.isCanceled()){
                    FailResponeHandler.handlerErrorRespone(context,t);
                }
            }
        });
    }


//    private void delData(){
////        String enteredpostid = postid.getText().toString();
////
////        progressDialog = new ProgressDialog(RetrofitActivity4.this);
////        progressDialog.setMessage(getString(R.string.loading));
////        progressDialog.setCancelable(false);
////        progressDialog.show();
//        //Defining retrofit api service
//        String baseUrl;
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        DeleteActivity deleteActivity = retrofit.create(DeleteActivity.class);
//        Call<ActivityModel> call = deleteActivity.delData(enteredpostid);
//        //calling the api
//        call.enqueue(new Callback<ActivityModel>() {
//            @Override
//            public void onResponse(Call<ActivityModel> call, Response<ActivityModel> response) {
//                //hiding progress dialog
//               // progressDialog.dismiss();
//                if(response.isSuccessful()){
//                    Toast.makeText(context.getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ActivityModel> call, Throwable t) {
//                //progressDialog.dismiss();
//                Toast.makeText(context.getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        });
//    }


}
