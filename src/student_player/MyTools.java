package student_player;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.concurrent.TimeUnit;

import pentago_twist.PentagoBoardState.Piece;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;
import boardgame.Move;

public class MyTools {
    public static final int QUAD_SIZE = 3;
    public static final int NUM_QUADS = 4;
    private static Piece[][] quad_1 = new Piece[][] { {}, {}, {} };
    private static ArrayList<String> all_moves = new ArrayList<String>(
            Arrays.asList("00", "01", "02", "10", "11", "12", "20", "21", "22"));
    public static PentagoBoardState board_state_after_my_last_move;
    public static PentagoBoardState board_state_opp_move;
    public static int[] quad_win_type = new int[] { 0, 0, 0, 0 };
    // 1:diagonal
    // 2:straight edge
    // 3:straight middle
    public static int[] quad_strat = new int[] { 0, 0, 0, 0 };
    // 0:continue best
    // 1:continue for win
    // 2:continue for draw
    // 3:won
    // 4:drawn

    public static int get_quadrant_updated() {
        if (board_state_opp_move.getTurnNumber() == 0) {
            if (board_state_opp_move.getTurnPlayer() == 0) {
                return 0;
            } else {
                for (int i = 0; i < NUM_QUADS; i++) {
                    if (get_number_pieces(get_quadrant_from_board(board_state_opp_move.getBoard(), i)) == 1) {
                        return i;
                    }
                }
            }
        }
        for (int i = 0; i < NUM_QUADS; i++) {
            if (get_number_pieces(get_quadrant_from_board(board_state_after_my_last_move.getBoard(),
                    i)) != get_number_pieces(get_quadrant_from_board(board_state_opp_move.getBoard(), i))) {
                return i;
            }
        }
        return 0;
    }

    public static Piece[][] get_quadrant_from_board(Piece[][] board, int quad_numb) {
        Piece[][] q = new Piece[QUAD_SIZE][QUAD_SIZE];
        for (int i = (int) (Math.floor(quad_numb / 2) * 3); i < (int) (Math.floor(quad_numb / 2) * 3) + 3; i++) {
            for (int j = (quad_numb % 2) * 3; j < (quad_numb % 2) * 3 + 3; j++) {
                q[i - (int) (Math.floor(quad_numb / 2) * 3)][j - (quad_numb % 2) * 3] = board[i][j];
            }
        }
        return q;
    }

    public static Move string_to_move(String move, int quad_numb, int playerId) {
        if (move == "") {
            return board_state_opp_move.getRandomMove();
        }
        Move m = new PentagoMove((int) (Math.floor(quad_numb / 2) * 3) + (move.charAt(0) - '0'),
                (quad_numb % 2) * 3 + (move.charAt(1) - '0'), 3, 0, playerId);
        return m;
    }

