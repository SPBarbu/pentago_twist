package student_player;

import pentago_twist.PentagoBoardState.Piece;
import java.util.concurrent.ThreadLocalRandom;

public class MyTools {
    static long[][] zobrist_kernel = {
            { 3426656090443505776L, 6498930185473388688L, 253487347233103987L, 8614390861676474625L,
                    921416348712736123L, 8426036869812339724L, 4141270094557949890L, 2623972657093294210L,
                    5493384634043826039L },
            { 8544448772556060564L, 4021900698997532170L, 5563267841454116692L, 4681788819837768675L,
                    6778026170414788438L, 4588469670436179290L, 8363641500665540096L, 1427310810430960288L,
                    3852229342363795255L } };

    public static double getSomething() {
        return Math.random();
    }

    public static void generate_zobrist_random_numbers() {
        for (int i = 0; i < zobrist_kernel[0].length * 2; i++) {
            zobrist_kernel[i % zobrist_kernel.length][i % zobrist_kernel[0].length] = ThreadLocalRandom.current()
                    .nextLong(Long.MAX_VALUE);
        }
    }

    public static void main(String[] args) {
        Piece[][] quad = { { Piece.WHITE, Piece.BLACK, Piece.EMPTY }, { Piece.WHITE, Piece.BLACK, Piece.EMPTY },
                { Piece.WHITE, Piece.BLACK, Piece.EMPTY } };
        quadrant_hash(quad, 0);
    }

    /**
     * Using Zobrist hashing in combination with the following link to encode a
     * quadrant state in a rotationaly invariant way.
     * https://computer-go.computer-go.narkive.com/RRLdmjMH/rotate-board
     */
    public static long quadrant_hash(Piece[][] q, int player) {
        long hash = 0;
        for (int i = 0; i < q.length * q.length; i++) {
            if (q[(int) (i / q.length)][i % q.length] == Piece.WHITE) {
                hash ^= zobrist_kernel[0][i];
            } else if (q[(int) (i / q.length)][i % q.length] == Piece.BLACK) {
                hash ^= zobrist_kernel[1][i];
            }
        }
        return hash;
    }
}