package ui;

import jGame.JGameDriver;
import jGame.model.game.GameScene;
import jGame.model.game.GameSceneManager;
import jGame.model.math.MathUtils;
import jGame.model.math.Vector2f;
import logic.design.Form;
import logic.design.elements.AbstractUIElement;
import ui.actor.AbstractUIActor;
import ui.scene.DemoScene;
import ui.scene.DesignScene;
import utils.FileIO;
import utils.NumberUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MachineProject extends JGameDriver {

    private ControlPanel cp;


    public MachineProject(String title, int w, int h, int fps){
        super(title, w, h, fps);
        Form.getInstance().machineProject = this;
        // Load game scenes
        GameScene sDemo = new DemoScene("DEMO_SCENE");
        GameScene sDesign = new DesignScene("DESIGN_SCENE");
        registerScene(sDemo);
        registerScene(sDesign);
        initialScene("DESIGN_SCENE");
        // Add game scene manager to control panel
        cp = new ControlPanel(180, h, sDemo.getGameSceneManager());
    }

    public void redrawPanels(){
        cp.redraw();
    }

    @Override
    public void start(){

        JFrame window = new JFrame(game.getTitle());
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.setSize(new Dimension(game.getWidth(), game.getHeight()));
        JPanel container = new JPanel();
        FlowLayout layout = new FlowLayout();
        layout.setVgap(0);
        layout.setHgap(0);
        container.setLayout(layout);
        container.add(cp);
        container.add(game);

        // center
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - game.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - game.getHeight()) / 2);
        window.setLocation(x, y);

        window.add(container);
        window.pack();
        window.setVisible(true);

    }

    public void loadStatsFromActor(){
        cp.loadStatsFromActor();
    }

    class ControlPanel extends JPanel implements ActionListener {
        // add label, button, input
        // reset
        // save XML
        // load XML
        // ---- enable when selecting ---
        // delete selected element
        // edit text
        // edit size
        // edit position (manual)
        // edit font (dropdown)
        private static final long serialVersionUID = 1L;

        private int panelWidth, panelHeight;
        private JButton btn_add_label;
        private JButton btn_add_input;
        private JButton btn_add_button;
        private JButton btn_load_xml;
        private JButton btn_save_xml;
        private JButton btn_reset;
        private JButton btn_delete;
        private JButton btn_apply;
        private JTextField txt_text;
        private JTextField txt_size_x;
        private JTextField txt_size_y;
        private JTextField txt_pos_x;
        private JTextField txt_pos_y;
        private JTextField txt_font;

        private int width;

        private GameSceneManager sDemo;

        public ControlPanel(int width, int height, GameSceneManager sDemo) {
            this.sDemo = sDemo;
            this.width = width;
            setPreferredSize(new Dimension(width, height));
            setPanelHeight(height);
            setPanelWidth(width);
            init();
        }

        public void init() {
            this.setAlignmentX(LEFT_ALIGNMENT);
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            btn_add_label = new JButton("Create Label");
            btn_add_input = new JButton("Create Input");
            btn_add_button = new JButton("Create Button");
            btn_reset = new JButton("Clear Layout");

            txt_text = new JTextField();
            //txt_font = new JTextField();
            txt_size_x = new JTextField();
            txt_size_y = new JTextField();
            txt_pos_x = new JTextField();
            txt_pos_y = new JTextField();
            btn_delete = new JButton("Delete");
            btn_apply = new JButton("Apply Changes");

            btn_save_xml = new JButton("Export XML");
            //btn_load_xml = new JButton("Import XML");

            btn_add_label.setMaximumSize(new Dimension(width, 30));
            btn_add_input.setMaximumSize(new Dimension(width, 30));
            btn_add_button.setMaximumSize(new Dimension(width, 30));
            btn_reset.setMaximumSize(new Dimension(width, 30));
            btn_delete.setMaximumSize(new Dimension(width, 30));
            btn_apply.setMaximumSize(new Dimension(width, 30));
            btn_save_xml.setMaximumSize(new Dimension(width, 30));
            //btn_load_xml.setMaximumSize(new Dimension(width, 30));
            btn_reset.setMaximumSize(new Dimension(width, 30));

            txt_text.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            //txt_font.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            txt_pos_x.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            txt_pos_y.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            txt_size_x.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            txt_size_y.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));

            btn_add_label.addActionListener(this);
            btn_add_input.addActionListener(this);
            btn_add_button.addActionListener(this);
            btn_reset.addActionListener(this);
            btn_delete.addActionListener(this);
            btn_apply.addActionListener(this);
            btn_save_xml.addActionListener(this);
            //btn_load_xml.addActionListener(this);
            btn_reset.addActionListener(this);

            this.add(new JLabel("Form Components"));
            this.add(btn_add_label);
            this.add(btn_add_input);
            this.add(btn_add_button);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            this.add(new JLabel("Edit Component"));
            this.add(new JLabel("  Text"));
            this.add(txt_text);
            //this.add(new JLabel("  Font"));
            //this.add(txt_font);
            this.add(new JLabel("  Size - x"));
            this.add(txt_size_x);
            this.add(new JLabel("  Size - y"));
            this.add(txt_size_y);
            this.add(new JLabel("  Position - x"));
            this.add(txt_pos_x);
            this.add(new JLabel("  Position - y"));
            this.add(txt_pos_y);
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            this.add(btn_delete);
            this.add(btn_apply);
            this.add(Box.createRigidArea(new Dimension(0, 10)));

            this.add(btn_reset);
            //this.add(btn_load_xml);
            this.add(btn_save_xml);

            redraw();
        }

        public void redraw() {

            // empty
            if(Form.getInstance().getSelectedElement() == null){

                txt_text.setEnabled(false);
                //txt_font.setEnabled(false);
                txt_size_x.setEnabled(false);
                txt_size_y.setEnabled(false);
                txt_pos_x.setEnabled(false);
                txt_pos_y.setEnabled(false);
                btn_apply.setEnabled(false);
                btn_delete.setEnabled(false);

                txt_text.setBackground(Color.LIGHT_GRAY);
                //txt_font.setBackground(Color.LIGHT_GRAY);
                txt_size_x.setBackground(Color.LIGHT_GRAY);
                txt_size_y.setBackground(Color.LIGHT_GRAY);
                txt_pos_x.setBackground(Color.LIGHT_GRAY);
                txt_pos_y.setBackground(Color.LIGHT_GRAY);
            } else {
                txt_text.setEnabled(true);
                //txt_font.setEnabled(true);
                txt_size_x.setEnabled(true);
                txt_size_y.setEnabled(true);
                txt_pos_x.setEnabled(true);
                txt_pos_y.setEnabled(true);
                btn_apply.setEnabled(true);
                btn_delete.setEnabled(true);

                txt_text.setBackground(Color.WHITE);
                //txt_font.setBackground(Color.WHITE);
                txt_size_x.setBackground(Color.WHITE);
                txt_size_y.setBackground(Color.WHITE);
                txt_pos_x.setBackground(Color.WHITE);
                txt_pos_y.setBackground(Color.WHITE);
            }

            this.validate();
            this.repaint();
        }


        public int getPanelWidth() {
            return panelWidth;
        }

        public void setPanelWidth(int panelWidth) {
            this.panelWidth = panelWidth;
        }

        public int getPanelHeight() {
            return panelHeight;
        }

        public void setPanelHeight(int panelHeight) {
            this.panelHeight = panelHeight;
        }

        public void loadStatsFromActor(){
            if(Form.getInstance().getSelectedElement() != null) {
                txt_text.setText(Form.getInstance().getSelectedElement().getText());
                txt_pos_x.setText("" + (int) Form.getInstance().getSelectedElement().getPosition().getX());
                txt_pos_y.setText("" + (int) Form.getInstance().getSelectedElement().getPosition().getY());
                txt_size_x.setText("" + (int) Form.getInstance().getSelectedElement().getScale().getX());
                txt_size_y.setText("" + (int) Form.getInstance().getSelectedElement().getScale().getY());
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btn_add_label) {
                sDemo.queueExternalAction(DesignScene.ACTION_CREATE_LABLE);
                redraw();
                /*
                if (input.length() > 0) {
                    int value;
                    try{
                        value = Integer.parseInt(input);
                    } catch (NumberFormatException e1){
                        e1.printStackTrace();
                        value = 0;
                    }
                    sDemo.queueExternalAction(GameScene.ACTION_RESET);
                    txt_input.setText("");
                    redraw();

                }
                */
            }
            if (e.getSource() == btn_add_input) {
                sDemo.queueExternalAction(DesignScene.ACTION_CREATE_INPUT);
                redraw();
            }
            if (e.getSource() == btn_add_button) {
                sDemo.queueExternalAction(DesignScene.ACTION_CREATE_BUTTON);
                redraw();
            }
            if(e.getSource() == btn_reset){
                sDemo.queueExternalAction(GameScene.ACTION_RESET);
                redraw();
            }
            if(e.getSource() == btn_delete){
                sDemo.queueExternalAction(DesignScene.ACTION_DELETE_ELEMENT);
                redraw();
            }
            if(e.getSource() == btn_apply){
                String sText = txt_text.getText();
                //String sFont = AbstractUIElement.DEFAULT_FONT;
                String sPosX = txt_pos_x.getText();
                String sPosY = txt_pos_y.getText();
                String sSizeX = txt_size_x.getText();
                String sSizeY = txt_size_y.getText();
                Vector2f vPosition = null, vScale = null;
                vPosition = new Vector2f(MathUtils.clampFloat(NumberUtils.toFloat(sPosX, 0),0,game.getWidth()),
                        MathUtils.clampFloat(NumberUtils.toFloat(sPosY, 0),0,game.getHeight()));
                vScale = new Vector2f(
                        MathUtils.clampFloat(NumberUtils.toFloat(sSizeX, AbstractUIElement.DEFAULT_WIDTH),30,game.getWidth()),
                        MathUtils.clampFloat(NumberUtils.toFloat(sSizeY, AbstractUIElement.DEFAULT_HEIGHT),AbstractUIElement.DEFAULT_HEIGHT,game.getHeight()));
                if(Form.getInstance().getSelectedElement() != null){
                    Form.getInstance().getSelectedElement().setText(sText);
                    Form.getInstance().getSelectedElement().setFont(AbstractUIElement.DEFAULT_FONT);
                    Form.getInstance().getSelectedElement().setPosition(vPosition);
                    Form.getInstance().getSelectedElement().setScale(vScale);
                }
                sDemo.queueExternalAction(DesignScene.ACTION_LOAD_FORM);
                redraw();
            }
            if(e.getSource() == btn_save_xml){
                sDemo.queueExternalAction(DesignScene.ACTION_LOAD_FORM);

                if(true) {
                    String filename = "", dir = "", code;

                    JFileChooser c = new JFileChooser();
                    c.setCurrentDirectory(new File
                            (System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop"));
                    c.setFileFilter(new FileNameExtensionFilter("xml file",".xml"));
                    //c.addChoosableFileFilter(new FileNameExtensionFilter("xml file", "xml"));
                    // Demonstrate "Save" dialog:
                    int rVal = c.showSaveDialog(this);
                    if (rVal == JFileChooser.APPROVE_OPTION) {
                        filename = c.getSelectedFile().getName()+((FileNameExtensionFilter)c.getFileFilter()).getExtensions()[0];
                        dir = c.getCurrentDirectory().toString();
                        code = Form.getInstance().generateXML();
                        FileIO.saveFile(dir,filename,code);
                    }
                    if (rVal == JFileChooser.CANCEL_OPTION) {
                        System.err.println("User canceled save.");
                    }
                } else
                    System.err.println("Invalid form! Please check if elements are overlapping each other or clipping");
                redraw();
            }
            /*
            if(e.getSource() == btn_load_xml){
                //TODO: fill form with XML
                Form.getInstance().reset();
                sDemo.queueExternalAction(DesignScene.ACTION_LOAD_FORM);
                redraw();
            }
            */
        }
    }
}