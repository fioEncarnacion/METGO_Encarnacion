package empresa.android.metgo_encarnacion.ui.sugerencia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import empresa.android.metgo_encarnacion.R;
import empresa.android.metgo_encarnacion.databinding.FragmentSugerenciaBinding;
import empresa.android.metgo_encarnacion.ui.reclamo.ReclamoFragment;
import empresa.android.metgo_encarnacion.ui.sugerencia.SugerenciaViewModel;

public class SugerenciaFragment extends Fragment {

    private EditText txtNombreSugerencia, txtEmailSugerencia, txtCodigoSugerencia, txtDescripcionSugerencia;
    private Button btnEnviarSugerencia;
    private List<String> codigosValidos = Arrays.asList("101", "102", "103", "200", "300");
    private FragmentSugerenciaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SugerenciaViewModel sugerenciaViewModel =
                new ViewModelProvider(this).get(SugerenciaViewModel.class);

        // Inflar el diseño del fragmento y obtener la vista
        View r = inflater.inflate(R.layout.fragment_sugerencia, container, false);

        // Inicializar los elementos de la interfaz
        txtNombreSugerencia = r.findViewById(R.id.txtnombre_s);
        txtEmailSugerencia = r.findViewById(R.id.txtemail_s);
        txtCodigoSugerencia = r.findViewById(R.id.txtcodigo_s);
        txtDescripcionSugerencia = r.findViewById(R.id.txtdescripcion_s);
        btnEnviarSugerencia = r.findViewById(R.id.btnenviar_s);

        // Evento para el botón enviar
        btnEnviarSugerencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombreSugerencia.getText().toString().trim();
                String email = txtEmailSugerencia.getText().toString().trim();
                String codigo = txtCodigoSugerencia.getText().toString().trim();
                String descripcion = txtDescripcionSugerencia.getText().toString().trim();

                // Validar que todos los campos estén llenos
                if (nombre.isEmpty() || email.isEmpty() || codigo.isEmpty() || descripcion.isEmpty()) {
                    Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar si el código de la parada o bus es válido
                if (!codigosValidos.contains(codigo)) {
                    Toast.makeText(getContext(), "Código no reconocido", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Enviar los datos si todo es válido
                new EnviarSugerencia().execute(nombre, email, codigo, descripcion);
            }
        });

        return r;
    }

    // AsyncTask para enviar los datos al servidor
    private class EnviarSugerencia extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://192.168.1.2:8012/crud_metgo/insert_sugerencia.php"); // Usa 10.0.2.2 en el emulador
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                // Parámetros a enviar
                String data = "nombre=" + URLEncoder.encode(params[0], "UTF-8") +
                        "&email=" + URLEncoder.encode(params[1], "UTF-8") +
                        "&codigo=" + URLEncoder.encode(params[2], "UTF-8") +
                        "&descripcion=" + URLEncoder.encode(params[3], "UTF-8");

                // Escribir los datos en el servidor
                OutputStream os = conn.getOutputStream();
                os.write(data.getBytes());
                os.flush();
                os.close();

                // Leer la respuesta del servidor
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    return "Error de conexión: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();

            // Limpiar los campos si el mensaje indica éxito
            if (result.equals("Enviado exitosamente")) {
                txtNombreSugerencia.setText("");
                txtEmailSugerencia.setText("");
                txtCodigoSugerencia.setText("");
                txtDescripcionSugerencia.setText("");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
