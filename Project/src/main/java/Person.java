import java.util.Random;

/**
 * Created by Jack on 3/5/2017.
 */
public class Person {
	Arguments args;
    Board board;
    int grain;
    int metabolism;
    int age;
    int maxAge;
    int vision;

    public Person(Arguments args, Board board, int grain, int metabolism, int maxAge) {
        this.args = args;
    	this.board = board;
        this.grain = grain;
        this.metabolism = metabolism;
        // random age between 0 and max age
        this.age = new Random().nextInt(maxAge);
        this.maxAge = maxAge;
        // random vision between 0 and max vision
        this.vision = 1 + new Random().nextInt(args.max_vision);
    }

    static Person makeRandom(Arguments args, Random random, Board board) {
        int metabolism = 1 + random.nextInt(args.metabolism_max);
        int wealth = metabolism + random.nextInt(50);
        int lifeExpectancy = args.life_expentancy_min
                + random.nextInt(args.life_expentancy_max + 1);
        Person person = new Person(args, board, wealth, metabolism, lifeExpectancy);
        return person;
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
    Point getNextPos() {
        Point at = board.getPosition(this);
        // TODO ask Board about patches and decide where to go
    	Patch patch;
    	int compareGrain;	
    	
    	patch = board.getPatchAt(at.getX(), at.getY());
    	
    	int x_prev = at.getX() - vision < 0 ? (at.getX() - vision + 1)+50  : at.getX() - vision;
        int x_next = at.getX() + vision >= 50 ? (at.getX() + vision)-50 : at.getX() + vision;
        int y_prev = at.getY() - vision < 0 ? (at.getY() - vision + 1)+50 : at.getY() - vision;
        int y_next = at.getY() + vision >= 50 ? (at.getY() + vision)-50 : at.getY() + vision;

    	compareGrain = patch.grain;	
    	
    	for (int i = x_prev; i != x_next+1; i++) {
            i = i >= 50 ? 0 : i;
            for (int j = y_prev; j != y_next+1; j++) {
            		j = j >= 50 ? 0 : j;
            				
                	patch = board.getPatchAt(i, j);
                	
                	if(compareGrain < patch.grain){
                		compareGrain = patch.grain;
                		at.x = i;
                		at.y = j;
                	}
            }
    	}   	
        return at;
    }

    /**
     * Move, eat, age and die (if out of grain)
     */
    void moveEatAgeDie(Point to) {
        // move
        board.move(this, to.getX(), to.getY());

        // eat
        grain -= metabolism;

        // age
        age += 1;

        // die (and produce offspring)
        if (age > maxAge || grain <= 0) {
            board.remove(this);
            board.put(makeRandom(args, new Random(), board), to.getX(), to.getY());
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
