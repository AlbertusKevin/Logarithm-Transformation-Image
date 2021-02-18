import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Main {
    private static void ImageProces(String img, int gambarKe){
        try {
            // buat file baru dengan pathname img, lalu simpan ke buffer
            BufferedImage input = ImageIO.read(new File(img));

            // ambil tinggi dan lebar
            int width = input.getWidth(null);
            int height = input.getHeight(null);

            //proses gambar log transform
            logTransform(input ,width, height, gambarKe);
        }catch (IOException io){
            JOptionPane.showMessageDialog(null, "Gagal Load Gambar\n"+io);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error \n"+e);
        }
    }

    private static void logTransform(BufferedImage im, int w, int h, int gambarKe){
        final int C = 100;
        try{
            // looping dari atas ke bawah, kiri ke kanan
            for(int x = 0; x < w; x++){
                for (int y = 0; y < h; y++){
                    // inisialisasi nilai pixel awal yang akan menampung nilai perubahan
                    int pix = 0;

                    // ambil value rgba, dalam bentuk byte, lalu dipetakan ke masing-masing channel
                    int alpha = 0xff & (im.getRGB(x,y)>>24);
                    int red = 0xff &(im.getRGB(x,y)>>16);
                    int green = 0xff &(im.getRGB(x,y)>>8);
                    int blue = 0xff & im.getRGB(x,y);

                    // operasi transformasi log
                    red = (int)Math.round( C*Math.log10((double)red+1));
                    green = (int)Math.round( C*Math.log10((double)green+1));
                    blue = (int)Math.round( C*Math.log10((double)blue+1));

                    // gabungkan ketiganya dengan operasi bitwise,
                    // untuk membentuk nilai rgba yang baru
                    pix = pix | blue;
                    pix = pix | (green <<8);
                    pix = pix | (red <<16);
                    pix = pix | (alpha <<24);

                    // set nilai rbg baru pada gambar
                    im.setRGB( x, y, pix);
                }
            }

            // buat gambar baru dengan format png, pada file ResultLogTransform.png,
            // berdasarkan data value rgb dari im yang sudah di set
            ImageIO.write(im, "PNG", new File("OutputImage/ResultLogTransform"+gambarKe+".png"));
            System.out.println("Logarithmic Transformation, image "+gambarKe+" Success");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public static void main(String[] args) {
//        for (int jumlah = 1; jumlah <= 6; jumlah++){
//            ImageProces("ImageSet/input"+jumlah+".jpg", jumlah);
//        }
        ImageProces("ImageSet/input"+7+".jpg", 7);

//        System.out.println();
//        if(y == 5){
//            System.exit(0);
//        }
//        String bits = String.format("%8s", Integer.toBinaryString(alpha)).replace(' ','0');
//        System.out.printf("%d = %s\n",alpha, bits);
    }
}
