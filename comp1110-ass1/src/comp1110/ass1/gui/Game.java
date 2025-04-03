package comp1110.ass1.gui;

import comp1110.ass1.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.Objects;


/**
 * Java class that defines gui for the game.
 *
 * YOU DO NOT NEED TO READ OR UNDERSTAND THIS CLASS TO COMPLETE ASSIGNMENT 1
 *
 * Although you might potentially find it useful for debugging your code.
 *
 * Note: All the code for the gui (and assets like the game instructions) have been
 *   put into this file for the purposes of making the structure of
 *   assignment 1 simpler. This means that this class is significantly longer
 *   than is ideal. You should aim to make your gui class/es in assignment 2
 *   shorter than this one.
 *
 */


public class Game extends Application {

    /*
    The root group is the data structure that holds all the visual assets in
    the JavaFX window. These assets are known as Nodes. You might notice
    that in the `start()` method, we create a Scene and pass this `root` through
    as a parameter. This is because we want everything inside this Group to be
    displayed in the Scene.
            */
    private final Group root = new Group();

    // The width and height of the window
    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;

    // Height and width of each tile
    private static final double Tile_Size = 120;

    // Pixel gap between the grey rectangles that indicates where the tiles are on the board
    private static final double BOARD_TILE_SHADOW_GAP = 20;

    // how much the blue background extends past the tiles
    private static final int BOARD_BORDER = 40;

    // thickness of the boxes that the cats want to go in
    public static double BOX_THICKNESS = 20;

    // The start of the board in the x-direction (ie: x = 0)
    private static final double START_X = 110.0;

    // The start of the board in the y-direction (ie: y = 0)
    private static final double START_Y = 110.0;

    /*
     The base of the filepath for all the assets in the game. This points to
     the "assets" directory within the directory containing this class.
     */
    private static final String URI_BASE = "assets/";



    // Group containing all the assets corresponding to pieces of the board.
    private final Group board = new Group();


    private final double boardWidth = CatsAndBoxes.BOARD_WIDTH*Tile_Size;
    private final double boardHeight = CatsAndBoxes.BOARD_HEIGHT*Tile_Size;

    // The margins used for all visual assets
    private static final int MARGIN_X = 100;
    private static final int MARGIN_Y = 50;

    /*
     Distance to leave from a button to the right - used for setting up
     all the buttons at the bottom of the window.
     */
    private static final double BUTTON_BUFFER = 100.0;

    /*
     Distance to leave from a slider to the right - the slider is a bit
     longer than the buttons, hence the need for differing lengths.
     */
    private static final double SLIDER_BUFFER = 200.0;


    // Group containing all the buttons, sliders and text used in this application.
    private final Group controls = new Group();

    // Group containing the piece assets used in this game
    private final Group pieces = new Group();

    // A slider for how difficult the challenge should be (from 1 to 4)
    private final Slider difficulty = new Slider(0, 4, 0);

    // Instruction text for the game
    private final static String INSTRUCTIONS = """ 
            Try to fit all the cats into boxes!
            The position of the cats cannot be changed.
            When you move a puzzle piece, you are allowed to rotate it and place it anywhere on the board where it fits.
            You can place a piece over a cat and then move it back off later, if needed.
            You can only move one piece at a time.
            If you want the game to suggest a move for you, press the hint button!
            """;

    // Text describing controls in the game
    private final static String CONTROLS = """
            Drag and drop the pieces to move them around the board with mouse left-click.
            Press the 'r' key while dragging a piece to rotate it.
            If you get stuck, press restart to reset the board. If you want a new challenge, press new game!
            """;

    // Text describing which challenge is being played
    private final Text challengeNumber = new Text("Challenge number: ");

    // backend instance for the game - defines the state of the game and game logic
    CatsAndBoxes catsAndBoxes;

    // colours of the different cats
    private final Color[] catColours = new Color[] {
            Color.WHITE,
            Color.BLACK,
            Color.GREY,
            Color.ORANGE,
            Color.SADDLEBROWN
    };

    // Tracks which piece to hint for next
    private int hintPiece = 0;

    // Tracks which move index to hint for next
    private int hintIndex = 0;

    // Stores the hinted piece currently displayed on the board
    private DraggablePiece hintedPiece = null;


    /**
     * A DraggablePiece object is created for every piece on the board (including hints).
     * It defines how the piece looks and how it can be moved.
     */
    class DraggablePiece extends Group {
        final Piece piece;  // the corresponding piece in the backend
        final PieceSegment[] segments; // the tiles making up this piece in the frontend

