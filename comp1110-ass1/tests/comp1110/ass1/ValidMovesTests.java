package comp1110.ass1;


import org.junit.jupiter.api.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
public class ValidMovesTests {


    private static final String[] VALID_MOVES = new String[]{
            "1: 1-0,3-90 2-4,2-270 3-3,2-90 3-4,0-270 4-2,1-180",
            "2: 4-3,1-90",
            "3: 1-3,4-0 2-3,1-90 4-2,1-180",
            "4: 2-3,3-270 2-3,3-90 4-0,1-0 4-2,1-90 4-2,1-180",
            "8: 1-0,1-90 2-0,3-90 4-3,0-90",
            "15: 1-0,3-90 4-1,2-0",
            "23: 1-1,0-180 1-2,1-0 4-4,3-180",
            "29: 4-3,3-270",
            "57: 1-0,3-90 4-2,1-180"
    };

    private static final String[] INVALID_MOVES = new String[]{
            "1: 1-0,0-0 1-0,0-270 1-0,0-180 1-0,0-90 1-0,1-0 1-0,1-270 1-0,1-180 1-0,1-90 1-0,2-0 1-0,2-270 1-0,2-180" +
                    " 1-0,2-90 1-0,3-0 1-0,3-270 1-0,3-180 1-0,4-0 1-0,4-270 1-0,4-180 1-0,4-90 1-1,0-0 1-1,0-270 " +
                    "1-1,0-180 1-1,0-90 1-1,1-0 1-1,1-270 1-1,1-180 1-1,1-90 1-1,2-0 1-1,2-270 1-1,2-180 1-1,2-90 1-1,3-0" +
                    " 1-1,3-270 1-1,3-180 1-1,3-90 1-1,4-0 1-1,4-270 1-1,4-180 1-1,4-90 1-2,0-0 1-2,0-270 1-2,0-180 " +
                    "1-2,0-90 1-2,1-0 1-2,1-270 1-2,1-180 1-2,1-90 1-2,2-0 1-2,2-270 1-2,2-180 1-2,2-90 1-2,3-0 1-2,3-270" +
                    " 1-2,3-180 1-2,3-90 1-2,4-0 1-2,4-270 1-2,4-180 1-2,4-90 1-3,0-0 1-3,0-270 1-3,0-180 1-3,0-90 1-3,1-0" +
                    " 1-3,1-270 1-3,1-180 1-3,1-90 1-3,2-0 1-3,2-270 1-3,2-180 1-3,2-90 1-3,3-0 1-3,3-270 1-3,3-180" +
                    " 1-3,3-90 1-3,4-0 1-3,4-270 1-3,4-180 1-3,4-90 1-4,0-0 1-4,0-270 1-4,0-180 1-4,0-90 1-4,1-0 1-4,1-270" +
                    " 1-4,1-180 1-4,1-90 1-4,2-0 1-4,2-270 1-4,2-180 1-4,2-90 1-4,3-0 1-4,3-270 1-4,3-180 1-4,3-90 1-4,4-0" +
                    " 1-4,4-270 1-4,4-180 1-4,4-90",
            "2: 4-2,4-90 2-4,1-180 2-4,1-90 2-4,2-0 2-4,2-180",
            "3: 2-3,4-180 2-3,2-90 4-2,4-180 3-1,4-180 3-1,4-90 3-2,0-0 3-2,0-270",
            "4: 1-0,0-0 1-0,0-270 1-0,0-180 1-0,0-90 1-0,1-0 1-0,1-270 1-0,1-180 1-0,1-90 1-0,2-0 1-0,2-270 1-0,2-180 " +
                    "1-0,2-90 1-0,3-0 1-0,3-270 1-0,3-180 1-0,3-90 1-0,4-0 1-0,4-270 1-0,4-180 1-0,4-90 1-1,0-0 1-1,0-270" +
                    " 1-1,0-180 1-1,0-90 1-1,1-0 1-1,1-270 1-1,1-180 1-1,1-90 1-1,2-0 1-1,2-270 1-1,2-180 1-1,2-90" +
                    " 1-1,3-0 1-1,3-270 1-1,3-180 1-1,3-90 1-1,4-0 1-1,4-270 1-1,4-180 1-1,4-90 1-2,0-0 1-2,0-270" +
                    " 1-2,0-180 1-2,0-90 1-2,1-0 1-2,1-270 1-2,1-180 1-2,1-90 1-2,2-0 1-2,2-270 1-2,2-180 1-2,2-90" +
                    " 1-2,3-0 1-2,3-270 1-2,3-180 1-2,3-90 1-2,4-0 1-2,4-270 1-2,4-180 1-2,4-90 1-3,0-0 1-3,0-270 1-3,0-180" +
                    " 1-3,0-90 1-3,1-0 1-3,1-270 1-3,1-180 1-3,1-90 1-3,2-0 1-3,2-270 1-3,2-180 1-3,2-90 1-3,3-0 1-3,3-270" +
                    " 1-3,3-180 1-3,3-90 1-3,4-0 1-3,4-270 1-3,4-180 1-3,4-90 1-4,0-0 1-4,0-270 1-4,0-180 1-4,0-90 1-4,1-0" +
                    " 1-4,1-270 1-4,1-180 1-4,1-90 1-4,2-0 1-4,2-270 1-4,2-180 1-4,2-90 1-4,3-0 1-4,3-270 1-4,3-180" +
                    " 1-4,3-90 1-4,4-0 1-4,4-270 1-4,4-180 1-4,4-90",
            "8: 1-0,1-180 2-0,3-180 4-0,0-90 2-0,1-0 2-0,1-270 2-0,1-180 2-0,1-90 2-0,2-0 2-0,2-270 2-0,2-180",
            "15: 2-0,1-0 4-0,1-90 3-3,2-180 3-3,2-90 3-3,3-0 3-3,3-270 3-3,3-180 3-3,3-90 3-3,4-0 3-3,4-270 3-3,4-180 3-3,4-90",
            "23: 3-0,0-180 1-2,3-0 4-4,1-180 1-0,1-0 1-0,1-270 1-0,1-180 1-0,1-90 1-0,2-0 1-0,2-270",
            "29: 4-1,3-180 2-4,3-180 2-4,3-90 2-4,4-0 2-4,4-270 2-4,4-180 2-4,4-90",
            "57: 1-1,3-0 4-1,1-180 3-0,0-270 3-0,0-180 3-0,0-90 3-0,1-0 3-0,1-270 3-0,1-180 3-0,1-90 3-0,2-0 3-0,2-270 3-0,2-180"
    };

