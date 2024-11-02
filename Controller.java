
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Controller implements Serializable {
    private final List<Model.Disciplina> disciplinas;
    private static final String FILE_PATH = "arquivo.bin";

    public Controller() {
        this.disciplinas = new ArrayList<>();
        carregar();
    }

    public void salvar() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            ControleDeMaterias controleDeMaterias = new ControleDeMaterias();
            controleDeMaterias.disciplinas.addAll(this.disciplinas);
            out.writeObject(controleDeMaterias);
            System.out.println("Disciplinas salvas com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao salvar os dados: " + e.getMessage());
        }
    }

    //Carregar

    public void carregar() {
        File file = new File(String.valueOf(FILE_PATH));
        if (!file.exists()) {
            System.out.println("Arquivo não encontrado");
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            ControleDeMaterias controle = (ControleDeMaterias) in.readObject();
            this.disciplinas.clear();
            this.disciplinas.addAll(controle.disciplinas);
            System.out.println("Disciplinas carregadas com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar os dados: " + e.getMessage());
        }
    }

    static class ControleDeMaterias implements Serializable  {
        private final List<Model.Disciplina> disciplinas;
        private static final long serialVersionUID = 1L;
        // Construtor do ControleDeMaterias
        public ControleDeMaterias() {
            this.disciplinas = new ArrayList<>();

        }



        // Método que adiciona uma disciplina
        public void adicionarMateria(String nome, double quantidade) {
            Model.Disciplina novaDisciplina = new Model.Disciplina(nome, quantidade);
            disciplinas.add(novaDisciplina);
            System.out.println("Materia" + nome + "adicionada com sucesso!");
        }

        // Método que modifica uma disciplina
        public void modificarMateria(String nome, String novoNome, double novaQuantidade) {
            // Procura a disciplina pelo nome
            for (Model.Disciplina disciplina : disciplinas) {
                if (disciplina.getNome().equals(nome)) {
                    disciplina.setNome(novoNome);
                    disciplina.setQuantidade(novaQuantidade);
                    System.out.println("Disciplina " + nome + " modificada para " + novoNome + " com quantidade " + novaQuantidade);
                    return; // Sai do método após modificar
                }
            }
            System.out.println("Não foi possível encontrar a disciplina." + nome);
        }

        // Método que remove uma disciplina
        public void removerMateria(int id) {
            // Verifica se a disciplina está dentro dos limites da lista
            if (id >= 0 && id < disciplinas.size()) {
                Model.Disciplina disciplina = disciplinas.remove(id);
                System.out.println("Disciplina " + disciplina.getNome() + " removida com sucesso.");
            } else {
                System.out.println("Não foi possível encontrar essa disciplina");
            }
        }

        public List<Model.Disciplina> listarMaterias() {
            return disciplinas;
        }
        public Model.Disciplina encontrarDisciplinaPorNome(String nome) {
            for (Model.Disciplina disciplina : disciplinas) {
                if (disciplina.getNome().equalsIgnoreCase(nome)) {
                    return disciplina;  // Retorna a disciplina se encontrar o nome
                }
            }
            return null;  // Retorna null se não encontrar
        }
    }

    static class ControleDeAlunos {

        private final List<Model.Aluno> alunos;

        // Construtor do ControleDeAlunos
        ControleDeAlunos() {
            this.alunos = new ArrayList<>();

        }

        public void matricularAluno(String nomeAluno, Model.Disciplina disciplina) {

            Model.Aluno aluno = new Model.Aluno(nomeAluno);
            disciplina.adicionarAluno(aluno);
            alunos.add(aluno);
            System.out.println("Aluno " + nomeAluno + " matriculado na disciplina " + disciplina.getNome());

        }



        public double notaNecessariaFinal(Model.Aluno aluno, Model.Disciplina disciplina) {
            return aluno.notaNecessariaFinal(aluno, disciplina); // Chama o cálculo da nota necessária para a final
        }

        public Model.Aluno encontrarAlunoPorNome(String nome) {
            for (Model.Aluno aluno : alunos) {
                if (aluno.getNome().equals(nome)) {
                    return aluno;
                }
            }
            System.out.println("Aluno" + nome + " nao encontrado.");
            return null; // Retorna null se o aluno não for encontrado
        }


        public double calcularMedia(Model.Aluno aluno, Model.Disciplina disciplina) {
            List<Double> notas = aluno.notasPorDisciplina.get(disciplina);
            return aluno.calcularMedia(aluno, disciplina);
        }

        public void adicionarNota(Model.Aluno aluno, Model.Disciplina disciplina, double nota) {
            aluno.adicionarNota(aluno, nota); // Você pode precisar implementar isso na classe Aluno.
        }



        // Listar alunos
        public List<Model.Aluno> listarAlunos() {
            return alunos;
        }
    }
}
