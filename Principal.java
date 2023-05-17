import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Month;
import java.time.Period;
import java.text.DecimalFormat;
import java.util.*;

public class Principal {
    private List<Funcionario> funcionarios;
    
    public Principal() {
        this.funcionarios = new ArrayList<>();
    }

    public void inserirFuncionario(Funcionario funcionario) {
        funcionarios.add(funcionario);
    }

    public void removerFuncionarioPeloNome(String nome) {
        funcionarios.removeIf(e -> e.getNome().equals(nome));
    }

    public void atualizarFuncionario(Funcionario funcionario, BigDecimal novoSalario) {
        funcionario.setSalario(novoSalario);
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public Map<String, List<Funcionario>> agruparFuncionariosPorFuncao() {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            List<Funcionario> funcionariosDaFuncao = funcionariosPorFuncao.getOrDefault(funcao, new ArrayList<>());
            funcionariosDaFuncao.add(funcionario);
            funcionariosPorFuncao.put(funcao, funcionariosDaFuncao);
        }

        return funcionariosPorFuncao;
    }

    public List<Funcionario> getFuncionariosAniversarioNoMes(Month mes) {
        List<Funcionario> funcionariosAniversario = new ArrayList<>();

        for (Funcionario funcionario : funcionarios) {
            if (funcionario.getDataNascimento().getMonth() == mes) {
                funcionariosAniversario.add(funcionario);
            }
        }

        return funcionariosAniversario;
    }

    public Funcionario getFuncionarioMaiorIdade() {
        Funcionario funcionarioMaiorIdade = null;
        int maiorIdade = 0;

        for (Funcionario funcionario : funcionarios) {
            int idade = funcionario.getIdade();

            if (idade > maiorIdade) {
                maiorIdade = idade;
                funcionarioMaiorIdade = funcionario;
            }
        }

        return funcionarioMaiorIdade;
    }

    public List<Funcionario> ordenarFuncionariosPorNome() {
        List<Funcionario> funcionariosOrdenados = new ArrayList<>(funcionarios);
        funcionariosOrdenados.sort(Comparator.comparing(Funcionario::getNome));
        return funcionariosOrdenados;
    }

    public BigDecimal getTotalSalarios() {
        BigDecimal totalSalarios = BigDecimal.ZERO;

        for (Funcionario funcionario : funcionarios) {
            totalSalarios = totalSalarios.add(funcionario.getSalario());
        }

        return totalSalarios;
    }

    public Map<Funcionario, BigDecimal> calcularSalariosMinimos() {
        Map<Funcionario, BigDecimal> salariosMinimos = new HashMap<>();
        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        for (Funcionario funcionario : funcionarios) {
            BigDecimal salarioMinimoGanho = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_DOWN);
            salariosMinimos.put(funcionario, salarioMinimoGanho);
        }

