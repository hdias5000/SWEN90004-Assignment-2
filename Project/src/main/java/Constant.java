/**
 * Store constants for the simulation
 * Created by Jack on 5/5/2017.
 */
public class Constant {
	// the maximum amount of grain which a patch can have
	public final static int MAX_GRAIN = 50;

	// the width of board (world space)
	public static final int BOARD_WIDTH = 50;

	// the height of board (world space)
	public static final int BOARD_HEIGHT = 50;

	// extension features below

	public static final boolean WEALTH_INHERITANCE_ENABLED = false;
	public static final double WEALTH_INHERITANCE = 0.5;

	// if set to true, patch growth rate will be a proportion of its maximum grain,
	// and absolute growth rate setting will be useless
	public static final boolean PROPORTIONAL_GROWTH_ENABLED = false;

	// the proportion of max grain as growth rate
	public static final double PATCH_GROWTH_PROPORTION = 0.2;
	
	// whether to generate all people in the same position
	public static final boolean RANDOM_POSITION_ENABLED = false;

	// whether to apply tax on rich people
	public static final boolean TAXATION_ENABLED = true;
	public static final double TAX_PERCENTAGE = 0.5;

	public static final boolean PROPORTIONAL_METABOLISM_ENABLED = false;
	public static final double METABOLISM_PROPORTION = 0.3;
	public static final int METABOLISM_MIN = 1;

}
