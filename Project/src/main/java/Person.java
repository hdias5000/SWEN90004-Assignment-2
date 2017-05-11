import java.util.Random;

/**
 * Created by Jack on 3/5/2017.
 */
public class Person {
    Board board;
    int grain;
    int metabolism;
    int age;
    int maxAge;
    int vision;

    public Person(Board board, int grain, int metabolism, int maxAge) {
        this.board = board;
        this.grain = grain;
        this.metabolism = metabolism;
        // random age between 0 and max age
        this.age = new Random().nextInt(maxAge);
        this.vision = 1 + new Random().nextInt(Constant.MAX_VISION);
    }

    /**
     * Get the amount of grain the person currently holds
     * @return amount of grain
     */
    public int getGrain() {
        return grain;
    }

    /**
     *
     * @param amount
     */
    public void addGrain(int amount) {
        grain += amount;
    }

    /**
     * Get the age of the person
     * @return age of the person
     */
    public int getAge() {
        return age;
    }

    /**
     * Find the next point to move to
     * @return next point to move to
     */
    Point getNextPos(Point at) {
        // TODO ask Board about patches and decide where to go
    	Patch patch;
    	int compareGrain;	
    	int xMove, yMove;
    	int x, y;
    	
    	xMove = new Random().nextInt(Constant.MAX_VISION);
    	yMove = new Random().nextInt(Constant.MAX_VISION);
    	
    	patch = board.getPatchAt(at.getX(), at.getY());
    	compareGrain = patch.grain;	
    	
    	x = at.getX() - xMove;
    	y = at.getY() - yMove;
    	
    	// TODO Fix the loop
    	for (int i = 0; i < xMove*2; i++) {
            for (int j = 0; j < yMove*2; j++) {
                	if(y >= Constant.BOARD_HEIGHT) 
                		y = 0;
                	
                	else 
                		y += 1;
                	
                	patch = board.getPatchAt(x, y);
                	
                	if(compareGrain < patch.grain){
                		compareGrain = patch.grain;
                		at.x = x;
                		at.y = y;
                	}
            }
            
            if(x >= Constant.BOARD_WIDTH) x = 0;
            else x++;
    	}
        return at;
    }

    /**
     * Move, eat, age and die (if out of grain)
     */
    void moveEatAgeDie(Point to) {
        // move
        board.put(this, to.getX(), to.getY());

        // eat
        grain -= metabolism;

        // age
        age += 1;

        // die
        if (age > maxAge || grain <= 0) {
            board.remove(this);
        }
    }

//    /**
//     * Perform action for one tick
//     */
//    public void tick() {
//        Point p = getNextPos();
//        harvest();
//        moveEatAgeDie(p);
//    }
}
