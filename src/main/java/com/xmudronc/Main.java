package com.xmudronc;

import java.io.IOException;
import java.util.ArrayList;

import org.jline.terminal.Size;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import com.xmudronc.renderer.Renderer;
import com.xmudronc.renderer.TerminalRenderer;

public class Main {
    private static Size startupSize;
    private static Size runSize = new Size(120, 32);
    private static Terminal terminal;
    private static NonBlockingReader reader;
    private static Renderer renderer;
    private static Map map = new Map();
    private static ArrayList<ArrayList<Integer>> buffer1 = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> buffer2 = new ArrayList<>();
    private static boolean mainBuffer = true;
    private static boolean running = false;
    private static double px, py, pdx, pdy, pa;
    private static Thread input = new Thread(new Runnable() {
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
    
    private static void move(Integer key) throws IOException {
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

    private static void resizeTerminal(Integer columns, Integer rows) {
        System.out.print("\u001B[8;" + rows + ";" + columns + "t"); 
    }

    private static double FixAng(double a) { 
        if (a > 359) { 
            a-=360;
        } 
        if(a < 0){ 
            a+=360;
        } 
        return a;
    }

    private static void fillBuffers() {
    int r,mx,my,mp,dof,side; 
    double vx,vy,ra,rx,ry,disV,disH; 
    Double xo=null, yo=null;
    boolean horFirst;
    
    ra=FixAng(pa+30); //ray set back 30 degrees
    
    for(r=0;r<runSize.getColumns();r+=4)
    {
        //---Vertical--- 
        horFirst=false;

        dof=0; 
        side=0; 
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
            horFirst=true;
        }                                      
            
        double ca=FixAng(pa-ra); 
        disH=disH*Math.cos(Math.toRadians(ca)); //fix fisheye 
        double lineH = (map.getMapS()*runSize.getRows())/(disH); 
        if (lineH>runSize.getRows()) { //line height and limit
            lineH=runSize.getRows();
        }                     
        double lineOff = runSize.getRows()/2 - lineH/2; //line offset
        
        //draw vertical wall to buffer
        for (int y = 1; y < runSize.getRows(); y++) {
            if (mainBuffer) {
                if (y<lineOff || y>lineOff+lineH) {
                    buffer2.get(r).set(y, 40);
                    buffer2.get(r+1).set(y, 40);
                    buffer2.get(r+2).set(y, 40);
                    buffer2.get(r+3).set(y, 40);
                } else {
                    if (horFirst) {
                        buffer2.get(r).set(y, 41);
                        buffer2.get(r+1).set(y, 41);
                        buffer2.get(r+2).set(y, 41);
                        buffer2.get(r+3).set(y, 41);
                    } else {
                        buffer2.get(r).set(y, 101);
                        buffer2.get(r+1).set(y, 101);
                        buffer2.get(r+2).set(y, 101);
                        buffer2.get(r+3).set(y, 101);
                    }
                }
            } else {
                if (y<lineOff || y>lineOff+lineH) {
                    buffer1.get(r).set(y, 40);
                    buffer1.get(r+1).set(y, 40);
                    buffer1.get(r+2).set(y, 40);
                    buffer1.get(r+3).set(y, 40);
                } else {
                    if (horFirst) {
                        buffer1.get(r).set(y, 41);
                        buffer1.get(r+1).set(y, 41);
                        buffer1.get(r+2).set(y, 41);
                        buffer1.get(r+3).set(y, 41);
                    } else {
                        buffer1.get(r).set(y, 101);
                        buffer1.get(r+1).set(y, 101);
                        buffer1.get(r+2).set(y, 101);
                        buffer1.get(r+3).set(y, 101);
                    }
                }
            }
        }

        ra=FixAng(ra-1);                                                              //go to next ray
    }
    }

    public static void main(String[] args) {
        try {
            terminal = TerminalBuilder.builder().build();
            terminal.enterRawMode();
            startupSize = terminal.getSize();
            resizeTerminal(runSize.getColumns(), runSize.getRows());
            reader = terminal.reader();

            ArrayList<Integer> column = new ArrayList<>();
            for (int y = 0; y < runSize.getRows(); y++) {
                column.add(40);
            }
            for (int x = 0; x < runSize.getColumns(); x++) {
                buffer1.add(column);
                buffer2.add(column);
            }

            px=150; 
            py=400; 
            pa=90;
            pdx=Math.cos(Math.toRadians(pa)); 
            pdy=-Math.sin(Math.toRadians(pa)); 

            running = true;
            input.start();

            renderer = new TerminalRenderer();
            renderer.init(buffer1, buffer2, mainBuffer);

            fillBuffers();
            renderer.render(buffer1, buffer2, mainBuffer);

            while (running) {
                fillBuffers();
                renderer.render(buffer1, buffer2, mainBuffer);
                if (mainBuffer) {
                    mainBuffer=false;
                } else {
                    mainBuffer=true;
                }
            }

            renderer.end();
            resizeTerminal(startupSize.getColumns(), startupSize.getRows());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
