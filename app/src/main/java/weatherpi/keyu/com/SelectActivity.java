package weatherpi.keyu.com;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import weatherpi.keyu.com.db.DBManager;
import weatherpi.keyu.com.entity.CityInfo;
import weatherpi.keyu.com.utils.Constant;
import weatherpi.keyu.com.utils.Utils;

import static weatherpi.keyu.com.utils.Constant.DB_NAME;
import static weatherpi.keyu.com.utils.Constant.DB_TABLE_COLUMN_ALLFIRSTPY;
import static weatherpi.keyu.com.utils.Constant.DB_TABLE_COLUMN_ALLPY;
import static weatherpi.keyu.com.utils.Constant.DB_TABLE_COLUMN_CITY;
import static weatherpi.keyu.com.utils.Constant.DB_TABLE_COLUMN_FIRSTPY;
import static weatherpi.keyu.com.utils.Constant.DB_TABLE_COLUMN_PROVINCE;
import static weatherpi.keyu.com.utils.Constant.DB_TABLE_NAME;

public class SelectActivity extends AppCompatActivity {
    private ListView lv;
    private WeatherAdapter weatherAdapter;
    private static List<CityInfo> citys;
    private static List<CityInfo> curCitys;
    private EditText editText;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.choose_city));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        lv =    (ListView)findViewById(R.id.lv);
        editText = (EditText) findViewById(R.id.edit_text_input);
        weatherAdapter = new WeatherAdapter(this);
        citys = DBManager.getCityInfo(this, DB_NAME);
        curCitys = citys;
        lv.setAdapter(weatherAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String input = editable.toString();
                int len = input.length();
                if (len == 0) {
                    curCitys = citys;
                } else if (len == 1) {
                    char c = input.charAt(0);
                    if (Utils.isChinese(c)) {//中文检索
                        String sql = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_TABLE_COLUMN_PROVINCE + " LIKE " + "\'" + input + "%\'" + " or " + DB_TABLE_COLUMN_CITY + " LIKE " + "\'" + input + "%\'";
                        curCitys = DBManager.getCityInfoBySql(SelectActivity.this, DB_NAME, sql);
                    } else if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                        String sql = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_TABLE_COLUMN_FIRSTPY + " LIKE " + "\'" + input + "%\'";
                        curCitys = DBManager.getCityInfoBySql(SelectActivity.this, DB_NAME, sql);
                    } else {//非法字符
                        curCitys = new ArrayList<>();
                    }
                } else {
                    if (Utils.isChinese(input)) {//含中文
                        String sql = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_TABLE_COLUMN_PROVINCE + " LIKE " + "\'" + input + "%\'" + " or " + DB_TABLE_COLUMN_CITY + " LIKE " + "\'" + input + "%\'";
                        curCitys = DBManager.getCityInfoBySql(SelectActivity.this, DB_NAME, sql);
                    } else if (input.matches("[a-zA-Z]+")) {//拼音
                        String sql = "SELECT * FROM " + DB_TABLE_NAME + " WHERE " + DB_TABLE_COLUMN_ALLFIRSTPY + " LIKE " + "\'" + input + "%\'" + " or " + DB_TABLE_COLUMN_ALLPY + " LIKE " + "\'" + input + "%\'";
                        curCitys = DBManager.getCityInfoBySql(SelectActivity.this, DB_NAME, sql);
                    } else {//含非法字符
                        curCitys = new ArrayList<>();
                    }
                }
                weatherAdapter.notifyDataSetChanged();

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(inputMethodManager.isActive()){
                    inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                }
                Intent intent = new Intent();
                intent.putExtra(Constant.CUR_CITY, curCitys.get(i));
                setResult(Constant.CHOOSE_CITY_RESULT_CODE, intent);
                finish();
            }
        });

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    final int DRAWABLE_RIGHT = 2;
                    if(motionEvent.getRawX() > editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()){
                        editText.setText("");
                        return  true;
                    }
                }
                return false;
            }
        });

    }


    public static class WeatherAdapter extends BaseAdapter{
        private Context context;
        public WeatherAdapter(Context mContext){
            context = mContext;
        }
        @Override
        public int getCount() {
            return curCitys.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View  view = LayoutInflater.from(context).inflate(R.layout.city_item, null);
            TextView tvCity = view.findViewById(R.id.tv_city);
            tvCity.setText(curCitys.get(position).getProvince() + "·" + curCitys.get(position).getCity());
            return view;
        }

        @Override
        public Object getItem(int position) {
            return curCitys.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
