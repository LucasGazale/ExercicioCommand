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
    void execute();
}

class NewPessoa implements Command {
    private Database db;
    private int id;
    private String nome;

    public NewPessoa(Database db, int id, String nome) {
        this.db = db;
        this.id = id;
        this.nome = nome;
    }

    public void execute() {
        db.addPessoa(new Pessoa(id, nome));
        System.out.println("Pessoa adicionada: " + db.getPessoa(id));
    }
}

class DeletePessoa implements Command {
    private Database db;
    private int id;

    public DeletePessoa(Database db, int id) {
        this.db = db;
        this.id = id;
    }

    public void execute() {
        db.deletePessoa(id);
        System.out.println("Pessoa removida: " + id);
    }
}

class GetPessoa implements Command {
    private Database db;
    private int id;

    public GetPessoa(Database db, int id) {
        this.db = db;
        this.id = id;
    }

    public void execute() {
        Pessoa pessoa = db.getPessoa(id);
        if (pessoa != null) {
            System.out.println(pessoa);
        } else {
            System.out.println("Pessoa not found");
        }
    }
}

class All implements Command {
    private Database db;

    public All(Database db) {
        this.db = db;
    }

    public void execute() {
        HashMap<Integer, Pessoa> pessoas = db.getAllPessoas();
        if (pessoas.isEmpty()) {
            System.out.println("No pessoas found");
        } else {
            for (Pessoa pessoa : pessoas.values()) {
                System.out.println(pessoa);
            }
        }
    }
}

class Server {
    private Database db;
    private HashMap<String, Function<String[], Command>> factories;

    public Server() {
        db = new Database();
        factories = new HashMap<>();
        factories.put("new",    parts -> new NewPessoa(db, Integer.parseInt(parts[1]), parts[2]));
        factories.put("delete", parts -> new DeletePessoa(db, Integer.parseInt(parts[1])));
        factories.put("get",    parts -> new GetPessoa(db, Integer.parseInt(parts[1])));
        factories.put("all",    parts -> new All(db));
    }

    public void service(String[] parts) {
        Function<String[], Command> factory = factories.get(parts[0]);
        if (factory != null) {
            factory.apply(parts).execute();
        } else {
            System.out.println("Invalid command or arguments");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        System.out.println("Server started. Listening for commands...");

        while (true) {
            System.out.print("Enter command (new <id> <nome>, delete <id>, get <id>, all) or 'exit' to quit: ");

            String input = System.console().readLine();
            String[] parts = input.split(" ");

            if (parts[0].equals("exit")) {
                System.out.println("Exiting...");
                break;
            }

            try {
                server.service(parts);
            } catch (NumberFormatException e) {
                System.out.println("Invalid command or arguments");
            }
        }
    }
}
