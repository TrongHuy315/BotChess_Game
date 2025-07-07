package com.TrongHuy.botchessgame;

import java.util.*;

/**
 * Lớp chứa toàn bộ logic cho Bot cờ vua cải tiến.
 * Sử dụng thuật toán Minimax với cắt tỉa Alpha-Beta và nhiều kỹ thuật tối ưu khác.
 */
public class ChessBot {

    // Lớp nội tại đơn giản để biểu diễn một nước đi
    public record Move(int fromY, int fromX, int toY, int toX, int capturedPiece) {}

    private final int searchDepth;
    private final Map<String, Integer> transpositionTable; // Bảng băm lưu trữ kết quả đã tính
    private final Map<String, Move> killerMoves; // Lưu trữ nước đi "killer" để cắt tỉa tốt hơn
    private int nodesSearched = 0;

    // Các hằng số để đánh giá bàn cờ (đã điều chỉnh)
    private static final int PAWN_VALUE = 100;
    private static final int KNIGHT_VALUE = 320;
    private static final int BISHOP_VALUE = 330;
    private static final int ROOK_VALUE = 500;
    private static final int QUEEN_VALUE = 900;
    private static final int KING_VALUE = 20000;

    // Bảng điểm vị trí cho từng loại quân (Position Square Tables)
    private static final int[][] PAWN_PST = {
        {  0,  0,  0,  0,  0,  0,  0,  0 },
        { 50, 50, 50, 50, 50, 50, 50, 50 },
        { 10, 10, 20, 30, 30, 20, 10, 10 },
        {  5,  5, 10, 25, 25, 10,  5,  5 },
        {  0,  0,  0, 20, 20,  0,  0,  0 },
        {  5, -5,-10,  0,  0,-10, -5,  5 },
        {  5, 10, 10,-20,-20, 10, 10,  5 },
        {  0,  0,  0,  0,  0,  0,  0,  0 }
    };

    private static final int[][] KNIGHT_PST = {
        {-50,-40,-30,-30,-30,-30,-40,-50 },
        {-40,-20,  0,  0,  0,  0,-20,-40 },
        {-30,  0, 10, 15, 15, 10,  0,-30 },
        {-30,  5, 15, 20, 20, 15,  5,-30 },
        {-30,  0, 15, 20, 20, 15,  0,-30 },
        {-30,  5, 10, 15, 15, 10,  5,-30 },
        {-40,-20,  0,  5,  5,  0,-20,-40 },
        {-50,-40,-30,-30,-30,-30,-40,-50 }
    };

    private static final int[][] BISHOP_PST = {
        {-20,-10,-10,-10,-10,-10,-10,-20 },
        {-10,  0,  0,  0,  0,  0,  0,-10 },
        {-10,  0,  5, 10, 10,  5,  0,-10 },
        {-10,  5,  5, 10, 10,  5,  5,-10 },
        {-10,  0, 10, 10, 10, 10,  0,-10 },
        {-10, 10, 10, 10, 10, 10, 10,-10 },
        {-10,  5,  0,  0,  0,  0,  5,-10 },
        {-20,-10,-10,-10,-10,-10,-10,-20 }
    };

    private static final int[][] ROOK_PST = {
        {  0,  0,  0,  0,  0,  0,  0,  0 },
        {  5, 10, 10, 10, 10, 10, 10,  5 },
        { -5,  0,  0,  0,  0,  0,  0, -5 },
        { -5,  0,  0,  0,  0,  0,  0, -5 },
        { -5,  0,  0,  0,  0,  0,  0, -5 },
        { -5,  0,  0,  0,  0,  0,  0, -5 },
        { -5,  0,  0,  0,  0,  0,  0, -5 },
        {  0,  0,  0,  5,  5,  0,  0,  0 }
    };

    private static final int[][] QUEEN_PST = {
        {-20,-10,-10, -5, -5,-10,-10,-20 },
        {-10,  0,  0,  0,  0,  0,  0,-10 },
        {-10,  0,  5,  5,  5,  5,  0,-10 },
        { -5,  0,  5,  5,  5,  5,  0, -5 },
        {  0,  0,  5,  5,  5,  5,  0, -5 },
        {-10,  5,  5,  5,  5,  5,  0,-10 },
        {-10,  0,  5,  0,  0,  0,  0,-10 },
        {-20,-10,-10, -5, -5,-10,-10,-20 }
    };

    private static final int[][] KING_PST = {
        {-30,-40,-40,-50,-50,-40,-40,-30 },
        {-30,-40,-40,-50,-50,-40,-40,-30 },
        {-30,-40,-40,-50,-50,-40,-40,-30 },
        {-30,-40,-40,-50,-50,-40,-40,-30 },
        {-20,-30,-30,-40,-40,-30,-30,-20 },
        {-10,-20,-20,-20,-20,-20,-20,-10 },
        { 20, 20,  0,  0,  0,  0, 20, 20 },
        { 20, 30, 10,  0,  0, 10, 30, 20 }
    };

