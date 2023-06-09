public class Text
{
    private int[] pos;
    private ZEICHENFENSTER window;
    private String content;
    private boolean highlight;
    
    public Text(int[] pos_, String content_){
        pos = pos_;
        window = ZEICHENFENSTER.gibFenster();
        content = content_;
        highlight = false;
    }
    
    public void set_highlight(boolean bool){
        highlight = bool;
    }
    
    public void draw(){
        if(highlight){
            window.zeichneTextHighlight(content, pos[0], pos[1]);
        }else{
             window.zeichneText(content, pos[0], pos[1]);
        }
    }
}
