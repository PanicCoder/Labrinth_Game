import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class Lobby
{
    
    private MyImage Image;
    private int player_count, pos_x, pos_y;
    private boolean in_lobby;
    private int[][] image_pos, image_dimension;
    private ZEICHENFENSTER window;
    
    public Lobby(){
        window = ZEICHENFENSTER.gibFenster();
        Image = new MyImage("/Images/Start_screen_2.png");
        player_count = 2;
        image_pos = new int[][]{new int[]{773,764},new int[]{1045,725}, new int[]{685,123}};
        image_dimension = new int[][]{new int[]{87,13}, new int[]{90,90}, new int[]{550,205}};
        pos_x = 0;
        pos_y = 0;
        in_lobby = true;
        window.get_panel().addMouseListener(new MouseListener(){
            @Override     
            public void mouseClicked(MouseEvent e) {
                }
                @Override     
            public void mousePressed(MouseEvent e) {
                pos_x=e.getX();         
                pos_y=e.getY();
             ;}
            @Override     
            public void mouseReleased(MouseEvent e) {
                if(in_lobby)
                {
                    int col = get_collision(pos_x, pos_y);
                    if(col == 2){
                        in_lobby = false;
                        
                    }else{
                        update_player_count(col);
                    }
                }
            ;}      
            @Override     
            public void mouseEntered(MouseEvent e) {         
            ;}      
            @Override     
            public void mouseExited(MouseEvent e) {
            ;}        
            });
    }
    
    public int loop(){
        draw();
        while(in_lobby){
            window.warte(100);
        }
        
        return player_count;
    }
    
    public void draw(){
        window.zeichneBild(Image.get_image(), 0, 0);
    }

    private void update_player_count(int col_num){
        if(col_num == -1){
            return;
        }
        if(col_num == 0){
            if(player_count -1 > 1){
                player_count--;
            }
        }else{
            if(player_count +1 < 5){
                player_count++;
            }
        }
        Image = new MyImage("/Images/Start_screen_"+ player_count +".png");
        draw();
    }
    
    private int get_collision(int x, int y){
        for(int i = 0; i < 3; i++){
            if(x > image_pos[i][0] && x < image_pos[i][0]+image_dimension[i][0] && y > image_pos[i][1] && y < image_pos[i][1]+image_dimension[i][1]){
                return i;    
            }
        }
        return -1;
    }
    
}