    /**
     * Khởi tạo Bot với một độ sâu tìm kiếm cụ thể.
     */
    public ChessBot(int searchDepth) {
        this.searchDepth = searchDepth;
        this.transpositionTable = new HashMap<>();
        this.killerMoves = new HashMap<>();
    }

    /**
     * Phương thức chính để gọi Bot với thuật toán Iterative Deepening.
     */
    public Move findBestMove(List<Long> currentBoard, boolean isBotWhite) {
        nodesSearched = 0;
        Move bestMove = null;
        
        // Iterative Deepening - tìm kiếm từ độ sâu 1 đến searchDepth
        for (int depth = 1; depth <= searchDepth; depth++) {
            int bestValue = isBotWhite ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            Move currentBestMove = null;
            
            List<Move> possibleMoves = generateMoves(currentBoard, isBotWhite);
            
            // Sắp xếp nước đi để tối ưu cắt tỉa
            possibleMoves = orderMoves(possibleMoves, currentBoard, isBotWhite);

            for (Move move : possibleMoves) {
                List<Long> newBoard = makeMove(currentBoard, move);
                
                int boardValue = minimax(newBoard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, !isBotWhite, depth);

                if (isBotWhite) {
                    if (boardValue > bestValue) {
                        bestValue = boardValue;
                        currentBestMove = move;
                    }
                } else {
                    if (boardValue < bestValue) {
                        bestValue = boardValue;
                        currentBestMove = move;
                    }
                }
            }
            
            if (currentBestMove != null) {
                bestMove = currentBestMove;
            }
            
            // System.out.println("Depth " + depth + ": Best move = " + bestMove + ", Value = " + bestValue);
        }
        
        // System.out.println("Nodes searched: " + nodesSearched);
        return bestMove;
    }

