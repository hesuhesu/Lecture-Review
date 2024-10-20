package ProgrammingLanguage;

import java.io.*;

class Calc {
    int token; int value; int ch;
    private PushbackInputStream input;
    final int NUMBER=256;

    Calc(PushbackInputStream is) {
        input = is;
    }

    int getToken( )  { /* tokens are characters */
        while(true) {
            try  {
	            ch = input.read();
                if (ch == ' ' || ch == '\t' || ch == '\r') ;
                
                else 
                    if (Character.isDigit(ch)) {
                        value = number( );
	               input.unread(ch);
		     return NUMBER;
	          }
	          else return ch;
	  } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private int number( )  {
    /* number -> digit { digit } */
        int result = ch - '0';
        try  {
            ch = input.read();
            while (Character.isDigit(ch)) {
                result = 10 * result + ch -'0';
                ch = input.read(); 
            }
            
        } catch (IOException e) {
            System.err.println(e);
        }
        return result;
    }

    void error( ) {
        System.out.printf("syntax error\n", ch);
    }

    void match(int c) { 
        if (token == c) 
	    token = getToken();
        else error();
    }

    void command( ) {
    /* command -> expr '\n' */
        int result = aexp(); //Need to be changed
        if (token == '\n') /* end the parse and print the result */
	    System.out.println(result);
        else error();
    }

    int expr() {
        /* expr -> bexp { '&&' bexp } */
        int result = bexp();
        while (token == '&') {
            match('&');
            match('&');
            int right = bexp();
            result = (result != 0 && right != 0) ? 1 : 0;
        }
        return result;
    }

    int bexp( ) {
        /* bexp -> aexp { '==' aexp } */
        int result = aexp();
        while (token == '=') {
            match('=');
            match('=');
            int right = aexp();
            result = (result == right) ? 1 : 0;
        }
        return result;
    }
    
    int aexp( ) {
    /* expr -> term { '+' term } */
        int result = term();
        while ((token == '+') | (token == '-')) {
            if ((token == '+')) {
            	match('+');
                result += term();
            }
            else if ((token == '-')) {
            	match('-');
                result -= term();
            }
        }
        return result;
    }
    
    
    int term( ) {
    /* term -> factor { '*' factor } */
       int result = factor();
       while ((token == '*') | (token == '/')) {
           if (token == '*') {
        	   match('*');
               result *= factor();
           }
           else if (token == '/') {
        	   match('/');
               result /= factor();
           }
       }
       return result;
    }

    int factor() {
    /* factor -> '(' expr ')' | number */
        int result = 0;
        if (token == '(') {
            match('(');
            result = expr();
            match(')');
        }
        else if (token == NUMBER) {
            result = value;
	        match(NUMBER); //token = getToken();
        }
        return result;
    }

    void parse( ) {
        token = getToken(); // get the first token
        command();          // call the parsing command
    }

    public static void main(String args[]) { 
        Calc calc = new Calc(new PushbackInputStream(System.in));
        while(true) {
            System.out.print(">> ");
            calc.parse();
        }
    }
}
