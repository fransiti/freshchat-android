package com.example.demoapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.freshchat.consumer.sdk.ConversationOptions;
import com.freshchat.consumer.sdk.FaqOptions;
import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatUser;
import com.freshchat.consumer.sdk.exception.MethodNotAllowedException;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_restore_user) {
            String restoreId = "abcd"; // TODO: Get restore id from app's backend
            String externalId = "yourAppUserID"; // TODO: You app's external id

            try {
                if (restoreId == null || restoreId.length() == 0) {
                    Freshchat.getInstance(getApplicationContext()).identifyUser(externalId, null);
                } else {
                    Freshchat.getInstance(getApplicationContext()).identifyUser(externalId, restoreId);
                }
            } catch (MethodNotAllowedException e) {
                Log.e("MyApp", e.toString());
            }
        } else if (id == R.id.nav_user_details) {
            FreshchatUser user = Freshchat.getInstance(this).getUser();
            user.setFirstName("Test User");
            try {
                Freshchat.getInstance(this).setUser(user);
            } catch (MethodNotAllowedException e) {
                Log.e("MyApp", e.toString());
            }
        } else if (id == R.id.nav_user_properties) {
            HashMap<String, String> map = new HashMap<>();
            map.put("UserId", String.valueOf(22131341));
            map.put("Plan", "Free");
            try {
                Freshchat.getInstance(this).setUserProperties(map);
            } catch (MethodNotAllowedException e) {
                Log.e("MyApp", e.toString());
            }
        } else if (id == R.id.nav_show_channels) {
            Freshchat.showConversations(this);
        } else if (id == R.id.nav_show_channel) {

            ArrayList<String> tags = new ArrayList<>();
            tags.add("premium");

            ConversationOptions options = new ConversationOptions();
            options.filterByTags(tags, "Premium Support");
            Freshchat.showConversations(this, options);
        } else if (id == R.id.nav_show_faqs) {
            Freshchat.showFAQs(this);
        } else if (id == R.id.nav_show_faq) {

            ArrayList<String> tags = new ArrayList<>();
            tags.add("product-refund");

            FaqOptions options = new FaqOptions();
            options.filterByTags(tags, "Get product refund");
            Freshchat.showFAQs(this, options);
        } else if (id == R.id.nav_reset_user) {
            Freshchat.resetUser(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
