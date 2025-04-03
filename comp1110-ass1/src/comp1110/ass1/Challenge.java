package comp1110.ass1;

import java.util.Random;
import java.util.List;



public class Challenge {
    // The challenge number from game booklet
    private final int challengeNumber;

    // The string encoding of a particular challenge
    private final String layout;

    // Each challenge in the game booklet
    public static final Challenge[] CHALLENGES = new Challenge[]{
            // Starter
            new Challenge(1, "0,0 4,0 2,1 0,3 3,4 1,4-0 3,3-180 3,0-270 0,1-0"),
            new Challenge(2, "0,0 3,1 1,2 2,3 3,4 1,4-0 0,2-90 2,0-180 3,3-270"),
            new Challenge(3, "0,0 3,1 1,3 4,3 3,4 2,3-90 4,1-270 0,4-90 1,2-270"),
            new Challenge(4, "0,0 2,1 4,1 2,3 3,3 3,0-180 4,3-270 0,4-90 2,2-180"),
            new Challenge(5, "0,2 2,2 2,3 4,3 1,4 3,4-0 1,2-270 0,0-180 3,2-270"),
            new Challenge(6, "0,0 2,1 3,2 1,3 4,4 3,3-180 1,4-0 4,0-270 0,1-0"),
            new Challenge(7, "3,0 0,2 2,3 4,3 1,4 3,4-0 1,2-270 0,0-180 3,2-270"),
            new Challenge(8, "3,0 2,2 4,2 0,3 1,4 1,0-180 2,3-0 4,4-0 3,2-270"),
            new Challenge(9, "0,1 4,1 1,3 3,3 3,4 3,0-180 2,3-90 0,4-90 2,1-180"),
            new Challenge(10, "0,0 1,1 0,3 3,3 2,4 3,1-90 4,3-270 2,0-270 2,3-180"),
            new Challenge(11, "1,2 2,2 4,2 1,4 3,4 0,3-90 3,3-180 0,0-180 3,2-270"),
            new Challenge(12, "3,0 0,2 2,2 4,2 1,4 1,1-0 3,2-90 4,4-0 1,2-90"),
            // Junior
            new Challenge(13, "0,0 4,0 2,1 2,3 3,3 1,4-0 4,3-270 3,0-270 1,1-90"),
            new Challenge(14, "0,1 2,1 2,2 4,3 1,4 1,0-180 3,3-270 4,0-270 1,2-90"),
            new Challenge(15, "3,1 4,1 1,2 0,3 3,3 1,4-0 4,3-270 0,0-180 3,2-180"),
            new Challenge(16, "0,1 1,1 4,1 3,3 1,4 3,0-180 3,2-0 4,4-0 1,2-90"),
            new Challenge(17, "0,0 1,1 0,3 4,3 2,4 4,1-270 1,3-270 2,0-270 2,3-0"),
            new Challenge(18, "0,0 2,1 3,1 1,3 2,4 4,3-270 3,0-180 0,4-90 0,1-0"),
            new Challenge(19, "4,1 0,2 1,3 4,3 3,4 3,2-90 1,4-0 2,0-180 0,1-0"),
            new Challenge(20, "0,0 4,0 2,1 2,3 1,4 0,3-90 4,3-270 3,0-270 0,1-0"),
            new Challenge(21, "0,0 2,1 4,1 1,3 4,4 3,0-180 1,1-270 2,4-0 4,3-180"),
            new Challenge(22, "1,1 4,1 2,2 4,3 1,4 0,3-90 3,4-0 0,0-180 4,2-180"),
            new Challenge(23, "2,1 2,2 0,3 4,3 2,4 0,1-90 1,3-270 4,0-270 2,3-0"),
            new Challenge(24, "0,0 4,1 2,2 4,3 1,4 3,0-180 3,3-90 0,1-180 1,2-90"),
            //Expert
            new Challenge(25, "0,1 1,2 4,2 1,4 3,4 0,3-90 3,3-180 1,0-180 3,2-270"),
            new Challenge(26, "0,1 2,1 4,1 3,3 1,4 1,0-180 0,3-90 3,2-90 1,3-0"),
            new Challenge(27, "0,2 2,2 4,2 1,4 3,4 3,1-0 1,2-270 0,0-180 3,2-90"),
            new Challenge(28, "3,0 0,2 2,2 4,2 3,4 1,4-0 3,3-180 0,0-180 3,2-270"),
            new Challenge(29, "4,1 1,2 0,3 3,3 4,3 0,1-90 2,4-0 2,0-180 3,1-90"),
            new Challenge(30, "3,0 4,1 2,2 0,3 2,4 4,3-270 2,1-180 0,0-180 2,3-180"),
            new Challenge(31, "0,1 4,1 1,3 4,3 3,4 2,3-90 1,1-90 0,4-90 2,1-0"),
            new Challenge(32, "0,0 3,1 2,3 4,3 2,4 2,0-180 4,1-270 0,4-90 2,2-180"),
            new Challenge(33, "0,1 3,1 2,3 4,3 2,4 0,3-90 3,0-180 3,4-90 2,1-180"),
            new Challenge(34, "0,0 3,1 0,2 4,2 2,3 1,1-270 1,4-0 2,0-180 4,3-180"),
            new Challenge(35, "0,0 4,0 2,1 1,3 3,4 3,1-90 1,4-0 4,3-0 0,1-0"),
            new Challenge(36, "1,1 2,2 4,2 1,4 3,4 0,3-90 3,3-180 0,0-180 3,2-270"),
            //Master
            new Challenge(37, "0,1 2,1 4,1 3,3 2,4 1,0-180 4,3-270 0,4-90 2,3-270"),
            new Challenge(38, "0,0 1,1 4,1 3,2 1,4 0,3-90 4,3-270 2,0-180 3,3-180"),
            new Challenge(39, "0,1 2,1 1,3 4,3 1,4 4,1-270 0,3-90 1,0-180 2,3-0"),
            new Challenge(40, "0,0 4,1 0,3 3,3 3,4 3,0-180 2,3-270 4,2-270 1,0-90"),
            new Challenge(41, "0,1 3,1 2,2 4,3 1,4 1,0-180 0,3-90 4,0-270 2,3-0"),
            new Challenge(42, "2,0 4,2 0,3 2,3 2,4 0,1-90 1,3-270 4,1-0 4,3-180"),
            new Challenge(43, "0,0 2,2 4,2 0,3 3,4 1,1-0 3,0-180 3,3-90 2,3-180"),
            new Challenge(44, "0,0 3,1 2,2 4,2 1,4 1,1-0 3,0-180 4,4-0 1,2-90"),
            new Challenge(45, "0,0 2,1 4,1 1,3 2,4 3,0-180 4,3-270 0,4-90 0,1-0"),
            new Challenge(46, "0,0 4,1 2,2 3,3 1,4 0,3-90 3,1-90 4,4-0 1,2-270"),
            new Challenge(47, "0,0 3,0 2,1 0,3 4,4 4,1-270 1,1-270 2,4-0 4,3-180"),
            new Challenge(48, "0,0 3,1 0,3 2,3 3,4 1,4-0 3,0-180 4,2-270 1,0-90"),
            //Wizard
            new Challenge(49, "3,0 0,3 1,3 4,3 3,4 4,1-270 0,1-90 2,4-0 1,1-0"),
            new Challenge(50, "2,1 0,3 1,3 4,3 3,4 3,0-180 0,1-90 2,4-0 3,3-270"),
            new Challenge(51, "0,0 2,2 4,2 0,3 1,4 3,0-180 2,3-0 4,4-0 1,0-90"),
            new Challenge(52, "2,0 0,1 4,1 0,3 3,4 1,4-0 3,1-90 4,3-0 2,1-180"),
            new Challenge(53, "1,1 2,1 4,2 0,3 3,4 1,4-0 3,3-0 0,2-90 4,1-180"),
            new Challenge(54, "0,0 3,2 0,3 1,3 3,4 4,3-270 3,1-0 2,4-0 0,1-0"),
            new Challenge(55, "0,1 2,2 1,3 4,3 3,4 3,2-90 3,0-180 0,4-90 2,1-180"),
            new Challenge(56, "0,0 1,1 3,2 4,2 1,4 0,3-90 3,0-180 4,4-0 1,2-0"),
            new Challenge(57, "2,0 2,1 4,1 0,3 3,4 1,4-0 3,3-180 3,2-90 0,1-0"),
            new Challenge(58, "3,0 4,1 0,3 3,3 3,4 2,3-270 0,1-90 4,2-270 2,0-90"),
            new Challenge(59, "3,0 2,2 0,3 4,3 2,4 1,0-180 3,1-180 3,4-90 2,3-180"),
            new Challenge(60, "0,0 2,1 4,1 0,3 4,4 1,2-270 2,0-180 2,4-0 4,3-180")
    };

