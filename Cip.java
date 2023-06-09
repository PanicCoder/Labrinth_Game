import java.awt.image.BufferedImage;
public class Cip {
    
    private ZEICHENFENSTER window;
    private MyImage Img;
    private BufferedImage image;
    private int[] pos;
    private String value;

    public Cip(String value_){
        window = ZEICHENFENSTER.gibFenster();
        Img = new MyImage("/Images/Coin_"+value_+".png");
        image = Img.get_image();
        pos = new int[2];
        value = value_;
    }

    public void draw(){
        window.zeichneBild(image, pos[0], pos[1]);
    }

    public void set_pos(int[] new_pos){
        int x = new_pos[0]-image.getWidth()/2;
        int y = new_pos[1]-image.getHeight()/2;
        pos = new int[]{x,y}; 
    }

    public int[] get_pos(){
        return pos;
    }

    public BufferedImage get_image(){
        return image;
    }

    public int get_value(){
        return Integer.valueOf(value);
    }
}
