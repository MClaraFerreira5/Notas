import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

public class View extends JFrame {
    Controller controle;
    Controller.ControleDeAlunos controleDeAlunos;
    Controller.ControleDeMaterias controleDeMaterias;

    JComboBox<String> caixaDeDisciplinas = new JComboBox<>();


    public View(Controller.ControleDeMaterias controleDeMaterias, Controller.ControleDeAlunos controleDeAlunos, Controller controle) {

        super("Gerenciamento de Dados Acadêmicos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLayout(new FlowLayout());
        this.controle = controle;
        this.controleDeAlunos = controleDeAlunos;
        this.controleDeMaterias = controleDeMaterias;



        // Botões
        JButton disciplinasBtn = new JButton("Gerenciar Disciplinas");
        JButton alunosBtn = new JButton("Gerenciar Alunos");
        JButton gerenciarNotasBtn = new JButton("Gerenciar Notas");
        JButton sairBtn = new JButton("Sair");


        // Colocar os botões
        add(disciplinasBtn);
        add(alunosBtn);
        add(gerenciarNotasBtn);
        add(sairBtn);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controle.salvar();
            }
        });



        // Evento dos botões
        disciplinasBtn.addActionListener(e -> listarDisciplinas());
        alunosBtn.addActionListener(e -> listarAlunos());
        gerenciarNotasBtn.addActionListener(e -> listaDeNotas());
        sairBtn.addActionListener(e -> System.exit(0));


        setVisible(true);
    }

    private void listarDisciplinas() {
        //Configurações iniciais
        JFrame frameDeDisciplinas = new JFrame("Gerenciamento de Disciplinas");
        frameDeDisciplinas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDeDisciplinas.setSize(600, 400);

        //Criação do painel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        frameDeDisciplinas.add(panelPrincipal);

        //Criação dos paineis de botões
        JPanel panelDosBotoes = new JPanel();
        panelPrincipal.add(panelDosBotoes);

        //Botões:
        JButton adicionarBtn = new JButton("Adicionar Disciplina");
        JButton alterarBtn = new JButton("Alterar Disciplina");
        JButton removerBtn = new JButton("Remover Disciplina");
        JButton sairBtn = new JButton("Sair");

        panelDosBotoes.add(adicionarBtn);
        panelDosBotoes.add(alterarBtn);
        panelDosBotoes.add(removerBtn);
        panelDosBotoes.add(sairBtn);

        // Campos do nome da disciplina e quantidade de alunos
        panelPrincipal.add(new JLabel("Nome da Disciplina: "));
        JTextField nomeDisciplina = new JTextField(15);
        nomeDisciplina.setBounds(300, 200, 40, 10);
        JTextField quantidadeAlunos = new JTextField(15);
        quantidadeAlunos.setBounds(300, 200, 40, 10);
        panelPrincipal.add(nomeDisciplina);
        panelPrincipal.add(new JLabel("Quantidade: "));
        panelPrincipal.add(quantidadeAlunos);

        //Evento sair
        sairBtn.addActionListener(e -> frameDeDisciplinas.dispose());

        //Evento de adicionar matéria
        adicionarBtn.addActionListener(e -> {
            String nome = nomeDisciplina.getText();
            String quantidade = quantidadeAlunos.getText();
            try {
                double quantd = Double.parseDouble(quantidade);
                if (!nome.isEmpty()) {//Caso o campo esteja vazio n entra
                    controleDeMaterias.adicionarMateria(nome, quantd);
                    JOptionPane.showMessageDialog(frameDeDisciplinas, "Disciplina " + nome + " adicionada com sucesso!");
                    nomeDisciplina.setText("");
                    quantidadeAlunos.setText("");
                } else {
                    JOptionPane.showMessageDialog(frameDeDisciplinas, "O nome da disciplina não pode ser vazio.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameDeDisciplinas, "Quantidade inválida.");
            }

        });

        //Evento alterar uma matéria
        alterarBtn.addActionListener(e -> {
            String nome = nomeDisciplina.getText();
            String novoNome = JOptionPane.showInputDialog("Novo Nome:");

            if (novoNome == null || novoNome.isEmpty()) {
                JOptionPane.showMessageDialog(frameDeDisciplinas, "O novo nome da disciplina não pode ser vazio.");
                return;
            }

            String quantidade = JOptionPane.showInputDialog("Nova Quantidade:");

            try {
                double novaQuantd = Double.parseDouble(quantidade);
                controleDeMaterias.modificarMateria(nome, novoNome, novaQuantd);
                JOptionPane.showMessageDialog(frameDeDisciplinas, "Disciplina " + nome + " alterada com sucesso!");
                nomeDisciplina.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameDeDisciplinas, "Quantidade inválida.");
            }
        });

        //Remover uma matéria
        removerBtn.addActionListener(e -> {
            String nome = nomeDisciplina.getText();
            List<Model.Disciplina> disciplinas = controleDeMaterias.listarMaterias();
            for (int i = 0; i < disciplinas.size(); i++) {
                if (disciplinas.get(i).getNome().equals(nome)) {
                    controleDeMaterias.removerMateria(i);
                    JOptionPane.showMessageDialog(frameDeDisciplinas, "Disciplina " + nome + " removida com sucesso!");
                    nomeDisciplina.setText("");
                    return;
                }
            }
            JOptionPane.showMessageDialog(frameDeDisciplinas, "Disciplina não encontrada.");
        });

        frameDeDisciplinas.setVisible(true);
    }

    private void listarAlunos() {
        //Configurações iniciais
        JFrame frameDeAlunos = new JFrame("Gerenciamento de Alunos");
        frameDeAlunos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDeAlunos.setSize(600, 400);

        //Criar o painel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        frameDeAlunos.add(panelPrincipal);


        JTextField nomeAluno = new JTextField(20);
        panelPrincipal.add(new JLabel("Nome do Aluno: "));
        panelPrincipal.add(nomeAluno);

        JTextField notaAluno = new JTextField(20);
        panelPrincipal.add(new JLabel("Nota do Aluno: "));
        panelPrincipal.add(notaAluno);


        panelPrincipal.add(new JLabel("Selecione a Disciplina:"));
        panelPrincipal.add(caixaDeDisciplinas);

        List<Model.Disciplina> disciplinas = controleDeMaterias.listarMaterias();
        for (Model.Disciplina disciplina : disciplinas) {
            caixaDeDisciplinas.addItem(disciplina.getNome());
        }
        //Panel dos botões
        JPanel panelDosBotoes = new JPanel();
        panelPrincipal.add(panelDosBotoes);

        //Botões
        JButton matriculaBtn = new JButton("Matricular Aluno");
        JButton addNotasBtn = new JButton("Adicionar Notas");
        JButton listarAlunosBtn = new JButton("Listar Alunos");
        JButton sairBtn = new JButton("Sair");

        panelDosBotoes.add(matriculaBtn);
        panelDosBotoes.add(addNotasBtn);
        panelDosBotoes.add(listarAlunosBtn);
        panelDosBotoes.add(sairBtn);

        //Evento sair
        sairBtn.addActionListener(e -> {
            frameDeAlunos.dispose();
        });
        //Evento Listar alunos(
        listarAlunosBtn.addActionListener(e -> listarAlunosPorDisciplina());

        //Evento Matricular
        matriculaBtn.addActionListener(e -> {
            String nome = nomeAluno.getText();
            String nomeDisciplina = (String) caixaDeDisciplinas.getSelectedItem();
            if (!nome.isEmpty() && nomeDisciplina != null) {
                Model.Disciplina disciplinaSelecionada = disciplinas.stream()
                        .filter(d -> d.getNome().equals(nomeDisciplina))
                        .findFirst()
                        .orElse(null);
                if (disciplinaSelecionada != null) {
                    if (disciplinaSelecionada.getQuantidade() > disciplinaSelecionada.quantidadeDeAlunosMatriculados()) {
                        controleDeAlunos.matricularAluno(nome, disciplinaSelecionada);
                        JOptionPane.showMessageDialog(frameDeAlunos, "Aluno " + nome + " matriculado na disciplina " + nomeDisciplina);
                        nomeAluno.setText("");
                    } else {
                        JOptionPane.showMessageDialog(frameDeAlunos, "Disciplina cheia!");
                    }

                }
            } else {
                JOptionPane.showMessageDialog(frameDeAlunos, "Disciplina não encontrada.");
            }

        });

        addNotasBtn.addActionListener(e -> {
            String nome = nomeAluno.getText();
            String nomeDisciplina = (String) caixaDeDisciplinas.getSelectedItem();
            String notaTexto = notaAluno.getText();

            if (nome.isEmpty() || nomeDisciplina == null || notaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(frameDeAlunos, "Preencha todos os campos.");
                return;
            }

            try {
                double nota = Double.parseDouble(notaTexto);

                // Encontra o aluno pelo nome
                Model.Aluno aluno = controleDeAlunos.encontrarAlunoPorNome(nome);
                if (aluno == null) {
                    JOptionPane.showMessageDialog(frameDeAlunos, "Aluno não encontrado.");
                    return;
                }
                Model.Disciplina disciplina = controleDeMaterias.listarMaterias().stream()
                        .filter(d -> d.getNome().equals(nomeDisciplina))
                        .findFirst()
                        .orElse(null);

                if (disciplina == null) {
                    JOptionPane.showMessageDialog(frameDeAlunos, "Disciplina não encontrada.");
                    return;
                }

                // Adiciona a nota à disciplina para o aluno
                controleDeAlunos.adicionarNota(aluno, disciplina, nota);
                JOptionPane.showMessageDialog(frameDeAlunos, "Nota " + nota + " adicionada ao aluno " + nome + " na disciplina " + nomeDisciplina);
                nomeAluno.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frameDeAlunos, "Nota inválida.");
            }
        });


        frameDeAlunos.setVisible(true);
    }

    private void atualizarComboBoxDisciplinas(JComboBox<String> caixaDeDisciplinas) {
        caixaDeDisciplinas.removeAllItems();
        List<Model.Disciplina> disciplinas = controleDeMaterias.listarMaterias();
        for (Model.Disciplina disciplina : disciplinas) {
            caixaDeDisciplinas.addItem(disciplina.getNome());
        }
    }

    private void listaDeNotas() {
        //Configurações iniciais
        JFrame frameDeNotas = new JFrame("Gerenciamento de Notas");
        frameDeNotas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameDeNotas.setSize(400, 300);

        //Painel principal e dos botões
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        frameDeNotas.add(panelPrincipal);
        JPanel panelDosBotoes = new JPanel();
        panelPrincipal.add(panelDosBotoes);
        panelPrincipal.add(caixaDeDisciplinas);

        //Botões Para calcular a média e situação do aluno
        JButton mediaBtn = new JButton("Calcular Média");
        JButton situacaoBtn = new JButton("Situação do Aluno");
        JButton listarNotasBtn = new JButton("Listar Notas");
        JButton sairbtn = new JButton("Sair");
        panelDosBotoes.add(mediaBtn);
        panelDosBotoes.add(situacaoBtn);
        panelDosBotoes.add(listarNotasBtn);
        panelDosBotoes.add(sairbtn);

        // Campos
        JTextField nomeAluno = new JTextField(20);
        panelPrincipal.add(new JLabel("Nome do Aluno: "));
        panelPrincipal.add(nomeAluno);

        //Evento sair
        sairbtn.addActionListener(e -> {
            frameDeNotas.dispose();
        });

        //Evento listar as notas
        listarNotasBtn.addActionListener(e -> listarNotasPorAluno());

        // Evento calcular média
        mediaBtn.addActionListener(e -> {
            String nome = nomeAluno.getText();
            String nomeDisciplina = (String) caixaDeDisciplinas.getSelectedItem();
            Model.Aluno aluno = controleDeAlunos.encontrarAlunoPorNome(nome);
            Model.Disciplina disciplina = controleDeMaterias.encontrarDisciplinaPorNome(nomeDisciplina);
            if (aluno != null) {
                double media = controleDeAlunos.calcularMedia(aluno, disciplina);
                JOptionPane.showMessageDialog(frameDeNotas, "Média do aluno " + nome + ": " + media);
            } else {
                JOptionPane.showMessageDialog(frameDeNotas, "Aluno não encontrado.");
            }
        });

        // Evento situação
        situacaoBtn.addActionListener(e -> {

            String nome = nomeAluno.getText();
            String nomeDisciplina = (String) caixaDeDisciplinas.getSelectedItem();
            Model.Aluno aluno = controleDeAlunos.encontrarAlunoPorNome(nome);
            Model.Disciplina disciplina = controleDeMaterias.encontrarDisciplinaPorNome(nomeDisciplina);

            if (aluno != null) {
                if (aluno.calcularMedia(aluno, disciplina) >= 7) {
                    Model.Aluno.Situacao situacao = Model.Aluno.Situacao.APROVADO;
                    JOptionPane.showMessageDialog(frameDeNotas, "Aluno " + nome + ": " + situacao);
                } else if (aluno.calcularMedia(aluno, disciplina) >= 3) {
                    double notaNecessaria = controleDeAlunos.notaNecessariaFinal(aluno, disciplina);
                    JLabel msg = new JLabel("O aluno precisará fazer a final");
                    JOptionPane.showMessageDialog(frameDeNotas, msg);
                    JLabel nota = new JLabel("A nota necessária é: " + notaNecessaria);
                    String novaNota = JOptionPane.showInputDialog("Nota da Final:");
                    double notafinalDouble = Double.parseDouble(novaNota);
                    if (notafinalDouble < notaNecessaria) {
                        Model.Aluno.Situacao situacao = Model.Aluno.Situacao.REPROVADO_NA_FINAL;
                        JOptionPane.showMessageDialog(frameDeNotas, "Aluno " + nome + ": " + situacao);
                    } else {
                        Model.Aluno.Situacao situacao = Model.Aluno.Situacao.APROVADO_NA_FINAL;
                        JOptionPane.showMessageDialog(frameDeNotas, "Aluno " + nome + ": " + situacao);
                    }

                } else {
                    Model.Aluno.Situacao situacao = Model.Aluno.Situacao.REPROVADO;
                    JOptionPane.showMessageDialog(frameDeNotas, "Aluno " + nome + ": " + situacao);
                }
            } else {
                JOptionPane.showMessageDialog(frameDeNotas, "Aluno não encontrado.");
            }
        });
        frameDeNotas.setVisible(true);
    }

    private void listarAlunosPorDisciplina() {
        // Configurações iniciais
        JFrame frameAlunosPorDisciplina = new JFrame("Alunos Matriculados por Disciplina");
        frameAlunosPorDisciplina.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameAlunosPorDisciplina.setSize(400, 300);

        // Painel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        frameAlunosPorDisciplina.add(panelPrincipal);

        // ComboBox para selecionar a disciplina
        JComboBox<String> caixaDeDisciplinas = new JComboBox<>();
        List<Model.Disciplina> disciplinas = controleDeMaterias.listarMaterias();
        for (Model.Disciplina disciplina : disciplinas) {
            caixaDeDisciplinas.addItem(disciplina.getNome());
        }
        panelPrincipal.add(new JLabel("Selecione a Disciplina:"));
        panelPrincipal.add(caixaDeDisciplinas);

        // Botão para mostrar alunos
        JButton mostrarAlunosBtn = new JButton("Mostrar Alunos Matriculados");
        panelPrincipal.add(mostrarAlunosBtn);

        // Área de texto para exibir alunos
        JTextArea areaDeTexto = new JTextArea(10, 30);
        areaDeTexto.setEditable(false);
        panelPrincipal.add(new JScrollPane(areaDeTexto));

        // Evento do botão
        mostrarAlunosBtn.addActionListener(e -> {
            String nomeDisciplina = (String) caixaDeDisciplinas.getSelectedItem();
            Model.Disciplina disciplinaSelecionada = controleDeMaterias.encontrarDisciplinaPorNome(nomeDisciplina);

            if (disciplinaSelecionada != null) {
                List<Model.Aluno> alunosMatriculados = disciplinaSelecionada.getAlunosMatriculados(); // Método que deve ser implementado na classe Disciplina
                areaDeTexto.setText(""); // Limpa a área de texto
                for (Model.Aluno aluno : alunosMatriculados) {
                    areaDeTexto.append(aluno.getNome() + "\n");
                }
            } else {
                areaDeTexto.setText("Disciplina não encontrada.");
            }
        });

        frameAlunosPorDisciplina.setVisible(true);
    }

    private void listarNotasPorAluno() {
        // Configurações iniciais
        JFrame frameNotasPorAluno = new JFrame("Notas por Aluno");
        frameNotasPorAluno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameNotasPorAluno.setSize(400, 300);

        // Painel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        frameNotasPorAluno.add(panelPrincipal);

        // ComboBox para selecionar o aluno
        JComboBox<String> caixaDeAlunos = new JComboBox<>();
        List<Model.Aluno> alunos = controleDeAlunos.listarAlunos();
        for (Model.Aluno aluno : alunos) {
            caixaDeAlunos.addItem(aluno.getNome());
        }
        panelPrincipal.add(new JLabel("Selecione o Aluno:"));
        panelPrincipal.add(caixaDeAlunos);

        // Botão para mostrar notas
        JButton mostrarNotasBtn = new JButton("Mostrar Notas");
        panelPrincipal.add(mostrarNotasBtn);

        // Área de texto para exibir notas
        JTextArea areaDeTexto = new JTextArea(10, 30);
        areaDeTexto.setEditable(false);
        panelPrincipal.add(new JScrollPane(areaDeTexto));

        mostrarNotasBtn.addActionListener(e -> {
            String nomeAluno = (String) caixaDeAlunos.getSelectedItem();
            Model.Aluno alunoSelecionado = controleDeAlunos.encontrarAlunoPorNome(nomeAluno);

            if (alunoSelecionado != null) {
                areaDeTexto.setText(""); // Limpa a área de texto
                for (Map.Entry<Model.Aluno, List<Double>> entry : alunoSelecionado.notasPorDisciplina.entrySet()) {
                    Model.Aluno disciplina = entry.getKey();
                    List<Double> notas = entry.getValue();
                    areaDeTexto.append("Disciplina: " + disciplina.getNome() + "\n");
                    areaDeTexto.append("Notas: " + notas.toString() + "\n\n");
                }
            } else {
                areaDeTexto.setText("Aluno não encontrado.");
            }
        });

        frameNotasPorAluno.setVisible(true);
    }
}