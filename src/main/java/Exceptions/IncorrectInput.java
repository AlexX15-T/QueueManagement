package Exceptions;

public class IncorrectInput extends Exception {
    private String msg;

    public IncorrectInput(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "    IncorrectInputError" + '\n' + msg + '\n';
    }

    public void display()
    {
        System.out.println(msg);
    }
}