        double mouseX,mouseY; // these coordinates are useful for drag-and-drop

        // the angle of the gui piece (different from the backend when rotating)
        Angle tempAngle;


        /*
        Piece's last position
        */
        double lastX, lastY;

        public DraggablePiece(Piece piece, boolean isHint) {
            super(); // call Group constructor
            this.piece = piece;
            this.tempAngle = piece.getTransform().getRotation();
            segments = new PieceSegment[piece.getSegmentLength()];
            // calculate where the piece is meant to be
            IntPair[] absolutePositions = piece.getAbsolutePositions();

            // create PieceSegments in the right positions
            for (int i = 0; i< piece.getSegmentLength(); i++) { // relativeTilePositions
                segments[i] = new PieceSegment(isBoxSegment(i), isHint, piece.getId());
                this.getChildren().add(segments[i].getView());
                segments[i].setLocation(absolutePositions[i]);
            }

            // move the piece to the start of the board
            this.setLayoutX(START_X);
            this.setLayoutY(START_Y);

            if (isHint) {
                /*
                 If the piece is a hint, we want to clear it when the player clicks on it, and not allow the player to
                    drag it around.
                 */
                this.setOnMousePressed(event -> {
                    clearHints();
                    this.setVisible(false);
                });
                this.snapToLast();
                return;
            }

            /*
             Event handler for if the user presses the mouse on this
             specific piece.
             */
            this.setOnMousePressed(event -> {

                // Set these values to prepare for drag-and-drop
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();

                root.setOnKeyPressed(keyEvent -> {
                    // rotate if the 'r' key is pressed
                    if (keyEvent.getCode().equals(KeyCode.R)) {
                        tempAngle = Angle.getAngleFromValue((tempAngle.value+90) % 360);
                        Transform currTransform = piece.getTransform();
                        Transform tempTransform = new Transform(currTransform.getTranslation(),tempAngle);
                        // temporarily
                        this.tempSetSegmentPositions(piece.calcNewAbsolutePositions(tempTransform));
                    }
                });
            });

            /*
             Event handler for if the user drags the mouse over this
             specific piece.
             */
            this.setOnMouseDragged(event -> {

                /*
                 Move the piece by the difference in mouse position
                 since the last drag.
                 */
                double diffX = event.getSceneX() - mouseX;
                double diffY = event.getSceneY() - mouseY;
                this.setLayoutX(this.getLayoutX() + diffX);
                this.setLayoutY(this.getLayoutY() + diffY);

                /*
                 Update `mouseX` and `mouseY` and repeat the process.
                 */
                this.mouseX = event.getSceneX();
                this.mouseY = event.getSceneY();
            });


             /*
             Event handler for if the user releases the left mouse button over this
             specific piece.
             */
            this.setOnMouseReleased(event -> {
                root.setOnKeyPressed(keyEvent -> {
                });
                IntPair p = this.getSnapPosition();
                Transform curr = piece.getTransform();
                Transform proposedMove = new Transform(curr.getTranslation().add(p),tempAngle);

                // checking if the move made is a valid move
                if (catsAndBoxes.isMoveValid(piece,proposedMove)) {
                    clearHints();
                    catsAndBoxes.movePiece(piece,proposedMove);
                    syncSegmentPositions();
                    this.setLayoutX(0);
                    this.setLayoutY(0);
                    this.getTransforms().clear();

                    // checking if the current game state is a solution to the problem
                    if (catsAndBoxes.isSolution()) {
                        Alert solved = new Alert(Alert.AlertType.INFORMATION);
                        solved.setTitle("Congratulations!");
                        solved.setHeaderText("You solved the puzzle!");
                        solved.setContentText("The cats are all happy in their boxes!");
                        solved.show();
                    }
                } else {
                    this.getTransforms().clear();
                    this.snapToLast();
                }
            });
            this.snapToLast();

        }


        /**
         * @return the closest position on the board to where this piece
         *         is currently positioned
         */
        public IntPair getSnapPosition() {
            int x = (int) Math.round((this.getLayoutX()) / Tile_Size);
            int y = (int) Math.round((this.getLayoutY()) / Tile_Size);
            return new IntPair(x, y);
        }


        /**
         * Snaps this piece back to its last position which must be valid
         */
        public void snapToLast() {
            this.setLayoutX(this.lastX);
            this.setLayoutY(this.lastY);
            this.syncSegmentPositions();
        }

