package TileMap;

import java.awt.*;
import java.awt.image.*;

import java.io.*;
import javax.imageio.ImageIO;

import Main.GamePanel;

public class TileMap {
	
	// position - Tọa độ x và y của bản đồ gạch trên màn hình.
	private double x;
	private double y;
	
	// bounds - Giới hạn tối thiểu và tối đa của tọa độ x và y để tránh di chuyển quá biên của bản đồ.
	private int xmin;
	private int ymin;
	private int xmax;
	private int ymax;
	
	//Hệ số giảm chuyển động khi thay đổi vị trí của bản đồ.
	private double tween; 
	
	// map  
	private int[][] map; //Mảng 2D chứa dữ liệu về loại gạch tại mỗi ô trên bản đồ.
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// tileset
	private BufferedImage tileset; //Hình ảnh chứa các loại gạch.
	private int numTilesAcross; //Số lượng gạch trên mỗi dòng trong hình ảnh tileset.
	private Tile[][] tiles; //Mảng 2D chứa các đối tượng Tile đại diện cho từng loại gạch.
	
	// drawing
	private int rowOffset; //Dịch chuyển (offset) của hàng và cột để xác định vị trí bắt đầu vẽ trên màn hình.
	private int colOffset;
	private int numRowsToDraw; //Số lượng hàng và cột của bản đồ được vẽ trên màn hình.
	private int numColsToDraw;
	
	public TileMap(int tileSize) {
		//Constructor của lớp, nhận vào kích thước của mỗi ô gạch.
		this.tileSize = tileSize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	public void loadTiles(String s) {
		//Phương thức để tải hình ảnh gạch từ tệp tin. 
		//Hình ảnh này sẽ được chia thành các ô gạch để sử dụng trong bản đồ.
		
		try {

			tileset = ImageIO.read(
				getClass().getResourceAsStream(s)
			);
			numTilesAcross = tileset.getWidth() / tileSize;
			//Tính toán số lượng ô gạch trên mỗi hàng trong hình ảnh bằng cách chia chiều rộng 
			//của hình ảnh cho kích thước của mỗi ô gạch (tileSize)
			
			tiles = new Tile[2][numTilesAcross];
			//Một mảng 2 chiều tiles được tạo để lưu trữ các đối tượng Tile đại diện cho từng loại gạch.
			
			BufferedImage subimage;
			for(int col = 0; col < numTilesAcross; col++) {
				subimage = tileset.getSubimage(
							col * tileSize,
							0,
							tileSize,
							tileSize
						);
				//Mỗi ô gạch được chọn bắt đầu từ vị trí (col * tileSize, 0) 
				//trên hình ảnh gốc và có kích thước tileSize x tileSize.
				tiles[0][col] = new Tile(subimage, Tile.NORMAL);
				//Sau khi một ô gạch được cắt ra, một đối tượng Tile mới được tạo với hình ảnh này và loại của nó 
				//(ví dụ: Tile.NORMAL hoặc Tile.BLOCKED). Đối tượng Tile này sau đó được lưu vào mảng tiles tại vị trí tương ứng.
				subimage = tileset.getSubimage(
							col * tileSize,
							tileSize,
							tileSize,
							tileSize
						);
				tiles[1][col] = new Tile(subimage, Tile.BLOCKED);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String s) {
		//Phương thức để tải dữ liệu bản đồ từ tệp tin. 
		//Dữ liệu này sẽ xác định loại gạch tại mỗi ô trên bản đồ.
		try {
			
			InputStream in = getClass().getResourceAsStream(s);
			BufferedReader br = new BufferedReader(
						new InputStreamReader(in)
					);
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			map = new int[numRows][numCols];
			width = numCols * tileSize;
			height = numRows * tileSize;
			
			xmin = GamePanel.WIDTH - width;
			xmax = 0;
			ymin = GamePanel.HEIGHT - height;
			ymax = 0;
			//Đọc dữ liệu bản đồ:
			String delims = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delims);
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTileSize() { return tileSize; }
	public double getx() { return x; }
	public double gety() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
	public int getType(int row, int col) {
		int rc = map[row][col];
		int r = rc / numTilesAcross;
		int c = rc % numTilesAcross;
		return tiles[r][c].getType();
	}
	
	public void setTween(double d) { tween = d; }
	
	public void setPosition(double x, double y) {
		
		this.x += (x - this.x) * tween;
		this.y += (y - this.y) * tween;
		
		fixBounds();
		
		colOffset = (int)-this.x / tileSize;
		rowOffset = (int)-this.y / tileSize;
		
	}
	
	private void fixBounds() {
		if(x < xmin) x = xmin;
		if(y < ymin) y = ymin;
		if(x > xmax) x = xmax;
		if(y > ymax) y = ymax;
	}
	
	public void draw(Graphics2D g) {
		//Phương thức để vẽ bản đồ gạch lên màn hình. 
		//Nó lấy từng ô trong bản đồ và vẽ hình ảnh tương ứng từ tileset lên màn hình.
		for(
			int row = rowOffset;
			row < rowOffset + numRowsToDraw;
			row++) {
			
			if(row >= numRows) break;
			
			for(
				int col = colOffset;
				col < colOffset + numColsToDraw;
				col++) {
				
				if(col >= numCols) break;
				
				if(map[row][col] == 0) continue;
				
				int rc = map[row][col];
				int r = rc / numTilesAcross;
				int c = rc % numTilesAcross;
				
				g.drawImage(
					tiles[r][c].getImage(),
					(int)x + col * tileSize,
					(int)y + row * tileSize,
					null
				);
				
			}
			
		}
		
	}
	
}



















