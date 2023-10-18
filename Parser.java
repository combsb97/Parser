/* Complete all the methods.
EBNF of Mini Language
Program" --> "("Sequence State")".
Sequence --> "("Statements")".
Statements --> Stmt*
Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
State -->  "("Pairs")".
Pairs -->  Pair*.
Pair --> "("Identifier Literal")".
NullStatement --> "skip".
Assignment --> "assign" Identifier Expression.
Conditional --> "conditional" Expression Stmt Stmt.
Loop --> "loop" Expression Stmt.
Block --> "block" Statements.
Expression --> Identifier | Literal | "("Operation Expression Expression")".
Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".

Note: Treat Identifier and Literal as terminal symbols. Every symbol inside " and " is a terminal symbol. The rest are non terminals.

*/
public class Parser{
  private Token currentToken;
  Scanner scanner;
  boolean many_stmt = true, many_pair = true, cond = true;

  private void accept(byte expectedKind) {
    if (currentToken.kind == expectedKind)
      currentToken = scanner.scan();
    else
      new Error("Syntax error: " + currentToken.spelling + " is not expected. expected type is: " + expectedKind,
                currentToken.line, currentToken.idx);
  }

  private void acceptIt() {
    currentToken = scanner.scan();
  }

  public void parse() {
    SourceFile sourceFile = new SourceFile();
    scanner = new Scanner(sourceFile.openFile());
    currentToken = scanner.scan();
    parseProgram();
    if (currentToken.kind != Token.EOT)
      new Error("Syntax error: Redundant characters at the end of program.",
                currentToken.line, currentToken.idx);
  }

  //Program" --> "("Sequence State")".
  private void parseProgram() {
    System.out.println("Entering parseProgram()");
    accept(Token.LPAREN);
    parseSequence();
    parseState();
    accept(Token.RPAREN);
    System.out.println("Exiting parseProgram()");
  }

  //Sequence --> "("Statements")".
  private void parseSequence(){
    System.out.println("Entering parseSequence()");
    accept(Token.LPAREN);
    parseStatements();
    accept(Token.RPAREN);
    System.out.println("Exiting parseSequence()");
  }

  //Statements --> Stmt*
  private void parseStatements(){
    System.out.println("Entering parseStatements()");
    while(many_stmt) {
      parseStmt();
    }
    System.out.println("Exiting parseStatements()");
  }

  //Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
  private void parseStmt(){
    System.out.println("Entering parseStmt()");
    boolean flag = true;
    accept(Token.LPAREN);
    while(flag) {
      if (currentToken.kind == Token.SKIP) {
        parseNullStatement();
      } else if (currentToken.kind == Token.ASSIGN) {
        parseAssignment();
      } else if (currentToken.kind == Token.CONDITIONAL) {
        cond = false;
        parseConditional();
        cond = true;
      } else if (currentToken.kind == Token.LOOP) {
        cond = false;
        parseLoop();
        cond = true;
      } else if (currentToken.kind == Token.BLOCK) {
        parseBlock();
      }
      else
        flag = false;
    }
    accept(Token.RPAREN);
    System.out.println("Exiting parseStmt()");

    if(cond){
      if(currentToken.kind == Token.RPAREN){
        many_stmt = false;
      }
    }
  }

  //State -->  "("Pairs")".
  private void parseState(){
    System.out.println("Entering parseState()");
    accept(Token.LPAREN);
    parsePairs();
    accept(Token.RPAREN);
    System.out.println("Exiting parseState()");
  }

  //Pairs --> Pair*.
  private void parsePairs(){
    System.out.println("Entering parsePairs()");
    while(many_pair) {
      parsePair();
    }
    System.out.println("Exiting parsePairs()");
  }

  //Pair --> "("Identifier Literal")".
  private void parsePair(){
    System.out.println("Entering parsePair()");
    accept(Token.LPAREN);
    accept(Token.IDENTIFIER);
    accept(Token.LITERAL);
    accept(Token.RPAREN);
    System.out.println("Exiting parsePair()");
    if(currentToken.kind == Token.RPAREN){
      many_pair = false;
    }
  }

  //NullStatement --> skip.
  private void parseNullStatement(){
    System.out.println("Entering parseNullStatement()");
    accept(Token.SKIP);
    System.out.println("Exiting parseNullStatement()");
  }

  //Assignment --> "assign" Identifier Expression.
  private void parseAssignment(){
    System.out.println("Entering parseAssignment()");
    accept(Token.ASSIGN);
    accept(Token.IDENTIFIER);
    parseExpression();
    System.out.println("Exiting parseAssignment()");
  }

  //Conditional --> "conditional" Expression Stmt Stmt.
  private void parseConditional(){
    System.out.println("Entering parseConditional()");
    accept(Token.CONDITIONAL);
    parseExpression();
    parseStmt();
    parseStmt();
    System.out.println("Exiting parseConditional()");
  }

  //Loop --> "loop" Expression Stmt.
  private void parseLoop(){
    System.out.println("Entering parseLoop()");
    accept(Token.LOOP);
    parseExpression();
    parseStmt();
    System.out.println("Exiting parseLoop()");
  }

  //Block --> "block" Statements.
  private void parseBlock(){
    System.out.println("Entering parseBlock()");
    accept(Token.BLOCK);
    parseStatements();
    System.out.println("Exiting parseBlock()");
  }

  //Expression --> Identifier | Literal | "("Operation Expression Expression")".
  private void parseExpression(){
    System.out.println("Entering parseExpression()");
    if(currentToken.kind == Token.IDENTIFIER || currentToken.kind == Token.LITERAL){
      acceptIt();
    }
    /*
    else if(currentToken.kind == Token.LITERAL){
      accept(Token.LITERAL);
    }

     */
    else{
      accept(Token.LPAREN);
      parseOperation();
      parseExpression();
      parseExpression();
      accept(Token.RPAREN);
    }
    System.out.println("Exiting parseExpression()");
  }

  //Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".
  private void parseOperation(){
    System.out.println("Entering parseOperation()");
    accept(Token.OPERATOR);
    System.out.println("Exiting parseOperation()");
  }
}
