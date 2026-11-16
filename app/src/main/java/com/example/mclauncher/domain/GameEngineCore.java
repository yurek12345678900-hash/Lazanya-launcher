package com.example.mclauncher.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameEngineCore {

    public static class OfflineProfile {
        public String username;
        public String uuid;
        public String accessToken;

        public OfflineProfile(String rawUsername) {
            this.username = rawUsername.replaceAll("[^a-zA-Z0-9_]", "");
            if (this.username.isEmpty()) {
                this.username = "Player_" + (int)(Math.random() * 1000);
            }
            this.uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.username).getBytes())
                            .toString().replace("-", "");
            this.accessToken = "0";
        }
    }

    public static List<String> buildLaunchArguments(
            String javaPath,
            String gameDir,
            String versionId,
            OfflineProfile profile,
            int memoryMB) {
        
        List<String> args = new ArrayList<>();
        
        args.add(javaPath);
        args.add("-Xmx" + memoryMB + "M");
        args.add("-Xms" + (memoryMB / 2) + "M");
        
        args.add("-Dorg.lwjgl.opengl.libname=libgl4es.so");
        
        args.add("-cp");
        args.add(gameDir + "/versions/" + versionId + "/" + versionId + ".jar");
        
        args.add("net.minecraft.client.main.Main");
        
        args.add("--username"); args.add(profile.username);
        args.add("--version"); args.add(versionId);
        args.add("--gameDir"); args.add(gameDir);
        args.add("--assetsDir"); args.add(gameDir + "/assets");
        args.add("--uuid"); args.add(profile.uuid);
        args.add("--accessToken"); args.add(profile.accessToken);
        args.add("--userType"); args.add("legacy");

        return args;
    }
}
