package javaroke.recommendation.core.utils.tranformers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.stream.JsonReader;
import javaroke.gui.search.Item;
import javaroke.recommendation.controllers.ControllerConfigLoader;
import com.google.gson.*;

public class SongIdTransformers {


    public static List<Item> changeSongIdToItem(List<String> songList) {
        String SONG_FOLDER = ControllerConfigLoader.get("SONG_FOLDER");

        List<Item> itemList = new ArrayList<>();

        for (String songId : songList) {
            try {
                Path file = Paths.get(SONG_FOLDER, songId + ".json");
                if (!Files.exists(file))
                    continue;

                JsonReader jr =
                        new JsonReader(Files.newBufferedReader(file, StandardCharsets.UTF_8));
                JsonObject jsonObject = JsonParser.parseReader(jr).getAsJsonObject();

                String title = jsonObject.has("title") ? jsonObject.get("title").getAsString()
                        : "Unknown Title";
                String artist = jsonObject.has("artist") ? jsonObject.get("artist").getAsString()
                        : "Unknown Artist";
                String id =
                        jsonObject.has("id") ? jsonObject.get("id").getAsString() : "Unknown id";

                itemList.add(new Item(id, title, artist));
            } catch (Exception e) {
                e.printStackTrace(); // It's good practice to log or print errors during parsing
            }
        }

        return itemList;
    }

}