    public static String get_best_move(Piece[][] q, int q_number, int my_turn, Piece my_piece) {
        int n_pieces_new = get_number_pieces(q);
        Piece en_piece = my_piece == Piece.WHITE ? Piece.BLACK : Piece.WHITE;

        if (my_turn == 0) {// if first to play on quad
            if (n_pieces_new == 0) {// first move
                return "00";
            } else if (n_pieces_new == 2) {
                if (q[1][1] == en_piece) {// center play
                    if (q[0][0] == my_piece) {
                        return "22";
                    } else if (q[0][2] == my_piece) {
                        return "20";
                    } else if (q[2][0] == my_piece) {
                        return "02";
                    } else {
                        return "00";
                    }
                } else {// not center play
                    if (q[0][0] == my_piece) {
                        if (q[0][1] == en_piece) {
                            return "20";
                        } else if (q[1][0] == en_piece) {
                            return "02";
                        } else {
                            if (q[2][0] == Piece.EMPTY) {
                                return "20";
                            } else {
                                return "02";
                            }
                        }
                    } else if (q[0][2] == my_piece) {
                        if (q[0][1] == en_piece) {
                            return "22";
                        } else if (q[1][2] == en_piece) {
                            return "00";
                        } else {
                            if (q[2][2] == Piece.EMPTY) {
                                return "22";
                            } else {
                                return "00";
                            }
                        }
                    } else if (q[2][0] == my_piece) {
                        if (q[1][0] == en_piece) {
                            return "22";
                        } else if (q[2][1] == en_piece) {
                            return "00";
                        } else {
                            if (q[2][2] == Piece.EMPTY) {
                                return "22";
                            } else {
                                return "00";
                            }
                        }
                    } else {
                        if (q[2][1] == en_piece) {
                            return "02";
                        } else if (q[1][2] == en_piece) {
                            return "20";
                        } else {
                            if (q[2][0] == Piece.EMPTY) {
                                return "20";
                            } else {
                                return "02";
                            }
                        }
                    }
                }
            } else if (n_pieces_new == 4) {
                if (q[1][1] == Piece.EMPTY) {// not center
                    if (q[0][0] == my_piece) {// top left
                        if (q[0][1] == en_piece) {// touch left
                            if (q[1][0] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                quad_win_type[q_number] = 2;
                                return "10";
                            } else {
                                quad_strat[q_number] = 1;
                                return "22";
                            }
                        } else if (q[1][0] == en_piece) {// touch right
                            if (q[0][1] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                quad_win_type[q_number] = 2;
                                return "01";
                            } else {
                                quad_strat[q_number] = 1;
                                return "22";
                            }
                        } else {
                            if (q[2][0] == my_piece) {
                                if (q[1][0] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
                                    quad_win_type[q_number] = 2;
                                    return "10";
                                } else {
                                    quad_strat[q_number] = 1;
                                    if (q[2][2] == Piece.EMPTY) {
                                        return "22";
                                    } else {
                                        return "02";
                                    }
                                }
                            } else {
                                if (q[0][1] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
                                    quad_win_type[q_number] = 2;
                                    return "01";
                                } else {
                                    quad_strat[q_number] = 1;
                                    if (q[2][2] == Piece.EMPTY) {
                                        return "22";
                                    } else {
                                        return "20";
                                    }
                                }
                            }
                        }
                    } else if (q[0][2] == my_piece) {// top right
                        if (q[1][2] == en_piece) {// touch left
                            if (q[0][1] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                quad_win_type[q_number] = 2;
                                return "01";
                            } else {
                                quad_strat[q_number] = 1;
                                return "20";
                            }
                        } else if (q[1][0] == en_piece) {// touch right
                            if (q[1][2] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                quad_win_type[q_number] = 2;
                                return "12";
                            } else {
                                quad_strat[q_number] = 1;
                                return "20";
                            }
                        } else {
                            if (q[0][0] == my_piece) {
                                if (q[0][1] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
                                    quad_win_type[q_number] = 2;
                                    return "01";
                                } else {
                                    quad_strat[q_number] = 1;
                                    if (q[2][2] == Piece.EMPTY) {
                                        return "22";
                                    } else {
                                        return "20";
                                    }
                                }
                            } else {
                                if (q[1][2] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
                                    quad_win_type[q_number] = 2;
                                    return "12";
                                } else {
                                    quad_strat[q_number] = 1;
                                    if (q[0][0] == Piece.EMPTY) {
                                        return "00";
                                    } else {
                                        return "20";
                                    }
                                }
                            }
                        }
                    } else if (q[2][2] == my_piece) {// bottom right
                        if (q[2][1] == en_piece) {// touch left
                            if (q[1][2] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                quad_win_type[q_number] = 2;
                                return "12";
                            } else {
                                quad_strat[q_number] = 1;
                                return "00";
                            }
                        } else if (q[1][0] == en_piece) {// touch right
                            if (q[2][1] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                quad_win_type[q_number] = 2;
                                return "21";
                            } else {
                                quad_strat[q_number] = 1;
                                return "00";
                            }
                        } else {
                            if (q[2][0] == my_piece) {
                                if (q[2][1] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
                                    quad_win_type[q_number] = 2;
                                    return "21";
                                } else {
                                    quad_strat[q_number] = 1;
                                    if (q[0][0] == Piece.EMPTY) {
                                        return "00";
                                    } else {
                                        return "02";
                                    }
                                }
                            } else {
                                if (q[1][2] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
                                    quad_win_type[q_number] = 2;
                                    return "12";
                                } else {
                                    quad_strat[q_number] = 1;
                                    if (q[0][0] == Piece.EMPTY) {
                                        return "00";
                                    } else {
                                        return "20";
                                    }
                                }
                            }
                        }
                    } else {// bottom left
                        if (q[1][0] == en_piece) {// touch left
                            if (q[2][1] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                quad_win_type[q_number] = 2;
                                return "21";
                            } else {
                                quad_strat[q_number] = 1;
                                return "02";
                            }
                        } else if (q[2][1] == en_piece) {// touch right
                            if (q[1][0] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                quad_win_type[q_number] = 2;
                                return "10";
                            } else {
                                quad_strat[q_number] = 1;
                                return "02";
                            }
                        } else {
                            if (q[0][0] == my_piece) {
                                if (q[1][0] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
                                    quad_win_type[q_number] = 2;
                                    return "10";
                                } else {
                                    quad_strat[q_number] = 1;
                                    if (q[2][2] == Piece.EMPTY) {
                                        return "22";
                                    } else {
                                        return "02";
                                    }
                                }
                            } else {
                                if (q[2][1] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
                                    quad_win_type[q_number] = 2;
                                    return "21";
                                } else {
                                    quad_strat[q_number] = 1;
                                    if (q[0][0] == Piece.EMPTY) {
                                        return "00";
                                    } else {
                                        return "02";
                                    }
                                }
                            }
                        }
                    }
                } else {// center
                    if (q[0][0] == my_piece) {// top left
                        if (q[2][0] == en_piece) {
                            quad_strat[q_number] = 1;
                            return "02";
                        } else if (q[0][2] == en_piece) {
                            quad_strat[q_number] = 1;
                            return "20";
                        } else {
                            quad_strat[q_number] = 2;
                            if (q[0][1] == en_piece) {
                                return "21";
                            } else if (q[1][0] == en_piece) {
                                return "12";
                            } else if (q[1][2] == en_piece) {
                                return "10";
                            } else {
                                return "01";
                            }
                        }
                    } else {// top right
                        if (q[0][0] == en_piece) {
                            quad_strat[q_number] = 1;
                            return "22";
                        } else if (q[2][2] == en_piece) {
                            quad_strat[q_number] = 1;
                            return "00";
                        } else {
                            quad_strat[q_number] = 2;
                            if (q[0][1] == en_piece) {
                                return "21";
                            } else if (q[1][0] == en_piece) {
                                return "12";
                            } else if (q[1][2] == en_piece) {
                                return "10";
                            } else {
                                return "01";
                            }
                        }
                    }
                }
            }
        } else {// if second to play on quad
            if (n_pieces_new == 1) {
                if (q[1][1] == en_piece) {
                    quad_strat[q_number] = 2;
                    return "01";
                } else if (q[0][0] == en_piece || q[0][2] == en_piece || q[2][0] == en_piece || q[2][2] == en_piece) {// corner
                    return "11";
                } else {// edge
                    return "11";
                }
            } else if (n_pieces_new == 3) {
                if (q[0][0] == en_piece) {
                    quad_strat[q_number] = 2;
                    if (q[2][2] == en_piece) {
                        return "01";
                    } else if (q[0][2] == en_piece) {
                        return "01";
                    } else if (q[2][0] == en_piece) {
                        return "10";
                    } else if (q[0][1] == en_piece) {
                        return "02";
                    } else if (q[2][0] == en_piece) {
                        return "20";
                    }
                } else if (q[0][2] == en_piece) {
                    quad_strat[q_number] = 2;
                    if (q[0][0] == en_piece) {
                        return "01";
                    } else if (q[2][0] == en_piece) {
                        return "01";
                    } else if (q[2][2] == en_piece) {
                        return "12";
                    } else if (q[0][1] == en_piece) {
                        return "00";
                    } else if (q[1][2] == en_piece) {
                        return "22";
                    }
                } else if (q[2][0] == en_piece) {
                    quad_strat[q_number] = 2;
                    if (q[0][0] == en_piece) {
                        return "10";
                    } else if (q[0][2] == en_piece) {
                        return "01";
                    } else if (q[2][2] == en_piece) {
                        return "21";
                    } else if (q[1][0] == en_piece) {
                        return "00";
                    } else if (q[2][1] == en_piece) {
                        return "22";
                    }
                } else if (q[2][2] == en_piece) {
                    quad_strat[q_number] = 2;
                    if (q[0][0] == en_piece) {
                        return "01";
                    } else if (q[0][2] == en_piece) {
                        return "12";
                    } else if (q[2][0] == en_piece) {
                        return "21";
                    } else if (q[2][1] == en_piece) {
                        return "20";
                    } else if (q[1][2] == en_piece) {
                        return "02";
                    }
                } else {// edge
                    if (q[0][1] == en_piece && q[2][1] == en_piece) {
                        return "10";
                    } else if (q[1][0] == en_piece && q[1][2] == en_piece) {
                        return "01";
                    } else {
                        quad_strat[q_number] = 2;
                        if (q[1][0] == en_piece && q[0][1] == en_piece) {
                            return "00";
                        } else if (q[1][0] == en_piece && q[2][1] == en_piece) {
                            return "20";
                        } else if (q[1][2] == en_piece && q[0][1] == en_piece) {
                            return "02";
                        } else if (q[2][1] == en_piece && q[1][2] == en_piece) {
                            return "22";
                        } else {
                            if (q[0][1] == Piece.EMPTY) {
                                return "01";
                            } else {
                                return "10";
                            }
                        }
                    }
                }
            } else if (n_pieces_new == 5) {
                if (q[0][1] == en_piece && q[2][1] == en_piece) {
                    if (q[1][0] == my_piece) {
                        if (q[1][2] == Piece.EMPTY) {
                            quad_strat[q_number] = 3;
                            quad_win_type[q_number] = 3;
                            return "12";
                        } else {
                            quad_strat[q_number] = 1;
                            return "20";
                        }
                    } else {
                        if (q[1][0] == Piece.EMPTY) {
                            quad_strat[q_number] = 3;
                            quad_win_type[q_number] = 3;
                            return "10";
                        } else {
                            quad_strat[q_number] = 1;
                            return "22";
                        }
                    }
                } else {
                    if (q[1][1] == my_piece) {
                        if (q[2][1] == Piece.EMPTY) {
                            quad_strat[q_number] = 3;
                            quad_win_type[q_number] = 3;
                            return "21";
                        } else {
                            quad_strat[q_number] = 1;
                            return "00";
                        }
                    } else {
                        if (q[1][1] == Piece.EMPTY) {
                            quad_strat[q_number] = 3;
                            quad_win_type[q_number] = 3;
                            return "11";
                        } else {
                            quad_strat[q_number] = 1;
                            return "20";
                        }
                    }
                }
            }
        }

        return "";
    }