    /**
     * Constructor for a challenge. It takes a challenge number (used to later
     * identify the challenge) and puzzle layout, denoting the board configuration.
     *
     * @param challengeNumber the challenge number as-per the game booklet
     * @param layout          of the board
     */
    public Challenge(int challengeNumber, String layout) {
        assert challengeNumber >= 1 && challengeNumber <= 60;
        this.challengeNumber = challengeNumber;
        this.layout = layout;
    }

    /**
     * @return the problem number of this challenge
     */
    public int getChallengeNumber() {
        return this.challengeNumber;
    }

    /**
     * @return the string encoding of a particular challenge
     */
    public String getLayout() {
        return this.layout;
    }

    /**
     * //
     * A random challenge of the provided difficulty
     *      * <p>
     *      * Difficulty is defined as:
     *      * 0 "Starter" includes challenge numbers 1 to 12 inclusive
     *      * 1 "Junior" includes challenge numbers 13 to 24 inclusive
     *      * 2 "Expert" includes challenge numbers 25 to 36 inclusive
     *      * 3 "Master" includes challenge numbers 37 to 48 inclusive
     *      * 4 "Wizard includes challenges numbers 49 to 60 inclusive
     *      *
     *      * @param difficulty a number within the range 0 to 4 inclusive, with higher numbers
     *      *                   denoting a harder difficulty
     *      * @return a random challenge of the given difficulty
     */


