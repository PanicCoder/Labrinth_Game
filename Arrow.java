import java.awt.image.BufferedImage;
public class Arrow{

    private ZEICHENFENSTER window;
    private MyImage Img;
    private int[] pos, b_pos;

    public Arrow(Tile tile_to_link, String location_){
        this.window = ZEICHENFENSTER.gibFenster();
        //laden des Bildes
        Img = new MyImage("/Images/arrow.png");
        pos = new int[2];
        b_pos = tile_to_link.get_board_pos();
        calculate_pos(tile_to_link, location_);
    }

    public boolean check_collision(int x, int y){
        if(x > pos[0] && x < pos[0]+Img.get_image().getWidth() && y > pos[1] && y < pos[1]+Img.get_image().getHeight()){
            return true;    
        }
        return false;
    }

    public void calculate_pos(Tile t, String location){
        int image_scale = Img.get_image().getHeight();
        int tile_image_scale = t.get_image().getWidth()/2;
        switch(location){

            case "above":
                set_pos(new int[]{t.get_pos()[0]+tile_image_scale-image_scale/2,t.get_pos()[1]-image_scale});
                break;

            case "under":
                set_pos(new int[]{t.get_pos()[0]+tile_image_scale-image_scale/2,t.get_pos()[1]+tile_image_scale*2});
                break;

            case "left_side":
                set_pos(new int[]{t.get_pos()[0]-image_scale,t.get_pos()[1]+tile_image_scale-image_scale/2});
                break;

            case "right_side":
                set_pos(new int[]{t.get_pos()[0]+tile_image_scale*2,t.get_pos()[1]+tile_image_scale-image_scale/2});
                break;
        }
    }

    public void draw(){
        window.zeichneBild(Img.get_image(), pos[0], pos[1]);
    }

    public int[] get_pos(){
        return pos;
    }

    public int[] get_board_pos(){
        return b_pos;
    }

    private void set_pos(int[] new_pos){
        pos = new_pos;
    }

    public void rotate(){
        Img.rotate();
    }
    
    public BufferedImage get_image(){
        return Img.get_image();
    }
}
