import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
public class MyImage
{
    private int rotation;
    private BufferedImage image;

    public MyImage(String path){
        try{
            image  =  ImageIO.read(this.getClass().getResource(path)); 
        }catch (IOException e){
            e.printStackTrace();
        }  
        rotation = 0;
    }
    
    public BufferedImage get_image(){
        return image;
    }
    
    public void rotate(){
        // Rotation information
        double rotationRequired = Math.toRadians (90);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        image = op.filter(image, null);
        if(rotation < 3){
            rotation++;
        }else{
            rotation = 0;
        }
    }
    
    public int get_rotation(){
        return rotation;
    }
}
