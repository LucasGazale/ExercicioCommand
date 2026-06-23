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

class Database {
    private HashMap<Integer, Pessoa> db = new HashMap<>();

    public void addPessoa(Pessoa pessoa) {
        db.put(pessoa.id, pessoa);
    }

    public void deletePessoa(int id) {
        db.remove(id);
    }

    public Pessoa getPessoa(int id) {
        return db.get(id);
    }

    public HashMap<Integer, Pessoa> getAllPessoas() {
        return db;
    }
}

interface Command {
    public abstract void execute();
}

class NewPessoa implements Command {
    // Database ID: Pessoa
    private Database db;

    public NewPessoa(Database db) {
        this.db = db;
    }

    public void execute() {

    }
}

class DeletePessoa implements Command {
    // Database ID: Pessoa
    private Database db;

    public DeletePessoa(Database db) {
        this.db = db;
    }

    public void execute() {

    }
}

class GetPessoa implements Command {
    // Database ID: Pessoa
    private Database db;

    public GetPessoa(Database db) {
        this.db = db;
    }

    public void execute() {

    }
}

class All implements Command {
    // Database ID: Pessoa
    private Database db;

    public All(Database db) {
        this.db = db;
    }

    public void execute() {

    }
}