    public static Challenge randomChallenge(int difficulty) {
        int a = 0;
        int b = 11;
        Random random = new Random();
        int randomNumber = random.nextInt(a, b);

        if (difficulty == 0) {
            return CHALLENGES[randomNumber];
            }

        int c = 12;
        int d = 23;

        int randomNumber1 = random.nextInt(c,d);
        if (difficulty == 1) {
            return CHALLENGES[randomNumber1];
        }

        int e = 24;
        int f = 35;
        int randomNumber2 = random.nextInt(e,f);
        if (difficulty == 2) {
            return CHALLENGES[randomNumber2];
        }

        int g = 36;
        int h = 47;
        int randomNumber3 = random.nextInt(g,h);
        if (difficulty == 3) {
            return CHALLENGES[randomNumber3];
        }

        int i = 48;
        int j = 59;
        int randomNumber4 = random.nextInt(i,j);
        if (difficulty == 4) {
            return CHALLENGES[randomNumber4];
        }

        return CHALLENGES[0]; // FIXME Task 5
        }



    /**
     * @param challengeNumber the challenge number as-per the game booklet
     * @return challenge associated with challenge number
     */
    public static Challenge challengeFromNumber(int challengeNumber) {
        for (Challenge chal : CHALLENGES) {
            if (chal.getChallengeNumber() == challengeNumber) {
                return chal;
            }
        }
        return null;
    }

}
