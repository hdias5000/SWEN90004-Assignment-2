/**
 * Created by Jack on 3/5/2017.
 */
public class Patch {
    int grain;
    int maxGrain;

    public Patch(int grain, int maxGrain) {
        this.grain = grain;
        this.maxGrain = maxGrain;
    }

    public int getGrain() {
        return grain;
    }

    public void addGrain(int amount) {
        if (Constant.MAX_GRAIN > maxGrain) {
            grain += amount;
        }
    }
}