        /**
         * A method which updates all the attached piece segments. This
         * method is called after the corresponding box has
         * been moved or rotated in some way.
         */
        public void syncSegmentPositions() {
            var positions = this.piece.getAbsolutePositions();
            for (int i = 0; i < this.segments.length; i++) {
                this.segments[i].setLocation(positions[i]);
            }
        }


        /**
         * This method is used when dragging a piece to rotate the piece appropriately.
         *
         * @param absolutePositions
         */
        public void tempSetSegmentPositions(IntPair[] absolutePositions) {
            for (int i = 0; i < this.segments.length; i++) {
                this.segments[i].setLocation(absolutePositions[i]);
            }
        }


        /**
         * calls the corresponding method in Piece
         * @param segmentIndex index of the segment as defined by the relative positions array.
         * @return true if it is a box segment, false otherwise
         */
        private boolean isBoxSegment(int segmentIndex) {
            return piece.indexCorrespondsToBox(segmentIndex);
        }

    }

    /**
     * Object is responsible for displaying a segment of each piece.
     */
    static class PieceSegment {
        // if the tile has a box on it
        public final boolean isBox;

        // if the tile is a hint
        public final boolean isHint;

        // what is displayed (javafx type)
        private final Node view;

        // get the javafx Node object
        public Node getView() {
            return view;
        }

        public PieceSegment(boolean isBox, boolean isHint, int pieceNum) {
            this.isBox = isBox;
            this.isHint = isHint;

            if (isBox && !isHint) {
                // create box segment:
                Rectangle box = new Rectangle((BOX_THICKNESS/2),(BOX_THICKNESS/2),
                        Tile_Size-BOX_THICKNESS,Tile_Size-BOX_THICKNESS);
                box.setFill(Color.TRANSPARENT);
                box.setStrokeWidth(BOX_THICKNESS);
                String path = piecePath(pieceNum);
                Image image = new Image(Objects.requireNonNull(Game.class.getResource(path)).toString());
                ImagePattern pattern = new ImagePattern(image, 0, 0, Tile_Size, Tile_Size, false);
                box.setStroke(pattern);
                Group boxGroup = new Group();
                boxGroup.getChildren().add(box);
                this.view = boxGroup;
            } else {
                // create normal segment
                String path = piecePath(pieceNum);
            /*
             NB: if you want to use assets in your own GUI, this is useful code
             to remember!
             */
                Image image = new Image(Objects.requireNonNull(Game.class.getResource(path)).toString());
                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(true);
                imageView.setFitHeight(Tile_Size);
                imageView.setImage(image);
                this.view = imageView;
            }
        }

        /**
         * @param pieceNum number of the piece (see readme)
         * @return the relative path to each type of piece segment
         */
        public String piecePath(int pieceNum) {
            String strColour = switch (pieceNum){
                case 1 -> "Blue";
                case 2 -> "Green";
                case 3 -> "Pink";
                case 4 -> "Orange";
                default -> throw new IllegalStateException("Unexpected value: " + pieceNum);
            };
            return URI_BASE + "tilePatten" + strColour + (isHint ? "_hint" : "") +".png";
        }

        /**
         * Method sets the location of the current segment (in pixels)
         * @param position absolute board position for which to set
         */
        public void setLocation(IntPair position) {
            this.view.setLayoutX(START_X + (position.getX() * Tile_Size));
            this.view.setLayoutY(START_Y + (position.getY() * Tile_Size));
        }

    }

    /**
     * Removes the existing cats and pieces associated with the current game.
     */
    public void reset() {
        this.pieces.getChildren().clear();
        this.board.getChildren().clear();
        this.clearHints();
    }

    /**
     * Restart the game, using the same challenge.
     */
    public void restart() {
        this.catsAndBoxes = new CatsAndBoxes(this.catsAndBoxes.challenge);
        this.reset();
        this.makeBoard();
        this.makePieces();
        this.clearHints();
    }



    /**
     * A method that interfaces with the backend of this project to create a
     * new conceptual "game".
     */
    private void newGame() {

        /* Generate a new instance of the game Cats and boxes using the
           `CatsAndBoxes` constructor. The difficulty is obtained from the Slider
           provided when playing the game.
        */
        this.catsAndBoxes = new CatsAndBoxes((int) difficulty.getValue());
        this.challengeNumber.setText("Challenge number: " + this.catsAndBoxes.challenge.getChallengeNumber());

        /* Calls to other methods that help set up the game. If you wish
           to inspect the source code of these methods, you can right-click
           on the method's name. Then, in the pop-up menu, click "Go To" ->
           "Declaration or Usages". (You can also left-click on the method
           name and then press "Ctrl" + "B" or "Cmd" + "B" for Windows/Mac
           respectively)
         */
        this.reset();
        this.makeBoard();
        this.makePieces();
        this.clearHints();
    }

