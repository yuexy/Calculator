package com.weimeng.example;

import com.weimeng.example.node.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by weimeng on 2016/5/29.
 */
public class Lexer
{
    private static BufferedReader mBufferedReader;
    private static int currentInt;
    private static char currentChar;
    private static int currentLine;

    public static LinkedList<Token> lexerAnalyse(BufferedReader br) throws IOException
    {
        currentLine = 1;
        mBufferedReader = br;
        LinkedList<Token> tokenList = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        readChar();

        while (currentInt != -1)
        {
            // 消耗空白字符
            if (currentChar == '\n' || currentChar == '\r' || currentChar == '\t' || currentChar == '\f' || currentChar == ' ')
            {
                readChar();
                continue;
            }

            // 简单符号
            switch (currentChar)
            {
            case ';':
                tokenList.add(new Token(Token.SEMI, currentLine));
                readChar();
                continue;

            case '+':
                tokenList.add(new Token(Token.PLUS, currentLine));
                readChar();
                continue;

            case '-':
                tokenList.add(new Token(Token.MINUS, currentLine));
                readChar();
                continue;

            case '*':
                tokenList.add(new Token(Token.MUL, currentLine));
                readChar();
                continue;

            case '/':
                tokenList.add(new Token(Token.DIV, currentLine));
                readChar();
                continue;

            case '(':
                tokenList.add(new Token(Token.LPAR, currentLine));
                readChar();
                continue;

            case ')':
                tokenList.add(new Token(Token.RPAR, currentLine));
                readChar();
                continue;

            case '=':
                tokenList.add(new Token(Token.ASSIGN, currentLine));
                readChar();
                continue;

            case '.':
                tokenList.add(new Token(Token.DOT, currentLine));
                readChar();
                continue;
            }

            // 数字
            if (currentChar >= '0' && currentChar <= '9')
            {
                boolean isFloat = false;
                while ((currentChar >= '0' && currentChar <= '9') || currentChar == '.')
                {
                    if (currentChar == '.')
                    {
                        isFloat = true;
                    }
                    sb.append(currentChar);
                    readChar();
                }

                if (isFloat)
                {
                    tokenList.add(new Token(Token.FLOAT_REAL, sb.toString(), currentLine));
                }
                else
                {
                    tokenList.add(new Token(Token.INT_REAL, sb.toString(), currentLine));
                }

                sb.delete(0, sb.length());
                continue;
            }

            // 字符组成的标识符,包括保留字和ID
            if ((currentChar >= 'a' && currentChar <= 'z') || currentChar == '_')
            {
                while ((currentChar >= 'a' && currentChar <= 'z') || (currentChar >= 'A' && currentChar <= 'Z') || currentChar == '_' || (currentChar >= '0' && currentChar <= '9'))
                {
                    sb.append(currentChar);
                    readChar();
                }

                Token token = new Token(currentLine);
                String sbString = sb.toString();

                if (sbString.equals("int"))
                {
                    token.setType(Token.INT);
                }
                else if (sbString.equals("float"))
                {
                    token.setType(Token.FLOAT);
                }
                else if (sbString.equals("write"))
                {
                    token.setType(Token.WRITE);
                }
                else
                {
                    token.setType(Token.IDENT);
                    token.setValue(sbString);
                }
                sb.delete(0, sb.length());
                tokenList.add(token);
                continue;
            }

            readChar();
        }

        return tokenList;
    }

    public static void readChar() throws IOException
    {
        currentChar = (char) (currentInt = mBufferedReader.read());
        if (currentChar == '\n')
        {
            currentLine++;
        }
    }
}
