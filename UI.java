import java.util.LinkedList;
public class UI {
    
    private int count_arrows;
    private Arrow[] arrows;
    private MyImage Image;
    private LinkedList<Text> texts;
    public UI(){
        count_arrows = 0;
        arrows = new Arrow[12];
        Image = new MyImage("/Images/Background.png");
        texts = new LinkedList<Text>();
    }

    public int[] check_collision(int x, int y){
        Arrow tmp_arrow = null;
        for(int i = 0; i < arrows.length; i++){
            if(arrows[i] != null){
                if(arrows[i].check_collision(x, y)){
                    tmp_arrow = arrows[i];
                    return tmp_arrow.get_board_pos();
                }
            }    
        }
        return new int[]{-1,-1};
    }
    
    public void set_text_highlight(boolean bool, int player_turn){
        texts.get(player_turn-1).set_highlight(bool);
    }
    
    public void add_text(String content, int[] pos){
        texts.add(new Text(pos, content));
    }

    public void add_arrow(Arrow a, int rotation){
        arrows[count_arrows] = a;
        for(int i = 0; i < rotation; i++){
            arrows[count_arrows].rotate();
        }
        count_arrows ++;
    }
    
    public void draw(){
        ZEICHENFENSTER.gibFenster().zeichneBild(Image.get_image(), 0, 0);
        for(int i = 0; i < arrows.length; i++){
            if(arrows[i] != null){
                arrows[i].draw();
            }
        }
        for(int i = 0; i < texts.size(); i++){
            if(texts.get(i) != null){
                texts.get(i).draw();
            }
        }
    }
}
