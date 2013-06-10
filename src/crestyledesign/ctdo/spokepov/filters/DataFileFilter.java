package crestyledesign.ctdo.spokepov.filters;

import java.io.File;
import javax.swing.filechooser.*;

public class DataFileFilter extends FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        if(f.getName().endsWith(".ser")) return true;

        return false;
    }

    public String getDescription() {
        return "Data files";
    }
}
