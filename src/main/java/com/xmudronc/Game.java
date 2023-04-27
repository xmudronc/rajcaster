package com.xmudronc;

import java.io.IOException;
import java.util.Arrays;

import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.utils.NonBlockingReader;

import com.xmudronc.renderer.Layers;
import com.xmudronc.renderer.RGB;
import com.xmudronc.renderer.Renderer;
import com.xmudronc.renderer.TerminalRenderer;

public class Game {
    private Size startupSize;
    private Size runSize = new Size(120, 32);
    //private Size runSize = new Size(240, 68);
    private Terminal terminal;
    private NonBlockingReader reader;
    private Renderer renderer;
    private Map map = new Map();
    private Layers layers;
    private RGB[][] buffer1 = new RGB[runSize.getColumns()][runSize.getRows()*2];
    private RGB[][] buffer2 = new RGB[runSize.getColumns()][runSize.getRows()*2];
    private boolean running = false;
    private double px, py, pdx, pdy, pa;
    private Thread input = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                if (reader != null) {
                    while (running) {
                        Integer value = reader.read();
                        if (value >= 65 && value <= 90) {
                            value += 32;
                        }
                        move(value);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    private Thread render = new Thread(new Runnable() {
        @Override
        public void run() {
            while (running) {
                fillBuffers();
                combineLayers();
                renderer.render(buffer1, buffer2);
                buffer2 = Arrays.copyOf(buffer1, buffer1.length);
            }
        }
    });
    
    private void move(Integer key) throws IOException {
        switch (key) {
            case 119: // w
                px+=pdx*3; 
                py+=pdy*3;
                break;
            case 115: // s
                px-=pdx*3; 
                py-=pdy*3;
                break;
            case 97: // a
                pa+=3; 
                pa=FixAng(pa); 
                pdx=Math.cos(Math.toRadians(pa)); 
                pdy=-Math.sin(Math.toRadians(pa));
                break;
            case 100: // d
                pa-=3; 
                pa=FixAng(pa); 
                pdx=Math.cos(Math.toRadians(pa)); 
                pdy=-Math.sin(Math.toRadians(pa));
                break;
            case 112:
                running = false;
                break;
            default:
                break;
        }
    }

    public Game(Terminal terminal) {
        this.terminal = terminal;
        this.terminal.enterRawMode();
        startupSize = terminal.getSize();
        reader = terminal.reader();
    }

    private double FixAng(double a) { 
        if (a > 359) { 
            a-=360;
        } 
        if(a < 0){ 
            a+=360;
        } 
        return a;
    }

    private void fillBuffers() {
        int r,mx,my,mp,dof; 
        double vx,vy,ra,rx,ry,disV,disH; 
        Double xo=null, yo=null;

        buffer1 = new RGB[runSize.getColumns()][runSize.getRows()*2];
        
        ra=FixAng(pa+30); //ray set back 30 degrees
        
        for(r=0;r<runSize.getColumns();r+=4) {
            //---Vertical--- 
            dof=0; 
            disV=100000;

            double tan=Math.tan(Math.toRadians(ra));
                
            if (Math.cos(Math.toRadians(ra))> 0.001) { //looking left
                rx=(((int)px>>6)<<6)+64;      
                ry=(px-rx)*tan+py; 
                xo= (double) 64; 
                yo=-xo*tan;
            } else if (Math.cos(Math.toRadians(ra))<-0.001) { //looking right
                rx=(((int)px>>6)<<6) -0.0001; 
                ry=(px-rx)*tan+py; 
                xo=(double) -64; 
                yo=-xo*tan;
            } else { //looking up or down. no hit  
                rx=px; 
                ry=py; 
                dof=8;
            }                                                  

            while(dof<8) { 
                mx=(int)(rx)>>6; 
                my=(int)(ry)>>6; 
                mp=my*map.getMapX()+mx;                     
                if (mp>0 && mp<map.getMapX()*map.getMapY() && map.getMap()[mp]==1) { //hit  
                    dof=8; 
                    disV=Math.cos(Math.toRadians(ra))*(rx-px)-Math.sin(Math.toRadians(ra))*(ry-py);
                } else { //check next horizontal
                    rx+=xo; 
                    ry+=yo; 
                    dof+=1;
                }                                               
            } 
            vx=rx; 
            vy=ry;

            //---Horizontal---
            dof=0; 
            disH=100000;
            tan=1.0/tan; 

            if (Math.sin(Math.toRadians(ra)) > 0.001) { //looking up 
                ry=(((int)py>>6)<<6) -0.0001; 
                rx=(py-ry)*tan+px; 
                yo=(double) -64; 
                xo=-yo*tan;
            } else if (Math.sin(Math.toRadians(ra)) < -0.001) { //looking down
                ry=(((int)py>>6)<<6)+64;      
                rx=(py-ry)*tan+px; 
                yo= (double) 64; 
                xo=-yo*tan;
            } else { //looking straight left or right
                rx=px; 
                ry=py; 
                dof=8;
            }                                                   
            
            while(dof<8) { 
                mx=(int)(rx)>>6; 
                my=(int)(ry)>>6; 
                mp=my*map.getMapX()+mx;                          
                if (mp>0 && mp<map.getMapX()*map.getMapY() && map.getMap()[mp]==1) { //hit   
                    dof=8; 
                    disH=Math.cos(Math.toRadians(ra))*(rx-px)-Math.sin(Math.toRadians(ra))*(ry-py);
                } else { //check next horizontal
                    rx+=xo; 
                    ry+=yo; 
                    dof+=1;
                }                                               
            } 
            
            if (disV<disH) { //horizontal hit first
                rx=vx; 
                ry=vy; 
                disH=disV; 
            }                                      
                
            double ca=FixAng(pa-ra); 
            disH=disH*Math.cos(Math.toRadians(ca)); //fix fisheye 
            double lineH = (map.getMapS()*runSize.getRows()*2)/(disH); 
            if (lineH>runSize.getRows()*2) { //line height and limit
                lineH=runSize.getRows()*2;
            }                     
            double lineOff = runSize.getRows()/* *2/2 */ - lineH/2; //line offset
            
            //draw vertical wall to buffer
            for (int y = 0; y < runSize.getRows()*2; y++) {
                if (y<lineOff || y>lineOff+lineH) {
                    layers.getLayer(3)[r][y] = new RGB(0, 0, 0, false);
                    //buffer1[r][y] = new RGB(0, 0, 0);
                    //buffer1[r+1][y] = 40;
                    //buffer1[r+2][y] = 40;
                    //buffer1[r+3][y] = 40;
                } else {
                    layers.getLayer(3)[r][y] = calculateLights(new RGB(255, 0, 0));
                    //buffer1[r][y] = new RGB(41, 0, 0);
                    //buffer1[r+1][y] = 41;
                    //buffer1[r+2][y] = 41;
                    //buffer1[r+3][y] = 41;
                }
            }

            ra=FixAng(ra-1);                                                              //go to next ray
        }
    }

    private RGB calculateLights(RGB rgb) {
        int lightStrength = 200;
        return new RGB(rgb.getR() - lightStrength, rgb.getG() - lightStrength, rgb.getB() - lightStrength);
    }

    private void combineLayers() {
        //RGB[][] layer1 = layers.getLayer(2);
        //RGB[][] layer2 = layers.getLayer(2);
        RGB[][] layer3 = layers.getLayer(3);
        RGB[][] layer4 = layers.getLayer(4);
        for (int x = 0; x < buffer1.length; x+=4) {
            for (int y = 0; y < buffer1[x].length; y++) {
                buffer1[x][y] = layer4[x][y];
                if (layer3[x][y].isOpaque()) {
                    buffer1[x][y] = layer3[x][y];
                }
                // if (layer2[x][y].isOpaque()) {
                //     buffer1[x][y] = layer2[x][y];
                // }
                // if (layer1[x][y].isOpaque()) {
                //     buffer1[x][y] = layer1[x][y];
                // }
            }
        }
    }

    public void run() throws IOException, InterruptedException {
        px=150; 
        py=400; 
        pa=90;
        pdx=Math.cos(Math.toRadians(pa)); 
        pdy=-Math.sin(Math.toRadians(pa)); 

        layers = new Layers(runSize);
        renderer = new TerminalRenderer(startupSize, runSize);
        renderer.init(buffer1, buffer2);

        RGB[][] layer4 = layers.getLayer(4);
        for (int x = 0; x < layer4.length; x+=4) {
            for (int y = 0; y < layer4[x].length; y++) {
                layer4[x][y] = new RGB(0, 128, y<layer4[x].length/2?128:0);
            }
        }

        running = true;

        input.start();
        render.start();

        input.join();
        render.join();

        renderer.end();
    }
}
