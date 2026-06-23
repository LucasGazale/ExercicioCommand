import java.util.HashMap;
import java.util.function.Function;

class Pessoa {
    int id;
    String nome;

    Pessoa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Pessoa{id=" + id + ", nome='" + nome + "'}";
    }
}

interface Command {
    public abstract void execute();
}

class NewPessoa implements Command {
    // Database ID: Pessoa
    private HashMap<Integer, Pessoa> db;

    public NewPessoa(HashMap<Integer, Pessoa> db) {
        this.db = db;
    }

    public void execute() {

    }
}