        return salariosMinimos;
    }

    public static void main(String[] args) {
        Principal principal = new Principal();

        // Adiciona Todos os funcionários
        principal.inserirFuncionario(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        principal.inserirFuncionario(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        principal.inserirFuncionario(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        principal.inserirFuncionario(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        principal.inserirFuncionario(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        principal.inserirFuncionario(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        principal.inserirFuncionario(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        principal.inserirFuncionario(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        principal.inserirFuncionario(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        principal.inserirFuncionario(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // Remove o funcionário João
        principal.removerFuncionarioPeloNome("João");

        // Recupera todas as informações de cada funcionário
        List<Funcionario> todosFuncionarios = principal.getFuncionarios();
        System.out.println("Todos os funcionários: ");
        for (Funcionario funcionario: todosFuncionarios) {
            // Formata a data para DD/MM/YYYY
            LocalDate data = funcionario.getDataNascimento();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = data.format(formatter);
            
            // Formata os números decimais 
            BigDecimal salarioMinimo = funcionario.getSalarioMinimo();
            BigDecimal salario = funcionario.getSalario();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            decimalFormat.setDecimalSeparatorAlwaysShown(true);
            String salarioFormatado = decimalFormat.format(salario);
            String salarioMinimoFormatado = decimalFormat.format(salarioMinimo);

            // Printa toda a lista de funcionários
            System.out.println("Nome: " + funcionario.getNome() + " | Data de Nascimento: " + dataFormatada + " | Idade: " + funcionario.getIdade() + " | Função: " + funcionario.getFuncao() + " | Salário: " + salarioFormatado + " | Quantidade de Salários Mínimos: " + salarioMinimoFormatado);
        };

        // Atualizar funcionário
        System.out.println("------ Adicionado 10% a todos os salários ------");
        for (Funcionario funcionario : todosFuncionarios) {
            BigDecimal porcentagemAdicionada = funcionario.getSalario().multiply(new BigDecimal("0.10"));

            BigDecimal novoSalario = funcionario.getSalario().add(porcentagemAdicionada);
            principal.atualizarFuncionario(funcionario, novoSalario);
        }

        // recupera a lista de funcionários após a atualização dos salários
        System.out.println("Todos os funcionários com seus salários atualizados: ");
        for (Funcionario funcionario: todosFuncionarios) {
            // Formata a data para DD/MM/YYYY
            LocalDate data = funcionario.getDataNascimento();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = data.format(formatter);
            
            // Formata os números decimais 
            BigDecimal salarioMinimo = funcionario.getSalarioMinimo();
            BigDecimal salario = funcionario.getSalario();
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
            decimalFormat.setDecimalSeparatorAlwaysShown(true);
            String salarioFormatado = decimalFormat.format(salario);
            String salarioMinimoFormatado = decimalFormat.format(salarioMinimo);

            // Printa toda a lista de funcionários
            System.out.println("Nome: " + funcionario.getNome() + " | Data de Nascimento: " + dataFormatada + " | Idade: " + funcionario.getIdade() + " | Função: " + funcionario.getFuncao() + " | Salário: " + salarioFormatado + " | Quantidade de Salários Mínimos: " + salarioMinimoFormatado);
        };

        // Recupera a lista de funcionários agrupados por função
        Map<String,List<Funcionario>> funcionariosPorFuncao = principal.agruparFuncionariosPorFuncao();
        System.out.println("\nFuncionários agrupados por função:");
        for (String funcao : funcionariosPorFuncao.keySet()) {
            System.out.println(funcao + ":");
            List<Funcionario> funcionariosDaFuncao = funcionariosPorFuncao.get(funcao);
            for (Funcionario funcionario : funcionariosDaFuncao) {
                System.out.println("- " + funcionario.getNome());
            }
        }

        // Imprimir os funcionários que fazem aniversário em um mês específico
        Month mesAniversario = Month.OCTOBER;
        Month mesAniversario2 = Month.DECEMBER;

        List<Funcionario> funcionariosAniversario = principal.getFuncionariosAniversarioNoMes(mesAniversario);
        List<Funcionario> funcionariosAniversario2 = principal.getFuncionariosAniversarioNoMes(mesAniversario2);
        System.out.println("\nFuncionários que fazem aniversário em " + mesAniversario.toString() + ":");
        for (Funcionario funcionario : funcionariosAniversario) {
            System.out.println(funcionario.getNome());
        }
        System.out.println("\nFuncionários que fazem aniversário em " + mesAniversario2.toString() + ":");
        for (Funcionario funcionario : funcionariosAniversario2) {
            System.out.println(funcionario.getNome());
        }

        // Imprimir o funcionário com a maior idade
        Funcionario funcionarioMaiorIdade = principal.getFuncionarioMaiorIdade();
        System.out.println("\nFuncionário com a maior idade:");
        System.out.println("Nome: " + funcionarioMaiorIdade.getNome());
        System.out.println("Idade: " + funcionarioMaiorIdade.getIdade());

        // Imprimir a lista de funcionários por ordem alfabética
        List<Funcionario> funcionariosOrdenados = principal.ordenarFuncionariosPorNome();
        System.out.println("\nFuncionários por ordem alfabética:");
        for (Funcionario funcionario : funcionariosOrdenados) {
            System.out.println(funcionario.getNome());
        }

         // Imprimir o total dos salários dos funcionários
         BigDecimal totalSalarios = principal.getTotalSalarios();
         System.out.println("\nTotal dos salários dos funcionários: R$ " + totalSalarios.toString());
 
         // Imprimir quantos salários mínimos ganha cada funcionário
         Map<Funcionario, BigDecimal> salariosMinimos = principal.calcularSalariosMinimos();
         System.out.println("\nSalários mínimos ganhos por funcionário:");
         for (Funcionario funcionario : salariosMinimos.keySet()) {
             BigDecimal salarioMinimoGanho = salariosMinimos.get(funcionario);
             System.out.println(funcionario.getNome() + ": " + salarioMinimoGanho.toString() + " salários mínimos");
         }
    }
}
