package com.example.mclauncher.data;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VersionApiClient {
    private static final String MANIFEST_URL = "https://piston-meta.mojang.com/mc/game/version_manifest_v2.json";

    public List<String> fetchReleaseVersions() throws Exception {
        List<String> versions = new ArrayList<>();
        URL url = new URL(MANIFEST_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject json = new JSONObject(response.toString());
        JSONArray versionsArray = json.getJSONArray("versions");

        for (int i = 0; i < versionsArray.length(); i++) {
            JSONObject v = versionsArray.getJSONObject(i);
            if ("release".equals(v.getString("type"))) {
                versions.add(v.getString("id"));
            }
        }
        return versions;
    }
}
