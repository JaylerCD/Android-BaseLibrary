package com.baselibrary.sample;

import android.widget.TextView;

import com.baselibrary.sample.base.BaseFragment;
import com.jl.baselibrary.ioc.annotation.ContentView;
import com.jl.baselibrary.ioc.annotation.ViewById;
import com.jl.sample.R;

@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment {

    /****Home****/
    @ViewById(R.id.tvHome)
    private TextView mTvHome;

}