    public static int get_turn_number_on_quad(Piece[][] q) {
        return get_number_pieces(q) % 2;
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        Piece[][] b = new Piece[][] { { Piece.EMPTY, Piece.EMPTY, Piece.EMPTY },
                { Piece.EMPTY, Piece.BLACK, Piece.EMPTY }, { Piece.BLACK, Piece.EMPTY, Piece.EMPTY } };

        System.out.println(continue_for_draw(b, 0, Piece.WHITE));
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
    }

    public static String continue_for_win(Piece[][] q, int q_number, Piece my_piece) {
        for (String move : get_legal_moves_quadrant(q)) {
            if (get_longest(process_move_quadrant(q, move, my_piece), my_piece) == 3) {
                quad_strat[q_number] = 3;
                quad_win_type[q_number] = get_win_type(q, my_piece);
                return move;
            }
        }
        return "";
    }

    public static String continue_for_draw(Piece[][] q, int q_number, Piece my_piece) {
        Piece en_piece = my_piece == Piece.WHITE ? Piece.BLACK : Piece.WHITE;
        if (get_number_pieces(q) == 8) {
            quad_strat[q_number] = 4;
        }
        String instant_win_possibility = continue_for_win(q, q_number, my_piece);
        if (instant_win_possibility == "") {
            for (String move : get_legal_moves_quadrant(q)) {
                if (get_longest(process_move_quadrant(q, move, en_piece), en_piece) == 3) {
                    return move;
                }
            }
        } else {
            quad_strat[q_number] = 4;
            return instant_win_possibility;
        }
        return get_random_move(q);
    }

