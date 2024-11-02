
public class Main {
    public static void main(String[] args) {
        Controller.ControleDeMaterias controleDeMaterias = new Controller.ControleDeMaterias();
        Controller.ControleDeAlunos controleDeAlunos = new Controller.ControleDeAlunos();
        Controller controller = new Controller();
        new View(controleDeMaterias, controleDeAlunos, controller);


    }
}