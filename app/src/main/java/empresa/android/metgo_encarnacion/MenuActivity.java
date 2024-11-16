package empresa.android.metgo_encarnacion;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import empresa.android.metgo_encarnacion.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_acercaDe, R.id.nav_buses,R.id.nav_busqueda,
                R.id.nav_mapaDeParadas,R.id.nav_paradas,
                R.id.nav_noticias,R.id.nav_reclamo,R.id.nav_sugerencia,
                R.id.nav_tarifario,R.id.nav_detalleparada)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        // Establecer el listener para actualizar el título según el fragmento actual
     /*   navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.nav_busqueda:
                    getSupportActionBar().setTitle("Búsqueda");
                    break;
                case R.id.nav_detalleparada:
                    getSupportActionBar().setTitle("Detalle de Parada");
                    break;
                case R.id.nav_acercaDe:
                    getSupportActionBar().setTitle("Acerca De");
                    break;
                case R.id.nav_buses:
                    getSupportActionBar().setTitle("Buses");
                    break;
                case R.id.nav_mapaDeParadas:
                    getSupportActionBar().setTitle("Mapa de paradas");
                    break;
                case R.id.nav_noticias:
                    getSupportActionBar().setTitle("Noticias");
                    break;
                case R.id.nav_sugerencia:
                getSupportActionBar().setTitle("Sugerencia");
                break;
                case R.id.nav_reclamo:
                getSupportActionBar().setTitle("Reclamo");
                break;
                case R.id.nav_paradas:
                    getSupportActionBar().setTitle("Paradas");
                    break;
                case R.id.nav_tarifario:
                    getSupportActionBar().setTitle("Tarifario");
                    break;
                // Agregar más casos para otros fragmentos según sea necesario
                default:
                    getSupportActionBar().setTitle("Mi Aplicación");
                    break;
            }
        });*/
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();

            if (destinationId == R.id.nav_busqueda) {
                getSupportActionBar().setTitle("Busqueda");
            } else if (destinationId == R.id.nav_detalleparada) {
                getSupportActionBar().setTitle("Detalle de Parada");
            }else if (destinationId == R.id.nav_acercaDe) {
                getSupportActionBar().setTitle("Acerca De");
            }else if (destinationId == R.id.nav_buses) {
                getSupportActionBar().setTitle("Buses");
            }else if (destinationId == R.id.nav_mapaDeParadas) {
                getSupportActionBar().setTitle("Mapa de Paradas");
            }else if (destinationId == R.id.nav_noticias) {
                getSupportActionBar().setTitle("Noticias");
            }else if (destinationId == R.id.nav_sugerencia) {
                getSupportActionBar().setTitle("Sugerencia");
            }else if (destinationId == R.id.nav_reclamo) {
                getSupportActionBar().setTitle("Reclamo");
            }else if (destinationId == R.id.nav_paradas) {
                getSupportActionBar().setTitle("Paradas");
            }else if (destinationId == R.id.nav_tarifario) {
                getSupportActionBar().setTitle("Tarifario");
            }
            else {
                getSupportActionBar().setTitle("Mi Aplicación");
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}