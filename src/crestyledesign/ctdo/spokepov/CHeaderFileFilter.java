package crestyledesign.ctdo.spokepov;

import java.io.File;
import javax.swing.filechooser.*;

public class CHeaderFileFilter extends FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        if(f.getName().endsWith(".h")) return true;

        return false;
    }

    public String getDescription() {
        return "C header files";
    }
}
