import java.util.*;
public class Main {
    
    private Board b;
    private UI ui;
    private Lobby lobby;
    private Game_loop g_loop;
    private LinkedList<String> cip_value;
    private String[] board_attributes;
    private int cip_num, player_num;
    private int[][]text_pos;
    public Main(){
        this.b = new Board();
        this.ui = new UI();
        this.lobby = new Lobby();
        int player_count = lobby.loop();
        g_loop = new Game_loop(b, new Rules(b), ui, player_count);
        cip_num = 0;
        player_num = 1;
        int corner_num = 0;
        int cross_num = 0;
        int count = 0;
        
        cip_value = new LinkedList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","25"));
        Collections.shuffle(cip_value);
        text_pos = new int[][]{new int[]{1200, 150},new int[]{1600, 150}, new int[]{1200, 550},new int[]{1600, 550}};
        board_attributes = new String[]{"Corner","Random","Cross","Random","Cross","Random","Corner",
                                        "Random","Cip","Cip","Cip","Cip","Cip","Random",
                                        "Cross","Cip","Start_tile","Cip","Start_tile","Cip","Cross",
                                        "Random","Cip","Cip","Cip","Cip","Cip","Random",
                                        "Cross","Cip","Start_tile","Cip","Start_tile","Cip","Cross",
                                        "Random","Cip","Cip","Cip","Cip","Cip","Random",
                                        "Corner","Random","Cross","Random","Cross","Random","Corner",};
        int[] start_tile_rotation = new int[]{0,1,3,2};
        int[] corner_rotations = new int[]{0,1,3,2};
        int[] cross_rotations = new int[]{1,1,0,2,0,2,3,3};
        for(int i = 0; i < 7;i++){
            for(int k = 0; k < 7; k++){
                Tile t = null;
                if( count < board_attributes.length){
                    switch(board_attributes[count]){
                        
                        case "Corner":
                            t = new Curve(new int[]{i,k}, false);
                            for(int j = 0; j < corner_rotations[corner_num]; j++){
                                t.rotate();
                            }
                            corner_num++;
                            break;

                        case "Cross":
                            t = new Cross(new int[]{i,k}, false);
                            for(int j = 0; j < cross_rotations[cross_num]; j++){
                                t.rotate();
                            }
                            cross_num++;
                            break;

                        case "Random":
                            t = do_random_rotation(random_tile(i, k));
                            break;
                        
                        case "Start_tile":
                            t = new Start_tile( new int[]{i,k},String.valueOf(start_tile_rotation[player_num-1]+1));
                            if(player_num < player_count+1){
                                b.add_player(t,new Player(t, player_num), player_num-1); 
                            }
                            for(int j = 0; j < start_tile_rotation[player_num-1]; j++){
                                t.rotate();
                            }
                            player_num++;
                            break;
                        
                        case "Cip":
                            t = do_random_rotation(random_tile(i, k));
                            b.add_tile(i, k, t);
                            do_cip(i, k);
                            break;

                        default:
                            break;
                    }
                    count++;
                    b.add_tile(i, k, t);
                }else{
                    b.add_tile(i, k, random_tile(i, k));
                }
            }
        }
        b.set_spare_tile(random_tile(6, 10));
        g_loop.calculate_arrows(); 
        do_text(player_count);
        g_loop.loop();
    }

    private Tile random_tile(int i, int k){
        int tile_num = 1 + (int)(Math.random() * 3);
        switch(tile_num){
            case 1:
                return do_random_rotation(new Cross(new int[]{i,k}, true));
            case 2:
                return do_random_rotation(new Line(new int[]{i,k}, true));
            case 3:
                return do_random_rotation(new Curve(new int[]{i,k}, true));
            default:
                return do_random_rotation(new Cross(new int[]{i,k}, true));
        }
        
    }
    
    private void do_text(int player_count){
        for(int i = 0; i < player_count; i++){
            ui.add_text("Spieler "+(i+1)+":", text_pos[i]);
        }
        ui.set_text_highlight(true, 1);
    }
    
    private void do_cip(int row, int column){ 
            b.add_cip(row, column, cip_value.get(cip_num));
            if(cip_num < 20){
                cip_num++;
            }
        
    }

    private Tile do_random_rotation(Tile t){
        int rot_num = 1 + (int)(Math.random() * 3);
        for(int i = 0; i < rot_num; i++){
            t.rotate();
        }
        return t;
    }
}
