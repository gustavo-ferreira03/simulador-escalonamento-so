package componentes;

public class Discos {
    private int quantidadeDisponivel = 4;

    public boolean utilizarDiscos(int numeroDeRecursos){
        if(numeroDeRecursos > getQuantidadeDisponivel()){
            //System.out.println("Quantidade em disco indisponivel espere liberar em bloqueado");
            return false;
        }else{
            setQuantidadeDisponivel(getQuantidadeDisponivel() - numeroDeRecursos);
            //System.out.println("Utilizando "+getQuantidadeDisponivel()+" De Discos");
            return true;
        }
    }


    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(int quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
