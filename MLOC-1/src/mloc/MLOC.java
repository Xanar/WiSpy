/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mloc;
import java.awt.*;
import java.applet.*;
import java.util.*;
/**
 *
 * @author Xanar
 */
public class MLOC extends Applet {
    float x[],y[],ix[],iy[],locx,locy;
    int n,ni,m,id[],r=30;
    
    public void findintersection() {
        float dx,dy,dist;
        double a,h,cx,cy;
        int k=0;
        ix=new float[n*4];
        iy=new float[n*4];
        for(int i=1;i<=n;i++)
            for(int j=1;j<=n;j++){
                if(i==j)
                    continue;
                // Find the distance between the centers.
                dx = x[i] - x[j];
                dy = y[i] - y[j];
                dist = (float) Math.sqrt(dx * dx + dy * dy);

                // See how many solutions there are.
                //(R0-R1)^2 <= (x0-x1)^2+(y0-y1)^2 <= (R0+R1)^2
                if((dist>=r-r)&&(dist<=(r+r)))              //assuming all routers have same radius
                {
                    // Find a and h.
                    a = (r * r - r * r + dist * dist) / (2 * dist);
                    h = Math.sqrt(r * r - a * a);

                    // Find P2.
                    cx = x[i] + a * (x[j] - x[i]) / dist;
                    cy = y[i] + a * (y[j] - y[i]) / dist;
                    
                    if (dist == r+r){
                        ix[k] =(float)(cx + h * (y[j] - y[i]) / dist);
                        iy[k] = (float)(cy - h * (x[j] - x[i]) / dist);
                    }
                    else{
                        ix[k] = (float)(cx + h * (y[j] - y[i]) / dist);
                        iy[k] = (float)(cy - h * (x[j] - x[i]) / dist);
                        k++;
                        ix[k] = (float)(cx - h * (y[j] - y[i]) / dist);
                        iy[k] = (float)(cy + h * (x[j] - x[i]) / dist);
                    }
                    k++;
                }
            }
        ni=k;
    }
    public void calcpos(){
        Scanner s=new Scanner(System.in);
        int t=0;
        float dx,dy,dist,sumx=0,sumy=0;
        System.out.println("Enter number of routers comunicating with mobile:");
        m=s.nextInt();
        id=new int[m+1];
        System.out.println("Enter the "+m+" router ids:");
        for(int i=1;i<=m;i++)
            id[i]=s.nextInt();
        for(int i=0;i<ni;i++)
            for(int j=1;j<=m;j++){
                dx = ix[i] - x[id[j]];
                dy = iy[i] - y[id[j]];
                dist = (float) Math.sqrt(dx * dx + dy * dy);
                //dist=Math.round(dist);
                if(dist<=r){
                    sumx+=ix[i];
                    sumy+=iy[i];
                    t++;
                }
                    
            }
        locx=sumx/t;
        locy=sumy/t;
        System.out.println("locx="+locx+" locy="+locy);
    }
            
    @Override
    public void init() {
       Scanner s=new Scanner(System.in);
       System.out.println("Enter number of routers");
       n=s.nextInt();
       x=new float[n+1];
       y=new float[n+1];
       for(int i=1;i<=n;i++){
           System.out.print("Enter co-ordinates of router "+i+":");
           x[i]=s.nextFloat();
           y[i]=s.nextFloat();
       }
       findintersection();
       calcpos();
        Canvas ca=new Canvas();
        ca.setSize(400, 400);
        ca.setBackground(Color.WHITE);
    }
    @Override
    public void paint(Graphics g){
        int temp,x1,y1;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.red);
        for(int i=1;i<=n;i++){
            temp=0;
            for(int j=1;j<=m;j++)
                if(i==id[j])
                    temp=1;
            x1=Math.round(x[i]-r);
            y1=Math.round(y[i]-r);
            if(temp==0)
                g.drawOval(x1,y1,r*2,r*2);
            else
                g2.drawOval(x1, y1, r*2, r*2); 
        }
        g2.setColor(Color.green);
        x1=(int) Math.round(locx-2.5);
        y1=(int) Math.round(locy-2.5);
        g2.fillOval(x1,y1,5,5);
                 
    }
    
}
