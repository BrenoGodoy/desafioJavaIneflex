import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public BigDecimal getSalarioMinimo() {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        return salario.divide(salarioMinimo, 2, RoundingMode.HALF_UP);
    }

    public void setSalario(BigDecimal novoSalario) {
        salario = novoSalario;
    }
}
