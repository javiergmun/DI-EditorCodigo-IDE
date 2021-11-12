import lombok.Data;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URI;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ventana extends JFrame {
    //tenemos que declarar todos los botones, paneles, items...

    private JPanel panelPrincipal;
    private JMenuBar menuHerramientas;


    private JMenu archivo;
    private JMenuItem menuArchivoNuevo;
    private JMenuItem menuArchivoGuardar;
    private JMenuItem menuArchivoAbrir;
    private JMenuItem menuArchivoGuardarComo;
    private JMenuItem menuArchivoImprimir;
    private JMenu editar;
    private JMenuItem menuEditarDeshacer;
    private JMenuItem menuEditarCopiar;
    private JMenuItem menuEditarCortar;
    private JMenuItem menuEditarPegar;
    private JMenuItem menuEditarEliminar;
    private JMenu ayuda;
    private JMenuItem menuAyudaAcercaDe;
    private JMenuItem menuAyudaVerAyuda;

    private JScrollPane zonaCentral;
    private JPanel zonaNorte;
    private JScrollPane zonaIzquierda;
    private JScrollPane zonaSur;
    private JMenuItem menuArchivoSalir;
    JTextArea textoZonaCentral = new JTextArea("public class ************ {\n" +
            "    public static void main(String[] args) {\n" + "    }\n"+"}"); //cuadro de texto que le paso a la zona central
    JTextArea textoZonaSur = new JTextArea();

    private JButton buildButton;
    ImageIcon iconoBuild = new ImageIcon("src/Images/martillo24.png");
    private JButton runButton;
    ImageIcon iconoRun = new ImageIcon("src/Images/run24.png");
    private JButton sim1Button;
    ImageIcon iconoBombilla = new ImageIcon("src/Images/bombilla.png");
    private JButton sim2Button;
    ImageIcon iconoInternet = new ImageIcon("src/Images/internet.png");
    private JButton sim3Button;
    ImageIcon iconoGit = new ImageIcon("src/Images/github.png");

    private File archivoBuild;
    private File archivoGuardado;
    private Clipboard portapapeles;
    private UndoableEdit deshacer;


//===========================================================================================================


    public Ventana() { //clase ventana tiene comportamientos, implementados en el void
        initComponents();
    } //la ventana tiene el metodo que implementa los componentes.

    private void initComponents() {  //donde inicializo mis componentes y les pongo valores, si las declaro y no las inicializo, no va.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //se pone para que cuando se cierre, no corra un hilo
        //setIconImage(new ImageIcon(getClass().getResource("icono.png")).getImage());
        setPreferredSize(new Dimension(850, 650));
        setMinimumSize(new Dimension(500, 400));


//==========================================================================================================

        //añado un espacio central para poner texto
        zonaCentral = new JScrollPane(textoZonaCentral); // crear la zona central CON SCROLL Y DE TIPO TEXTO AREA
        zonaCentral.setPreferredSize(new Dimension(600, 425));
        this.add(zonaCentral, BorderLayout.CENTER); //AÑADIR A MI VENTANA LA ZONA CENTRAL
        //añado un espacio norte a mi ventana como panel para meter la barra herramientas (tipo panel

        zonaNorte = new JPanel();
        zonaNorte.setBackground(Color.GRAY);
        zonaNorte.setLayout(new FlowLayout(FlowLayout.LEADING));
        zonaNorte.setPreferredSize(new Dimension(850, 48));
        this.add(zonaNorte, BorderLayout.NORTH);
        //Creo una zona izquierda con scroll y de tipo tree para ver los directorios
        zonaIzquierda = new JScrollPane(new JTree());
        zonaIzquierda.setPreferredSize(new Dimension(175, 425));
        this.add(zonaIzquierda, BorderLayout.WEST);

        zonaSur = new JScrollPane(textoZonaSur); ////////////????????????????? meter consola
        zonaSur.setPreferredSize(new Dimension(100, 175));
        textoZonaSur.setBackground(Color.lightGray);
        this.add(zonaSur, BorderLayout.SOUTH);


//==============================================================================================================
        //menu de herramientas


        menuHerramientas = new JMenuBar();
        // menuHerramientas.setBackground(Color.white);
        // menuHerramientas.setBorderPainted(true);
        zonaNorte.add(menuHerramientas); // SE LA AÑADO A MI zona norte

        //meterle cosas a mi barra de herramientas
        archivo = new JMenu();
        archivo.setText("Archivo");
        // AÑADO EL MENU DE OPCIONES ARCHIVO A MI MENU HERRAMIENTAS
        menuHerramientas.add(archivo);
        menuArchivoNuevo = new JMenuItem();
        menuArchivoNuevo.setText("Nuevo");
        menuArchivoNuevo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                nuevo();
            }
        });
        menuArchivoAbrir = new JMenuItem();
        menuArchivoAbrir.setText("Abrir");
        menuArchivoGuardar = new JMenuItem();
        menuArchivoGuardar.setText("Guardar");
        menuArchivoGuardar.addActionListener(new ActionListener(){
            @SneakyThrows
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
        menuArchivoGuardarComo = new JMenuItem();
        menuArchivoGuardarComo.setText("Guardar Como");
        menuArchivoGuardarComo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardarArchivoComo();
            }
        });

        menuArchivoImprimir = new JMenuItem();
        menuArchivoImprimir.setText("Imprimir");
        menuArchivoImprimir.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {

            }
        });
        menuArchivoSalir = new JMenuItem("Salir");
        menuArchivoSalir.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        //añado todas las opciones a ARCHIVO
        archivo.add(menuArchivoNuevo); // añado archivo nuevo a archivo
        archivo.add(menuArchivoAbrir); // añado abrir a archivo
        archivo.add(menuArchivoGuardar); // añado guardar a archivo
        archivo.add(menuArchivoGuardarComo); // añado guardar como a archivo
        archivo.add(menuArchivoImprimir); // añado imprimir a archivo
        archivo.add(menuArchivoSalir);

        editar = new JMenu();
        editar.setText("Editar");
        // AÑADO EL MENU EDITAR A MI BARRA DE HERRAMIENTAS
        menuHerramientas.add(editar);
        menuEditarDeshacer = new JMenuItem("Deshacer"); // se puede poner el nombre directo al item
        menuEditarDeshacer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                deshacer();
            }
        });
        menuEditarCopiar = new JMenuItem("Copiar");
        menuEditarCopiar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                copiar();
            }
        });
        menuEditarCortar = new JMenuItem("Cortar");
        menuEditarCortar.addActionListener(new ActionListener(){
            @SneakyThrows
            public void actionPerformed(ActionEvent e) {
                cortar();
            }
        });
        menuEditarPegar = new JMenuItem("Pegar");
        menuEditarPegar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                pegar();
            }
        });
        menuEditarEliminar = new JMenuItem("Eliminar");
        menuEditarEliminar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                textoZonaCentral.setText("Se han eliminado los cambios");
            }
        });
        //AÑADO ITEMS A EDITAR
        editar.add(menuEditarDeshacer);
        editar.add(menuEditarCopiar);
        editar.add(menuEditarCortar);
        editar.add(menuEditarPegar);
        editar.add(menuEditarEliminar);

        ayuda = new JMenu("Ayuda");
        menuHerramientas.add(ayuda);
        //añado items
        menuAyudaAcercaDe = new JMenuItem("Acerca de ...");
        menuAyudaAcercaDe.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                acercaDe();
            }
        });
        menuAyudaVerAyuda = new JMenuItem("Ver ayuda ...");
        menuAyudaVerAyuda.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                verAyuda();
            }
        });
        //añado items al menu ayuda
        ayuda.add(menuAyudaAcercaDe);
        ayuda.add(menuAyudaVerAyuda);

        buildButton = new JButton("BUILD");
        buildButton.setIcon(iconoBuild);
        buildButton.setIconTextGap(4);
        buildButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        buildButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        buildButton.setHorizontalTextPosition(SwingConstants.LEFT);
        buildButton.setVerticalTextPosition(SwingConstants.CENTER);
        buildButton.addActionListener(new ActionListener(){
            @SneakyThrows
            public void actionPerformed(ActionEvent e) {
                build();
            }
        });
        zonaNorte.add(buildButton);

        runButton = new JButton("RUN");
        runButton.addActionListener(new ActionListener() {
            @SneakyThrows
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });
        runButton.setIcon(iconoRun);
        runButton.setIconTextGap(5);
        runButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        runButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
        runButton.setHorizontalTextPosition(SwingConstants.LEFT);
        runButton.setVerticalTextPosition(SwingConstants.CENTER);
        zonaNorte.add(runButton);

        sim1Button = new JButton();
        sim1Button.setIcon(iconoBombilla);
        sim1Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                buscarAyuda();
            }
        });
        zonaNorte.add(sim1Button);

        sim2Button = new JButton();
        sim2Button.setIcon(iconoInternet);
        sim2Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                buscarInternet();
            }
        });
        zonaNorte.add(sim2Button);

        sim3Button = new JButton();
        sim3Button.setIcon(iconoGit);
        sim3Button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                buscarGit();
            }
        });
        zonaNorte.add(sim3Button);

