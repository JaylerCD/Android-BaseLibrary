package com.jl.baselibrary.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jl.baselibrary.R;
import com.jl.baselibrary.utils.ScreenUtils;
import com.jl.baselibrary.widget.dialog.wheel.WheelView;
import com.jl.baselibrary.widget.dialog.wheel.WheelViewTheme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * Created by Jayler on 2016/9/12.
 */
public class DateSelectDialog extends Dialog implements WheelView.OnSelectItemListener, View.OnClickListener {
    WheelView wheel_year;
    WheelView wheel_month;
    WheelView wheel_day;
    TextView tv_cancel;
    TextView tv_ok;
    private View rootView;
    private ArrayList<String> monthList;
    private ArrayList<String> yearList;
    private ArrayList<String> listDay;

    public DateSelectDialog(Context context) {
        super(context, R.style.BottomDialog);
        init();
    }

    private static final int ITEM_COUNT = 5;
    private static final int OFFSET = ITEM_COUNT / 2;

    private void init() {
        rootView = View.inflate(getContext(), R.layout.date_select_layout, null);
        wheel_day = (WheelView) rootView.findViewById(R.id.wheel_day);
        wheel_month = (WheelView) rootView.findViewById(R.id.wheel_month);
        wheel_year = (WheelView) rootView.findViewById(R.id.wheel_year);
        tv_cancel = (TextView) rootView.findViewById(R.id.tv_cancel);
        tv_ok = (TextView) rootView.findViewById(R.id.tv_ok);
        tv_ok.setTextColor(WheelViewTheme.getDefaultTheme(getContext()).getNormalTextColor());
        wheel_year.setItemCount(ITEM_COUNT);
        wheel_month.setItemCount(ITEM_COUNT);
        wheel_day.setItemCount(ITEM_COUNT);
        tv_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        wheel_year.setOnSelectItemListener(this);
        wheel_month.setOnSelectItemListener(this);
        wheel_day.setOnSelectItemListener(this);
        setData();
        prepareView();
    }

    public void setYearEnable(boolean isEnable) {
        wheel_year.setVisibility(isEnable ? View.VISIBLE : View.GONE);
    }

    public void setDayEnable(boolean isEnable) {
        wheel_day.setVisibility(isEnable ? View.VISIBLE : View.GONE);
    }

