public class Rules {
    
    private Board board;
    private int cip_num_to_pick_up;

    public Rules(Board b){
        this.board = b;
        cip_num_to_pick_up = 1;
    }

    public void collect_coin(Player p, Tile t){
        if(t.get_cip() == null){
            return;
        }
        if(t.get_cip().get_value() == cip_num_to_pick_up){
            p.add_points(t.get_cip());
            t.remove_cip();
            board.draw();   
            if(cip_num_to_pick_up == 20){
                cip_num_to_pick_up = 25;
                return;
            }
            cip_num_to_pick_up++;
        }
        
    }

    public boolean check_winner(){
        if(cip_num_to_pick_up == 26){
            return true;
        }
        return false;
    }

    public boolean check_player_move(String direction, Tile current_tile, int player_num){
        if(board.get_player(player_num).get_place_tile()){
            return false;
        }
        
        String[] av_dir = current_tile.get_available_directions();
        Tile tile_to_move_to;       
        switch(direction){
            case "Down":
                if(current_tile.get_board_pos()[0]+1 < 7){
                    tile_to_move_to = board.get_tile(current_tile.get_board_pos()[0]+1, current_tile.get_board_pos()[1]);
                }else{
                    return false;
                }
                break;
            case "Right":
                if(current_tile.get_board_pos()[1]+1 < 7){
                    tile_to_move_to = board.get_tile(current_tile.get_board_pos()[0], current_tile.get_board_pos()[1]+1);
                }else{
                    return false;
                }
                break;
            case "Left":
                if(current_tile.get_board_pos()[1]-1 >= 0){
                    tile_to_move_to = board.get_tile(current_tile.get_board_pos()[0], current_tile.get_board_pos()[1]-1);
                }else{
                    return false;
                }
                break;
            default:
                if(current_tile.get_board_pos()[0]-1 >= 0){
                    tile_to_move_to = board.get_tile(current_tile.get_board_pos()[0]-1, current_tile.get_board_pos()[1]);
                }else{
                    return false;
                }
                break;
        }
        String[] av_dir_next = tile_to_move_to.get_available_directions();
        av_dir_next = reverse_list(av_dir_next);
        for(int i = 0; i < av_dir.length; i++){
            if (direction.equalsIgnoreCase(av_dir[i])){
                for(int k = 0; k < av_dir_next.length; k++){
                    if(av_dir[i].equalsIgnoreCase(av_dir_next[k])){
                        return true;
                    }
                } 
            } 
        }
        return false;
    }

    private String[] reverse_list(String[] list){
        for(int i = 0; i < list.length; i++){
            switch(list[i]){
                case "Up":
                    list[i] = "Down";
                    break;
                case "Down":
                    list[i] = "Up";
                    break;
                case "Left":
                    list[i] = "Right";
                    break;
                case "Right":
                    list[i] = "Left";
                    break;
            }
        }
        return list;
    }
}
