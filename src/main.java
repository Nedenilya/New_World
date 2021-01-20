
import javax.swing.JFrame;


public class main extends JFrame {

	public static void main(String[] args) {
		
		JFrame f = new JFrame("Wrld Game");
		
		f.setSize(825,550);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new game());
		f.setResizable(false);
		f.setVisible(true);    
		
	}

}
