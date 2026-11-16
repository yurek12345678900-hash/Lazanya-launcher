package com.example.mclauncher;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mclauncher.domain.GameEngineCore;
import com.example.mclauncher.data.VersionApiClient;

public class MainActivity extends AppCompatActivity {

    private EditText inputUsername;
    private Button btnLaunch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputUsername = findViewById(R.id.inputUsername);
        btnLaunch = findViewById(R.id.btnLaunch);

        btnLaunch.setOnClickListener(v -> {
            String username = inputUsername.getText().toString().trim();
            
            GameEngineCore.OfflineProfile profile = new GameEngineCore.OfflineProfile(username);
            
            Toast.makeText(this, "Giriş yapıldı: " + profile.username, Toast.LENGTH_SHORT).show();
            
            new Thread(() -> {
                try {
                    VersionApiClient client = new VersionApiClient();
                    String firstVersion = client.fetchReleaseVersions().get(0);
                    System.out.println("En son sürüm: " + firstVersion);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });
    }
}