    public static class ChallengeMoves {
        final Challenge challenge;
        final Transform[][] validMovesForChallenge;

        ChallengeMoves(Challenge challenge,Transform[][] validMovesForChallenge) {
            this.challenge = challenge;
            this.validMovesForChallenge = validMovesForChallenge;
        }
    }
    ChallengeMoves[] allValidMoves;
    ChallengeMoves[] allInvalidMoves;


    @BeforeAll
    public void decodeMoveUnordered(){
        allValidMoves = decodeMoveUnordered(VALID_MOVES,CatsAndBoxes.MOVES_UPPER_BOUND);
        allInvalidMoves = decodeMoveUnordered(INVALID_MOVES,1000);
    }

    private static ChallengeMoves[] decodeMoveUnordered(String[] challengesMovesEncoded, int movesUpperBound) {
        ChallengeMoves[] challengesMovesDecoded = new ChallengeMoves[challengesMovesEncoded.length];
        int challMovesIndex = 0;
        for (String validMovesForChallenge : challengesMovesEncoded) {
            int[] indexTracker = new int[CatsAndBoxes.NUMBER_OF_PIECES];
            String[] colonSeperated = validMovesForChallenge.split(":");
            int challengeNum = Integer.parseInt(colonSeperated[0]);
            Challenge challenge = Challenge.challengeFromNumber(challengeNum);
            String[] pieceTransformsEncoded = colonSeperated[1].substring(1).split(" ");
            challengesMovesDecoded[challMovesIndex] = new ChallengeMoves(challenge,
                    new Transform[CatsAndBoxes.NUMBER_OF_PIECES][movesUpperBound]);
            for (String encodedPieceTransform : pieceTransformsEncoded) {
                int pieceNum = Integer.parseInt(String.valueOf(encodedPieceTransform.charAt(0)));
                String encodedTransform = encodedPieceTransform.substring(2);
                Transform transform = Transform.constructFromString(encodedTransform);
                challengesMovesDecoded[challMovesIndex].validMovesForChallenge[pieceNum-1][indexTracker[pieceNum-1]] =
                        transform;
                indexTracker[pieceNum-1] += 1;
            }
            challMovesIndex += 1;
        }
        return challengesMovesDecoded;
    }


    // simple and advanced tests may not correspond to difficulty of implementation
    @Test
    public void isMoveValidTestSimple() {
        ChallengeMoves[] simpleValidMoves = new ChallengeMoves[1];
        ChallengeMoves[] simpleInvalidMoves = new ChallengeMoves[1];
        System.arraycopy(allValidMoves,0,simpleValidMoves,0,simpleValidMoves.length);
        System.arraycopy(allInvalidMoves,0,simpleInvalidMoves,0,simpleInvalidMoves.length);
        isMoveValidTestFalseNegative(simpleValidMoves);
        isMoveValidTestFalsePositive(simpleInvalidMoves);
    }

    @Test
    public void isMoveValidTestAdvanced() {
        ChallengeMoves[] advancedValidMoves = new ChallengeMoves[VALID_MOVES.length-1];
        ChallengeMoves[] advancedInvalidMoves = new ChallengeMoves[INVALID_MOVES.length-1];
        System.arraycopy(allValidMoves,1,advancedValidMoves,0,advancedValidMoves.length);
        System.arraycopy(allInvalidMoves,1,advancedInvalidMoves,0,advancedInvalidMoves.length);
        isMoveValidTestFalseNegative(advancedValidMoves);
        isMoveValidTestFalsePositive(advancedInvalidMoves);
    }




