public class GameBoard {
    // SR-SB-OR-OB-E -> int() - boolean Boolean
    private Boolean[][] board;// true = S   false = O  null = E
    private Boolean[][] owner;// true = B   false = R  null = E
    private boolean GameType; // true = PvE   false= PvP
    public GameBoard(int w, int h , boolean type){
        board=new Boolean[h][w];
        owner=new Boolean[h][w];
        GameType=type;
    }

    public int getBoardWidth(){
        return board[0].length;
    }
    public int getBoardHeight(){
        return board.length;
    }
}
