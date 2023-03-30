package com.strangeone101.amongusfools.amogusfools;

import com.projectkorra.projectkorra.ProjectKorra;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import com.xxmicloxx.NoteBlockAPI.songplayer.SongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

public class SongManager {

    private static Song song;
    private static final File localNBS = new File(ProjectKorra.plugin.getDataFolder(), "Abilities/amongus.nbs");

   static {
        if (!localNBS.exists()) {
            Bukkit.getScheduler().runTaskAsynchronously(ProjectKorra.plugin, () -> {
                downloadNBS();
                song = NBSDecoder.parse(localNBS);
                ProjectKorra.log.info("Successfully downloaded amogus.nbs!");
            });
        } else song = NBSDecoder.parse(localNBS);
    }

    /*private InputStream readFile() {
        try {
            URL url = AmogusFools.class.getClassLoader().getResource("amogus.nbs");
            Enumeration<URL> em = AmogusFools.class.getClassLoader().getResources("com/strangeone101");

            System.out.println("Getting resources");
            while (em.hasMoreElements()) {
                System.out.println(em.nextElement().toString());
            }
            if (url == null) {
                return null;
            }

            URLConnection connection = null;
            try {
                connection = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

    public static void downloadNBS() {
        try {

            // Create a URL for the desired page.
            final URLConnection url = new URL("https://www.dropbox.com/s/m4i88l6itaeulqe/amogus.nbs?dl=1").openConnection();
            url.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            url.setConnectTimeout(3000);


            FileOutputStream outStream = new FileOutputStream(localNBS);
            int bytesRead = -1;
            byte[] buffer = new byte[4096];
            while ((bytesRead = url.getInputStream().read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            outStream.close();
            url.getInputStream().close();
        } catch (final Exception e) {
            ProjectKorra.log.info("Failed to download amogus.nbs file");
            e.printStackTrace();
        }
    }

    public SongPlayer playAt(Location location) {
        PositionSongPlayer songPlayer = new PositionSongPlayer(song);
        songPlayer.setTargetLocation(location);
        songPlayer.setTick((short)0);
        songPlayer.setDistance(50);
        songPlayer.setPlaying(true);
        Bukkit.getOnlinePlayers().forEach(songPlayer::addPlayer);
        return songPlayer;
    }
}
