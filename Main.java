import java.util.HashMap;

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
    public abstract void execute(Object arg);
}

class NewPessoa implements Command {
    // Database ID: Pessoa
    private Database db;

    public NewPessoa(Database db) {
        this.db = db;
    }

    public void execute(Object arg) {
        if (arg instanceof Pessoa) {
            db.addPessoa((Pessoa) arg);
        } else {
            throw new IllegalArgumentException("Argument must be of type Pessoa");
        }
    }
}

class DeletePessoa implements Command {
    // Database ID: Pessoa
    private Database db;

    public DeletePessoa(Database db) {
        this.db = db;
    }

    public void execute(Object arg) {
        if (arg instanceof Integer) {
            db.deletePessoa((Integer) arg);
        } else {
            throw new IllegalArgumentException("Argument must be of type Integer");
        }
    }
}

class GetPessoa implements Command {
    // Database ID: Pessoa
    private Database db;

    public GetPessoa(Database db) {
        this.db = db;
    }

    public void execute(Object arg) {
        if (arg instanceof Integer) {
            Pessoa pessoa = db.getPessoa((Integer) arg);
            if (pessoa != null) {
                System.out.println(pessoa);
            } else {
                System.out.println("Pessoa not found");
            }
        } else {
            throw new IllegalArgumentException("Argument must be of type Integer");
        }
    }
}

class All implements Command {
    // Database ID: Pessoa
    private Database db;

    public All(Database db) {
        this.db = db;
    }

    public void execute(Object args) {
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
    private HashMap<String, Command> commands;

    public Server() {
        db = new Database();
        commands = new HashMap<>();
        commands.put("new", new NewPessoa(db));
        commands.put("delete", new DeletePessoa(db));
        commands.put("get", new GetPessoa(db));
        commands.put("all", new All(db));
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}

public class Main  {
    public static void main(String[] args) {
        Server server = new Server();
        System.out.println("Server started. Listening for commands...");
        while (true)  {
            System.out.print("Enter command (new <id> <nome>, delete <id>, get <id>, all) or 'exit' to quit: ");

            String input = System.console().readLine();
            String[] parts = input.split(" ");
            String commandName = parts[0];

            if (commandName.equals("exit")) {
                System.out.println("Exiting...");
                break;
            }
            else if (commandName.equals("new") && parts.length == 3) {
                try {
                    int id = Integer.parseInt(parts[1]);
                    String nome = parts[2];
                    server.getCommand("new").execute(new Pessoa(id, nome));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid command or arguments");
                    continue;
                }
            } else if (commandName.equals("delete") && parts.length == 2) {
                try {
                    int id = Integer.parseInt(parts[1]);
                    server.getCommand("delete").execute(id);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid command or arguments");
                    continue;
                }
            } else if (commandName.equals("get") && parts.length == 2) {
                try {
                    int id = Integer.parseInt(parts[1]);
                    server.getCommand("get").execute(id);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid command or arguments");
                    continue;
                }
            } else if (commandName.equals("all")) {
                server.getCommand("all").execute(null);
            } else {
                System.out.println("Invalid command or arguments");
                
            }
        }
    }
}