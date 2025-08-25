package com.lucas.locationfavorite.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.lucas.locationfavorite.Controller.DBController_loc;
import com.lucas.locationfavorite.DB.DBLocation;
import com.lucas.locationfavorite.Model.LocationItem;
import com.lucas.locationfavorite.R;

import java.util.ArrayList;
import java.util.List;

public class LocationFavoriteActivity extends AppCompatActivity {
    private SearchView searchView;
    private TextView tx_counter;
    private Button btnAdd;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private List<LocationItem> locationList = new ArrayList<>();
    private LocationAdapter adapter;

    private Uri selectedImageUri = null;

    private final ActivityResultLauncher<Intent> pickImageLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    Toast.makeText(this, "Foto selecionada!", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.location_activity);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        searchView = findViewById(R.id.searchView);
        btnAdd = findViewById(R.id.btnAdd);
        tx_counter = findViewById(R.id.tx_counter);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DBLocation dbLocation = new DBLocation(this);
        Cursor cursor = dbLocation.getAllLocations();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double lat = cursor.getDouble(cursor.getColumnIndexOrThrow("lat"));
                double lng = cursor.getDouble(cursor.getColumnIndexOrThrow("long"));
                String photo = cursor.getString(cursor.getColumnIndexOrThrow("photoUri"));

                locationList.add(new LocationItem(id, name, lat, lng, photo));
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new LocationAdapter(locationList, this);
        recyclerView.setAdapter(adapter);

        // Atualiza contador
        tx_counter.setText(String.valueOf(locationList.size()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                Location location = locationResult.getLastLocation();
                if (location != null) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    showLocation(lat, lng);

                    fusedLocationClient.removeLocationUpdates(locationCallback);
                }
            }
        };

        btnAdd.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        });
    }

    private void showLocation(double lat, double lng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialogadd_activity, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        EditText etName = view.findViewById(R.id.et_local_name);
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        Button btnSave = view.findViewById(R.id.btn_save);
        Button btnPickPhoto = view.findViewById(R.id.btn_pick_photo);

        selectedImageUri = null;

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnPickPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (!name.isEmpty()) {
                DBController_loc dbController_loc = new DBController_loc(this);
                String result = dbController_loc.insertData(
                        name,
                        lat,
                        lng,
                        selectedImageUri != null ? selectedImageUri.toString() : null
                );

                if (result.equals("Record inserted")) {
                    SQLiteDatabase db = new DBLocation(this).getReadableDatabase();
                    Cursor last = db.rawQuery("SELECT * FROM location ORDER BY id DESC LIMIT 1", null);
                    if (last.moveToFirst()) {
                        int id = last.getInt(last.getColumnIndexOrThrow("id"));
                        String photo = last.getString(last.getColumnIndexOrThrow("photoUri"));
                        LocationItem newItem = new LocationItem(id, name, lat, lng, photo);
                        adapter.addLocation(newItem);
                        tx_counter.setText(String.valueOf(adapter.getItemCount()));
                    }
                    last.close();

                    Toast.makeText(this, "Location saved successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Error when saving!", Toast.LENGTH_SHORT).show();
                }
            } else {
                etName.setError("Enter a name");
            }
        });

        dialog.show();
    }
}
