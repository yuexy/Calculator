package com.weimeng.example;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TopLevelAttribute;
import com.weimeng.example.exception.InterpretException;
import com.weimeng.example.exception.ParserException;
import com.weimeng.example.node.*;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by weimeng on 2016/5/29.
 */
public class Interpreter
{
    private static int mLevel;
    private static int pc;
    private static SymbolTable symbolTable;

    public static void main(String[] args)
    {
        symbolTable = SymbolTable.getSymbolTable();
        symbolTable.newTable();

        Interpreter interpreter = new Interpreter();
        try
        {
            LinkedList<Token> tokenLinkedList = Utils.getTokenList(args[0]);

            LinkedList<TreeNode> treeNodes = Utils.getNodeList(tokenLinkedList);


            for (TreeNode t : treeNodes)
            {
                interpreter.interpret(t);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParserException e)
        {
            e.printStackTrace();
        }
        catch (InterpretException e)
        {
            e.printStackTrace();
        }
    }

    private Value interpret(TreeNode t) throws InterpretException
    {
        int type = t.getType();

        if (type == TreeNode.DECLARE_STMT)
        {
            Symbol s;
            if (t.getLeft().getDataType() == Token.FLOAT)
            {
                s = new Symbol(t.getLeft().getValue(), Symbol.SINGLE_FLOAT, 0);
            }
            else
            {
                s = new Symbol(t.getLeft().getValue(), Symbol.SINGLE_INT, 0);
            }

            if (t.getMiddle() != null)
            {
                s.setValue(interpret(t.getMiddle()));
            }

            symbolTable.register(s);
        }
        else if (type == TreeNode.WRITE_STMT)
        {
            System.out.println(interpret(t.getLeft()).toString());
        }
        else if (type == TreeNode.ASSIGN_STMT)
        {
            symbolTable.setSymbolValue(t.getLeft().getValue(), interpret(t.getMiddle()));
        }
        else if (type == TreeNode.EXP)
        {
            if (t.getMiddle().getDataType() == Token.PLUS)
            {
                return interpret(t.getLeft()).PLUS(interpret(t.getRight()));
            }
            else
            {
                return interpret(t.getLeft()).MINUS(interpret(t.getRight()));
            }
        }
        else if (type == TreeNode.TERM)
        {
            if (t.getMiddle().getDataType() == Token.MUL)
            {
                return interpret(t.getLeft()).MUL(interpret(t.getRight()));
            }
            else
            {
                return interpret(t.getLeft()).DIV(interpret(t.getRight()));
            }
        }
        else if (type == TreeNode.FACTOR)
        {
            return interpret(t.getLeft());
        }
        else if (type == TreeNode.NUM)
        {
            if (t.getDataType() == Token.INT_REAL)
            {
                Value v = new Value(Symbol.SINGLE_INT);
                v.setInt(Integer.parseInt(t.getValue()));
                return v;
            }
            else if (t.getDataType() == Token.FLOAT_REAL)
            {
                Value v = new Value(Symbol.SINGLE_FLOAT);
                v.setFloat(Double.parseDouble(t.getValue()));
                return v;
            }
        }
        else if (type == TreeNode.VAR)
        {
            return symbolTable.getSymbolValue(t.getValue());
        }

        return null;
    }
}
