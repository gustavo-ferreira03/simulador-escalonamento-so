package relatorio;

import java.util.ArrayList;

public class FilasRelatorio {
    ArrayList<String> io;
    ArrayList<String> p0;
    ArrayList<String>[] p1;

    public FilasRelatorio() {
        this.io = new ArrayList<String>();
        this.p0 = new ArrayList<String>();
        this.p1 = new ArrayList[3];
    }

    public ArrayList<String> getIo() {
        return io;
    }

    public void setIo(ArrayList<String> io) {
        this.io = io;
    }

    public ArrayList<String> getP0() {
        return p0;
    }

    public void setP0(ArrayList<String> p0) {
        this.p0 = p0;
    }

    public ArrayList<String>[] getP1() {
        return p1;
    }

    public void setP1(ArrayList<String>[] p1) {
        this.p1 = p1;
    }
}