    public static String get_last_move(Piece[][] last_quad, Piece[][] new_quad) {
        int n_pieces_last = get_number_pieces(last_quad);
        int n_pieces_new = get_number_pieces(new_quad);
        if (n_pieces_last == n_pieces_new) {
            if (toString(rotate_right(last_quad)).equals(toString(new_quad))) {
                return "r";
            } else if (toString(flip_horizontal(last_quad)).equals(toString(new_quad))) {
                return "f";
            } else {
                return "";
            }
        } else if (n_pieces_new == n_pieces_last + 1) {
            int numb_not_common = 0;
            for (int i = 0; i < QUAD_SIZE; i++) {
                for (int j = 0; j < QUAD_SIZE; j++) {

                }
            }
            return "TODO";
        } else {
            return "";
        }
    }

    public static Piece[][] rotate_right(Piece[][] q) {
        Piece[][] n = new Piece[QUAD_SIZE][QUAD_SIZE];
        n[0][0] = q[2][0];
        n[0][1] = q[1][0];
        n[0][2] = q[0][0];
        n[1][0] = q[2][1];
        n[1][1] = q[1][1];
        n[1][2] = q[0][1];
        n[2][0] = q[2][2];
        n[2][1] = q[1][2];
        n[2][2] = q[0][2];
        return n;
    }

