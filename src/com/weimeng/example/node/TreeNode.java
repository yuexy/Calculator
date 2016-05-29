package com.weimeng.example.node;

/**
 * Created by weimeng on 2016/5/29.
 */
public class TreeNode
{
    public static final int NULL = 0;

    public static final int WRITE_STMT = 1;

    public static final int DECLARE_STMT = 2;

    public static final int ASSIGN_STMT = 3;

    public static final int EXP = 4;

    public static final int TERM = 5;

    public static final int FACTOR = 6;

    public static final int NUM = 7;

    public static final int VAR = 8;

    public static final int ADD_OP = 9;

    public static final int MUL_OP = 10;

    private int type;
    private TreeNode mLeft;
    private TreeNode mMiddle;
    private TreeNode mRight;

    private int dataType;

    private String value;

    public TreeNode(int t)
    {
        type = t;
    }

    public int getType()
    {
        return type;
    }

    public TreeNode setType(int t)
    {
        type = t;
        return this;
    }

    public TreeNode getLeft()
    {
        return mLeft;
    }

    public TreeNode setLeft(TreeNode t)
    {
        mLeft = t;
        return this;
    }

    public TreeNode getMiddle()
    {
        return mMiddle;
    }

    public TreeNode setMiddle(TreeNode t)
    {
        mMiddle = t;
        return this;
    }

    public TreeNode getRight()
    {
        return mRight;
    }

    public TreeNode setRight(TreeNode t)
    {
        mRight = t;
        return this;
    }

    public int getDataType()
    {
        return dataType;
    }

    public void setDataType(int t)
    {
        dataType = t;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String v)
    {
        value = v;
    }
}
