package weatherpi.keyu.com;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import weatherpi.keyu.com.db.DBManager;
import weatherpi.keyu.com.entity.CityInfo;
import weatherpi.keyu.com.utils.Constant;

public class SelectActivity extends AppCompatActivity {
    private ListView lv;
    private WeatherAdapter weatherAdapter;
    private static List<CityInfo> citys;
    private static List<CityInfo> curCitys;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        lv =    (ListView)findViewById(R.id.lv);
        editText = (EditText) findViewById(R.id.edit_text_input);
        weatherAdapter = new WeatherAdapter(this);
        citys = DBManager.getCityInfo(this, Constant.DB_NAME);
        curCitys = citys;
        lv.setAdapter(weatherAdapter);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                curCitys = DBManager.getCityInfoBySql(SelectActivity.this, Constant.DB_NAME, Constant.DB_TABLE_COLUMN_ALLFIRSTPY, charSequence.toString());
                weatherAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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


}
