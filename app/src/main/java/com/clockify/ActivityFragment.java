package com.clockify;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
import com.clockify.service.Update;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
    int id;

    private SimpleDateFormat parser;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyy", Locale.getDefault());

    List<ActivityModel> timer = new ArrayList<>();
    private ProgressDialog progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_activity2, container, false);
        context = rootView.getContext();

        initLayout(rootView);
        initEvent();
        return rootView;
    }



    @Override
    public void onResume() {
        super.onResume();
        apiActivity();
    }

    private void initLayout(ViewGroup viewGroup) {
        recyclerView = viewGroup.findViewById(R.id.list_stopwatch);
        searchActivity = viewGroup.findViewById(R.id.search_activity);
        shortBy = viewGroup.findViewById(R.id.shortby);

        adapter = new ActivityAdapter(context);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

//        adapter.setOnItemClickListener(new ActivityAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(int position) {
//                adapter.setContext(context);
//
//            }
//        });

        swipe = new Swipe(context, recyclerView, 200) {
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
                                Toast.makeText(context, "Delete click", Toast.LENGTH_SHORT);
                                if (timer != null)
                                    id = timer.get(pos).getId();
                                    apiDelete();
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
                                Toast.makeText(context, "Update click", Toast.LENGTH_SHORT);
                            }
                        }));
            }
        };
    }

    private void initEvent() {
        searchActivity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    Editable searchEditable = searchActivity.getText();
                    if (searchEditable != null) {
                        String searchToLower = searchEditable.toString().toLowerCase();
                        search(searchToLower);
                    }
                    return true;
                }
                return false;
            }
        });


        searchActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable searchEditable = searchActivity.getText();
                if (searchEditable != null) {
                    String searchToLower = searchEditable.toString().toLowerCase();
                    search(searchToLower);
                }
            }
        });
    }

    private void search(String searchString) {
        if (!searchString.isEmpty()) {
            //Search
            List<ActivityModel> result = new ArrayList<>();
            for (ActivityModel model : timer) {
                if (model.getActivity().toLowerCase().contains(searchString)) {
                    result.add(model);
                }
            }
            adapter.updateAdapter(result, "data");
        } else {
            //Reset data
            adapter.updateAdapter(timer, searchString);
        }
    }


    // ini apinya buat nampilin
    public void apiActivity() {
        ShowActivity showActivity = ClocklifyService.create(ShowActivity.class);
        showActivity.ActivityList().enqueue(new Callback<List<ActivityModel>>() {
            @Override
            public void onResponse(Call<List<ActivityModel>> call, Response<List<ActivityModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    timer = response.body();
//                    List<ActivityModel> newList = new ArrayList<>();
//                    for(ActivityModel model : timer){
//                        if(newList.size()>0){
//                            if(newList.contains(model)){
//                             int index = newList.indexOf(model);
//                             ActivityModel newListModel = newList.get(index);
//                             String createdAt = dateFormat.format(stringDateFormatter(model.getCreatedAt()));
//                             String createdAt2 = dateFormat.format(stringDateFormatter(newListModel.getCreatedAt()));
//                                //String dtStart = "2010-10-15T09:27:37Z";
//                                SimpleDateFormat format = new SimpleDateFormat("dd mmm yyyy");
//                                try {
//                                    Date date = format.parse(createdAt);
//                                    Date date2 = format.parse(createdAt2);
//                                    if(date.compareTo(date2)>0){
//                                        newList.set(index,model);
//                                    }
//                                    //System.out.println(date);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                                }
//                            }else {
//                            newList.add(model);
//
//                        }
//
//                    }
                    adapter.notifyDataSetChanged();
                    if (timer != null) {
                        adapter.updateAdapter(timer, "");
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    FailResponeHandler.handleRespone(context, response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ActivityModel>> call, Throwable t) {
                if (!call.isCanceled()) {
                    FailResponeHandler.handlerErrorRespone(context, t);
                }
            }
        });
    }

    public void apiDelete() {
        progress = new ProgressDialog(context);
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
        //id = timer.get(pos).getId();

        DeleteActivity deleteActivity = ClocklifyService.create(DeleteActivity.class);
        deleteActivity.delData(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progress.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Success delete.", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } else {
                    FailResponeHandler.handleRespone(context, response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progress.dismiss();
                if (!call.isCanceled()) {
                    FailResponeHandler.handlerErrorRespone(context, t);
                }
            }
        });
    }

    public Date stringDateFormatter(String dateString) {
        Date date = null;
        if (parser == null) {
            parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        }
        parser.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        try {
            date = parser.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }



}