//=========================================================================================================
        pack();
    }
    // METODOS =========================================================================================
    public File guardarArchivoComo() {
        JFileChooser selector = new JFileChooser();
        int opcion = selector.showSaveDialog(this);
        File archivoGuardado = selector.getSelectedFile();
        try (FileWriter escritor = new FileWriter(archivoGuardado)) {

            if (opcion == JFileChooser.APPROVE_OPTION)
                if (archivoGuardado != null)
                    escritor.write(textoZonaCentral.getText());
                    JOptionPane.showMessageDialog(null, "Guardado correctamente", "Guardar como...", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            System.out.println("Error: ");
        }
        return archivoGuardado;
    }

    private void guardar() throws IOException {
        if (archivoGuardado == null) {
            archivoGuardado = guardarArchivoComo();
        } else {
            try (FileWriter writer = new FileWriter(archivoGuardado)) {
                writer.write(textoZonaCentral.getText());
                JOptionPane.showMessageDialog(null, "Guardado correctamente", "Guardar...", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error mientras guardabas", "Error", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void deshacer() {
        if (deshacer.canUndo())
            deshacer.undo();
    }

    private void cortar() throws AWTException {
        if (textoZonaCentral.getSelectedText() != null) {
            StringSelection seleccion = new StringSelection("" + textoZonaCentral.getSelectedText());
            portapapeles.setContents(seleccion, seleccion);
            remove();
        }
    }
    private void remove() throws AWTException {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);
    }

    private void copiar() {
        if (textoZonaCentral.getSelectedText() != null) {
            StringSelection seleccion = new StringSelection("" + textoZonaCentral.getSelectedText());
            portapapeles.setContents(seleccion, seleccion);
        }
    }

    private void pegar() {
        Transferable datos = portapapeles.getContents(null);
        try {
            if (datos != null && datos.isDataFlavorSupported(DataFlavor.stringFlavor))
                textoZonaCentral.replaceSelection("" + datos.getTransferData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException | IOException ex) {
            System.err.println(ex);
        }
    }

    private void nuevo() {

        archivo = null;
        textoZonaCentral.setText("Nuevo Archivo creado...");
        this.setTitle("Nuevo Proyecto" + "(sin titulo)");
    }

    public void buscarInternet() {
        try{
            Desktop.getDesktop().browse(new URI("https://www.google.com/"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarGit() {
        try{
            Desktop.getDesktop().browse(new URI("https://github.com/javiergmun"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarAyuda() {
        try{
            Desktop.getDesktop().browse(new URI("https://www.w3schools.com/java/"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void verAyuda() {
        try{
            Desktop.getDesktop().browse(new URI("https://es.wikipedia.org/wiki/Swing_(biblioteca_gr%C3%A1fica)"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void acercaDe() {
        try{
            Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dx_sfmbV-bo"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void build() throws IOException {
        if (Objects.equals(textoZonaCentral.getText(), "") || archivoGuardado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona una clase .java ", "AVISO",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            guardar();
            Runtime cmd = Runtime.getRuntime();
            String buildJava = "javac " + archivoGuardado.getPath();
            readInConsole(cmd, buildJava);
            JOptionPane.showMessageDialog(null, "Construido! Puedes echarlo a correr", "EXITO", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void readInConsole(Runtime cmd, String command) throws IOException {

        Process proc = cmd.exec(command);
        InputStream inputStream = proc.getInputStream();
        InputStream errorStream = proc.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        InputStreamReader errorStreamReader = new InputStreamReader(errorStream);
        BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);
        BufferedReader errorBufferedReader = new BufferedReader(errorStreamReader);
        String inputline = "";
        String errorline = "";
        textoZonaSur.setText("");
        textoZonaSur.setForeground(Color.WHITE);
        while ((inputline = inputBufferedReader.readLine()) != null) {
            textoZonaSur.append(inputline + "\n");
        }
        while ((errorline = errorBufferedReader.readLine()) != null) {
            textoZonaSur.append(errorline + "\n");
            textoZonaSur.setForeground(Color.RED);
        }
    }
    private void run() throws IOException {

        if (Objects.equals(textoZonaCentral.getText(), "") || archivo == null) {
            JOptionPane.showMessageDialog(null, "Selecciona una clase .java o guarda el proyecto ", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            guardar();
            Runtime cmd = Runtime.getRuntime();
            String runJava = "java " + archivoGuardado.getPath();
            readInConsole(cmd, runJava);
            JOptionPane.showMessageDialog(null, "Run done!", "Message Dialog", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}