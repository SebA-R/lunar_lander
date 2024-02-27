import java.util.Map;
import java.util.HashMap;

/**
 * The Main class that drives the program.
 */
public class Main {

    /**
     * The main method that is executed when the program starts.
     * @param args The command-line arguments.
     */
    public static void main(String[] args){
        // Create a map of characters for terrain, foreground, and background
        Map<String, Character> character = new HashMap<>() {{
            put("terrain", '█');
            put("foreground", '█');
            put("background", '*');
            put("platform", '=');
        }};

        // Create a map of terrain_generations for terrain length, range low, range high, window, and star spread
        Map<String, Integer> terrain_generation = new HashMap<>() {{
            put("terrain_length", 190);
            put("range_low", 1);
            put("range_high", 20);
            put("window", 4);
            put("star_spread", 500);
        }};

        int[] n_platforms = {0, 3, 1, 1, 1};
        int[] size_platforms = {0, 10, 8, 6, 4};

        // Create a new Canvas object with the specified characters and star spread
        Canvas canvas = new Canvas(
                                    character.get("terrain"), 
                                    character.get("foreground"), 
                                    character.get("background"), 
                                    character.get("platform"),
                                    terrain_generation.get("star_spread"),
                                    n_platforms,
                                    size_platforms);

        // Create a new Terrain object with the specified terrain length, range, and window
        Terrain terrain = new Terrain(
                                        terrain_generation.get("terrain_length"), 
                                        new int[]{terrain_generation.get("range_low"), 
                                        terrain_generation.get("range_high")}, 
                                        n_platforms,
                                        size_platforms,
                                        terrain_generation.get("window"));

        // Create the terrain string
        String terrain_string = canvas.createWithPlatforms(terrain.create());
        
        // Print the terrain string
        System.out.println(terrain_string);
    }
}