    /**
     * Method that creates the buttons, sliders and text for the game.
     */
    public void makeControls() {
        // Settings for a Button instance
        Button restart = new Button();
        restart.setLayoutX(MARGIN_X);
        restart.setLayoutY(WINDOW_HEIGHT - MARGIN_Y);
        restart.setOnAction(event -> this.restart()); // Lambda expression
        restart.setText("Restart");
        this.controls.getChildren().add(restart);

        Button newGame = new Button();
        newGame.setLayoutX(restart.getLayoutX() + BUTTON_BUFFER);
        newGame.setLayoutY(WINDOW_HEIGHT - MARGIN_Y);
        newGame.setOnAction(event -> this.newGame());
        newGame.setText("New Game");
        this.controls.getChildren().add(newGame);

        Button help = new Button();
        help.setLayoutX(newGame.getLayoutX() + BUTTON_BUFFER);
        help.setLayoutY(WINDOW_HEIGHT - MARGIN_Y);
        help.setOnAction(event -> {
            // Creates an alert, providing it with a type and with body text
            Alert alert = new Alert(Alert.AlertType.INFORMATION, INSTRUCTIONS);
            alert.setTitle("Instructions");
            alert.setHeaderText("Instructions");
            alert.show();
        });
        help.setText("Instructions");
        this.controls.getChildren().add(help);

        Button controlsButton = new Button();
        controlsButton.setLayoutX(help.getLayoutX() + BUTTON_BUFFER);
        controlsButton.setLayoutY(WINDOW_HEIGHT - MARGIN_Y);
        controlsButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, CONTROLS);
            alert.setTitle("Controls");
            alert.setHeaderText("Controls");
            alert.show();
        });
        controlsButton.setText("Controls");
        this.controls.getChildren().add(controlsButton);

        Button hint = new Button();
        hint.setLayoutX(controls.getLayoutX() + BUTTON_BUFFER);
        hint.setLayoutY(WINDOW_HEIGHT - MARGIN_Y - BUTTON_BUFFER);
        hint.setOnAction(event -> highlightHint());
        hint.setText("Hint");
        this.controls.getChildren().add(hint);


        // Settings for a Slider instance
        this.difficulty.setShowTickLabels(true);
        this.difficulty.setShowTickMarks(true);
        this.difficulty.setMajorTickUnit(1);
        this.difficulty.setMinorTickCount(0);
        this.difficulty.setSnapToTicks(true);
        this.difficulty.setLayoutX(controlsButton.getLayoutX() + BUTTON_BUFFER);
        this.difficulty.setLayoutY(WINDOW_HEIGHT - MARGIN_Y);
        this.controls.getChildren().add(this.difficulty);

        // Settings for a Text instance
        this.challengeNumber.setFont(javafx.scene.text.Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        this.challengeNumber.setLayoutX(this.difficulty.getLayoutX() + SLIDER_BUFFER);
        this.challengeNumber.setLayoutY(WINDOW_HEIGHT - 30.0);
        this.controls.getChildren().add(challengeNumber);

    }


    /**
     * Creates the board for the application.
     */
    private void makeBoard() {
        // creating rectangle to represent the blue background of the board
        Rectangle boardBack = new Rectangle(
                START_X-BOARD_BORDER+(BOARD_TILE_SHADOW_GAP / 2),
                START_Y-BOARD_BORDER+(BOARD_TILE_SHADOW_GAP / 2),
                boardWidth+(2*BOARD_BORDER)- BOARD_TILE_SHADOW_GAP,
                boardHeight+(2*BOARD_BORDER)- BOARD_TILE_SHADOW_GAP);
        boardBack.setFill(Color.web("6aa0c1"));
        boardBack.setArcHeight(30.0d);
        boardBack.setArcWidth(30.0d);
        // adding the rectangle to the board group
        board.getChildren().add(boardBack);

        // creating the grey rectangles to indicate where the tiles are
        for (int x=0; x < CatsAndBoxes.BOARD_WIDTH; x++) {
            for (int y=0; y < CatsAndBoxes.BOARD_HEIGHT; y++) {
                Rectangle tileShadow = new Rectangle(
                        START_X+(x*Tile_Size)+(BOARD_TILE_SHADOW_GAP / 2),
                        START_Y+(y*Tile_Size)+(BOARD_TILE_SHADOW_GAP / 2),
                        Tile_Size - BOARD_TILE_SHADOW_GAP,
                        Tile_Size - BOARD_TILE_SHADOW_GAP);
                tileShadow.setFill(Color.TRANSPARENT);
                tileShadow.setStrokeWidth(10);
                tileShadow.setStroke(Color.GREY);
                tileShadow.setOpacity(0.5);
                board.getChildren().add(tileShadow);
            }
        }
        assert catsAndBoxes.catPositions.length == catColours.length;

        // creating circles representing the cats
        for (int i = 0; i < catsAndBoxes.catPositions.length; i++) {
            createCat(catsAndBoxes.catPositions[i], catColours[i]);
        }
    }

    /**
     * A method that creates a cat piece (a circle) and adds it to the board.
     **/
    private void createCat(IntPair catPosition, Color color) {
        int x = catPosition.getX();
        int y = catPosition.getY();
        Circle cat = new Circle(START_X+(x*Tile_Size)+(Tile_Size)/2,
                START_Y+(y*Tile_Size)+(Tile_Size)/2, (Tile_Size)/3);
        cat.setFill(color);
        board.getChildren().add(cat);
    }

    /**
     * A method that creates the pieces for the game.
     */
    private void makePieces() {
        for (int pieceNum=1; pieceNum<=CatsAndBoxes.NUMBER_OF_PIECES; pieceNum++) {
            DraggablePiece draggablePiece = new DraggablePiece(catsAndBoxes.getPiece(pieceNum), false);
            pieces.getChildren().add(draggablePiece);
        }
    }


    /**
     * Method that is necessary to run any JavaFX class. This is equivalent
     * to `public static void main(String[] args)` in Java classes.
     *
     * @param stage the class which holds all data related to the application
     * @throws Exception if there is an error in the code - hopefully this doesn't happen!
     */

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cats and Boxes!");
        /*
          A "Scene" is the window corresponding to the JavaFX application.
          Notice that the last two parameters of a Scene are the width and
          height of the window. The other (first) parameter is a Group,
          which holds all the assets (images, buttons, et cetera) in the application.
          Chat to your lecturer/tutor about any of this if it's unclear.
        */
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        /*
          Here we add smaller Groups of assets to our main Group. The `board`
          Group contains board-related assets, `controls` contains buttons, text fields et cetera, and `pieces`
          contains the pieces.
         */
        this.root.getChildren().add(this.board);
        this.root.getChildren().add(this.controls);
        this.root.getChildren().add(this.pieces);

        newGame();
        makeControls();

        stage.setScene(scene);
        stage.show();

        root.requestFocus();

    }

    /**
     * Creates a visually distinct "hinted" piece, which is a suggested move. Cycles through all possible moves, first
     * by piece, then by move index.
     */
    private void highlightHint() {
        if (hintedPiece != null) {
            this.root.getChildren().remove(hintedPiece);
            hintedPiece = null;
        }
        Transform[][] validMoves = catsAndBoxes.findAllValidMoves();
        if (validMoves == null) {
            String message = "You must complete findAllValidMoves() before you can use this feature.";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
            alert.setTitle("Hints not available");
            alert.setHeaderText("Hints not available");
            alert.show();
            return;
        }
        for (int i = 0; i<validMoves.length; i++) {
            if (i < hintPiece) continue;
            for (int j = 0; j<validMoves[i].length; j++) {
                if (j < hintIndex) continue;
                if (validMoves[i][j] != null) {
                    hintedPiece = new DraggablePiece(new Piece(i+1, validMoves[i][j]), true);
                    this.root.getChildren().add(hintedPiece);
                    hintPiece = ++i;
                    if (hintPiece == validMoves.length) {
                        hintPiece = 0;
                        hintIndex++;
                    }
                    return;
                } else break;
            }
        }
        hintPiece = 0;
        hintIndex = 0;
    }

    /**
     * Clears the hints from the board, and resets the hint counter.
     */
    private void clearHints() {
        if (hintedPiece != null) {
            hintedPiece.setOpacity(0); //This avoids a visual bug where the hint piece is still visible after hints are cleared
            this.root.getChildren().remove(hintedPiece);
            hintedPiece = null;
        }
        hintPiece = 0;
        hintIndex = 0;
    }
}
