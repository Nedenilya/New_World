import java.awt.Color; 
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class game extends JPanel implements ActionListener,MouseListener,MouseMotionListener {
	
	int mx1,my1,my,mx,i,j,wood = 1,stone = 2,gold = 3,diamond = 4,money = 300,w1,s1,g1,d1,lvlw = 1,lvls = 1,lvlg = 1,lvld = 1;
	float w = 0,s = 0,gl = 0,d = 0,cw,cs,cg,cd;
	double kafw = 0.05,kafs = 0.05, kafg = 0.02, kafd = 0.01;
	int[][] world = new int[9][13];
	boolean[][]empty = new boolean[9][13];
	boolean ingame = true,clickwood,clickstone,clickgold,clickdiamond,minew,mines,mineg,mined,woodlvl,stonelvl,goldlvl,diamondlvl;
	Image iwood,istone,igold,idiamond,iw,is,igl,id;
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	Timer timer = new Timer(20, this);
	public game(){
		setBackground(Color.LIGHT_GRAY);
		timer.start();
		loadimg();
		addMouseListener(this);
		addMouseMotionListener(this);
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 12; j++){
				empty[i][j] = true;
			}
		}
		setFocusable(true);	
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public void loadimg(){
		ImageIcon wood = new ImageIcon("wood.png");
		iwood = wood.getImage();
		ImageIcon stone = new ImageIcon("stone.png");
		istone = stone.getImage();
		ImageIcon gold = new ImageIcon("gold.png");
		igold = gold.getImage();
		ImageIcon diamond = new ImageIcon("diamond.png");
		idiamond = diamond.getImage();
		
		ImageIcon w = new ImageIcon("w.png");
		iw = w.getImage();
		ImageIcon s = new ImageIcon("s.png");
		is = s.getImage();
		ImageIcon gl = new ImageIcon("gl.png");
		igl = gl.getImage();
		ImageIcon d = new ImageIcon("d.png");
		id = d.getImage();
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	public void SaveToFile(){
		   
		try{
			FileWriter fw = new FileWriter("saves.txt");
			for(i = 0;i < 8;i++){
				for(j = 0;j < 12;j++){
					fw.write(""+world[i][j]);
					fw.flush();
				}
			}
			
			fw.close();
		}catch(IOException ex){
			ex.getStackTrace();
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////

	public void OpenFile(){
	   int z,c = 0;
	   char s;
		try(FileReader fr = new FileReader("saves.txt");){	
			i = 0; j = 0;
	      while((z = fr.read()) != -1){
	    	s =(char)z;	
	    	
	    	
			world[i][j] = (int)s-48;
            if(world[i][j] != 0){
            	empty[i][j] = false;
            } else if (world[i][j] == 0){
            	empty[i][j] = true;
            }
			j++;
			if(j == 12){
				i++;
				j = 0;
		      if(i == 8) break;
			}
		  }		
		}catch(IOException ex){
			ex.getStackTrace();
		}	
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	public void buttons(){
		 
		if(mx1 > 720 && mx1 < 770 && my1 > 470 && my1 < 520){
			SaveToFile();		
	    	my1 = 1000;
		}
		if(mx1 > 670 && mx1 < 720 && my1 > 470 && my1 < 520){
			OpenFile();		
	    	my1 = 1000;
		}
		if(mx1 > 770 && mx1 < 820 && my1 > 470 && my1 < 520){
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 12; j++){
					world[i][j] = 0;
					empty[i][j] = true;
				}
			}		
	    	my1 = 1000;
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void paint(Graphics g){
		int x = 0,y = 0;
		super.paint(g);
		if(ingame){
		for(i = 0;i < 8;i++){
			for(j = 0;j < 12;j++){
				if(x >= 0 && x < 600 && y >= 0 && y < 400){
				g.drawRect(x,y,50,50);
				
				g.setColor(Color.red);
				g.drawRect(750,0,70,50);
				g.fillRect(750,0,70,50);
				
				g.setFont(new Font("TimesRoman", Font.PLAIN, 95));
				g.drawString("x",770, 520);
				
				g.setColor(Color.gray);
				g.drawRect(620,35,100,25);
				g.fillRect(10,430,590,70);
				g.setColor(Color.black);
				
				g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
				g.drawRect(720,470,50,50);
				g.drawString("Save",720, 500);
				g.drawRect(670,470,50,50);
				g.drawString("Open",670, 500);
				
				
				g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
				g.drawString("־בלוםÿעü",640,52);
				
				g.setFont(new Font("TimesRoman", Font.PLAIN, 17));
				g.drawString("$ = " + money,620,25);
				
				
				
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			    
	            buttons();
	             
				g.setColor(Color.black);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
				
				exchange();
			    if(mx1 > 750 && mx1 < 820 && my1 > 0 && my1 < 60){
			    	clickwood = false;
			    	clickstone = false;
			        clickgold = false;
			    	clickdiamond = false;
			    }
/////////////////////////////////////////////////////////////////////////////////////////////////	    
				if(clickwood){
					g.drawImage(iwood,mx,my,null);
					if(mx1 > 0 && mx1 < 600 && my1 > 0 && my1 < 400 && empty[my1 / 50][mx1 / 50] && money >= 100){
					  g.drawImage(iwood,(mx1 / 50) * 50,(my1 / 50) * 50,null);
					  world[(my1 / 50)][(mx1 / 50)] = 1;
					  empty[my1 / 50][mx1 / 50] = false;
					  money -= 100;
					  clickwood = false;
					}				
			    }	
				if(clickstone){
					g.drawImage(istone,mx,my,null);
					if(mx1 > 0 && mx1 < 600 && my1 > 0 && my1 < 400 && empty[my1 / 50][mx1 / 50] && money >= 100){
					  g.drawImage(istone,(mx1 / 50) * 50,(my1 / 50) * 50,null);
					  world[my1 / 50][mx1 / 50] = 2;
					  empty[my1 / 50][mx1 / 50] = false;
					  money -= 100;
					  clickstone = false;
					}
			    }		
				if(clickgold){
					g.drawImage(igold,mx,my,null);
					if(mx1 > 0 && mx1 < 600 && my1 > 0 && my1 < 400 && empty[my1 / 50][mx1 / 50] && money >= 200){
					  g.drawImage(igold,(mx1 / 50) * 50,(my1 / 50) * 50,null);
					  world[my1 / 50][mx1 / 50] = 3;
					  empty[my1 / 50][mx1 / 50] = false;
					  money -= 200;
					  clickgold = false;
					}					
			    }				
				if(clickdiamond){
					g.drawImage(idiamond,mx,my,null);
					if(mx1 > 0 && mx1 < 600 && my1 > 0 && my1 < 400 && empty[my1 / 50][mx1 / 50] && money >= 500){
					  g.drawImage(idiamond,(mx1 / 50) * 50,(my1 / 50) * 50,null);
					  world[my1 / 50][mx1 / 50] = 4;
					  empty[my1 / 50][mx1 / 50] = false;
					  money -= 500;
					  clickdiamond = false;
					}					
			    }	
				
/////////////////////////////////////////////////////////////////////////////////////////////////
							
				if(world[i][j] == 1){
					g.drawImage(iwood,j * 50,i * 50,null);	
					cw++;
				}
				if(world[i][j] == 2){
					g.drawImage(istone,j * 50,i * 50,null);
					cs++;
				}
				if(world[i][j] == 3){
					g.drawImage(igold,j * 50,i * 50,null);
					cg++;
				}
				if(world[i][j] == 4){
					g.drawImage(idiamond,j * 50,i * 50,null);
					cd++;
				}
				
/////////////////////////////////////////////////////////////////////////////////////////////////
				
				if(mx1 > 750 && mx1 < 800 && my1 > 65 && my1 < 115 && cw > 0 && money >= 500){
					woodlvl = true;
					lvlUp();
					mx1 = 1000;
				}
				if(mx1 > 750 && mx1 < 800 && my1 > 170 && my1 < 220 && cs > 0 && money >= 500){
					stonelvl = true;
					lvlUp();
					mx1 = 1000;
				}
				if(mx1 > 750 && mx1 < 800 && my1 > 275 && my1 < 325 && cg > 0 && money >= 800){
					goldlvl = true;
					lvlUp();
					mx1 = 1000;
				}
				if(mx1 > 750 && mx1 < 800 && my1 > 380 && my1 < 430 && cd > 0 && money >= 1000){
					diamondlvl = true;
					lvlUp();
					mx1 = 1000;
				}		
				}
				x += 50;
			}
			x = 0;
			y += 50;
		}
/////////////////////////////////////////////////////////////////////////////////////////////////
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		g.drawImage(iwood,620,65,null);
		g.drawRect(750,65,50,50);
		g.drawString("lvlUp^",755,95);
		g.drawString("Wood",675,90);
		g.drawString("$100",675,102);
		g.drawString("lvl "+lvlw,675,124);
		g.drawImage(istone,620,170,null);
		g.drawRect(750,170,50,50);
		g.drawString("lvlUp^",755,200);
		g.drawString("Stone",675,195);
		g.drawString("$100",675,207);
		g.drawString("lvl "+lvls,675,219);
		g.drawImage(igold,620,275,null);
		g.drawRect(750,275,50,50);
		g.drawString("lvlUp^",755,305);
		g.drawString("Gold",675,300);
		g.drawString("$200",675,312);
		g.drawString("lvl "+lvlg,675,324);
		g.drawImage(idiamond,620,380,null);
		g.drawRect(750,380,50,50);
		g.drawString("lvlUp^",755,410);
		g.drawString("Diamonds",675,405);
		g.drawString("$500",675,417);
		g.drawString("lvl "+lvld,675,429);
		
		g.drawImage(iw,20,450, null);
		g.drawImage(is,167,450, null);
		g.drawImage(igl,314,450, null);
		g.drawImage(id,461,450, null);
		
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g.drawString("" + w1,50,465);
		g.drawString("" + s1,197,465);
		g.drawString("" + g1,344,465);
		g.drawString("" + d1,491,465);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
		
		farmres();
		}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void farmres() {
		if(cw > 0){
			w += (float) (cw * kafw);		
		}
		if(cs > 0){
		    s += (float) (cs * kafs);
		}
		if(cg > 0){
			gl += (float) (cg * kafg);		
		}
		if(cd > 0){
		    d += (float) (cd * kafd);
		}
		w1 = (int)w;
		s1 = (int)s;
		g1 = (int)gl;
		d1 = (int)d;
		cw = 0;
		cs = 0;
		cg = 0;
		cd = 0;
		
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void lvlUp(){
		if(woodlvl){
			lvlw++;
			kafw += lvlw * 0.0015;
			money -= 500;
			woodlvl = false;
		}
		if(stonelvl){
			lvls++;
			kafs += lvls * 0.0015;
			money -= 500;
			stonelvl = false;
		}
		if(goldlvl){
			lvlg++;
			kafg += lvlg * 0.001;
			money -= 800;
			goldlvl = false;
		}
		if(diamondlvl){
			lvld++;
			kafd += lvld * 0.001;
			money -= 1000;
			diamondlvl = false;
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////

	
	private void exchange() {
		if(w >= 100 && mx1 > 620 && mx1 < 720 && my1 > 35 && my1 < 60){
			money += 100;
			w -= 100;
			my1 = 0;
		}
		if(s >= 100 && mx1 > 620 && mx1 < 720 && my1 > 35 && my1 < 60){
			money += 100;
			s -= 100;
			my1 = 0;
		}
		if(gl >= 50 && mx1 > 620 && mx1 < 720 && my1 > 35 && my1 < 60){
			money += 100;
			gl -= 50;
			my1 = 0;
		}
		if(d >= 50 && mx1 > 620 && mx1 < 720 && my1 > 35 && my1 < 60){
			money += 150;
			d -= 50;
			my1 = 0;
		}
		
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void mousePressed(MouseEvent e) {
		mx1 = e.getX();
		my1 = e.getY();

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e1) {
	   mx = e1.getX();
	   my = e1.getY();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

/////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void mouseReleased(MouseEvent e) {
		mx = e.getX();
        my = e.getY();
        if(mx > 620 && mx < 670 && my > 65 && my < 115){
     	   clickwood = true;
     	   clickstone = false;
     	   clickgold = false;
     	   clickdiamond = false;
        }
        if(mx > 620 && mx < 670 && my > 170 && my < 270){
     	   clickstone = true;
     	   clickwood = false;
     	   clickgold = false;
     	   clickdiamond = false;
        }
        if(mx > 620 && mx < 670 && my > 275 && my < 325){
      	   clickgold = true;
      	   clickwood = false;
      	   clickstone = false;
      	   clickdiamond = false;
        }
        if(mx > 620 && mx < 670 && my > 380 && my < 430){
           clickdiamond = true;
       	   clickwood = false;
       	   clickstone = false;
       	   clickgold = false;
        }
		
	}
    
		
}