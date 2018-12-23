package ui;

import model.Game.Board;
import model.Game.Game;
import model.Piece.Cell;
import model.Piece.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Renderer2 {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private Board chessboard;
    private static String defaultPieceImagesPath = "simple/presidents/";

    private Cell destinationTile;

    private List<Cell> highlights;
    private boolean selected;

    private Game game;

    private final static Dimension OUT_FRAME_DIMENSION = new Dimension(600,600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);

    private final static Color darkTileColor = new Color(255, 206, 157);
    private final static Color lightTileColor = new Color(209, 139, 71);



    public Renderer2(Game game) {
        this.game = game;
        this.gameFrame = new JFrame("Checkers");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);

        this.chessboard = game.getBoard();

        this.gameFrame.setSize(OUT_FRAME_DIMENSION);

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem openPGN = new JMenuItem("Welcome");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader br = null;

                try {
                    String theURL = "https://www.ugrad.cs.ubc.ca/~cs210/2018w1/welcomemsg.html"; //this can point to any URL
                    URL url = new URL(theURL);
                    br = new BufferedReader(new InputStreamReader(url.openStream()));

                    String line;

                    StringBuilder sb = new StringBuilder();

                    while ((line = br.readLine()) != null) {

                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }

                    System.out.println(sb);
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } finally {

                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem toggleStrictGame = new JMenuItem("Toggle Strict Game");
        toggleStrictGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.toggleIsStrictGame();
            }
        });
        fileMenu.add(toggleStrictGame);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        final JMenuItem standardPiecesMenuItem = new JMenuItem("Standard Pieces");
        standardPiecesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("standard pieces selected");
                System.out.println("simple is called");
                defaultPieceImagesPath = "simple/basic/";
                boardPanel.drawBoard();
            }
        });
        fileMenu.add(standardPiecesMenuItem);

        final JMenuItem dogsCatsPiecesMenuItem = new JMenuItem("Dogs and Cats");
        dogsCatsPiecesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cats and dogs selected");
                defaultPieceImagesPath = "simple/dogsandcats/";
                boardPanel.drawBoard();
            }
        });
        fileMenu.add(dogsCatsPiecesMenuItem);

        final JMenuItem presidentsMenuItem = new JMenuItem("Presidents");
        presidentsMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("presidents selected");
                defaultPieceImagesPath = "simple/presidents/";
                boardPanel.drawBoard();
            }
        });
        fileMenu.add(presidentsMenuItem);

        final JMenuItem rickmortyMenuItem = new JMenuItem("Rick and Morty");
        rickmortyMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Rick and Morty selected");
                defaultPieceImagesPath = "simple/rickandmorty/";
                boardPanel.drawBoard();
            }
        });
        fileMenu.add(rickmortyMenuItem);

        final JMenuItem poggersMenuItem = new JMenuItem("Poggers :O");
        poggersMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Poggers selected");
                defaultPieceImagesPath = "simple/poggers/";
                boardPanel.drawBoard();
            }
        });
        fileMenu.add(poggersMenuItem);

        final JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                game.save();
            }

        });
        fileMenu.add(saveMenuItem);

        final JMenuItem loadMenuItem = new JMenuItem("Load Saved Game");
        loadMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                game.load();
            }
        });
        fileMenu.add(loadMenuItem);


        return fileMenu;
    }

    public void redraw() {
        boardPanel.drawBoard();
    }

    private class BoardPanel extends JPanel {
        final List<TilePanel> boardTiles;

        BoardPanel () {
            super(new GridLayout(8,8));
            this.boardTiles = new ArrayList<>();
            for (int i = 0 ; i<8;  i++) {
                for (int j = 0 ; j < 8 ; j++) {
                    final TilePanel tilePanel = new TilePanel(this, i, j);
                    this.boardTiles.add(tilePanel);
                    add(tilePanel);
                }

            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard() {
            for (TilePanel tilePanel: boardTiles) {
                tilePanel.drawTile(game);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    private class TilePanel extends JPanel {

        private final int row;
        private final int column;
        private Cell tilecell;

        TilePanel(final BoardPanel boardPanel,
                  final int row,
                  final int column) {
            super(new GridBagLayout());
            this.row = row;
            this.column = column;
            tilecell = game.getBoard().getAt(this.row, this.column);
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(game);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    if (isRightMouseButton(e)) {

                        if (game.isMoveover()) {
                        game.deSelect();
                        selected = false;
                        highlights = null;
                        System.out.println("selection cleared");
                        } else {
                            System.out.println("LOCKED. ENFORCED MOVE.");
                        }


                    } else if (isLeftMouseButton(e)) {

                        if (!selected) {
                            // no piece , nothing happens
                            Piece selectedPiece = tilecell.getValue();
                            if (selectedPiece == null ) {

                            // valid piece
                            } else if (game.isStrictGame()) {
                                if (game.isp1turn() && selectedPiece.getColor().equals("BLACK")) {
                                    System.out.println("WHITE'S TURN");
                                } else if ((!game.isp1turn()) && selectedPiece.getColor().equals("WHITE")) {
                                    System.out.println("BLACK'S TURN");
                                } else {
                                    selected = true;
                                    game.select(selectedPiece.getPosition());
                                    highlights = game.getHighlights();
                                }
                            } else {
                                selected = true;
                                game.select(selectedPiece.getPosition());
                                highlights = game.getHighlights();
                            }


                        } else {
                            destinationTile = tilecell;
                            if (!game.getSelected().isValidMove(destinationTile)) {
                                System.out.println("INVALID MOVE");
                            } else {
                                game.makeMove(destinationTile);
                                if (game.isMoveover()) {
                                    game.specials();
                                    redraw();
                                    game.nextturn();
                                    game.deSelect();
                                    selected = false;
                                    highlights = null;
                                    destinationTile = null;
                                } else {
                                    highlights = game.getHighlights();
                                }
                            }


                        }

                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.drawBoard();
                        }
                    });
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        public void assignTilePieceIcon(Game game) {
            boolean highlighted ;
            this.removeAll();

            if (highlights == null) {
                highlighted = false;
            } else {
                highlighted = highlights.contains(tilecell);
            }
            String filestring = defaultPieceImagesPath;
            if ((tilecell.isEmpty()) && !highlighted) {
            } else {

                if (!tilecell.isEmpty()) {
                    filestring += tilecell.getValue().getFileName();
                }
                if (highlighted) {
                    filestring += "H";
                }
                filestring += ".gif";

                try {
                    final BufferedImage image = ImageIO.read(
                            new File(filestring)
                    );
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void drawTile(Game game) {
            assignTileColor();
            assignTilePieceIcon(game);
            validate();
            repaint();
        }

        private void assignTileColor() {
            if ( ( this.row%2 == this.column%2 ) ) {
                setBackground(lightTileColor);
            } else {
                setBackground(darkTileColor);
            }

        }

    }

}
