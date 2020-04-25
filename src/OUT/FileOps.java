
package OUT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class FileOps {

    public static File Get_File(boolean folder, boolean open, String name) {
        final JFileChooser ch = new JFileChooser();
        ch.setFileSystemView(ch.getFileSystemView());
        ch.setMultiSelectionEnabled(false);
        ch.setDialogTitle("Choose file");
        if (folder) {
            ch.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        } else {
            ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
        }
        if (open) {
            FileFilter filter = new FileFilter() {
                @Override
                public boolean accept(File f) {
                    String h = f.getName();
                    String point = ".";
                    if (f.isFile() && !h.contains(point)) return false;
                    return (f.isFile()) ? h.substring(h.lastIndexOf('.')).equals(".str") : true;
                }

                @Override
                public String getDescription() {
                    return "Solo ficheros del tipo .str";
                }
            };
            ch.setFileFilter(filter);
            ch.showOpenDialog(null);
        } else {
            ch.setSelectedFile(new File(name + ".str"));
            ch.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (e.getActionCommand().equals("CancelSelection")){
                        ch.setSelectedFile(null);
                    }
                }
            });
            ch.showSaveDialog(null);
            
        }
        return ch.getSelectedFile();
    }

    public static void SaveAs(String text) {
        File sav = Get_File(false, false, "project");
        if (sav != null){
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(sav));
                out.writeObject(text);
                out.close();
                JOptionPane.showMessageDialog(null, "Done\nSaved to:\n"+sav.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(FileOps.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String Load() {
        String loaded="";
        File load = Get_File(false, true, loaded);
        try {
            if (load != null){
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(load));
            loaded = (String) in.readObject();
            
            in.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(FileOps.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loaded;
    }
    
}