    private void isMoveValidTestFalseNegative(ChallengeMoves[] validMoves) {
        for (ChallengeMoves challengeMoves : validMoves) {
            Challenge challenge = challengeMoves.challenge;
            assert challenge != null;
            CatsAndBoxes catsAndBoxes = new CatsAndBoxes(challenge);
            for (int pieceNum=1; pieceNum<CatsAndBoxes.NUMBER_OF_PIECES; pieceNum++) {
                Piece piece = catsAndBoxes.getPiece(pieceNum);
                Transform[] validTransformMoves =  challengeMoves.validMovesForChallenge[pieceNum-1];
                for (Transform validMove : validTransformMoves) {
                    if (validMove == null) {
                        break;
                    }
                    assertTrue(catsAndBoxes.isMoveValid(piece,validMove), "Expected " +
                            "move to be valid but was not:\n   piece: " + piece + "\n   move: " + validMove);
                }
            }
        }
    }

    private void isMoveValidTestFalsePositive(ChallengeMoves[] invalidMoves) {
        for (ChallengeMoves challengeMoves : invalidMoves) {
            Challenge challenge = challengeMoves.challenge;
            assert challenge != null;
            CatsAndBoxes catsAndBoxes = new CatsAndBoxes(challenge);
            for (int pieceNum=1; pieceNum<CatsAndBoxes.NUMBER_OF_PIECES; pieceNum++) {
                Piece piece = catsAndBoxes.getPiece(pieceNum);
                Transform[] invalidTransformMoves =  challengeMoves.validMovesForChallenge[pieceNum-1];
                for (Transform invalidMove : invalidTransformMoves) {
                    if (invalidMove == null) {
                        break;
                    }
                    assertFalse(catsAndBoxes.isMoveValid(piece,invalidMove), "Expected " +
                            "move to be invalid but was not:\n   piece: " + piece + "\n   move: " + invalidMove);
                }
            }
        }
    }


    @Test
    void allValidMovesForPieceTest() {
        for (ChallengeMoves challengeMoves : allValidMoves) {
            Challenge challenge = challengeMoves.challenge;
            assert challenge != null;
            CatsAndBoxes catsAndBoxes = new CatsAndBoxes(challenge);
            for (int pieceNum=1; pieceNum<CatsAndBoxes.NUMBER_OF_PIECES; pieceNum++) {
                Transform[] allValidMovesActual  = catsAndBoxes.findAllValidMovesForPiece(catsAndBoxes.getPiece(pieceNum));
                Transform[] allValidMovesExpected =  challengeMoves.validMovesForChallenge[pieceNum-1];
                compareMoves(allValidMovesExpected,allValidMovesActual,challenge.getChallengeNumber(),pieceNum);
            }
        }
    }


    @Test
    void allValidMovesTest() {
        for (ChallengeMoves challengeMoves : allValidMoves) {
            Challenge challenge = challengeMoves.challenge;
            assert challenge != null;
            CatsAndBoxes catsAndBoxes = new CatsAndBoxes(challenge);
            Transform[][] allValidMovesExpected = challengeMoves.validMovesForChallenge;
            Transform[][] allValidMovesActual  = catsAndBoxes.findAllValidMoves();
            compareMoves(allValidMovesExpected,allValidMovesActual,challenge.getChallengeNumber());
        }
    }


    private void compareMoves(Transform[][] movesExpected, Transform[][] movesActual, int challengeNum) {
        for (int pieceNum=1; pieceNum<CatsAndBoxes.NUMBER_OF_PIECES; pieceNum++) {
            compareMoves(movesExpected[pieceNum-1],movesActual[pieceNum-1],challengeNum,pieceNum);
        }
    }

    private void compareMoves(Transform[] movesExpected, Transform[] movesActual, int challengeNum, int pieceNum) {
        Set<Transform> pieceMovesExpected = createSet(movesExpected);
        Set<Transform> pieceMovesActual = createSet(movesActual);
        for (Transform move : pieceMovesExpected) {
            assertTrue(pieceMovesActual.contains(move),"For challenge " + challengeNum + ": \n   For piece "
                    + pieceNum + ": Expected transform representing move: " + move + " but wasn't found. \n" +
                    "      Expected moves: " + pieceMovesExpected + "\n      Actual moves" + pieceMovesActual);
        }
        if (pieceMovesActual.size() > pieceMovesExpected.size()) {
            fail("For challenge " + challengeNum + ": \n   Invalid moves found for piece " + pieceNum +
                    ":\n      Expected transforms: " + pieceMovesExpected + "\n      Actual transforms" + pieceMovesActual);
        }
    }

    private Set<Transform> createSet(Transform[] movesArray){
        Set<Transform> moves = new HashSet<>();
        for (Transform move : movesArray) {
            if (move == null) {
                break;
            }
            moves.add(move);
        }
        return moves;
    }



}