    /**
     * Thuật toán Minimax cải tiến với nhiều tối ưu.
     */
    private int minimax(List<Long> board, int depth, int alpha, int beta, boolean isMaximizingPlayer, int originalDepth) {
        nodesSearched++;
        
        String boardHash = getBoardHash(board);
        
        // Kiểm tra Transposition Table
        if (transpositionTable.containsKey(boardHash)) {
            return transpositionTable.get(boardHash);
        }

        // Trường hợp cơ bản
        if (depth == 0) {
            int eval = evaluateBoard(board);
            transpositionTable.put(boardHash, eval);
            return eval;
        }

        List<Move> possibleMoves = generateMoves(board, isMaximizingPlayer);
        
        // Kiểm tra thế cờ kết thúc
        if (possibleMoves.isEmpty()) {
            if (isInCheck(board, isMaximizingPlayer)) {
                // Checkmate - trừng phạt nặng nhưng ưu tiên mate sớm
                int mateValue = isMaximizingPlayer ? -KING_VALUE : KING_VALUE;
                return mateValue + (isMaximizingPlayer ? depth : -depth);
            } else {
                // Stalemate
                return 0;
            }
        }

        // Sắp xếp nước đi
        possibleMoves = orderMoves(possibleMoves, board, isMaximizingPlayer);

        int eval;
        if (isMaximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                List<Long> newBoard = makeMove(board, move);
                eval = minimax(newBoard, depth - 1, alpha, beta, false, originalDepth);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                
                if (beta <= alpha) {
                    // Lưu killer move
                    killerMoves.put(boardHash + depth, move);
                    break;
                }
            }
            eval = maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                List<Long> newBoard = makeMove(board, move);
                eval = minimax(newBoard, depth - 1, alpha, beta, true, originalDepth);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                
                if (beta <= alpha) {
                    // Lưu killer move
                    killerMoves.put(boardHash + depth, move);
                    break;
                }
            }
            eval = minEval;
        }

        // Lưu vào Transposition Table
        transpositionTable.put(boardHash, eval);
        return eval;
    }

    /**
     * Đánh giá bàn cờ cải tiến với nhiều yếu tố.
     */
    private int evaluateBoard(List<Long> board) {
        int materialScore = 0;
        int positionalScore = 0;
        int mobilityScore = 0;
        int numberBox = Config.getInt("numberBox");

        int whiteKingPos = -1, blackKingPos = -1;
        int whitePieces = 0, blackPieces = 0;

        for (int y = 0; y < numberBox; y++) {
            for (int x = 0; x < numberBox; x++) {
                int pieceType = (int)((board.get(y) >> ((numberBox - 1 - x) * 4)) & 15);
                
                if (pieceType == 0) continue;

                boolean isWhite = pieceType <= 8;
                int pieceValue = getPieceValue(pieceType);
                
                // Điểm vật chất
                materialScore += pieceValue;
                
                // Điểm vị trí
                positionalScore += getPositionalValue(pieceType, x, y, isWhite);
                
                // Đếm quân cờ
                if (isWhite) {
                    whitePieces++;
                    if (pieceType == 1) whiteKingPos = y * numberBox + x;
                } else {
                    blackPieces++;
                    if (pieceType == 9) blackKingPos = y * numberBox + x;
                }
            }
        }

        // Điểm di động (số nước đi có thể)
        mobilityScore += generateMoves(board, true).size() * 2;   // Trắng
        mobilityScore -= generateMoves(board, false).size() * 2;  // Đen

        // Điểm an toàn vua
        int kingSafety = 0;
        if (whiteKingPos != -1) {
            kingSafety += evaluateKingSafety(board, whiteKingPos % numberBox, whiteKingPos / numberBox, true);
        }
        if (blackKingPos != -1) {
            kingSafety -= evaluateKingSafety(board, blackKingPos % numberBox, blackKingPos / numberBox, false);
        }

        // Điểm endgame
        int totalPieces = whitePieces + blackPieces;
        int endgameWeight = Math.max(0, 32 - totalPieces) * 2;
        
        return materialScore + positionalScore + mobilityScore + kingSafety + endgameWeight;
    }

    /**
     * Đánh giá an toàn của vua.
     */
    private int evaluateKingSafety(List<Long> board, int kingX, int kingY, boolean isWhite) {
        int safety = 0;
        int numberBox = Config.getInt("numberBox");
        
        // Kiểm tra các ô xung quanh vua
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                
                int newX = kingX + dx;
                int newY = kingY + dy;
                
                if (newX >= 0 && newX < numberBox && newY >= 0 && newY < numberBox) {
                    int pieceType = (int)((board.get(newY) >> ((numberBox - 1 - newX) * 4)) & 15);
                    if (pieceType != 0) {
                        boolean pieceIsWhite = pieceType <= 8;
                        if (pieceIsWhite == isWhite) {
                            safety += 10; // Quân cờ bảo vệ
                        }
                    }
                }
            }
        }
        
        return safety;
    }

    /**
     * Lấy giá trị vị trí của quân cờ.
     */
    private int getPositionalValue(int pieceType, int x, int y, boolean isWhite) {
        int value = 0;
        int adjustedY = isWhite ? y : (7 - y);
        
        if (x >= 0 && x < 8 && adjustedY >= 0 && adjustedY < 8) {
            switch (pieceType) {
                case 6: case 14: value = PAWN_PST[adjustedY][x]; break;
                case 4: case 12: value = KNIGHT_PST[adjustedY][x]; break;
                case 3: case 11: value = BISHOP_PST[adjustedY][x]; break;
                case 5: case 13: value = ROOK_PST[adjustedY][x]; break;
                case 2: case 10: value = QUEEN_PST[adjustedY][x]; break;
                case 1: case 9:  value = KING_PST[adjustedY][x]; break;
            }
        }
        
        return isWhite ? value : -value;
    }

    /**
     * Sắp xếp nước đi để tối ưu cắt tỉa Alpha-Beta.
     */
    private List<Move> orderMoves(List<Move> moves, List<Long> board, boolean isWhite) {
        List<Move> orderedMoves = new ArrayList<>(moves);
        
        orderedMoves.sort((m1, m2) -> {
            int score1 = getMoveScore(m1, board);
            int score2 = getMoveScore(m2, board);
            return Integer.compare(score2, score1); // Sắp xếp giảm dần
        });
        
        return orderedMoves;
    }

    /**
     * Tính điểm cho một nước đi để sắp xếp.
     */
    private int getMoveScore(Move move, List<Long> board) {
        int score = 0;
        
        // Ưu tiên nước đi ăn quân
        if (move.capturedPiece != 0) {
            score += 1000 + getPieceValue(move.capturedPiece);
        }
        
        // Ưu tiên nước đi vào trung tâm
        int centerBonus = 0;
        if (move.toX >= 3 && move.toX <= 4 && move.toY >= 3 && move.toY <= 4) {
            centerBonus = 50;
        }
        score += centerBonus;
        
        return score;
    }

    /**
     * Kiểm tra xem vua có đang bị chiếu không.
     */
    private boolean isInCheck(List<Long> board, boolean isWhite) {
        // Tìm vị trí vua
        int kingX = -1, kingY = -1;
        int numberBox = Config.getInt("numberBox");
        int kingType = isWhite ? 1 : 9;
        
        for (int y = 0; y < numberBox; y++) {
            for (int x = 0; x < numberBox; x++) {
                int pieceType = (int)((board.get(y) >> ((numberBox - 1 - x) * 4)) & 15);
                if (pieceType == kingType) {
                    kingX = x;
                    kingY = y;
                    break;
                }
            }
            if (kingX != -1) break;
        }
        
        if (kingX == -1) return false;
        
        // Kiểm tra xem có quân địch nào tấn công vua không
        List<Move> opponentMoves = generateMoves(board, !isWhite);
        for (Move move : opponentMoves) {
            if (move.toX == kingX && move.toY == kingY) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Tạo hash của bàn cờ để sử dụng trong Transposition Table.
     */
    private String getBoardHash(List<Long> board) {
        StringBuilder sb = new StringBuilder();
        for (Long row : board) {
            sb.append(row.toString());
        }
        return sb.toString();
    }

    /**
     * Lấy giá trị của quân cờ.
     */
    private int getPieceValue(int pieceType) {
        return switch (pieceType) {
            case 6, 14 -> PAWN_VALUE;
            case 4, 12 -> KNIGHT_VALUE;
            case 3, 11 -> BISHOP_VALUE;
            case 5, 13 -> ROOK_VALUE;
            case 2, 10 -> QUEEN_VALUE;
            case 1, 9  -> KING_VALUE;
            default    -> 0;
        } * (pieceType > 8 ? -1 : 1);
    }

    /**
     * Sinh ra tất cả nước đi có thể.
     */
    private List<Move> generateMoves(List<Long> board, boolean isWhiteTurn) {
        List<Move> allMoves = new ArrayList<>();
        int numberBox = Config.getInt("numberBox");

        for (int y = 0; y < numberBox; y++) {
            for (int x = 0; x < numberBox; x++) {
                int pieceType = (int)((board.get(y) >> ((numberBox - 1 - x) * 4)) & 15);
                
                if (pieceType == 0) continue;

                boolean pieceIsWhite = pieceType <= 8;
                if (isWhiteTurn == pieceIsWhite) {
                    int pixelX = Config.getInt("startBoardX") + Config.getInt("boxSize") * x;
                    int pixelY = Config.getInt("startBoardY") + Config.getInt("boxSize") * y;
                    
                    Chess tempChess = createChessFromType(pieceType, pixelX, pixelY);

                    if (tempChess != null) {
                        List<List<Integer>> possibleDestinations = tempChess.getMovedBox(board);
                        for (List<Integer> dest : possibleDestinations) {
                            int toX = dest.get(0);
                            int toY = dest.get(1);
                            int capturedPiece = (int)((board.get(toY) >> ((numberBox - 1 - toX) * 4)) & 15);
                            allMoves.add(new Move(y, x, toY, toX, capturedPiece));
                        }
                    }
                }
            }
        }
        return allMoves;
    }

    /**
     * Tạo đối tượng quân cờ từ mã và vị trí pixel.
     */
    private Chess createChessFromType(int chessType, int x_pixel, int y_pixel) {
        boolean colorType = chessType > 8;
        switch (chessType) {
            case 1: case 9:  return new GeneralChess(false, colorType, x_pixel, y_pixel);
            case 2: case 10: return new QueenChess(false, colorType, x_pixel, y_pixel);
            case 3: case 11: return new BishopChess(false, colorType, x_pixel, y_pixel);
            case 4: case 12: return new KnightChess(false, colorType, x_pixel, y_pixel);
            case 5: case 13: return new RookChess(false, colorType, x_pixel, y_pixel);
            case 6: case 14: return new PawnChess(false, colorType, x_pixel, y_pixel);
            default:         return null;
        }
    }

    /**
     * Tạo ra một trạng thái bàn cờ mới sau khi thực hiện một nước đi.
     */
    private List<Long> makeMove(List<Long> board, Move move) {
        List<Long> newBoard = new ArrayList<>(board);
        int numberBox = Config.getInt("numberBox");

        int fromX_logic = numberBox - 1 - move.fromX;
        int toX_logic = numberBox - 1 - move.toX;

        long pieceType = (newBoard.get(move.fromY) >> (fromX_logic * 4)) & 15;
        
        long updatedSourceRow = BitCalculation.replace4Bits(newBoard.get(move.fromY), fromX_logic * 4, 0);
        newBoard.set(move.fromY, updatedSourceRow);

        long updatedTargetRow = BitCalculation.replace4Bits(newBoard.get(move.toY), toX_logic * 4, pieceType);
        newBoard.set(move.toY, updatedTargetRow);
        
        return newBoard;
    }

    /**
     * Xóa bộ nhớ cache để tối ưu hiệu suất.
     */
    public void clearCache() {
        transpositionTable.clear();
        killerMoves.clear();
    }
}
