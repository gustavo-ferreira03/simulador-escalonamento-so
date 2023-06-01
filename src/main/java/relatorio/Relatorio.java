package relatorio;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Relatorio {
    private final Gson gson = new Gson();
    private static Relatorio instance = null;
    private ArrayList<String> processos;
    private ArrayList<BlocoTimeline> timeline;

    public Relatorio() {
        this.processos = new ArrayList<String>();
        this.timeline = new ArrayList<BlocoTimeline>();
    }

    public void addProcesso(String processo) {
        this.processos.add(processo);
    }

    public void addBlocoTimeline(BlocoTimeline bloco) {
        this.timeline.add(bloco);
    }

    public void registrarEvento(String evento) {
        getBlocoTimelineAtual().addEvento(evento);
    }

    public BlocoTimeline getBlocoTimelineAtual() {
        if(this.timeline.size() == 0) {
            return null;
        }
        return this.timeline.get(this.timeline.size() - 1);
    }

    public ArrayList<BlocoTimeline> getTimeline() {
        return timeline;
    }

    public void gerarRelatorio() {
        String template = readTemplate();
        template = formatTemplate(template);

        String data = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss").format(new Date());
        String nomeArquivo = "./simulacoes/relatorio-" + data  + ".html";
        try {
            FileWriter writer = new FileWriter(nomeArquivo);
            writer.write(template);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatTemplate(String template) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("processos", processos);
        jsonMap.put("timeline", timeline);

        String relatorioJson = gson.toJson(jsonMap);
        template = template.replace("<%= relatorioJson %>", relatorioJson);
        return template;
    }

    private String readTemplate() {
        File templateFile = new File("./src/main/resources/template.html");
        StringBuilder template = new StringBuilder();

        try {
            Scanner reader = new Scanner(templateFile);
            while (reader.hasNextLine()) {
                template.append(reader.nextLine().strip());
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
            e.printStackTrace();
        }
        return template.toString();
    }
}
