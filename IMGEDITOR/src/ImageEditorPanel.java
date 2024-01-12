import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ImageEditorPanel extends JPanel implements KeyListener {

    Color[][] pixels;

    public ImageEditorPanel() {
        BufferedImage imageIn = null;
        try {
            // the image should be in the main project folder, not in \src or \bin
            imageIn = ImageIO.read(new File("flowerpixelpicture.jpg"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        pixels = makeColorArray(imageIn);
        setPreferredSize(new Dimension(pixels[0].length, pixels.length));
        setBackground(Color.BLACK);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        // paints the array pixels onto the screen
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                g.setColor(pixels[row][col]);
                g.fillRect(col, row, 1, 1);
            }
        }
    }

    public void run() {
        // call your image-processing methods here OR call them from keyboard event
        // handling methods
        // write image-processing methods as pure functions - for example: pixels =
        repaint();
    }

    public Color[][] blur(Color[][] pixels) {
        final int SCALE = 5;
        Color[][] newArr = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[0].length; c++) {
                int red = 0;
                int blue = 0;
                int green = 0;
                int numPixels = 0;
                for (int i = r - SCALE; i < r + SCALE; i++) {
                    for (int j = c - SCALE; j < c + SCALE; j++) {
                        if(i < newArr.length && i > 0 && j < newArr[0].length && j > 0 ){
                            Color myColor = pixels[i][j];
                            red += myColor.getRed();
                            blue += myColor.getBlue();
                            green += myColor.getGreen();
                            numPixels++;
                        }
                    }
                }
                newArr[r][c] = new Color(red / numPixels, green / numPixels, blue / numPixels);
            }
        }
        return newArr;
    }

    public Color[][] blackAndWhite(Color[][] pixels) {
        final int BLACK = 255;
        final int WHITE = 0;
        final int MIDDLE = 127;
        Color[][] newArr = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[0].length; c++) {
                Color myColor = pixels[r][c];
                int red = myColor.getRed();
                int blue = myColor.getBlue();
                int green = myColor.getGreen();
                int color = WHITE;
                if ((red + green + blue) / 3 > MIDDLE) {
                    color = BLACK;
                }
                Color blackOrWhite = new Color(color, color, color);
                newArr[r][c] = blackOrWhite;
            }
        }
        return newArr;
    }

    public Color[][] contrast(Color[][] pixels) {
        final double DARK_SCALE = 1.5;
        final double LIGHT_SCALE = 0.5;
        final int MIDDLE = 127;
        Color[][] newArr = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[0].length; c++) {
                final int MAX = 255;
                Color myColor = pixels[r][c];
                int red = myColor.getRed();
                int blue = myColor.getBlue();
                int green = myColor.getGreen();
                if (myColor.getRed() > MIDDLE) {
                    if ((int) (myColor.getRed() * DARK_SCALE) > 255) {
                        red = MAX;
                    } else {
                        red = (int) (myColor.getRed() * DARK_SCALE);
                    }
                } else {
                    red = (int) (myColor.getRed() * LIGHT_SCALE);
                }
                if (myColor.getGreen() > MIDDLE) {
                    if ((int) (myColor.getGreen() * DARK_SCALE) > 255) {
                        green = MAX;
                    } else {
                        green = (int) (myColor.getGreen() * DARK_SCALE);
                    }
                } else {
                    green = (int) (myColor.getGreen() * LIGHT_SCALE);
                }
                if (myColor.getBlue() > MIDDLE) {
                    if ((int) (myColor.getBlue() * DARK_SCALE) > 255) {
                        blue = MAX;
                    } else {
                        blue = (int) (myColor.getBlue() * DARK_SCALE);
                    }
                } else {
                    blue = (int) (myColor.getBlue() * LIGHT_SCALE);
                }
                Color contrast = new Color(red, green, blue);
                newArr[r][c] = contrast;
            }
        }
        return newArr;
    }

    public Color[][] vintage(Color[][] pixels) {
        final double SCALE = 1.2;
        Color[][] newArr = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[0].length; c++) {
                Color myColor = pixels[r][c];
                int red = myColor.getRed();
                int blue = myColor.getBlue();
                int green = myColor.getGreen();
                if ((int) (myColor.getRed() * SCALE) > 255) {
                    red = 255;
                } else {
                    red = (int) (myColor.getRed() * SCALE);
                }
                if ((int) (myColor.getGreen() * SCALE) > 255) {
                    green = 255;
                } else {
                    green = (int) (myColor.getGreen() * SCALE);
                }
                Color sepia = new Color(red, green, blue);
                newArr[r][c] = sepia;
            }
        }
        return newArr;
    }

    public Color[][] grayScale(Color[][] pixels) {
        Color[][] newArr = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[0].length; c++) {
                Color myColor = pixels[r][c];
                int red = myColor.getRed();
                int blue = myColor.getBlue();
                int green = myColor.getGreen();
                int gray = (red + blue + green) / 3;
                Color grayTone = new Color(gray, gray, gray);
                newArr[r][c] = grayTone;
            }
        }
        return newArr;
    }

    public Color[][] flipHoriz(Color[][] pixels) {
        Color[][] newArr = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[r].length; c++) {
                newArr[r][(newArr[0].length - 1) - c] = pixels[r][c];
            }
        }
        return newArr;
    }

    public Color[][] flipVert(Color[][] pixels) {
        Color[][] newArr = new Color[pixels.length][pixels[0].length];
        for (int r = 0; r < newArr.length; r++) {
            for (int c = 0; c < newArr[r].length; c++) {
                newArr[newArr.length - 1 - r][c] = pixels[r][c];
            }
        }
        return newArr;
    }

    public Color[][] makeColorArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = new Color(image.getRGB(col, row), true);
                result[row][col] = c;
            }
        }
        // System.out.println("Loaded image: width: " +width + " height: " + height);
        return result;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_V) {
            pixels = vintage(pixels);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
            pixels = contrast(pixels);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_B) {
            pixels = blackAndWhite(pixels);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            pixels = grayScale(pixels);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            pixels = blur(pixels);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pixels = flipHoriz(pixels);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            pixels = flipVert(pixels);
            repaint();
        }
    }

    @Override
    public  void keyTyped(KeyEvent e) {
        //unused
    }

    @Override
    public  void keyReleased(KeyEvent e) {
        //unused
    }
}
