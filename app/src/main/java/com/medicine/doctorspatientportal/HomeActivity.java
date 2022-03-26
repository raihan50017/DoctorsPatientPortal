package com.medicine.doctorspatientportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        setSupportActionBar(toolbar);

//TAB FRAGMENT DESIGN

        HomeFragment homeFragment = new HomeFragment();
        NotificationFragment notificationFragment = new NotificationFragment();
        MessageFragment messageFragment = new MessageFragment();
        UserProfileFragment userProfileFragment = new UserProfileFragment();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(homeFragment, "Home");
        viewPagerAdapter.addFragment(notificationFragment, "Notifications");
        viewPagerAdapter.addFragment(messageFragment, "Message");
        viewPagerAdapter.addFragment(userProfileFragment, "Profile");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt( 0).setIcon(R.drawable.ic_baseline_home_24);
        tabLayout.getTabAt( 1).setIcon(R.drawable.ic_baseline_notifications_24);
        tabLayout.getTabAt( 2).setIcon(R.drawable.ic_baseline_email_24);
        tabLayout.getTabAt( 3).setIcon(R.drawable.ic_baseline_perm_identity_24);



//DRAWER LAYOUT DESIGN

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.profile){
                    Toast.makeText(HomeActivity.this, "PROFILE", Toast.LENGTH_SHORT).show();
                }

                if(item.getItemId() == R.id.user_logout){
                    FirebaseAuth.getInstance().signOut();

                    Toast.makeText(HomeActivity.this, "Signout successfull", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(HomeActivity.this, UserSignInActivity.class);
                    startActivity(intent);
                }

                return false;
            }
        });

    }

//VIEW PAGER ADAPTER

    class  ViewPagerAdapter extends FragmentPagerAdapter{


        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;


        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @NonNull
        @Override
        public  CharSequence getPageTitle(int position){
            return titles.get(position);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

    }


}