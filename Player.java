public class Player {
    
    private int points;
    private Cip[] collected_cips;
    private int[][] start_cip_pos;
    private Tile on_Tile;
    private int player_num, cip_num;
    private boolean place_tile;
    private ZEICHENFENSTER window;
    private MyImage Image;
    
    public Player(Tile on_tile, int player_num_){
        points = 0;
        cip_num = 0;
        start_cip_pos = new int[][]{new int[]{1200, 200},new int[]{1600, 200}, new int[]{1200, 600},new int[]{1600, 600}};
        collected_cips = new Cip[21];
        place_tile = true;
        on_Tile = on_tile;
        window = ZEICHENFENSTER.gibFenster();
        player_num = player_num_;
        Image = new MyImage("/Images/Player_"+player_num+".png");
    }

    public int get_points(){
        return points;
    }

    public void set_place_tile(boolean bool){
        place_tile = bool;
    }

    public boolean get_place_tile(){
        return place_tile;
    }
    
    public Tile get_tile(){
        return on_Tile;
    }

    public void set_tile(Tile t){
        on_Tile = t;
    }

    public void add_points(Cip to_add){   
        collected_cips[cip_num] = change_cip_pos(to_add);
        points += to_add.get_value();
        cip_num++;
    }

    private Cip change_cip_pos(Cip c){
        if(cip_num != 0){
            int[] temp_cip_pos = collected_cips[cip_num-1].get_pos();
            if(temp_cip_pos[0] > start_cip_pos[player_num-1][0] + 200){
                c.set_pos(new int[]{start_cip_pos[player_num-1][0],temp_cip_pos[1]+c.get_image().getHeight()/2+75});
            }else{
                c.set_pos(new int[]{temp_cip_pos[0]+c.get_image().getWidth()+20,temp_cip_pos[1]+c.get_image().getHeight()/2});
            }
        }else{
            c.set_pos(start_cip_pos[player_num-1]);
        }
        return c;
    }

    
    public int get_player_num(){
        return player_num;
    }
    
    public void draw(){
        int[] pos = on_Tile.get_pos();
        window.zeichneBild(Image.get_image(), pos[0] + (on_Tile.get_image().getWidth() - Image.get_image().getWidth())/2, pos[1] + (on_Tile.get_image().getHeight() - Image.get_image().getHeight())/2);
        for(int i = 0; i < collected_cips.length; i++){
            if(collected_cips[i] != null){
                collected_cips[i].draw();
            } 
        }
    }
}
