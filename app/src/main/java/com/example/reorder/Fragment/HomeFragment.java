package com.example.reorder.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.reorder.Activity.GoogleMapActivity;
import com.example.reorder.Adapter.BookMarkStoreAdapter;
import com.example.reorder.R;
import com.example.reorder.Result.GetBookMarkResult;
import com.example.reorder.Adapter.StoreAdapter;
import com.example.reorder.Api.GetBookMarkApi;
import com.example.reorder.globalVariables.CurrentBookMarkStoreInfo;
import com.example.reorder.globalVariables.CurrentStoreInfo;
import com.example.reorder.globalVariables.CurrentUserInfo;
import com.example.reorder.globalVariables.serverURL;
import com.example.reorder.info.BookMarkStoreInfo;
import com.example.reorder.info.StoreInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Button bt_near_store;
    private Button bt_bookmark;
    private Button bt_map;
    private ViewPager pager;
    private RecyclerView lv_bookmark_store;
    private RecyclerView lv_near_store;
    private Button bt_cart;
    private RecyclerView.Adapter store_adapter;
    private RecyclerView.Adapter bookmark_store_adapter;
    private List<StoreInfo> currentStoreInfos;
    private List<BookMarkStoreInfo> currentBookMarkStoreList;
    String url= serverURL.getUrl();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view=inflater.inflate(R.layout.fragment_home,container,false);

        lv_bookmark_store=view.findViewById(R.id.lv_bookmark_store);
        lv_near_store=view.findViewById(R.id.lv_near_store);

        //전체 store 리스트뷰 연결
        lv_near_store.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        currentStoreInfos = CurrentStoreInfo.getStore().getStoreInfoList();
        store_adapter = new StoreAdapter(currentStoreInfos,inflater.getContext());
        lv_near_store.setAdapter(store_adapter);

        bt_near_store = (Button) view.findViewById(R.id.bt_near_store);
        bt_near_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("11111","왜 안 돼?");
                lv_bookmark_store.setVisibility(View.INVISIBLE);
                lv_near_store.setVisibility(View.VISIBLE);
                Log.d("11111","왜 안 돼");

            }
        });

        bt_bookmark=(Button)view.findViewById(R.id.bt_bookmark);
        bt_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    GetBookMarkApi getBookMarkApi = retrofit.create(GetBookMarkApi.class);
                    getBookMarkApi.getid(String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId()))
                            .enqueue(new Callback<GetBookMarkResult>() {
                                @Override
                                public void onResponse(Call<GetBookMarkResult> call, Response<GetBookMarkResult> response) {
                                    if(response.isSuccessful())
                                    {
                                        GetBookMarkResult getBookMarkResult=response.body();
                                        switch (getBookMarkResult.getResult()){
                                            case 1://성공
                                                lv_bookmark_store.setVisibility(View.VISIBLE);
                                                lv_near_store.setVisibility(View.INVISIBLE);
                                                List<BookMarkStoreInfo> bookMarkStoreInfoList=getBookMarkResult.getBookMarkStoreInfoList();
                                                CurrentBookMarkStoreInfo.getBookMarkStore().setBookMarkStoreInfoList(bookMarkStoreInfoList);
                                                break;
                                            case 0://실패
                                                break;
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<GetBookMarkResult> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        bt_bookmark=(Button)view.findViewById(R.id.bt_bookmark);
        bt_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    final String id=String.valueOf(CurrentUserInfo.getUser().getUserInfo().getId());
                    Log.d("bt_bookmark",id);
                    GetBookMarkApi getBookMarkApi = retrofit.create(GetBookMarkApi.class);
                    getBookMarkApi.getid(id)
                            .enqueue(new Callback<GetBookMarkResult>() {
                                @Override
                                public void onResponse(Call<GetBookMarkResult> call, Response<GetBookMarkResult> response) {
                                    Log.d("bt_bookmark","respone");
                                    Log.d("bt_bookmark",""+response.body());
                                    if(response.isSuccessful())
                                    {
                                        Log.d("bt_bookmark","respone성공");
                                        GetBookMarkResult getBookMarkResult=response.body();
                                        switch (getBookMarkResult.getResult()){
                                            case 1://성공
                                                Log.d("bt_bookmark","body성공");
                                                lv_bookmark_store.setVisibility(View.VISIBLE);
                                                lv_near_store.setVisibility(View.INVISIBLE);
                                                Log.d("bt_bookmark","2");
                                                List<BookMarkStoreInfo> bookMarkStoreInfoList=getBookMarkResult.getBookMarkStoreInfoList();
                                                CurrentBookMarkStoreInfo.getBookMarkStore().setBookMarkStoreInfoList(bookMarkStoreInfoList);
                                                lv_bookmark_store.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
                                                currentBookMarkStoreList= CurrentBookMarkStoreInfo.getBookMarkStore().getBookMarkStoreInfoList();
                                                bookmark_store_adapter= new BookMarkStoreAdapter(currentBookMarkStoreList,inflater.getContext());
                                                lv_bookmark_store.setAdapter(bookmark_store_adapter);
                                                break;
                                            case 0://실패
                                                break;
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<GetBookMarkResult> call, Throwable t) {
                                    t.printStackTrace();
                                    Log.d("bt_bookmark", String.valueOf(call));
                                    Log.d("bt_bookmark","fail");
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("bt_bookmark","excep");
                }

            }
        });

        bt_map=(Button)view.findViewById(R.id.bt_map);
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=store_adapter.getItemCount();
                Intent MapIntent=new Intent(getActivity(), GoogleMapActivity.class); //this 대신 getActivity() : 현재의 context받아올 수 있음
                MapIntent.putExtra("count",count);
                startActivity(MapIntent);

            }
        });


        return view;
    }



}
