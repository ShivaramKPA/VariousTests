/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qrcodegenerator;

//import com.google.zxing.qrcode.encoder.QRCode;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Navin Reddy: https://www.youtube.com/watch?v=yFpkdkibvfg, kpadhikari
 */
public class QRCodeGenerator {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //String details = "Navin Reddy : Channel Name : Telusko Learnings ...";
        String details = "Krishna Adhikari : Channel Name : Nepalese Learnings ...";
        
        ByteArrayOutputStream out = QRCode.from(details).to(ImageType.JPG).stream(); //from java.io package
        
        File f = new File("/Users/kpadhikari/GitProj/ShivaramKPA/VariousTests/ExtLibs/KrishnaGeneratedQRCode.jpg");
        FileOutputStream fos = new FileOutputStream(f);
        
        fos.write(out.toByteArray());
        fos.flush();
    }
    
}
