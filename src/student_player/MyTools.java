package student_player;

import pentago_twist.PentagoBoardState.Piece;

public class MyTools {
    private static final int QUAD_SIZE = 3;

    public static String get_coord_last_move(Piece[][] last_m, Piece[][] new_m) {
        if (get_number_pieces(last_m) == get_number_pieces(new_m)) {
            return "";
        } else {

        }
        return "";
    }

    // assumes move did happen, ie. different number of pieces
    public static int move_is_rotate(Piece[][] last_m, Piece[][] new_m, int new_num_pieces) {
        int n = 0;
        Piece[][] temp = new Piece[QUAD_SIZE][QUAD_SIZE];
        for (int j = 0; j < QUAD_SIZE; j++) {
            for (int i = QUAD_SIZE - 1; i >= 0; i--) {
                System.arraycopy(last_m[i], j, temp[j], QUAD_SIZE - 1 - i, 1);
                if(){

                }
            }
        }
    }

    // assumes move did happen, ie. different number of pieces
    public static boolean move_is_flip(Piece[][] last_m, Piece[][] new_m, int new_num_pieces) {

    }

    public static int get_number_pieces(Piece[][] q) {
        int n = 0;
        for (int i = 0; i < QUAD_SIZE; i++) {
            for (int j = 0; j < QUAD_SIZE; j++) {
                if (q[i][j] != Piece.EMPTY) {
                    n += 1;
                }
            }
        }
        return n;
    }
}