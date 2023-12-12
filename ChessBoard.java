import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

enum PieceType {
    KING(0),
    QUEEN(200),
    BISHOP(400),
    KNIGHT(600),
    ROOK(800),
    PAWN(1000);

    private int num;

    PieceType(int x) {
        this.num = x;
    }

    int returnType() {
        return this.num;
    }
}

enum PieceColor {
    WHITE(0), 
    BLACK(200);

    private int num;

    PieceColor(int x) {
        this.num = x;
    }
        
    int returnColor() {
        return this.num;
    }
        
}


record Piece(PieceType type, PieceColor color) {}

public class ChessBoard extends JPanel implements MouseListener{

    public int squareSize = 0;

    public int boardX = (getWidth() - (squareSize * 8)) / 2;
    public int boardY = (getHeight() - (squareSize * 8)) / 2 + squareSize * 8;


    public ChessBoard() {
        addMouseListener(this); 
    }

    public static Piece[][] chess = new Piece[8][8];
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            ChessBoard chessBoard = new ChessBoard();
            frame.add(chessBoard);
            setBoard(chess);

            frame.getContentPane().setPreferredSize(new Dimension(820, 820));


            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void setBoard(Piece[][] chess) {
        Piece PawnW = new Piece(PieceType.PAWN, PieceColor.WHITE);
        Piece PawnB = new Piece(PieceType.PAWN, PieceColor.BLACK);
    
        Piece KingW = new Piece(PieceType.KING, PieceColor.WHITE);
        Piece KingB = new Piece(PieceType.KING, PieceColor.BLACK);
    
        Piece QueenW = new Piece(PieceType.QUEEN, PieceColor.WHITE);
        Piece QueenB = new Piece(PieceType.QUEEN, PieceColor.BLACK);
    
        Piece BishopW = new Piece(PieceType.BISHOP, PieceColor.WHITE);
        Piece BishopB = new Piece(PieceType.BISHOP, PieceColor.BLACK);

        Piece KnightW = new Piece(PieceType.KNIGHT, PieceColor.WHITE);
        Piece KnightB = new Piece(PieceType.KNIGHT, PieceColor.BLACK);
        
        Piece RookW = new Piece(PieceType.ROOK, PieceColor.WHITE);
        Piece RookB = new Piece(PieceType.ROOK, PieceColor.BLACK);  
    
        chess[0][7] = RookB;    
        chess[1][7] = KnightB;
        chess[2][7] = BishopB;
        chess[3][7] = QueenB;
        chess[4][7] = KingB;
        chess[5][7] = BishopB;
        chess[6][7] = KnightB;
        chess[7][7] = RookB;
            
        for(int i = 0; i < 8; i++) {
            chess[i][6] = PawnB;
        }
    
        chess[0][0] = RookW;    
        chess[1][0] = KnightW;
        chess[2][0] = BishopW;
        chess[3][0] = QueenW;
        chess[4][0] = KingW;
        chess[5][0] = BishopW;
        chess[6][0] = KnightW;
        chess[7][0] = RookW;
    
        for(int i = 0; i < 8; i++) {
            chess[i][1] = PawnW;
        }
    }
    
    public BufferedImage image = null;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        
        try {
            image = ImageIO.read(new File("/home/tgr/Desktop/chess/chess_pieces.png"));
        } catch (IOException e) {   
            e.printStackTrace();
        }

        drawChessBoard(g);
    }

    private void drawChessBoard(Graphics g) {



        
        if(getHeight()< getWidth()) {
            
            squareSize = (getHeight() - 20) / 8;
            
        } else {
            
            squareSize = (getWidth() - 20) / 8;
            
        }

        
        

        boardX = (getWidth() - (squareSize * 8)) / 2;
        boardY = (getHeight() - (squareSize * 8)) / 2 + squareSize * 8;

        


        for(int y = 8; y > 0; y--) {
            for(int x = 0; x < 8; x++) {
                if((x + y) % 2 == 0) {
                    g.setColor(Color.decode("#f2dfd3"));
                    g.fillRect(boardX + (squareSize *x), boardY - (squareSize * y), squareSize, squareSize);
                } else {
                    g.setColor(Color.decode("#964b00"));
                    g.fillRect(boardX + (squareSize *x), boardY - (squareSize * y), squareSize,squareSize);
                }
                
                int imageX = boardX + (squareSize * x);
                int imageY = boardY - (squareSize*y);
                int imageXEnd = boardX + (squareSize * (x+1));
                int imageYEnd  = boardY - (squareSize * (y - 1));
                
                if(!(chess[x][y-1] == null)) {
                    g.drawImage(image, imageX, imageY, imageXEnd, imageYEnd, chess[x][y-1].type().returnType(), chess[x][y-1].color().returnColor(), chess[x][y-1].type().returnType() + 200, chess[x][y-1].color().returnColor() + 200, null);
                }
            }
        }
    }
    public int clkdX = -2;
    public int clkdY = -2;

    public void mouseClicked(MouseEvent e) {
        System.out.println("The mouse is on the cords: " + e.getX() + " and " + e.getY());

        int row; 
        int col;
   

        if(boardX < e.getX() && e.getX() < boardX + squareSize * 8 && boardY - squareSize * 8 < e.getY() && e.getY() < boardY + squareSize * 1) {
            row = (boardX + e.getX()) / squareSize;
            col = (boardY - e.getY()) / squareSize;
        } else {
            row = -1;
            col = -1;
        }
        System.out.println("The row is : " + row  + " and the column is : " + col);

        if(clkdX == -2 && clkdY == -2) {
            clkdX = row;
            clkdY = col;

            System.out.println("The clkdX: "  + clkdX);
            System.out.println("THe clkdY: " + clkdY);
        } else {
            if(!(chess[clkdX][clkdY] == null)) {
                System.out.println("THere is something here: ");

                chess[row][col] = chess[clkdX][clkdY];
                
                chess[clkdX][clkdY] = null;

                clkdX = -2;
                clkdY = -2;

                repaint();
            }

            
        }

        
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {}

    @Override
    public void mouseExited(MouseEvent arg0) {}

    @Override
    public void mousePressed(MouseEvent arg0) {}

    @Override
    public void mouseReleased(MouseEvent arg0) {}
    
}

