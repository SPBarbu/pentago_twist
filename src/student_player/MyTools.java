package student_player;

import java.util.ArrayList;
import java.util.Arrays;

import pentago_twist.PentagoBoardState.Piece;

public class MyTools {
    private static final int QUAD_SIZE = 3;
    private static Piece[][] quad_1 = new Piece[][] { {}, {}, {} };
    private static ArrayList<String> all_moves = new ArrayList<String>(
            Arrays.asList("00", "01", "02", "10", "11", "12", "20", "21", "22"));
    private static int[] quad_strat = new int[] { 0, 0, 0, 0 };
    // 0:continue best
    // 1:continue for win
    // 2:continue for draw
    // 3:won
    // 4:drawn

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
                    }
                }
            } else if (n_pieces_new == 4) {
                if (q[1][1] == Piece.EMPTY) {// not center
                    if (q[0][0] == my_piece) {// top left
                        if (q[0][1] == en_piece) {// touch left
                            if (q[1][0] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                return "10";
                            } else {
                                quad_strat[q_number] = 1;
                                return "22";
                            }
                        } else if (q[1][0] == en_piece) {// touch right
                            if (q[0][1] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                return "01";
                            } else {
                                quad_strat[q_number] = 1;
                                return "22";
                            }
                        } else {
                            if (q[2][0] == my_piece) {
                                if (q[1][0] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
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
                                return "01";
                            } else {
                                quad_strat[q_number] = 1;
                                return "20";
                            }
                        } else if (q[1][0] == en_piece) {// touch right
                            if (q[1][2] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                return "12";
                            } else {
                                quad_strat[q_number] = 1;
                                return "20";
                            }
                        } else {
                            if (q[0][0] == my_piece) {
                                if (q[0][1] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
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
                                return "12";
                            } else {
                                quad_strat[q_number] = 1;
                                return "00";
                            }
                        } else if (q[1][0] == en_piece) {// touch right
                            if (q[2][1] == Piece.EMPTY) {
                                quad_strat[q_number] = 3;
                                return "21";
                            } else {
                                quad_strat[q_number] = 1;
                                return "00";
                            }
                        } else {
                            if (q[2][0] == my_piece) {
                                if (q[2][1] == Piece.EMPTY) {
                                    quad_strat[q_number] = 3;
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
                            return "12";
                        } else {
                            quad_strat[q_number] = 1;
                            return "20";
                        }
                    } else {
                        if (q[1][0] == Piece.EMPTY) {
                            quad_strat[q_number] = 3;
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
                            return "21";
                        } else {
                            quad_strat[q_number] = 1;
                            return "00";
                        }
                    } else {
                        if (q[1][1] == Piece.EMPTY) {
                            quad_strat[q_number] = 3;
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

    public static void main(String[] args) {
        Piece[][] a = process_move_quadrant(new Piece[][] { { Piece.WHITE, Piece.EMPTY, Piece.EMPTY },
                { Piece.EMPTY, Piece.EMPTY, Piece.EMPTY }, { Piece.EMPTY, Piece.EMPTY, Piece.EMPTY } }, "12",
                Piece.BLACK);
        System.out.println(toString(a));
        ArrayList<String> c = get_legal_moves_quadrant(a);
        ArrayList<Piece[][]> d = new ArrayList<Piece[][]>();
        for (int i = 0; i < c.size(); i++) {
            d.add(process_move_quadrant(a, c.get(i), Piece.WHITE));
        }
        int b = 0;
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
}