    public void setMonthEnable(boolean isEnable) {
        wheel_month.setVisibility(isEnable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void show() {
        Window window = this.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = ScreenUtils.getScreenHeight(getContext()) - rootView.getMeasuredHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        this.onWindowAttributesChanged(wl);
        super.show();
    }

    private void prepareView() {
        setDialogFillWindow();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(rootView);
    }

    /**
     * 设置对话框填充整个窗口
     */
    private void setDialogFillWindow() {
        Window window = this.getWindow();
        // 设置宽高
        window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//         设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);
    }

    private int getMaxYear() {
        int _maxYear;
        if (maxYear > 0) {
            _maxYear = maxYear;
        } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            String currDate = format.format(new Date(System.currentTimeMillis()));
            _maxYear = Integer.valueOf(currDate);
        }

        return _maxYear;
    }

    /**
     * 设置相应wheelView的数据
     */
    private void setData() {
        //设置年份集合
        yearList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String currDate = format.format(new Date(System.currentTimeMillis()));
        year = getMaxYear() - OFFSET + "";
        for (int i = getMaxYear(); i >= 1900; i--) {
            yearList.add(i + "");
        }

        //向相应的wheelView中设置数据
        wheel_year.setData(yearList);
        //设置月份集合
        alterMonth();
//        wheel_month.setData(monthList);

        wheel_year.setSelection(ITEM_COUNT - 1);

        int day = days(Integer.valueOf(year), Integer.valueOf(month));
        setDayData(day);
    }


    //记录年份
    private String year;
    //记录月份
    private String month = "1";
    //记录天数
    private String day = "1";

    SimpleDateFormat dateFormat;

    private SimpleDateFormat getDateFormat() {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        return dateFormat;
    }

    public void setTime(long time) {
        if (maxTime > 0) {
            if (time > 0 && time > maxTime || time < 0 && maxTime > time) {
                time = maxTime;
            }
        }

        SimpleDateFormat dateFormat = getDateFormat();
        String t = dateFormat.format(new Date(time));
        String arr[] = t.split("-");

        setYear(arr[0]);
        alterMonth();
        setMonth(arr[1]);
        days(Integer.valueOf(year), Integer.valueOf(month));
        setDay(arr[2]);
    }

    public long getTime() {
        SimpleDateFormat dateFormat = getDateFormat();
        String strTime = year + "-" + month + "-" + day;
        Date date = null;
        try {
            date = dateFormat.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date == null ? 0 : date.getTime();
    }

    public String getTimeStr() {
        return (year + "-" + month + "-" + day);
    }

    public void setDay(String day) {
        int count = Integer.valueOf(day);
        if (count > 31) {
            return;
        }
        this.day = String.valueOf(count);
        setSelect(this.day, listDay, wheel_day);
    }


    public void setYear(String year) {

        int index = yearList.indexOf(year);
        if (index >= 0 && index < yearList.size()) {
            this.year = year;
            setSelect(this.year, yearList, wheel_year);

            parseDay(this.year, this.month);
        }
    }

    public void setMonth(String month) {
        int count = Integer.valueOf(month);
        if (count > 12) {
            return;
        }
        this.month = String.valueOf(count);
        setSelect(this.month, monthList, wheel_month);
        parseDay(this.year, this.month);
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        if (month.length() == 1) {
            return 0 + month;
        }
        return month;
    }

    public String getDay() {
        if (day.length() == 1) {
            return 0 + day;
        }
        return day;
    }

    private void setSelect(String select, List<String> dataList, WheelView wheelView) {
        if (dataList != null) {
            int index = dataList.indexOf(select);
            if (index >= 0 && index < dataList.size()) {
                wheelView.setSelection(index);
            }
        }
    }

    @Override
    public void onSelectItem(WheelView view, String str, int position) {
        int id = view.getId();
        if (id == R.id.wheel_year) {
            year = str;
            alterMonth();
        } else if (id == R.id.wheel_month) {
            month = str;
        } else if (id == R.id.wheel_day) {
            day = str;
        }
        if (id != R.id.wheel_day) {
            parseDay(year, month);
        }
    }

    private void alterMonth() {
        if (monthList == null) {
            monthList = new ArrayList<>();
        } else {
            monthList.clear();
        }
        if (Integer.valueOf(year) == maxYear) {
            for (int i = 1; i <= maxMonth; i++) {
                monthList.add(i + "");
            }
            setMonthData();
        } else if (monthList.size() != 12 + (OFFSET * 2)) {
            for (int i = 1; i <= 12; i++) {
                monthList.add(i + "");
            }
            setMonthData();
        }

    }

    private void parseDay(String year, String month) {
        try {
            int day;
            if (maxDay > 0 && Integer.valueOf(year) == maxYear) {
                day = maxDay;
                int d = days(Integer.valueOf(year), Integer.valueOf(month));
                if (day > d) {
                    day = d;
                }
            } else {
                day = days(Integer.valueOf(year), Integer.valueOf(month));
            }

            setDayData(day);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getDayFatalism(int day) {
        if (Integer.valueOf(year) == maxYear && Integer.valueOf(month) == maxMonth) {
            day = maxDay;
        }
        return day;
    }

    private void setDayData(int day) {
        if (listDay == null) {
            listDay = new ArrayList<>();
        } else {
            listDay.clear();
        }
        listDay = new ArrayList<>();
        int dayFatalism;

        for (int i = 1; i <= getDayFatalism(day); i++) {
            listDay.add(i + "");
        }
        wheel_day.setData(listDay);
        this.day = "1";
    }

    private void setMonthData() {
        if (monthList == null) return;
        wheel_month.setData(monthList);
        this.month = "1";
    }

    @Override
    public void onClickSelectItem(WheelView view, String str, int position) {

    }

    OnItemOnClickListener onItemOnClickListener;

    public void setOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.onItemOnClickListener = onItemOnClickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel) {
            if (onItemOnClickListener != null) {
                onItemOnClickListener.onClickCancel(this);
            }
        } else if (id == R.id.tv_ok) {
            if (onItemOnClickListener != null) {
                onItemOnClickListener.onClickOk(this);
            }
        }
    }

    public interface OnItemOnClickListener {
        void onClickOk(Dialog dialog);

        void onClickCancel(Dialog dialog);
    }

    public int days(int year, int month) {
        int days = 0;
        if (month != 2) {
            switch (month) {
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    days = 31;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    days = 30;

            }
        } else {
            //闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
                days = 29;
            else
                days = 28;

        }
        return days;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        wheel_day.setSelection(wheel_day.getFirstVisibleItemPosition() + OFFSET);
        wheel_month.setSelection(wheel_month.getFirstVisibleItemPosition() + OFFSET);
        wheel_year.setSelection(wheel_year.getFirstVisibleItemPosition() + OFFSET);
    }

    int maxYear = -1;
    int maxMonth = -1;
    int maxDay = -1;
    long maxTime = -1;

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
        SimpleDateFormat format = getDateFormat();
        String time = format.format(new Date(maxTime));
        String[] arr = time.split("-");
        maxYear = Integer.valueOf(arr[0]);
        maxMonth = Integer.valueOf(arr[1]);
        maxDay = Integer.valueOf(arr[2]);
        setData();
    }

    public void resetTime() {
        maxYear = -1;
        maxMonth = -1;
        maxDay = -1;
        maxTime = -1;
        setData();
    }
}