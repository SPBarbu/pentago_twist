package student_player;

import java.util.ArrayList;
import pentago_twist.PentagoBoardState.Piece;

public class MyTools {
    private static final int QUAD_SIZE = 3;

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

    public static void main(String[] args) {
        Piece[][] a = process_move_quadrant(new Piece[][] { { Piece.WHITE, Piece.EMPTY, Piece.EMPTY },
                { Piece.EMPTY, Piece.EMPTY, Piece.EMPTY }, { Piece.EMPTY, Piece.EMPTY, Piece.EMPTY } }, "12",
                Piece.BLACK);
        ArrayList<String> c = get_legal_moves_quadrant(a);
        int b = 0;
    }

    public static ArrayList<String> get_legal_moves_quadrant(Piece[][] quad) {
        ArrayList<String> moves = new ArrayList<String>();
        for (int i = 0; i < QUAD_SIZE; i++) {
            for (int j = 0; j < QUAD_SIZE; j++) {
                if (quad[i][j] == Piece.EMPTY) {
                    moves.add(i + "" + j);
                }
            }
        }
        return moves;
    }

    public static Piece[][] process_move_quadrant(Piece[][] q, String m, Piece player) {
        Piece[][] p = new Piece[QUAD_SIZE][QUAD_SIZE];
        if (q[Integer.parseInt(m.charAt(0) + "")][Integer.parseInt(m.charAt(1) + "")] != Piece.EMPTY) {
            return null;
        }
        for (

                int i = 0; i < QUAD_SIZE; i++) {
            for (int j = 0; j < QUAD_SIZE; j++) {
                p[i][j] = q[i][j];
            }
        }
        p[Integer.parseInt(m.charAt(0) + "")][Integer.parseInt(m.charAt(1) + "")] = player;
        return p;
    }

    public static void get_best_moves(Piece[][] quadrant, Piece player) {
        ArrayList<String> moves = new ArrayList<String>();
        if (get_number_pieces(quadrant) == 0) {
            moves.add("00, 02, 20, 22");
        }
    }

    public static int get_longest(Piece[][] quadrant, Piece player) {
        int l = 0;
        int t = 0;
        // longest horizontal
        for (int i = 0; i < QUAD_SIZE; i++) {
            t = get_longest_line(quadrant[i], player);
            if (t > l) {
                l = t;
            }
        }

        // longest vertical
        Piece[] rot;
        for (int j = 0; j < QUAD_SIZE; j++) {
            rot = new Piece[QUAD_SIZE];
            for (int i = 0; i < QUAD_SIZE; i++) {
                rot[i] = quadrant[i][0];
                rot[i] = quadrant[i][0];
                rot[i] = quadrant[i][0];
            }
            t = get_longest_line(rot, player);
            if (t > l) {
                l = t;
            }
        }

        // longest diagonal right
        Piece[][] diag_r = { { quadrant[0][0] }, { quadrant[1][0], quadrant[0][1] }, { quadrant[2][1], quadrant[1][2] },
                { quadrant[2][2] }, { quadrant[2][0], quadrant[1][1], quadrant[0][2] } };
        for (int i = 0; i < diag_r.length; i++) {
            t = get_longest_line(diag_r[i], player);
            if (t > l) {
                l = t;
            }
        }

        // longest diagonal left
        Piece[][] diag_l = { { quadrant[2][0] }, { quadrant[1][0], quadrant[2][1] }, { quadrant[0][1], quadrant[1][2] },
                { quadrant[0][2] }, { quadrant[0][0], quadrant[1][1], quadrant[2][2] } };
        for (int i = 0; i < diag_r.length; i++) {
            t = get_longest_line(diag_l[i], player);
            if (t > l) {
                l = t;
            }
        }
        return l;

    }

    private static int get_longest_line(Piece[] line, Piece player) {
        if (line.length == 3) {
            if (line[0] == player && line[1] == player && line[2] == player) {
                return 3;
            } else if ((line[0] == player && line[1] == player) || (line[1] == player && line[2] == player)) {
                return 2;
            } else if (line[0] == player || line[1] == player || line[2] == player) {
                return 1;
            }
        } else if (line.length == 2) {
            if (line[0] == player && line[1] == player) {
                return 2;
            } else if (line[0] == player || line[1] == player) {
                return 1;
            }
        } else if (line.length == 1) {
            if (line[0] == player) {
                return 1;
            }
        }
        return 0;
    }
}