package com.strangeone101.amongusfools.amogusfools;

import com.projectkorra.projectkorra.ProjectKorra;
import static org.bukkit.Sound.*;
import net.raphimc.noteblocklib.parser.Note;
import net.raphimc.noteblocklib.parser.nbs.NBSParser;
import net.raphimc.noteblocklib.parser.nbs.NBSSong;
import net.raphimc.noteblocklib.player.ISongPlayerCallback;
import net.raphimc.noteblocklib.player.SongPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;

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

    private static NBSSong song;
    private static final File localNBS = new File(ProjectKorra.plugin.getDataFolder(), "Abilities/song.nbs");

   static {
        if (!localNBS.exists()) {
            Bukkit.getScheduler().runTaskAsynchronously(ProjectKorra.plugin, () -> {
                downloadNBS();
                song = NBSParser.parseFile(localNBS);
                //ProjectKorra.log.info("Successfully downloaded amogus.nbs!");
            });
        } else song = NBSParser.parseFile(localNBS);
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

    public static class SongCallback implements ISongPlayerCallback {

        private Location location;
        private static final Sound[] INSTRUMENTS = {
                BLOCK_NOTE_BLOCK_HARP,
                BLOCK_NOTE_BLOCK_BASS,
                BLOCK_NOTE_BLOCK_BASEDRUM,
                BLOCK_NOTE_BLOCK_SNARE,
                BLOCK_NOTE_BLOCK_HAT,
                BLOCK_NOTE_BLOCK_GUITAR,
                BLOCK_NOTE_BLOCK_FLUTE,
                BLOCK_NOTE_BLOCK_BELL,
                BLOCK_NOTE_BLOCK_CHIME,
                BLOCK_NOTE_BLOCK_XYLOPHONE,
                BLOCK_NOTE_BLOCK_IRON_XYLOPHONE,
                BLOCK_NOTE_BLOCK_COW_BELL,
                BLOCK_NOTE_BLOCK_DIDGERIDOO,
                BLOCK_NOTE_BLOCK_BIT,
                BLOCK_NOTE_BLOCK_BANJO,
                BLOCK_NOTE_BLOCK_PLING};


        public SongCallback(Location location) {
            this.location = location;
        }

        public Sound getInstrument(byte b) {
            return INSTRUMENTS[b % 16];
        }

        public float getPitch(byte b) {
            return (float)(Math.pow(2, (b - 45) / 12F));
        }

        @Override
        public void playNote(Note note) {
            this.location.getWorld().playSound(location, getInstrument(note.getInstrument()), 1.0F, getPitch(note.getKey()));
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }
    }

    public SongPlayer playAt(Location location) {
        SongPlayer songPlayer = new SongPlayer(song, new SongCallback(location));
        songPlayer.play();
        return songPlayer;
    }
}
