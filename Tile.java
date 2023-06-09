import java.awt.image.BufferedImage;
public abstract class Tile {
    
    //draws the tile onto the surface
    public abstract void draw();

    //returns the possible direction a player can go on the tile
    public abstract String[] get_available_directions();

    //returns the position of the tile
    public abstract int[] get_pos();

    //returns the rotation of the tile
    public abstract int get_rotation();

    //sets a new position for the tile
    protected abstract void set_pos(int[] new_pos);

    //calculates the position of the tile
    protected abstract void calculate_pos(int[] board_pos, boolean update_board_pos);

    //calculates the cip position for the current tile
    public abstract void calculate_cip_pos(int row, int column);

    //rotates the tile clockwise
    public abstract void rotate();

    //sets if a player is on the tile
    public abstract void set_player(Player pl, int player_num);

    //sets if a cip is on the tile
    public abstract void set_cip(Cip ci);

    //removes the cip
    public abstract void remove_cip();

    //returns if the tile is able to move
    public abstract boolean get_moveable();
    
    //returns the plyers on a tile
    public abstract Player[] get_Players();

    //removes the player from the current tile
    public abstract void remove_player(int pl_number);

    //return the cip of a tile
    public abstract Cip get_cip();

    //returns the position in the board
    public abstract int[] get_board_pos();
        
    //returns the tile_image
    public abstract BufferedImage get_image();
}
