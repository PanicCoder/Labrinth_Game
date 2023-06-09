public class Board {
    
    private Tile[][] board;
    private Tile spare_tile;
    private Player[] player_list;

    public Board(){
        this.board = new Tile[7][7];
        spare_tile = null;
        player_list = new Player[4];
    }

    public Tile get_spare_tile(){
        return spare_tile;
    }

    public void add_tile(int row, int column, Tile new_tile){
        board[row][column] = new_tile;
    }

    public void add_player(Tile t, Player p, int player_num){
        t.set_player(p, player_num);
        player_list[player_num] = p;
    }
    
    public void set_spare_tile(Tile spare_tile_){
        spare_tile  = spare_tile_;
    }
    
    public Tile get_tile(int row, int column){
        return board[row][column];
    }

    public Tile[][] get_board(){
        return board;
    }
    
    public Player get_player(int player_num){
        return player_list[player_num];
    }

    public void add_cip(int row, int column, String value){
       if(board[row][column] != null){
            board[row][column].set_cip(new Cip(value));
            board[row][column].calculate_cip_pos(row, column);
        }
    }
    
    public void draw(){
        for(int i = 0; i < board.length; i++){
            for(int k = 0; k < board[i].length;k++){
                if (board[i][k] != null){
                    board[i][k].draw();
                }
            }
        }
        spare_tile.draw();
        
    }
}
