package student_player;

import boardgame.Move;

import pentago_twist.PentagoPlayer;
import pentago_twist.PentagoBoardState.Piece;
import pentago_twist.PentagoBoardState;
import pentago_twist.PentagoMove;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260806224");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        long startTime = System.nanoTime();
        // update opponent's move
        MyTools.board_state_opp_move = (PentagoBoardState) boardState.clone();

        Piece my_piece = Piece.BLACK;
        int quad_updated = MyTools.get_quadrant_updated();
        int q_strat = MyTools.quad_strat[quad_updated];
        String b_m = "";
        Piece[][] play_q = MyTools.get_quadrant_from_board(MyTools.board_state_opp_move.getBoard(), quad_updated);
        boolean play_q_isfull = MyTools.get_number_pieces(play_q) == 9;
        Move myMove;

        if (q_strat == 0) {
            if (!play_q_isfull) {
                b_m = MyTools.get_best_move(play_q, quad_updated, 1, my_piece);
                myMove = MyTools.string_to_move(b_m, quad_updated, this.player_id);
            } else {// play on new board when quad is full
                quad_updated = (quad_updated + 1) % 4;
                play_q = MyTools.get_quadrant_from_board(MyTools.board_state_opp_move.getBoard(), quad_updated);
                b_m = MyTools.get_best_move(
                        MyTools.get_quadrant_from_board(MyTools.board_state_opp_move.getBoard(), quad_updated),
                        quad_updated, 0, my_piece);
                myMove = MyTools.string_to_move(b_m, quad_updated, this.player_id);
            }
        } else if (q_strat == 1) {
            b_m = MyTools.continue_for_win(play_q, quad_updated, my_piece);
            myMove = MyTools.string_to_move(b_m, quad_updated, this.player_id);
        } else if (q_strat == 2) {
            if (play_q_isfull) {
                MyTools.quad_strat[quad_updated] = 4;
                quad_updated = (quad_updated + 1) % 4;
                play_q = MyTools.get_quadrant_from_board(MyTools.board_state_opp_move.getBoard(), quad_updated);
                b_m = MyTools.get_best_move(
                        MyTools.get_quadrant_from_board(MyTools.board_state_opp_move.getBoard(), quad_updated),
                        quad_updated, 0, my_piece);
                myMove = MyTools.string_to_move(b_m, quad_updated, this.player_id);
            } else {
                b_m = MyTools.continue_for_draw(play_q, quad_updated, my_piece);
                myMove = MyTools.string_to_move(b_m, quad_updated, this.player_id);
            }
        } else {
            myMove = MyTools.board_state_opp_move.getRandomMove();
        }

        // save my last move
        MyTools.board_state_after_my_last_move = (PentagoBoardState) MyTools.board_state_opp_move.clone();
        MyTools.board_state_after_my_last_move.processMove((PentagoMove) myMove);

        // Return your move to be processed by the server.
        long endTime = System.nanoTime();
        System.out.println(endTime - startTime);
        return myMove;
    }
}