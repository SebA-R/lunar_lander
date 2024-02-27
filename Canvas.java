import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Canvas {
    private static final Random random = new Random();
    char terrain_char = '♟';
    char background_char = '*';
    char foreground_char = '♜';
    char platform_char = '_';
    int star_spread = 500;
    int[] n_platforms;
    int[] size_platforms;

    public Canvas(char terrain_char, char foreground_char, char background_char, char platform_char, int star_spread, int[] n_platforms, int[] size_platforms) {
        this.terrain_char = terrain_char;
        this.background_char = background_char;
        this.foreground_char = foreground_char;
        this.platform_char = platform_char;
        this.star_spread = star_spread;
        this.n_platforms = n_platforms; //{0, 1, 1, 1, 1}
        this.size_platforms = size_platforms; //{0, 3, 3, 2, 1}
    }
    
    public String create(int[] terrain){
        int max_height = Arrays.stream(terrain).max().getAsInt();
        int length = terrain.length;
        StringBuilder[] canvas = new StringBuilder[length];
        StringBuilder stringCanvas = new StringBuilder();

        for (int x = 0; x < length; x++) {
            canvas[x] = new StringBuilder();

            for (int back = 0; back < terrain[x]; back++) {
                if (random.nextInt(star_spread) == 0) {
                    canvas[x].append(background_char);
                } else {
                    canvas[x].append(' ');
                }
            }
            canvas[x].append(terrain_char);
            for (int front = terrain[x] + 1; front <= max_height; front++) {
                canvas[x].append(foreground_char);
            }
        }

        for (int j=0; j<max_height; j++){
            for (int k=0; k<length; k++){
                stringCanvas.append(canvas[k].charAt(j));
            }
            stringCanvas.append("\n"); 
        }

        return stringCanvas.toString();
    }

    public String createWithPlatforms(int[] terrain){
        int max_height = Arrays.stream(terrain).max().getAsInt();
        int length = terrain.length;
        StringBuilder[] canvas = new StringBuilder[length];
        StringBuilder stringCanvas = new StringBuilder();

        // Create a list to store the platforms
        List<Platform> platforms = new ArrayList<>();

        // Create platforms
        for (int i = 0; i < n_platforms.length; i++) {
            for (int j = 0; j < n_platforms[i]; j++) {
                int platform_start;
                int platform_end;
                boolean overlaps;
                do {
                    platform_start = random.nextInt(length - size_platforms[i]);
                    platform_end = platform_start + size_platforms[i];
                    overlaps = false;
                    for (Platform platform : platforms) {
                        if ((platform_start >= platform.getStart() && platform_start < platform.getEnd()) ||
                            (platform_end > platform.getStart() && platform_end <= platform.getEnd()) ||
                            (platform_start < platform.getStart() && platform_end > platform.getEnd())) {
                            overlaps = true;
                            break;
                        }
                    }
                } while (overlaps);
                // Calculate the average height of the left and right neighbors
                int platform_height = (terrain[Math.max(0, platform_start - 1)] + terrain[Math.min(length - 1, platform_end)]) / 2;
                for (int k = platform_start; k < platform_end; k++) {
                    terrain[k] = platform_height;
                }
                // Add the platform to the list
                platforms.add(new Platform(platform_start, platform_end, platform_height));
            }
        }

        for (int x = 0; x < length; x++) {
            canvas[x] = new StringBuilder();

            for (int back = 0; back < terrain[x]; back++) {
                // Randomly place a star in the background
                if (random.nextInt(star_spread) == 0) {
                    canvas[x].append(background_char);
                } else {
                    canvas[x].append(' ');
                }
            }
            // Determine whether to append a platform character or a terrain character
            char charToAppend = terrain_char;
            for (Platform platform : platforms) {
                if (x >= platform.getStart() && x < platform.getEnd()) {
                    charToAppend = (terrain[x] == platform.getHeight()) ? platform_char : terrain_char;
                    break;
                }
            }
            canvas[x].append(charToAppend);
            for (int front = terrain[x] + 1; front <= max_height; front++) {
                canvas[x].append(foreground_char);
            }
        }

        for (int j=0; j<max_height; j++){
            for (int k=0; k<length; k++){
                stringCanvas.append(canvas[k].charAt(j));
            }
            stringCanvas.append("\n"); 
        }

        return stringCanvas.toString();
    }
    

}