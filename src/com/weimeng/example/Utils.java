package com.weimeng.example;

import com.weimeng.example.exception.ParserException;
import com.weimeng.example.node.Token;
import com.weimeng.example.node.TreeNode;

import java.io.*;
import java.util.LinkedList;

/**
 * Created by weimeng on 2016/5/29.
 */
public class Utils
{

    public static LinkedList<Token> getTokenList(String filestr) throws IOException, ParserException
    {
        FileReader fr;
        fr = new FileReader(filestr);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filestr), "UTF-8"));
        LinkedList<Token> tokenList = Lexer.lexerAnalyse(br);
        br.close();
        fr.close();
        return tokenList;
    }

    public static LinkedList<TreeNode> getNodeList(LinkedList<Token> tokenList) throws ParserException
    {
        LinkedList<TreeNode> nodeList = Parser.syntacticAnalyse(tokenList);
        return nodeList;
    }
}
