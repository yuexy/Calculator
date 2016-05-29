package com.weimeng.example;

import com.weimeng.example.exception.ParserException;
import com.weimeng.example.node.Token;
import com.weimeng.example.node.TreeNode;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by weimeng on 2016/5/29.
 */
public class Parser
{
    private static LinkedList<TreeNode> treeNodeList;
    private static Token currentToken = null;
    private static ListIterator<Token> iterator = null;

    public static LinkedList<TreeNode> syntacticAnalyse(LinkedList<Token> tokenList) throws ParserException
    {
        treeNodeList = new LinkedList<>();
        iterator = tokenList.listIterator();
        while (iterator.hasNext())
        {
            treeNodeList.add(parseStmt());
        }
        return treeNodeList;
    }

    /**
     * 广义上的语句处理
     *
     * @throws ParserException
     */
    private static TreeNode parseStmt() throws ParserException
    {
        switch (getNextTokenType())
        {
        case Token.WRITE:
            return parseWriteStmt();
        case Token.INT: // same as REAL
        case Token.FLOAT:
            return parseDeclareStmt();
        case Token.IDENT:
            return parseAssignStmt();
        default:
            throw new ParserException("line " + getNextTokenLineNo() + " : expected token");
        }
    }

    /**
     * write语句
     *
     * @throws ParserException
     */
    private static TreeNode parseWriteStmt() throws ParserException
    {
        TreeNode node = new TreeNode(TreeNode.WRITE_STMT);
        consumeNextToken(Token.WRITE);
        node.setLeft(parseExp());
        consumeNextToken(Token.SEMI);
        return node;
    }

    /**
     * declare语句
     *
     * @throws ParserException
     */
    private static TreeNode parseDeclareStmt() throws ParserException
    {
        TreeNode node = new TreeNode(TreeNode.DECLARE_STMT);
        TreeNode varNode = new TreeNode(TreeNode.VAR);
        if (checkNextTokenType(Token.INT, Token.FLOAT))
        {
            currentToken = iterator.next();
            int type = currentToken.getType();
            if (type == Token.INT)
            {
                varNode.setDataType(Token.INT);
            }
            else
            {
                varNode.setDataType(Token.FLOAT);
            }
        }
        else
        {
            throw new ParserException("line " + getNextTokenLineNo() + " : next token should be variable type");
        }

        if (checkNextTokenType(Token.IDENT))
        {
            currentToken = iterator.next();
            varNode.setValue(currentToken.getValue());
        }
        else
        {
            throw new ParserException("line " + getNextTokenLineNo() + " : next token should be ID");
        }

        if (getNextTokenType() == Token.ASSIGN)
        {
            consumeNextToken(Token.ASSIGN);
            node.setMiddle(parseExp());
        }

        consumeNextToken(Token.SEMI);
        node.setLeft(varNode);
        return node;
    }

    /**
     * assign语句
     *
     * @throws ParserException
     */
    private static TreeNode parseAssignStmt() throws ParserException
    {
        TreeNode node = new TreeNode(TreeNode.ASSIGN_STMT);
        node.setLeft(variableName());
        consumeNextToken(Token.ASSIGN);
        node.setMiddle(parseExp());
        consumeNextToken(Token.SEMI);
        return node;
    }

    /**
     * 变量名,可能是单个的变量,也可能是数组的一个元素
     *
     * @throws ParserException
     */
    private static TreeNode variableName() throws ParserException
    {
        TreeNode node = new TreeNode(TreeNode.VAR);
        if (checkNextTokenType(Token.IDENT))
        {
            currentToken = iterator.next();
            node.setValue(currentToken.getValue());
        }
        else
        {
            throw new ParserException("line " + getNextTokenLineNo() + " : next token should be ID");
        }

        return node;
    }

    /**
     * 表达式
     *
     * @throws ParserException
     */
    private static TreeNode parseExp() throws ParserException
    {
        TreeNode node = new TreeNode(TreeNode.EXP);
        node.setDataType(Token.ADDTIVE_EXP);
        TreeNode leftNode = term();
        if (checkNextTokenType(Token.PLUS, Token.MINUS))
        {
            node.setLeft(leftNode);
            node.setMiddle(addtiveOp());
            node.setRight(parseExp());
        }
        else
        {
            return leftNode;
        }
        return node;
    }

