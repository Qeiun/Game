import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

class ImagePanel extends JComponent {
	
	private Image image;
	
	public ImagePanel(Image image) 
	{
		this.image = image;
	}
	
	public void changeImage(Image image)
	{
		this.image = image;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
	
	public static boolean showQuitDialog(Component parent) {
		int result = JOptionPane.showConfirmDialog(
		parent,
		"Are you sure?",
		"Quit Game",
		JOptionPane.YES_NO_OPTION,
		JOptionPane.QUESTION_MESSAGE
		);
		
		if (result == JOptionPane.YES_OPTION) {
			System.out.println("GoodBye");
			System.exit(0);
			return true;
		}
		return false;
		}
	
	public static ImagePanel initialize(String filename, ImagePanel ip, JFrame frame)
	{
		//graphics
		try
		{
		BufferedImage mbPic = ImageIO.read(new File(filename));
		ip = new ImagePanel(mbPic);
		frame.setContentPane(ip);
		frame.setIconImage(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1248,832); //<- SET TO SIZE OF ARTWORK (ALL THE SAME SIZE)
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - frame.getWidth()) / 2;
		int y = (screenSize.height - frame.getHeight()) / 2;
		frame.setLocation(x, y);
		
		frame.setLayout(null);
		frame.setUndecorated(true); // Hide window border
		frame.setVisible(true);
		// AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("mappingGame/00.wav"));
		// Clip clip = AudioSystem.getClip();
		// clip.open(audioInputStream);
		// clip.start();
		} catch (IOException e) {
		System.out.println("File not found");
		e.printStackTrace();
		} catch (Exception e) {
		System.out.println("Audio Error");
		e.printStackTrace();
		}
		return ip;
		}
	
	public static void movePic(int x, int y,JFrame frame, ImagePanel ip)
	{
		try
		{
			BufferedImage mbPic = ImageIO.read(new File("../data/" + x + y + ".jpg"));
			ip.changeImage(mbPic);
			SwingUtilities.updateComponentTreeUI(frame);
			frame.invalidate();
			frame.validate();
			frame.repaint();
		} catch (Exception e) {
			System.out.println("File Error");
			e.printStackTrace();
		}
	}
}
