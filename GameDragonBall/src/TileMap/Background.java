package TileMap;

import Main.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Background {
	
	private BufferedImage image;
	
	private double x;
	private double y; //Tọa độ x và y của nền.
	private double dx;
	private double dy; //Giá trị tăng hoặc giảm tọa độ x và y của nền, điều này cho phép nền di chuyển.
	
	private double moveScale; //Quy mô di chuyển của nền.
	
	public Background(String s, double ms) {
		
		try {
			image = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void setPosition(double x, double y) {
		//Đặt vị trí của nền. Các tham số x và y được nhân với moveScale và lấy phần dư 
		//của kích thước của GamePanel để xác định vị trí cuối cùng của nền.
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	
	public void setVector(double dx, double dy) {
		//Đặt vector di chuyển cho nền.
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		//Cập nhật vị trí của nền bằng cách thêm dx và dy vào x và y.
		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g) {
		//Vẽ hình ảnh nền lên màn hình. Nếu hình ảnh nền đi ra ngoài biên của màn hình, 
		//phương thức này sẽ vẽ một bản sao của hình ảnh để đảm bảo rằng nền luôn hiển 
		//thị một cách liền mạch.
		
		g.drawImage(image, (int)x, (int)y, null);
		
		if(x < 0) {
			g.drawImage(
				image,
				(int)x + GamePanel.WIDTH,
				(int)y,
				null
			);
		}
		if(x > 0) {
			g.drawImage(
				image,
				(int)x - GamePanel.WIDTH,
				(int)y,
				null
			);
		}
	}
	
}