    /**
     * Term
     */
    private static TreeNode term() throws ParserException
    {
        TreeNode node = new TreeNode(TreeNode.TERM);
        node.setDataType(Token.TERM_EXP);
        TreeNode leftNode = factor();
        if (checkNextTokenType(Token.MUL, Token.DIV))
        {
            node.setLeft(leftNode);
            node.setMiddle(multiplyOp());
            node.setRight(term());
        }
        else
        {
            return leftNode;
        }
        return node;
    }

    private static TreeNode factor() throws ParserException
    {
        if (iterator.hasNext())
        {
            TreeNode expNode = new TreeNode(TreeNode.FACTOR);
            switch (getNextTokenType())
            {
            case Token.INT_REAL:
            case Token.FLOAT:
                expNode.setLeft(litreal());
                break;
            case Token.LPAR:
                consumeNextToken(Token.LPAR);
                expNode = parseExp();
                consumeNextToken(Token.RPAR);
                break;
            case Token.MINUS:
                expNode.setDataType(Token.MINUS);
                currentToken = iterator.next();
                expNode.setLeft(term());
                break;
            case Token.PLUS:
                currentToken = iterator.next();
                expNode.setLeft(term());
                break;
            default:
                //返回的不是expNode
                return variableName();
            }
            return expNode;
        }
        throw new ParserException("line " + getNextTokenLineNo() + " : next token should be factor");
    }

    private static TreeNode litreal() throws ParserException
    {
        if (iterator.hasNext())
        {
            currentToken = iterator.next();
            int type = currentToken.getType();
            TreeNode node = new TreeNode(TreeNode.NUM);
            node.setDataType(type);
            node.setValue(currentToken.getValue());
            if (type == Token.INT_REAL || type == Token.FLOAT_REAL)
            {
                return node;
            }
            else
            {
                // continue execute until throw
            }
        }
        throw new ParserException("line " + getNextTokenLineNo() + " : next token should be litreal value");
    }

    /**
     * 加减运算符
     *
     * @throws ParserException
     */
    private static TreeNode addtiveOp() throws ParserException
    {
        if (iterator.hasNext())
        {
            currentToken = iterator.next();
            int type = currentToken.getType();
            if (type == Token.PLUS || type == Token.MINUS)
            {
                TreeNode node = new TreeNode(TreeNode.ADD_OP);
                node.setDataType(type);
                return node;
            }
        }
        throw new ParserException("line " + getNextTokenLineNo() + " : next token should be addtive operator");
    }

    /**
     * 乘除运算符
     *
     * @throws ParserException
     */
    private static TreeNode multiplyOp() throws ParserException
    {
        if (iterator.hasNext())
        {
            currentToken = iterator.next();
            int type = currentToken.getType();
            if (type == Token.MUL || type == Token.DIV)
            {
                TreeNode node = new TreeNode(TreeNode.MUL_OP);
                node.setDataType(type);
                return node;
            }
        }
        throw new ParserException("line " + getNextTokenLineNo() + " : next token should be multiple operator");
    }

    /**
     * 获取下一个token的type,如果没有下一个token,则返回{@link Token#EOF}
     *
     * @return
     */
    private static int getNextTokenType()
    {
        if (iterator.hasNext())
        {
            int type = iterator.next().getType();
            iterator.previous();
            return type;
        }
        return Token.EOF;
    }

    /**
     * 获取下一个token的lineNo,如果没有下一个token,则返回-1
     *
     * @return
     */
    private static int getNextTokenLineNo()
    {
        if (iterator.hasNext())
        {
            int lineNo = iterator.next().getLine();
            iterator.previous();
            return lineNo;
        }
        return -1;
    }

    /**
     * 消耗掉下一个token,要求必须是type类型,消耗之后currentToken值将停在最后消耗的token上
     *
     * @param type
     * @throws ParserException 消耗失败则抛出
     */
    private static void consumeNextToken(int type) throws ParserException
    {
        if (iterator.hasNext())
        {
            currentToken = iterator.next();
            if (currentToken.getType() == type)
            {
                return;
            }
        }
        throw new ParserException("line " + getNextTokenLineNo() + " : next token should be -> " + new Token(type, 0));
    }

    /**
     * 检查下一个token的类型是否和type中的每一个元素相同,调用此函数currentToken位置不会移动
     *
     * @param type
     * @return 相同为true, 不同为false
     */
    private static boolean checkNextTokenType(int... type)
    {
        if (iterator.hasNext())
        {
            int nextType = iterator.next()
                    .getType();
            iterator.previous();
            for (int each : type)
            {
                if (nextType == each)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
