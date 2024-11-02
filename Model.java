import javax.swing.*;
import java.io.*;
import java.util.*;
public class Model {

    static class Aluno implements Serializable {


        private final String nome;
        Map<Model.Aluno, List<Double>> notasPorDisciplina;

        enum Situacao {
            APROVADO,
            REPROVADO,
            APROVADO_NA_FINAL,
            REPROVADO_NA_FINAL;
        }

        //Construtor
        public Aluno(String nome) {
            this.nome = nome;
            this.notasPorDisciplina = new HashMap<>();
        }

        public void adicionarNota(Model.Aluno disciplina, double nota) {
            notasPorDisciplina.putIfAbsent(disciplina, new ArrayList<>());
            notasPorDisciplina.get(disciplina).add(nota);
        }

        //Pega o nome
        public String getNome() {
            return nome;
        }

        //Pega nota
        public Map<Aluno, List<Double>> getNotasPorDisciplina() {
            return notasPorDisciplina;
        }


        //Calcula a média

        public double calcularMedia(Aluno aluno, Disciplina disciplina) {
            // Verifica se o aluno tem notas para a disciplina
            List<Double> notas = aluno.notasPorDisciplina.get(disciplina);

            if (notas == null || notas.size() < 2) {
                return 0;  // Não é possível calcular a média com menos de 2 notas
            }

            // Ordena as notas em ordem decrescente
            List<Double> notasOrdenadas = new ArrayList<>(notas);
            notasOrdenadas.sort(Collections.reverseOrder());

            // Retorna a média das duas maiores notas
            return (notasOrdenadas.get(0) + notasOrdenadas.get(1)) / 2.0;
        }


        public double notaNecessariaFinal(Aluno aluno, Disciplina disciplina) {
            double media = calcularMedia(aluno, disciplina);
            if (media >= 3.0 && media < 7.0) {
                return 10.0 - media;

            } else {
                return 0;
            }
        }

    }
    //Classe Disciplina
    static class Disciplina implements Serializable {


        private String nome;
        private double quantidade;
        private final List<Aluno> alunosMatriculados;


        Disciplina(String nome, double quantidade) {
            this.nome = nome;
            this.quantidade = quantidade;
            this.alunosMatriculados = new ArrayList<>();
        }


        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public double getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(double quantidade) {
            this.quantidade = quantidade;

        }

        public List<Model.Aluno> getAlunosMatriculados() {
            return alunosMatriculados;
        }

        public int quantidadeDeAlunosMatriculados() {
            return getAlunosMatriculados().size();
        }

        public void adicionarAluno(Aluno aluno) {
            if (!alunosMatriculados.contains(aluno)) {
                alunosMatriculados.add(aluno);
            } else {
                System.out.println("O aluno já está matriculado nesta disciplina.");
            }
        }

        public void removerAluno(Aluno aluno) {
            alunosMatriculados.remove(aluno);
        }
    }

    }
