package weatherpi.keyu.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private  MyViewPagerAdapter pagerAdapter;
    private static List<Fragment> fragments;
    private static boolean isFirst = true;
    private static SharedPreferences sp;
    private  static  final int  DELAY_MILLIS = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        fragments = new ArrayList<>();
        fragments.add(new SplashFragment());
        fragments.add(new SplashFragment2());
        fragments.add(new SplashFragment3());
        sp = getPreferences(Context.MODE_PRIVATE);
        isFirst = sp.getBoolean("isFirst", true);
        if(isFirst){
            pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(pagerAdapter);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    switchActivity();
                }
            }, DELAY_MILLIS);
        }


    }

    public static class  MyViewPagerAdapter extends FragmentStatePagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public static class SplashFragment extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.frag_splash, null);
            return rootView;
        }
    }

    public static class SplashFragment2 extends Fragment{
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.frag_splash2, null);
            return rootView;
        }
    }

    public static class SplashFragment3 extends Fragment{
        private Button btnStart;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = LayoutInflater.from(getContext()).inflate(R.layout.frag_splash3, null);
            btnStart = rootView.findViewById(R.id.btn_start);
            btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    isFirst = false;
                    sp.edit().putBoolean("isFirst", isFirst).commit();
                    getActivity().finish();
                }
            });
            return rootView;
        }


    }
    private  void switchActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
