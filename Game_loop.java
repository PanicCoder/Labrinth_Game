
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game_loop{

    private Board board;
    private Rules rules;
    private UI ui;
    private ZEICHENFENSTER window;
    private int player_turn, pos_x, pos_y, max_players;
    private boolean won;

    public Game_loop(Board b, Rules r, UI ui_, int max_players_){
        this.board = b;
        this.rules = r;
        this.ui = ui_;
        this.window = ZEICHENFENSTER.gibFenster();
        pos_x = 0;
        pos_y = 0;
        max_players = max_players_;
        won = false;
        player_turn = 1;

    }

    private void get_tile_insert(){
        if(!board.get_player(player_turn-1).get_place_tile()){
            return;
        }
        int[] check = ui.check_collision(pos_x, pos_y);
        if(check[1] != -1){
            if(check[1] == 0){
                move_row(check[0], board.get_spare_tile(), true);
            }else if(check[1] == 6){
                move_row(check[0], board.get_spare_tile(), false);
            }else if(check[0] == 0){
                move_column(check[1], board.get_spare_tile(), true);
            }else if(check[0] == 6){
                move_column(check[1], board.get_spare_tile(), false);
            }
            board.get_player(player_turn-1).set_place_tile(false);
        }
    }

    public void loop(){
        ui.draw();
        board.draw();
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
                    if(e.getX() != 0 || e.getY() != 0)
                    {
                        get_tile_insert();
                    }
                    ;}      

                @Override     
                public void mouseEntered(MouseEvent e) {         
                    ;}      

                @Override     
                public void mouseExited(MouseEvent e) {
                    ;}        
            });

        window.get_Frame().addKeyListener(new KeyListener()
            {
                @Override
                public void keyPressed(KeyEvent e)
                {   
                    switch(e.getKeyCode()){

                        case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;

                        case KeyEvent.VK_R:
                        board.get_spare_tile().rotate();
                        break;

                        case KeyEvent.VK_UP:
                        if(rules.check_player_move("Up", board.get_player(player_turn-1).get_tile(),player_turn-1)){
                            move_player("Up");
                            rules.collect_coin(board.get_player(player_turn-1), board.get_player(player_turn-1).get_tile());
                        }
                        break;

                        case KeyEvent.VK_DOWN:
                        if(rules.check_player_move("Down", board.get_player(player_turn-1).get_tile(),player_turn-1)){
                            move_player("Down");
                            rules.collect_coin(board.get_player(player_turn-1), board.get_player(player_turn-1).get_tile());
                        }
                        break;

                        case KeyEvent.VK_LEFT:
                        if(rules.check_player_move("Left", board.get_player(player_turn-1).get_tile(),player_turn-1)){
                            move_player("Left");
                            rules.collect_coin(board.get_player(player_turn-1), board.get_player(player_turn-1).get_tile());
                        }
                        break;

                        case KeyEvent.VK_RIGHT:
                        if(rules.check_player_move("Right", board.get_player(player_turn-1).get_tile(),player_turn-1)){
                            move_player("Right");
                            rules.collect_coin(board.get_player(player_turn-1), board.get_player(player_turn-1).get_tile());
                        }
                        break;

                        case KeyEvent.VK_ENTER:                        
                        board.get_player(player_turn-1).set_place_tile(true);
                        ui.set_text_highlight(false, player_turn);
                        if(player_turn < max_players){
                            player_turn++;
                        }else{
                            player_turn = 1;
                        }
                        ui.set_text_highlight(true, player_turn);

                        break;
                    }
                    window.loescheAlles();
                    ui.draw();
                    board.draw();
                }

                @Override
                public void keyReleased(KeyEvent e)
                {  

                }

                @Override
                public void keyTyped(KeyEvent e)
                {}

            });
        while(!rules.check_winner()){
            window.warte(10);
            won = true;

        }
        java.util.EventListener listeners[] = window.get_Frame().getListeners(KeyListener.class);   
        if(listeners.length!=0){
            window.get_Frame().removeKeyListener((KeyListener)listeners[0]);
        }

        int curent_max = board.get_player(0).get_points();
        int pl = 0;
        for(int i = 0; i < max_players; i++){
            if(curent_max < board.get_player(i).get_points()){
                curent_max = board.get_player(i).get_points();
                pl = i;
            }
        }
        MyImage Image = new MyImage("/Images/End_screen_"+(pl+1)+".png");
        window.zeichneBild(Image.get_image(), (window.get_Frame().getWidth()/2)-Image.get_image().getWidth()/2-350, (window.get_Frame().getHeight()/2)-Image.get_image().getWidth()/2); 
        window.zeichneTextPoints("Points:"+String.valueOf(board.get_player(pl).get_points()), 500, 675);
        while(true){
        }
    }

    public void calculate_arrows(){
        Tile[][] b = board.get_board();
        for(int i = 0; i < b.length; i++){
            if(b[0][i].get_moveable()){
                ui.add_arrow(new Arrow(b[0][i], "above"),1);
            }if(b[i][0].get_moveable()){
                ui.add_arrow(new Arrow(b[i][0], "left_side"),0);
            }if(b[6][i].get_moveable()){
                ui.add_arrow(new Arrow(b[6][i], "under"),3);
            }if(b[i][6].get_moveable()){
                ui.add_arrow(new Arrow(b[i][6], "right_side"),2);
            }
        }
    }

    public void move_column(int column, Tile t_to_insert, boolean from_top){
        if(from_top){
            if(board.get_tile(0, column).get_moveable()){
                Tile last_tile = board.get_tile(6,column);         
                for(int i = 5; i >= 0; i--){
                    board.get_tile(i, column).calculate_pos(new int[]{i+1, column}, true);
                    board.add_tile(i+1, column, board.get_tile(i, column));
                    board.draw();
                    window.warte(250);
                } 
                if(last_tile.get_cip() != null){
                    Cip c = last_tile.get_cip();
                    last_tile.remove_cip();
                    t_to_insert.set_cip(c);

                }
                t_to_insert.calculate_pos(new int[]{0, column},true);
                board.add_tile(0, column, t_to_insert);
                Player[] pl_list = last_tile.get_Players();
                for(int j = 0; j < pl_list.length; j++){
                    if(pl_list[j] != null){
                        pl_list[j].set_tile(board.get_tile(0, column));
                        board.get_tile(0, column).set_player(pl_list[j], j);
                    }
                }
                last_tile.calculate_pos(new int[]{6, 10},false);
                board.set_spare_tile(last_tile);
                board.draw();
            }
        }else{
            if(board.get_tile(6, column).get_moveable()){
                Tile last_tile = board.get_tile(0,column);         
                for(int i = 1; i < 7; i++){
                    board.get_tile(i, column).calculate_pos(new int[]{i-1, column}, true);
                    board.add_tile(i-1, column, board.get_tile(i, column));
                    board.draw();
                    window.warte(100);
                } 
                if(last_tile.get_cip() != null){
                    Cip c = last_tile.get_cip();
                    last_tile.remove_cip();
                    t_to_insert.set_cip(c);

                }
                t_to_insert.calculate_pos(new int[]{6, column},true);
                board.add_tile(6, column, t_to_insert);
                Player[] pl_list = last_tile.get_Players();
                for(int j = 0; j < pl_list.length; j++){
                    if(pl_list[j] != null){
                        pl_list[j].set_tile(board.get_tile(6, column));
                        board.get_tile(6, column).set_player(pl_list[j], j);
                    }
                }
                last_tile.calculate_pos(new int[]{6, 10},false);
                board.set_spare_tile(last_tile);
                board.draw();
            }
        }
    }

    public void move_row(int row, Tile t_to_insert, boolean from_left){
        if(from_left){
            if(board.get_tile(row, 0).get_moveable()){
                Tile last_tile = board.get_tile(row,6);         
                for(int i = 5; i >= 0; i--){
                    board.get_tile(row, i).calculate_pos(new int[]{row, i+1}, true);
                    board.add_tile(row, i+1, board.get_tile(row, i));
                    board.draw();
                    window.warte(100);
                } 
                if(last_tile.get_cip() != null){
                    Cip c = last_tile.get_cip();
                    last_tile.remove_cip();
                    t_to_insert.set_cip(c);

                }
                t_to_insert.calculate_pos(new int[]{row, 0},true);
                board.add_tile(row, 0, t_to_insert);
                Player[] pl_list = last_tile.get_Players();
                for(int j = 0; j < pl_list.length; j++){
                    if(pl_list[j] != null){
                        pl_list[j].set_tile(board.get_tile(row, 0));
                        board.get_tile(row, 0).set_player(pl_list[j], j);
                    }
                }
                last_tile.calculate_pos(new int[]{6, 10},false);
                board.set_spare_tile(last_tile);
                board.draw();
            }
        }else{
            if(board.get_tile(row, 6).get_moveable()){
                Tile last_tile = board.get_tile(row,0);         
                for(int i = 1; i < 7; i++){
                    board.get_tile(row, i).calculate_pos(new int[]{row, i-1}, true);
                    board.add_tile(row, i-1, board.get_tile(row, i));
                    board.draw();
                    window.warte(100);
                } 
                if(last_tile.get_cip() != null){
                    Cip c = last_tile.get_cip();
                    last_tile.remove_cip();
                    t_to_insert.set_cip(c);

                }
                t_to_insert.calculate_pos(new int[]{row, 6},true);
                board.add_tile(row, 6, t_to_insert);
                Player[] pl_list = last_tile.get_Players();
                for(int j = 0; j < pl_list.length; j++){
                    if(pl_list[j] != null){
                        pl_list[j].set_tile(board.get_tile(row, 6));
                        board.get_tile(row, 6).set_player(pl_list[j], j);
                    }
                }
                last_tile.calculate_pos(new int[]{6, 10},false);
                board.set_spare_tile(last_tile);
                board.draw();
            }
        }

    }

    public void move_player(String direction){
        Player pl = board.get_player(player_turn-1);
        Tile t = pl.get_tile();
        Tile new_tile = null;
        int[] pos = t.get_board_pos();
        t.remove_player(player_turn-1);
        switch (direction){
            case "Left":
            if(pos[1]-1 >-1){
                new_tile = board.get_tile(pos[0], pos[1]-1);
            }else{
                new_tile = board.get_tile(pos[0], 6);
            }
            break;

            case "Right":
            if(pos[1]+1 < 7){
                new_tile = board.get_tile(pos[0], pos[1]+1);
            }else{
                new_tile = board.get_tile(pos[0], 0);
            }
            break;

            case "Up":
            if(pos[0]-1 >-1){
                new_tile = board.get_tile(pos[0]-1, pos[1]);
            }else{
                new_tile = board.get_tile(6, pos[1]);
            }
            break;

            case "Down":
            if(pos[0]+1 < 7){
                new_tile = board.get_tile(pos[0]+1, pos[1]);
            }else{
                new_tile = board.get_tile(0, pos[1]);
            }
            break;

            default:
            ;
        }
        pl.set_tile(new_tile);
        new_tile.set_player(pl, player_turn-1);
    }

}