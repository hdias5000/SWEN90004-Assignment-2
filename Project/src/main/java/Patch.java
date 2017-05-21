/**
 * Patch in the simulation
 * Created by Jack on 3/5/2017.
 */
public class Patch {
    private int grain;
    private int maxGrain;

    /**
     * Construct a new patch
     * @param grain initial amount of grain on the patch
     * @param maxGrain maximum amount of grain on the patch
     */
    public Patch(int grain, int maxGrain) {
        this.grain = grain;
        this.maxGrain = maxGrain;
    }

    /**
     * Get the amount of grain on the patch
     * @return the amount of grain on the patch
     */
    public int getGrain() {
        return grain;
    }

    /**
     * Get the maximum amount of grain on the patch
     * @return the maximum amount of grain on the patch
     */
    public int getMaxGrain() {
        return maxGrain;
    }

    /**
     * Remove all grain on the patch
     * @return the amount of grain removed from patch
     */
    public int removeAll() {
        int amount = grain;
        this.grain = 0;
        return amount;
    }

    /**
     * Add an amount of grain to the patch
     * @param amount amount of grain to be added
     */
    public void addGrain(int amount) {
        if (Constant.MAX_GRAIN > maxGrain) {
            grain += amount;
        }
    }

    @Override
    public String toString() {
        return grain + "/" + maxGrain;
    }
}
