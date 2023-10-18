/*
1. You do not need a separate token (number) for each operator. All of them should be translated to one token (number) which is OPERATOR. See below
2. In the switch the code for identifier and literal is complete. Do not change it

3. Symbol e means epsilon.

 BNF grammar of Mini Language

 Program" --> "("Sequence State")".
 Sequence --> "("Statements")".
 Statements --> Statements  Stmt | e
 Stmt --> "(" {NullStatement | Assignment | Conditional | Loop | Block}")".
 State -->  "("Pairs")".
 Pairs -->  Pairs Pair | e.
 Pair --> "("Identifier Literal")".
 NullStatement --> "skip".
 Assignment --> "assign" Identifier Expression.
 Conditional --> "conditional" Expression Stmt Stmt.
 Loop --> "loop" Expression Stmt.
 Block --> "block" Statements.
 Expression --> Identifier | Literal | "("Operation Expression Expression")".
 Operation --> "+" |"-" | "*" | "/" | "<" | "<=" | ">" | ">=" | "=" | "!=" | "or" | "and".

 Note: Treat Identifier and Literal as terminal symbols. Every symbol inside " and " is a terminal symbol. The rest are non terminals.

Input file: test.txt
Output:
 Line: 1, spelling = [(], kind = 9
 Line: 1, spelling = [)], kind = 10
 Line: 1, spelling = [sum], kind = 0
 Line: 1, spelling = [a], kind = 0
 Line: 1, spelling = [2], kind = 1
 Line: 1, spelling = [xyz], kind = 0
 Line: 2, spelling = [skip], kind = 6
 Line: 2, spelling = [assign], kind = 2
 Line: 2, spelling = [conditional], kind = 3
 Line: 2, spelling = [loop], kind = 4
 Line: 2, spelling = [block], kind = 5
 Line: 3, spelling = [1234], kind = 1
 Line: 4, spelling = [+], kind = 11
 Line: 4, spelling = [-], kind = 11
 Line: 4, spelling = [*], kind = 11
 Line: 4, spelling = [/], kind = 11
 Line: 4, spelling = [<], kind = 11
 Line: 4, spelling = [<=], kind = 11
 Line: 4, spelling = [>], kind = 11
 Line: 4, spelling = [>=], kind = 11
 Line: 4, spelling = [=], kind = 11
 Line: 4, spelling = [!=], kind = 11
 Line: 4, spelling = [or], kind = 8
 Line: 4, spelling = [and], kind = 7
 Line: 5, spelling = [-], kind = 11
 Line: 5, spelling = [1234], kind = 1
 Line 6: wrong token !

Note: After you get an error message for the symbol = remove this symbol and
run the program. Repeat this until the last wrong token which is: ?

You should get the following error messges:
 Line 6: wrong token !
 Line 6: wrong token ?
*/

import java.io.*;
import java.util.concurrent.LinkedTransferQueue;

public class Scanner{
  private char currentChar;
  private byte currentKind;
  private StringBuffer currentSpelling;
  private BufferedReader inFile;
  private static int line = 1;
  private static int idx = 1;

  public Scanner(BufferedReader inFile){
    this.inFile = inFile;
    try{
      int i = this.inFile.read();
      if(i == -1) //end of file
        currentChar = '\u0000';
      else
        currentChar = (char)i;
    }
    catch(IOException e){
        System.out.println(e);
    }
  }

  private void takeIt(){
    currentSpelling.append(currentChar);
    try{
      int i = inFile.read();
      if(i == -1) //end of file
        currentChar = '\u0000';
      else
        currentChar = (char)i;
      idx+=1;
    }
    catch(IOException e){
        System.out.println(e);
    }
  }

  private void discard(){
    try{
      int i = inFile.read();
      if(i == -1) //end of file
        currentChar = '\u0000';
      else
        currentChar = (char)i;
    }
    catch(IOException e){
        System.out.println(e);
    }
  }

  private void print(int line, String c, byte kind){
    System.out.println("Line "+ line + " sentence \"" + c + "\" kind " + kind);
  }

  private byte scanToken(){
    byte toCurrentKind = 0;
    switch(currentChar){
      case '(':
        toCurrentKind = Token.LPAREN;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case ')':
        toCurrentKind = Token.RPAREN;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case '\u0000':
        toCurrentKind = Token.EOT;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case '+':
        toCurrentKind = Token.OPERATOR;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case '*':
        toCurrentKind = Token.OPERATOR;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case '/':
        toCurrentKind = Token.OPERATOR;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case '-':
        toCurrentKind = Token.OPERATOR;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case '>':
        toCurrentKind = Token.OPERATOR;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case'<':
        toCurrentKind = Token.OPERATOR;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case'!':
        toCurrentKind = Token.OPERATOR;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      case'=':
        toCurrentKind = Token.OPERATOR;
        print(line, String.valueOf(currentChar), toCurrentKind);
        takeIt();
        break;

      default:
        if(isLetter(currentChar)){
          String s1 = "";
          while(isLetter(currentChar)){
            s1 += currentChar;
            takeIt();
          }
          switch (s1) {
            case "Loop":
              toCurrentKind = Token.LOOP;
              print(line, s1, toCurrentKind);
              break;
            case "Skip":
              toCurrentKind = Token.SKIP;
              print(line, s1, toCurrentKind);
              break;
            case "assign":
              toCurrentKind = Token.ASSIGN;
              print(line, s1, toCurrentKind);
              break;
            case "block":
              toCurrentKind = Token.BLOCK;
              print(line, s1, toCurrentKind);
              break;
            case "conditional":
              toCurrentKind = Token.CONDITIONAL;
              print(line, s1, toCurrentKind);
              break;
            case "and":
              toCurrentKind = Token.AND;
              print(line, s1, toCurrentKind);
              break;
            case "or":
              toCurrentKind = Token.OR;
              print(line, s1, toCurrentKind);
              break;
            default:
              toCurrentKind = Token.IDENTIFIER;
              print(line, s1, toCurrentKind);
          }
        }
        else if(isDigit(currentChar)){
          String literal = "";
          while(isDigit(currentChar)){
            literal += currentChar;
            takeIt();
          }
          print(line, literal, toCurrentKind);
          return Token.LITERAL;
        }
    }
    return toCurrentKind;                 //After you replace this switch with your, remove this line.
  }

  private void scanSeparator(){
    switch(currentChar){
      case ' ': case '\n': case '\r': case '\t':
        if(currentChar == '\n')
          //idx = 1;
          line++;
        discard();
    }
  }

  public Token scan(){
    currentSpelling = new StringBuffer("");
    while(currentChar == ' ' || currentChar == '\n' || currentChar == '\r')
      scanSeparator();
    currentKind = scanToken();
    return new Token(currentKind, currentSpelling.toString(), line, idx);
  }

  private boolean isGraphic(char c){
    return c == '\t' ||(' ' <= c && c <= '~');
  }

  private boolean isDigit(char c){
    return '0' <= c && c <= '9';
  }

  private boolean isLetter(char c){
    return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
  }
}
