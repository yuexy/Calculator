package com.weimeng.example;

import com.weimeng.example.exception.ParserException;
import com.weimeng.example.node.SymbolTable;
import com.weimeng.example.node.Token;
import com.weimeng.example.node.TreeNode;
import com.weimeng.example.node.Value;

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
        Interpreter interpreter = new Interpreter();
        try
        {
            LinkedList<Token> tokenLinkedList = Utils.getTokenList(args[0]);

            LinkedList<TreeNode> treeNodes = Utils.getNodeList(tokenLinkedList);


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ParserException e)
        {
            e.printStackTrace();
        }
    }

    private Value interpretFourCode(TreeNode t)
    {
        int type = t.getType();

        if (type == TreeNode.DECLARE_STMT)
        {
            t.getLeft().getDataType();
            t.getLeft().getValue();
        }
        else if (type == TreeNode.WRITE_STMT)
        {

        }
        else if (type == TreeNode.ASSIGN_STMT)
        {

        }
        else if (type == TreeNode.EXP)
        {

        }
        else if (type == TreeNode.TERM)
        {

        }
        else if (type == TreeNode.FACTOR)
        {

        }
        else if (type == TreeNode.NUM)
        {

        }

        return null;
    }
}
