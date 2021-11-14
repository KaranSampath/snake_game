import java.util.Random;

/**
 * Used to Generate new Random Locations for Food
 */
public class RandomNumberGenerator {

    Random r;

    public RandomNumberGenerator() {
        r = new Random();
    }

    public int next(int bound) {
        return r.nextInt(bound);
    }

}