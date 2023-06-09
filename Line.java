import java.awt.image.BufferedImage;
public class Line extends Tile{
    
    private ZEICHENFENSTER window;
    private MyImage Image;
    private int[] pos, board_pos;
    private Player[] players;
    private Cip cip;
    private boolean moveable;

    public Line(int[] b_pos_, boolean moveable_){
        this.window = ZEICHENFENSTER.gibFenster();
        this.cip = null;
        this.players = new Player[4];
        //laden des Bildes
        Image = new MyImage("/Images/Line.png");
        pos = new int[2];
        board_pos = b_pos_;
        calculate_pos(board_pos, true);
        moveable = moveable_;
    }

    protected void calculate_pos(int[] board_pos_, boolean update_board_pos){
        int image_scale = Image.get_image().getWidth();
        set_pos(new int[]{board_pos_[1]*image_scale+100,board_pos_[0]*image_scale+35});
        calculate_cip_pos(board_pos_[0], board_pos_[1]);
        if(update_board_pos){
            board_pos = board_pos_;
        } 
    }

    public String[] get_available_directions(){
        switch(Image.get_rotation()){
            case 1:
                return new String[]{"Right","Left"};
            case 2:
                return new String[]{"Up","Down"};
            case 3:
                return new String[]{"Right","Left"};
            default:
                return new String[]{"Up","Down"};
        }
    }

    public void remove_cip(){
        cip = null;
    }

    public void calculate_cip_pos(int row, int column){
        if(cip != null){
            int x = Image.get_image().getWidth() / 2;
            int y = Image.get_image().getHeight() / 2;
            cip.set_pos(new int[]{pos[0]+x, pos[1]+y});
        }
    }

    public void draw(){
        window.zeichneBild(Image.get_image(), pos[0], pos[1]);
        if(cip != null){
            cip.draw();
            
        }
        for(int i = 0; i < players.length; i++){
            if(players[i] != null){
                players[i].draw();
            }
        }
    }

    public int[] get_pos(){
        return pos;
    }

    protected void set_pos(int[] new_pos){
        pos = new_pos;
    }

    public void remove_player(int pl_number){
        players[pl_number] = null;
    }
    
    public int get_rotation(){
        return Image.get_rotation();
    }

    public void rotate(){
        Image.rotate();
    }

    public void set_player(Player pl, int player_num){
        players[player_num] = pl;
    }

    public void set_cip(Cip ci){
        cip = ci;
    }   

    public boolean get_moveable(){
        return moveable;
    }

    public Player[] get_Players(){
        return players;
    }

    public Cip get_cip(){
        return cip;
    }

    public int[] get_board_pos(){
        return board_pos;
    }
    
    public BufferedImage get_image(){
        return Image.get_image();
    }
}
