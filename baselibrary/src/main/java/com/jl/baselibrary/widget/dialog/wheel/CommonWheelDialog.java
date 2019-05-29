package com.jl.baselibrary.widget.dialog.wheel;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


import com.jl.baselibrary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JayLer on 2019/5/23.
 */

public class CommonWheelDialog extends WheelViewDialog implements WheelView.OnSelectItemListener, View.OnClickListener {

    private static final int ITEM_COUNT = 5;
    private TextView mTvOk;
    private TextView mTvCancel;
    private WheelView mWheelFirst;
    private WheelView mWheelSecond;
    private WheelView mWheelThird;
    private OnItemOnClickListener onItemOnClickListener;
    private List<String> mWheelFirstList;
    private List<String> mWheelFirstListCopy;
    private List<String> mWheelSecondList;
    private List<String> mWheelThirdList;
    private Map<String, List<String>> mWheelSecondMap;
    private Map<String, List<String>> mWheelThirdMap;
    private String mWheelFirstStr;
    private String mWheelSecondStr;
    private String mWheelThirdStr;
    private String mFormat;

    public void setFormat(String format) {
        this.mFormat = format;
    }

    public CommonWheelDialog(@NonNull Context context) {
        super(context);
        init();
    }


    @Override
    public int getLayoutId() {
        return R.layout.dialog_common_wheel;
    }

    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.onItemOnClickListener = onItemOnClickListener;
    }

    public void setFirstWheelEnable(boolean isEnable) {
        if (mWheelFirst == null) return;
        mWheelFirst.setVisibility(isEnable ? View.VISIBLE : View.GONE);
    }

    public void setSecondWheelEnable(boolean isEnable) {
        if (mWheelSecond == null) return;
        mWheelSecond.setVisibility(isEnable ? View.VISIBLE : View.GONE);
    }

    public void setThirdWheelEnable(boolean isEnable) {
        if (mWheelThird == null) return;
        mWheelThird.setVisibility(isEnable ? View.VISIBLE : View.GONE);
    }

    private void init() {

        setGravity(Gravity.BOTTOM);
        setFullHorizontalWindows();

        mTvOk = findViewById(R.id.tvOk);
        mTvCancel = findViewById(R.id.tvCancel);
        mWheelFirst = findViewById(R.id.wheelFirst);
        mWheelSecond = findViewById(R.id.wheelSecond);
        mWheelThird = findViewById(R.id.wheelThird);

        mWheelFirst.setItemCount(ITEM_COUNT);
        mWheelSecond.setItemCount(ITEM_COUNT);
        mWheelThird.setItemCount(ITEM_COUNT);

        mTvOk.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mWheelFirst.setOnSelectItemListener(this);
        mWheelSecond.setOnSelectItemListener(this);
        mWheelThird.setOnSelectItemListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvCancel && onItemOnClickListener != null) {
            onItemOnClickListener.onClickCancel(this);
            this.dismiss();
        } else if (id == R.id.tvOk && onItemOnClickListener != null) {
            onItemOnClickListener.onClickOk(this, getDataList());
            this.dismiss();
        }
    }

    @Override
    public void onSelectItem(WheelView view, String str, int position) {
        int vId = view.getId();
        if (vId == R.id.wheelFirst) {
            alterSecondWheelData(str);
            if (mWheelSecondMap != null) {
                List<String> stringList = mWheelSecondMap.get(str);
                if (stringList == null || stringList.size() == 0) return;
                alterThirdWheelData(stringList.get(0));
            }

        } else if (vId == R.id.wheelSecond) {
            alterThirdWheelData(str);
        } else if (vId == R.id.wheelThird) {
            mWheelThirdStr = str;
        }
    }

    /**
     * 根据第一个Wheel的key更新，更新第二Wheel的数据
     *
     * @param key
     */
    private void alterSecondWheelData(String key) {
        mWheelFirstStr = key;
        if (mWheelSecondMap == null||mWheelSecondMap.isEmpty()) return;

        List<String> stringList = mWheelSecondMap.get(key);
        if (stringList == null) stringList = new ArrayList<>();
        if (mWheelSecondList == null) {
            mWheelSecondList = new ArrayList<>();
        }
        mWheelSecondList.clear();
        mWheelSecondList.addAll(stringList);
        mWheelSecond.setData(mWheelSecondList);
        if (stringList.isEmpty()) {
            if (mWheelThirdList == null) {
                mWheelThirdList = new ArrayList<>();
            }
            mWheelThirdList.clear();
            mWheelThirdList.addAll(new ArrayList<String>());
            mWheelThird.setData(mWheelThirdList);
        }
    }

    /**
     * 根据第二个Wheel的key更新，更新第三Wheel的数据
     *
     * @param key
     */
    private void alterThirdWheelData(String key) {
        mWheelSecondStr = key;
        if (mWheelThirdMap == null) {
            mWheelThirdStr = "";
            return;
        }
        List<String> list;
        if (TextUtils.isEmpty(mFormat)) {
            list = mWheelThirdMap.get(mWheelSecondStr);
        } else {
            list = mWheelThirdMap.get(mWheelFirstStr + mFormat + mWheelSecondStr);
        }
        if (list != null && !list.isEmpty()) {
            mWheelThirdStr = list.get(0);
        }else{
            mWheelThirdStr="";
        }
        List<String> stringList;
        if (TextUtils.isEmpty(mFormat)) {
            stringList = mWheelThirdMap.get(key);
        }else {
            stringList = mWheelThirdMap.get(mWheelFirstStr + mFormat + key);
        }
        if (stringList == null) stringList = new ArrayList<>();
        if (mWheelThirdList == null) {
            mWheelThirdList = new ArrayList<>();
        }
        mWheelThirdList.clear();
        mWheelThirdList.addAll(stringList);
        mWheelThird.setData(mWheelThirdList);
    }

    public List<String> getDataList() {
        List<String> dataList = new ArrayList<>();
        if (!TextUtils.isEmpty(mWheelFirstStr)) {
            dataList.add(mWheelFirstStr);
        }
        if (!TextUtils.isEmpty(mWheelSecondStr)) {
            dataList.add(mWheelSecondStr);
        }
        if (!TextUtils.isEmpty(mWheelThirdStr)) {
            dataList.add(mWheelThirdStr);
        }
        return dataList;
    }


    @Override
    public void onClickSelectItem(WheelView view, String str, int position) {

    }

    public void setData(List<String> wheelFirstList){
        setData(wheelFirstList, null);
    }

    public void setData(List<String> wheelFirstList, Map<String, List<String>> wheelSecondMap){
        setData(wheelFirstList, wheelSecondMap, null);
    }

    public void setData(List<String> wheelFirstList, Map<String, List<String>> wheelSecondMap, Map<String, List<String>> wheelThirdMap) {
        mWheelFirstListCopy = wheelFirstList;
        mWheelFirstList = new ArrayList<>(wheelFirstList);
        mWheelSecondMap = wheelSecondMap;
        mWheelThirdMap = wheelThirdMap;

        mWheelFirst.setData(mWheelFirstList);
        if (mWheelFirstList != null && !mWheelFirstList.isEmpty()) {
            String selectOne = "";
            String selectTwo = "";
            String selectThree = "";

            for (int i = 0; i < mWheelFirstList.size(); i++) {
                String str = mWheelFirstList.get(i);
                if (!TextUtils.isEmpty(str)) {
                    selectOne = str;
                    break;
                }
            }

            if (mWheelSecondMap != null) {
                List<String> list = mWheelSecondMap.get(selectOne);
                if (list != null && !list.isEmpty()) {
                    selectTwo = list.get(0);
                }
            }
            if (mWheelThirdMap != null) {
                List<String> list;
                if (TextUtils.isEmpty(mFormat)) {
                    list = mWheelThirdMap.get(selectTwo);
                } else {
                    list = mWheelThirdMap.get(selectOne + mFormat + selectTwo);
                }
                if (list != null && !list.isEmpty()) {
                    selectThree = list.get(0);
                }
            }

            setSelectItem(selectOne, selectTwo, selectThree);
        }
        if (mWheelSecondMap == null) {
            mWheelSecond.setVisibility(View.GONE);
        } else {
            mWheelSecond.setVisibility(View.VISIBLE);
        }
        if (mWheelThirdMap == null) {
            mWheelThird.setVisibility(View.GONE);
        } else {
            mWheelThird.setVisibility(View.VISIBLE);
        }
    }

    public void setSelectItem(String firstStr){
        setSelectItem(firstStr, null);
    }


        public void setSelectItem(String firstStr, String secondStr){
        setSelectItem(firstStr, secondStr, null);
    }

    public void setSelectItem(String firstStr, String secondStr, String thirdStr) {
        mWheelFirstStr = TextUtils.isEmpty(firstStr)? mWheelFirstStr : firstStr;
        mWheelSecondStr = TextUtils.isEmpty(secondStr) ? mWheelSecondStr : secondStr;
        mWheelThirdStr = TextUtils.isEmpty(thirdStr) ? mWheelThirdStr : thirdStr;

        // firstStr 为空，默认
        if (TextUtils.isEmpty(firstStr) && mWheelFirstListCopy != null && !mWheelFirstListCopy.isEmpty()) {
            String key = mWheelFirstListCopy.get(0);
            if (TextUtils.isEmpty(key)) return;
            alterSecondWheelData(key);
            if (mWheelSecondMap == null) {
                return;
            }
            List<String> stringList = mWheelSecondMap.get(key);
            if (stringList == null || stringList.size() == 0) return;
            mWheelSecondStr = stringList.get(0);
            if (mWheelThirdMap == null || mWheelThirdMap.size() == 0 || mWheelThirdMap.get(stringList.get(0)) == null) {
                mWheelThirdStr = "";
                return;
            }
            alterThirdWheelData(stringList.get(0));
            mWheelThirdStr = mWheelThirdMap.get(stringList.get(0)).get(0);
        }

        if (TextUtils.isEmpty(mWheelFirstStr) && mWheelFirstList != null && !mWheelFirstList.isEmpty()) {
            mWheelFirstStr = mWheelFirstList.get(0);
        }
        if (TextUtils.isEmpty(mWheelSecondStr) && mWheelSecondList != null && !mWheelSecondList.isEmpty()) {
            mWheelSecondStr = mWheelSecondList.get(0);
        }
        if (TextUtils.isEmpty(mWheelThirdStr) && mWheelThirdList != null && !mWheelThirdList.isEmpty()) {
            mWheelThirdStr = mWheelThirdList.get(0);
        }
        if (mWheelFirstList != null && !mWheelFirstList.isEmpty()) {
            mWheelFirst.setSelection(mWheelFirstList.indexOf(mWheelFirstStr));
            alterSecondWheelData(mWheelFirstStr);
        }
        if (mWheelSecondList != null && !mWheelSecondList.isEmpty()) {
            mWheelSecond.setSelection(mWheelSecondList.indexOf(mWheelSecondStr));
            alterThirdWheelData(mWheelSecondStr);
        }
        if (mWheelThirdList != null && !mWheelThirdList.isEmpty()) {
            mWheelThird.setSelection(mWheelThirdList.indexOf(mWheelThirdStr));
        }
    }

    public interface OnItemOnClickListener {

        void onClickOk(Dialog dialog, List<String> curValueList);

        void onClickCancel(Dialog dialog);
    }
}