    public static Piece[][] flip_horizontal(Piece[][] q) {
        Piece[][] n = new Piece[QUAD_SIZE][QUAD_SIZE];
        n[0][0] = q[0][2];
        n[0][1] = q[0][1];
        n[0][2] = q[0][0];
        n[1][0] = q[1][2];
        n[1][1] = q[1][1];
        n[1][2] = q[1][0];
        n[2][0] = q[2][2];
        n[2][1] = q[2][1];
        n[2][2] = q[2][0];
        return n;
    }

    public static String toString(Piece[][] q) {
        String s = "";
        for (int i = 0; i < QUAD_SIZE; i++) {
            for (int j = 0; j < QUAD_SIZE; j++) {
                s += (q[i][j] == Piece.EMPTY ? "e" : (q[i][j] == Piece.WHITE ? "w" : "b"));
            }
        }
        return s;
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
        if (q[m.charAt(0) - '0'][m.charAt(1) - '0'] != Piece.EMPTY) {
            return null;
        }
        for (int i = 0; i < QUAD_SIZE; i++) {
            for (int j = 0; j < QUAD_SIZE; j++) {
                p[i][j] = q[i][j];
            }
        }
        p[m.charAt(0) - '0'][m.charAt(1) - '0'] = player;
        return p;
    }

    public static int get_win_type(Piece[][] q, Piece player) {
        if ((q[0][0] == player && q[1][0] == player && q[2][0] == player)
                || (q[0][2] == player && q[1][2] == player && q[2][2] == player)
                || (q[0][0] == player && q[0][1] == player && q[0][2] == player)
                || (q[2][0] == player && q[2][1] == player && q[2][2] == player)) {
            return 2;
        } else if ((q[1][0] == player && q[1][1] == player && q[1][2] == player)
                || (q[0][1] == player && q[1][1] == player && q[2][1] == player)) {
            return 3;
        } else if ((q[0][0] == player && q[1][1] == player && q[2][2] == player)
                || (q[2][0] == player && q[1][1] == player && q[0][0] == player)) {
            return 1;
        }
        return 0;
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
        Piece[] rot = new Piece[QUAD_SIZE];
        for (int j = 0; j < QUAD_SIZE; j++) {
            rot[0] = quadrant[0][j];
            rot[1] = quadrant[1][j];
            rot[2] = quadrant[2][j];
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

    public static int get_longest_line(Piece[] line, Piece player) {
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

    public static String get_random_move(Piece[][] q) {
        for (int i = 0; i < QUAD_SIZE; i++) {
            for (int j = 0; j < QUAD_SIZE; j++) {
                if (q[i][j] == Piece.EMPTY) {
                    return i + "" + j;
                }
            }
        }
        return "";
    }

}