/**
 * 
 */
package org.apache.amber;

/**
 * @author pidster
 * @version $Revision$ $Date$
 * 
 */
public enum Version {

    /**
     * 1.0
     * 
     * @since 1.0
     */
    v1_0(1, 0),

    /**
     * 1.0a
     * 
     * @since 1.0a
     */
    v1_0a(1, 0, 'a')

    ; // End of enum type definitions

    private int major;
    private int minor;
    private char variant;

    /**
     * 
     */
    Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
        this.variant = ' ';
    }

    /**
     * 
     */
    Version(int major, int minor, char variant) {
        this.major = major;
        this.minor = minor;
        this.variant = variant;
    }

    /**
     * @return handle
     */
    public String toHandle() {
        StringBuilder s = new StringBuilder();

        String className = Version.class.getName();
        className = className.toLowerCase().replace('.', '_');
        s.append(className);
        s.append("_");
        s.append(this.major);
        s.append("_");
        s.append(this.minor);
        if (this.variant != ' ') {
            s.append("_");
            s.append(this.variant);
        }

        return s.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append(this.major);
        s.append(".");
        s.append(this.minor);
        if (this.variant != ' ') {
            s.append(this.variant);
        }

        return s.toString();
    }

}
