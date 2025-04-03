package comp1110.ass1;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsSolutionTest {

    private static final String[] CHALLENGE_SOLUTIONS = new String[]{
            "1: 1-0,3-90 2-3,4-0 3-4,0-270 4-2,1-180",
            "2: 4-3,1-90 1-3,4-0 2-1,2-270 3-0,0-180",
            "3: 2-3,1-90 1-4,3-270 4-1,3-0 3-0,0-180",
            "30: 4-0,3-0 1-3,3-180 2-4,1-270 4-2,2-270 1-2,4-0 3-0,3-90",
            "60: 4-3,3-270 3-4,4-0 1-0,3-90 2-0,3-90 2-2,2-90 " +
                    "4-2,1-0 1-2,3-0 2-0,3-90 3-0,0-180 1-2,4-0 " +
                    "4-4,2-180 3-1,0-180 2-1,2-0 1-1,4-0 4-2,3-0 " +
                    "1-4,1-270 2-0,3-90 4-2,2-90 3-4,4-0 1-2,1-0 " +
                    "2-4,1-270 4-2,3-180 1-2,1-180 3-0,0-180 4-0,3-0 " +
                    "1-3,3-180 2-3,1-90 4-1,1-90 1-1,4-0 3-4,3-0 " +
                    "4-2,1-180 1-0,3-90 3-4,4-0 2-4,1-270"
    };

    private static final String[] NON_CHALLENGE_SOLUTIONS = new String[]{
            "1: 1-0,3-90 2-3,4-0 3-3,0-270 4-2,1-180",
            "2: 4-3,1-90 1-3,4-0 2-1,2-270",
            "3: 2-3,1-90 1-4,3-270 4-1,3-0",
            "30: 4-0,3-0 1-3,3-180 2-4,1-270 4-2,2-270 1-2,4-0",
            "60: 4-3,3-270 3-4,4-0 1-0,3-90 2-0,3-90 2-2,2-90 " +
                    "4-2,1-0 1-2,3-0 2-0,3-90 3-0,0-180 1-2,4-0 " +
                    "4-4,2-180 3-1,0-180 2-1,2-0 1-1,4-0 4-2,3-0 " +
                    "1-4,1-270 2-0,3-90 4-2,2-90 3-4,4-0 1-2,1-0 " +
                    "2-4,1-270 4-2,3-180 1-2,1-180 3-0,0-180 4-0,3-0 " +
                    "1-3,3-180 2-3,1-90 4-1,1-90 1-1,4-0 3-4,3-0 " +
                    "4-2,1-180 1-0,3-90 3-4,4-0"
    };

    public static class PieceMove {
        final int pieceNum;
        final Transform move;

        public PieceMove(int pieceNum, Transform move) {
            this.pieceNum = pieceNum;
            this.move = move;
        }
    }

    public static class ChallengeSolMoves {
        final Challenge challenge;
        final PieceMove[] pieceMoves;

        public ChallengeSolMoves(Challenge challenge, PieceMove[] pieceMoves) {
            this.challenge = challenge;
            this.pieceMoves = pieceMoves;
        }

    }

    private static ChallengeSolMoves[] decodeMovesOrdered(String[] challengesSolEncoded, int movesUpperBound) {
        ChallengeSolMoves[] challengesSolMovesDecoded = new ChallengeSolMoves[challengesSolEncoded.length];
        int challMovesIndex = 0;
        for (String solMovesForChallenge : challengesSolEncoded) {
            String[] colonSeperated = solMovesForChallenge.split(":");
            int challengeNum = Integer.parseInt(colonSeperated[0]);
            Challenge challenge = Challenge.challengeFromNumber(challengeNum);
            String[] pieceTransformsEncoded = colonSeperated[1].substring(1).split(" ");
            PieceMove[] pieceMoves = new PieceMove[movesUpperBound];
            int pieceMovesIndex = 0;
            for (String encodedPieceTransform : pieceTransformsEncoded) {
                int pieceNum = Integer.parseInt(String.valueOf(encodedPieceTransform.charAt(0)));
                String encodedTransform = encodedPieceTransform.substring(2);
                Transform transform = Transform.constructFromString(encodedTransform);
                pieceMoves[pieceMovesIndex] = new PieceMove(pieceNum,transform);;
                pieceMovesIndex++;
            }
            challengesSolMovesDecoded[challMovesIndex] = new ChallengeSolMoves(challenge,pieceMoves);
            challMovesIndex += 1;
        }
        return challengesSolMovesDecoded;
    }

    ChallengeSolMoves[] movesToSolution;

    // simple, medium and advanced tests may not correspond to difficulty of implementation
    @Test
    public void testSimple() {
        String[] simpleSolution = new String[1];
        String[] simpleNonSolution = new String[1];
        System.arraycopy(CHALLENGE_SOLUTIONS,0,simpleSolution,0,simpleSolution.length);
        System.arraycopy(NON_CHALLENGE_SOLUTIONS,0,simpleNonSolution,0,simpleNonSolution.length);
        falseNegativesTest(simpleSolution);
        falsePositivesTest(simpleNonSolution);
    }

    @Test void testMedium() {
        String[] simpleSolution = new String[2];
        String[] simpleNonSolution = new String[2];
        System.arraycopy(CHALLENGE_SOLUTIONS,1,simpleSolution,0,simpleSolution.length);
        System.arraycopy(NON_CHALLENGE_SOLUTIONS,1,simpleNonSolution,0,simpleNonSolution.length);
        falseNegativesTest(simpleSolution);
        falsePositivesTest(simpleNonSolution);
    }

    @Test void testAdvanced() {
        String[] simpleSolution = new String[2];
        String[] simpleNonSolution = new String[2];
        System.arraycopy(CHALLENGE_SOLUTIONS,3,simpleSolution,0,simpleSolution.length);
        System.arraycopy(NON_CHALLENGE_SOLUTIONS,3,simpleNonSolution,0,simpleNonSolution.length);
        falseNegativesTest(simpleSolution);
        falsePositivesTest(simpleNonSolution);
    }



    private void falseNegativesTest(String[] challengeSolutions) {
        movesToSolution = decodeMovesOrdered(challengeSolutions,40);
        for (var challengemoves : movesToSolution) {
            Challenge challenge = challengemoves.challenge;
            CatsAndBoxes game = new CatsAndBoxes(challenge);
            applyMoves(game,challengemoves);
            assertTrue(game.isSolution(), "Challenge " + challenge.getChallengeNumber() + ": \n  " +
                    " Expected to register as solution but did not! State: \n" + getTransformState(game));
        }
    }


    private void falsePositivesTest(String[] challengeNonSolutions) {
        movesToSolution= decodeMovesOrdered(challengeNonSolutions,40);
        for (var challengemoves : movesToSolution) {
            Challenge challenge = challengemoves.challenge;
            CatsAndBoxes game = new CatsAndBoxes(challenge);
            applyMoves(game,challengemoves);
            assertFalse(game.isSolution(), "Challenge " + challenge.getChallengeNumber() + ": \n  " +
                    " Expected to NOT register as solution but it did! State: \n" + getTransformState(game));
        }
    }

    private String getTransformState(CatsAndBoxes game) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int pieceNum=1; pieceNum<=CatsAndBoxes.NUMBER_OF_PIECES; pieceNum++) {
            Piece piece = game.getPiece(pieceNum);
            stringBuilder.append("   Piece ").append(piece.getId()).append(" transform: ");
            stringBuilder.append(piece.getTransform());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    private void applyMoves(CatsAndBoxes game, ChallengeSolMoves challengeSolMoves) {
        for (PieceMove pieceMove : challengeSolMoves.pieceMoves) {
            if (pieceMove == null) {
                break;
            }
            Transform move = pieceMove.move;
            Piece piece = game.getPiece(pieceMove.pieceNum);
            game.movePiece(piece, move);
        }
    }
}
