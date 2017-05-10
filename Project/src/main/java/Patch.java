/**
 * Created by Jack on 3/5/2017.
 */
public class Patch {
    int grain;
    int maxGrain;

    /**
     *
     * @param grain
     * @param maxGrain
     */
    public Patch(int grain, int maxGrain) {
        this.grain = grain;
        this.maxGrain = maxGrain;
    }

    /**
     *
     * @return
     */
    public int getGrain() {
        return grain;
    }

    public int removeGrain() {
        int amount = grain;
        this.grain = 0;
        return amount;
    }

    /**
     *
     * @param amount
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
