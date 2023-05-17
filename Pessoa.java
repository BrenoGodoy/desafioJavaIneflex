import java.time.LocalDate;
import java.time.Period;

class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getIdade() {
        LocalDate currentDate = LocalDate.now();
        return Period.between(dataNascimento, currentDate).getYears();
    }
}
