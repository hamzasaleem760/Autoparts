package com.auto.autoparts_fyp_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ImageView engineMechanical,carExterior,electricalLighting;
    private ImageView Brakes,carCare,carInterior;
    private ImageView audioVideo,tyresRims,heatingCooling;
    private ImageView Shoppingcart;
    private Button login;

    private DatabaseReference RootRef;
    private String currentCustomerId;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private DatabaseReference UsersRef;


    private DatabaseReference VendorsRef;
    String vid;
    private Button signIn;
    private MaterialSearchBar  searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null )
                {
                    Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please Login First To See Items in cart", Toast.LENGTH_SHORT).show();
                }


            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headView=navigationView.getHeaderView(0);
        // Button signIn=headView.findViewById(R.id.nav_signin);
        signIn=headView.findViewById(R.id.nav_signin);

        RootRef = FirebaseDatabase.getInstance().getReference();




        engineMechanical = (ImageView) findViewById(R.id.engine_mechanical);
        carExterior = (ImageView) findViewById(R.id.car_exterior);
        electricalLighting = (ImageView) findViewById(R.id.electrical_lighting);


        Brakes = (ImageView) findViewById(R.id.brakes);
        carCare = (ImageView) findViewById(R.id.car_care);
        carInterior = (ImageView) findViewById(R.id.car_interior);


        audioVideo = (ImageView) findViewById(R.id.audio_video);
        tyresRims = (ImageView) findViewById(R.id.tyres_rims);
        heatingCooling = (ImageView) findViewById(R.id.heating_cooling);
        searchBar=(MaterialSearchBar) findViewById(R.id.search_Bar);


        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchProductsActivity.class);
                startActivity(intent);

            }
        });





        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectAccountTypeActivity.class);
                startActivity(intent);

            }
        });


        engineMechanical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "engineMechanical", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, EngineMechanicalActivity.class);
                startActivity(intent);
            }
        });

        carExterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "carExterior", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, CarExteriorActivity.class);
                startActivity(intent);

            }
        });

        electricalLighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "electricalLighting", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ElectricalLightingActivity.class);
                startActivity(intent);

            }
        });


        Brakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Brakes", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BrakesActivity.class);
                startActivity(intent);

            }
        });

        carCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "carCare", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CarCareActivity.class);
                startActivity(intent);

            }
        });


        carInterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "carInterior", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, CarInteriorActivity.class);
                startActivity(intent);

            }
        });


        audioVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "audioVideo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AudioVideoActivity.class);
                startActivity(intent);

            }
        });

        tyresRims.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "tyresRims", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, TyresRimsActivity.class);
                startActivity(intent);

            }
        });

        heatingCooling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "heatingCooling", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HeatingCoolingActivity.class);
                startActivity(intent);

            }
        });


//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Cart", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, CartActivity.class);
//                startActivity(intent);
//
//            }
//        });





    }// on create bracket



    @Override
    protected void onStart() {


        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            signIn.setVisibility(View.INVISIBLE);

            String currentVendorId = user.getUid();
            Query query = FirebaseDatabase.getInstance().getReference("Vendors").orderByChild("vid").equalTo(currentVendorId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        Intent intent = new Intent(MainActivity.this, VendorMainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Welcome Customer", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else {
            Toast.makeText(this, "Welcome to Autoparts", Toast.LENGTH_SHORT).show();
            signIn.setVisibility(View.VISIBLE);


        }
    }//on start


//            String accountType="vendor";
//
               // Query vendorQuery=RootRef.child("Vendors").orderByChild("vid").equalTo(currentVendorId);
//
//            Query vendorQuery2=RootRef.child("Vendors").orderByChild("accountType").equalTo("vendor");
//
//            vendorQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Intent intent = new Intent(MainActivity.this, VendorMainActivity.class);
//                    startActivity(intent);
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });




//            String currentCustomerId= vendor.getUid();
//            Query customerQuery=RootRef.child("Customers").orderByChild("cid").equalTo(currentCustomerId);
//            customerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    Toast.makeText(MainActivity.this, "Hello Customer", Toast.LENGTH_SHORT).show();
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

//            if (user!=null)
//            {
//                String currentVendorId= user.getUid();
//                Query query=FirebaseDatabase.getInstance().getReference("Customers").orderByChild("cid").equalTo(currentVendorId);
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Intent intent = new Intent(MainActivity.this, VendorMainActivity.class);
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

        //}



//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser == null)
//        {
//            Toast.makeText(this, "Login First", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//
//            String currentVendorId= currentUser.getUid();
//            Query query=FirebaseDatabase.getInstance().getReference("Vendors").child("vid").equalTo(currentVendorId);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Intent intent = new Intent(MainActivity.this, VendorMainActivity.class);
//                    startActivity(intent);
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        }




//        if (mAuth!=null)
//        {
//            final FirebaseAuth mAuth=FirebaseAuth.getInstance();
//            currentVendorId = mAuth.getCurrentUser().getUid();
//            Query query=FirebaseDatabase.getInstance().getReference("Products").child("vid").equalTo(currentVendorId);
//            query.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    VendorData vendorData=new VendorData();
//                    String id= vendorData.getVsaddress();
//                    if (id!=null)
//                    {
//                        Toast.makeText(MainActivity.this, "Vendor", Toast.LENGTH_LONG).show();
//                        Intent intent=new Intent(getApplicationContext(),VendorMainActivity.class);
//                        startActivity(intent);
//                    }
//                    else if (id==null)
//                    {
//                        Toast.makeText(MainActivity.this, "Customer", Toast.LENGTH_SHORT).show();
//
//                    }
//                    else
//                    {
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        }
//        else
//        {
//            Toast.makeText(getApplicationContext(), "Not SignIn OR Session Out", Toast.LENGTH_SHORT).show();
//            Intent intent=new Intent(getApplicationContext(),SelectAccountTypeActivity.class);
//            startActivity(intent);
//        }
//        currentVendorId = mAuth.getCurrentUser().getUid();
//       // currentCustomerId = mAuth.getCurrentUser().getUid();
//
//        Query Vendorquery=FirebaseDatabase.getInstance().getReference().child("Vendors").orderByChild("vid").equalTo(currentVendorId);
//        //Query Customerquery=FirebaseDatabase.getInstance().getReference().child("Customers").orderByChild("cid").equalTo(currentCustomerId);
//
//        if (mAuth.getCurrentUser().equals(Vendorquery))
//        {
//            Intent intent = new Intent(MainActivity.this, VendorMainActivity.class);
//            startActivity(intent);
//
//        }

//        else
//        {
//            Intent intent = new Intent(MainActivity.this, MainActivity.class);
//            startActivity(intent);
//        }






    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        /*if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            // Handle the camera action
        } else if (id == R.id.nav_categories)
        {

        } else if (id == R.id.nav_vendors)
        {

        }
        else if (id == R.id.nav_vehicle)
        {

        }
        else if (id == R.id.nav_about)
        {

            /*
            Paper.book().destroy();
            Intent intent = new Intent (HomeActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            */
            Toast.makeText(this, "LOGOUT", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_contact_us)
        {

        }

        else if (id == R.id.nav_logout)
        {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null)
            {
                mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Login First", Toast.LENGTH_SHORT).show();

            }



        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
