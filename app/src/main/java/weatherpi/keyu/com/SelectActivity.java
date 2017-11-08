package weatherpi.keyu.com;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import weatherpi.keyu.com.db.DBManager;
import weatherpi.keyu.com.entity.CityInfo;

public class SelectActivity extends AppCompatActivity {
    private ListView lv;
    private WeatherAdapter weatherAdapter;
    private static List<CityInfo> citys;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        lv =    (ListView)findViewById(R.id.lv);
        weatherAdapter = new WeatherAdapter(this);
        citys = DBManager.getCityInfo(this, "city.db", 10);
        lv.setAdapter(weatherAdapter);

    }


    public static class WeatherAdapter extends BaseAdapter{
        private Context context;
        public WeatherAdapter(Context mContext){
            context = mContext;
        }
        @Override
        public int getCount() {
            return citys.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           View  view = LayoutInflater.from(context).inflate(R.layout.city_item, null);
            TextView tvCity = view.findViewById(R.id.tv_city);
            tvCity.setText(citys.get(position).getCity());
            return view;
        }

        @Override
        public Object getItem(int position) {
            return citys.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }


}
