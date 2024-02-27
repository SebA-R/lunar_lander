import java.util.Random;

public class Terrain {
    private static final Random random = new Random();
    int[] terrain;
    int[] smoothedTerrain;
    int[] range;
    int[] n_platforms;
    int[] size_platforms;
    int window;

    public Terrain(int terrain_length, int[] range, int[] n_platforms, int[] size_platforms, int window){
        this.terrain = new int[terrain_length];
        this.range = range;
        this.n_platforms = n_platforms;
        this.size_platforms = size_platforms;
        this.window = window;
    }

    public int[] create(){
        GenerateTerrain(random.nextInt(range[1]-range[0]+1)+range[0], random.nextInt(range[1]-range[0]+1)+range[0],1, terrain.length);
        SmoothTerrain();
        return smoothedTerrain;
    }

    public void GenerateTerrain(int left, int right, int start, int end) {
        // Base case
        if (end - start < 2) {
            return ;
        }

        // Generate terrain
        int mid = (start + end) / 2;
        terrain[mid] = (left + right) / 2 + random.nextInt(range[1]-range[0]+1)+range[0];

        GenerateTerrain(left, terrain[mid], start, mid);
        GenerateTerrain(terrain[mid], right, mid, end);

    }

    public void SmoothTerrain() {
        // Smooth terrain
        smoothedTerrain = new int[terrain.length];
        for (int i = 0; i < terrain.length; i++) {
            int smoothStart = Math.max(0, i - window);
            int smoothEnd = Math.min(terrain.length - 1, i + window);
            int sum = 0;
            for (int j = smoothStart; j <= smoothEnd; j++) {
                sum += terrain[j];
            }
            smoothedTerrain[i] = sum / (smoothEnd - smoothStart + 1);
        }

        return ;
    }

    
}