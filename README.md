# Sistema de Controle de Alunos e Disciplinas

Este projeto é um sistema simples de gerenciamento de alunos e disciplinas, utilizando Java, com interface gráfica em Swing e persistência de dados através de serialização de objetos.

## Funcionalidades

1. **Gerenciamento de Disciplinas**:
   - Adicionar novas disciplinas.
   - Modificar disciplinas existentes.
   - Remover disciplinas.
   - Listar todas as disciplinas.

2. **Gerenciamento de Alunos**:
   - Matricular alunos em disciplinas.
   - Adicionar notas aos alunos em uma disciplina.
   - Calcular a média do aluno com base nas duas maiores notas.
   - Calcular a nota necessária para passar na final (se aplicável).
   - Listar todos os alunos.

3. **Persistência de Dados**:
   - Os dados de disciplinas e alunos são armazenados em arquivos binários (`arquivo.bin`) e carregados automaticamente ao iniciar o programa.
   - Salvamento automático de dados ao encerrar o programa.

 Estrutura de Código

1. Model
A camada `Model` contém as classes que representam as entidades do sistema:
- `Aluno`: Representa um aluno e armazena o nome, além de um mapa com as disciplinas e as notas.
- `Disciplina`: Representa uma disciplina, contendo o nome, a quantidade de créditos e uma lista de alunos matriculados.
- Ambos implementam a interface `Serializable` para permitir a serialização.

2. Controller
A camada `Controller` gerencia as operações lógicas do sistema, incluindo as operações de salvar e carregar os dados, bem como o controle de alunos e disciplinas:
- **ControleDeMaterias**: Gerencia as disciplinas (adicionar, modificar, remover e listar).
- **ControleDeAlunos**: Gerencia as operações dos alunos, como matrícula, adicionar notas, calcular médias, entre outras.
- A classe `Controller` é responsável por carregar e salvar os dados em arquivos binários.

3. Interface Gráfica (Swing)
Ainda não foi implementada uma interface gráfica completa, mas o projeto já possui um esqueleto que pode ser adaptado para criar telas interativas para o usuário. A interface gráfica pode ser expandida com botões e campos de entrada para facilitar o uso do sistema.




 
