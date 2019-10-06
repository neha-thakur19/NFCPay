package com.the43appmart.nfc.example;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.the43appmart.nfc.example.DustBin.MyWallete;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences savedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setTitle( "NFC Pay-Home" );

        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view );
        View v = navigationView.getHeaderView( 0 );
        TextView name = (TextView) v.findViewById( R.id.txtnavName );
        TextView email = (TextView) v.findViewById( R.id.txtnavEmail );
        TextView wholesalerid = (TextView) v.findViewById( R.id.txtUserId );

        savedUser = PreferenceManager.getDefaultSharedPreferences( MainActivity.this );
        String Name = savedUser.getString( "strUserName", "" );
        String Id = savedUser.getString( "strId", "" );
        String E_mail = savedUser.getString( "strEmail", "" );
        email.setText( E_mail );
        name.setText( Name );
        wholesalerid.setText( Id );
        navigationView.setNavigationItemSelectedListener( this );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment f = null;
        if (id == R.id.nav_add_card) {
            f = new AddCard();
        } else if (id == R.id.nav_transaction) {
            f = new Transaction();
        } else if (id == R.id.nav_logout) {
            LogOut();
        } else if (id == R.id.nav_nfc_wallete) {
            f = new MyWallete();
        } else if (id == R.id.nav_add_money) {
            f = new AddMoney();
        }

        if (f != null) {
            FragmentManager FM = getSupportFragmentManager();
            FM.beginTransaction()
                    .setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out )
                    .replace( R.id.layout_MainActivity, f )
                    .addToBackStack( String.valueOf( FM ) )
                    .commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }

    private void LogOut() {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(
                MainActivity.this );
        alertDialog.setTitle( "Logout application?" );
        alertDialog.setMessage( "Are you sure you want to Logout the application?" );
        final android.app.AlertDialog.Builder builder = alertDialog.setPositiveButton( "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent( MainActivity.this, Splash.class );
                        startActivity( i );
                        finish();

                        SharedPreferences savedUser = PreferenceManager
                                .getDefaultSharedPreferences( getApplicationContext() );
                        SharedPreferences.Editor editer = savedUser.edit();
                        editer.remove( "strUserName" );
                        editer.commit();
                    }
                } );
        alertDialog.setNegativeButton( "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } );
        alertDialog.show();
    }
}
