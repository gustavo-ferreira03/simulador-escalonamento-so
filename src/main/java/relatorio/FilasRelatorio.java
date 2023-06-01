package relatorio;

import java.util.ArrayList;
import java.util.List;

public class FilasRelatorio {
    List<String> io;
    List<String> p0;
    List<List<String>> p1;

    public FilasRelatorio() {
        this.io = new ArrayList<String>();
        this.p0 = new ArrayList<String>();
        this.p1 = new ArrayList<List<String>>();
        for(int i = 0; i < 3; i ++) {
            this.p1.add(new ArrayList<String>());
        }
    }

    public List<String> getIo() {
        return io;
    }

    public void setIo(List<String> io) {
        this.io = io;
    }

    public List<String> getP0() {
        return p0;
    }

    public void setP0(List<String> p0) {
        this.p0 = p0;
    }

    public List<List<String>> getP1() {
        return p1;
    }

    public void setP1(List<List<String>> p1) {
        this.p1 = p1;
    }
}
