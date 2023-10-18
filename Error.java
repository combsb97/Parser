public class Error{
  public Error(String message, int line, int idx){
    System.out.println("Line " + line + " idx " + idx + ": " + message);
    System.exit(0);
  }
}