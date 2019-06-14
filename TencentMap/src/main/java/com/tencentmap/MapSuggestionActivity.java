package com.tencentmap;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.view.Window;
import android.widget.TextView;

import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.jl.baselibrary.base.BaseActivity;
import com.rxokhttplibrary.base.BaseObserver;
import com.tencentmap.entity.SuggestionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JayLer on 2019/5/20.
 */
public class MapSuggestionActivity extends BaseActivity implements SearchView.OnQueryTextListener, View.OnClickListener {

    private SearchView searchView;
    private int pageIndex = 1;
    private int poiCount;
    private RecyclerView recyclerView;
    private List<SuggestionEntity.DataBean> mList;
    private LoadMoreAdapter<SuggestionEntity.DataBean> mAdapter;
    private boolean mHasNext;
    private String keyword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_map_suggestion);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        initRecyclerView();
        initSearchView();
    }

    private void initSearchView() {
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        //设置SearchView默认为展开显示
        searchView.setIconified(false);
        searchView.onActionViewExpanded();
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(false);
        TextView txt_search = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        txt_search.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//14sp
        View view = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        view.setBackgroundColor(Color.TRANSPARENT);
    }

    private void initData() {

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        suggestion(newText);
        return false;
    }


    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
        mAdapter = new LoadMoreAdapter<SuggestionEntity.DataBean>(this, mList) {
            @Override
            protected int getItemLayoutID() {
                return R.layout.holder_item_address;
            }

            @Override
            protected int getItemHeaderLayoutID() {
                return R.layout.holder_item_address_header;
            }

            @Override
            protected void bindDataToView(LoadMoreBaseViewHolder holder, final int position, final SuggestionEntity.DataBean entity, final int itemViewType) {

                TextView tvTitle = holder.getView(R.id.tvTitle);
                TextView tvAddress = holder.getView(R.id.tvAddress);
                tvTitle.setText(TextUtils.isEmpty(entity.getTitle()) ? "" : entity.getTitle());
                tvAddress.setText(TextUtils.isEmpty(entity.getAddress()) ? "" : entity.getAddress());
                holder.getView(R.id.layoutItem).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        LatLng latLng = new LatLng(entity.getLocation().getLat(), entity.getLocation().getLng());
                        intent.putExtra(Constants.LAT_LNG, latLng);
                        intent.putExtra(Constants.TITLE, entity.getTitle());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }

        };

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isVisBottom(recyclerView)) {
                    if (mAdapter.getItemCount() == 0) {
                        mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
                        return;
                    }
                    if (!mHasNext) {
                        mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
                        return;
                    }
                    addData();
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
        recyclerView.setAdapter(mAdapter);
        addData();
        mAdapter.setLoadState(mAdapter.LOADING);
        mAdapter.notifyDataSetChanged();

    }

    private void addData() {
        suggestion(keyword);
    }

    public static boolean isVisBottom(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();
        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }

    private void suggestion(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            this.keyword = keyword;
            mAdapter.setLoadState(mAdapter.LOADING);
            recyclerView.setVisibility(View.GONE);
            return;
        }

        recyclerView.setVisibility(View.VISIBLE);
        if (!keyword.equals(this.keyword)) {
            poiCount = 0;
            pageIndex = 1;
            mHasNext = true;
            mAdapter.setLoadState(mAdapter.LOADING);
        }
        this.keyword = keyword;
        BaseObserver<SuggestionEntity> observer = new BaseObserver<SuggestionEntity>(this) {

            @Override
            public void onSuccess(SuggestionEntity result) {


                mAdapter.notifyDataSetChanged();
                if (pageIndex == 1) {
                    mList.clear();
                    poiCount = result.getCount();
                    recyclerView.smoothScrollToPosition(0);
                }
                List<SuggestionEntity.DataBean> list = result.getData();
                mList.addAll(list);
                if (mList.size() < poiCount) {
                    mHasNext = true;
                    pageIndex++;
                } else {
                    mHasNext = false;
                    mAdapter.setLoadState(mAdapter.LOADING_COMPLETE);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(Throwable e) {
            }
        };

        MapManager.getInstance().suggestion(this, keyword, pageIndex, observer);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back){
            finish();
        }
    }
}
