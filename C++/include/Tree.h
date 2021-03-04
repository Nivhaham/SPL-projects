#ifndef TREE_H_
#define TREE_H_

//TODO : Need to check if need here include Session or class Session
#include <vector>
#include "../include/Tree.h"
//#include "../include/Session.h"
using namespace std;

class Session;

class Tree {
public:

    //constructor
    Tree(int rootLabel);

    //destructor
    virtual ~Tree();

    // copy constructor
    Tree(const Tree &tree);

    //copy operator=
    Tree &operator=(const Tree &tree);

    //move constructor
    Tree(Tree &&tree);

    //move assignment operator
    Tree &operator=(const Tree &&tree);

    virtual Tree *Clone() const = 0;

    void addChild(const Tree &child);

    static Tree *createTree(const Session &session, int rootLabel);


    // static Tree *createTree(const Session &session, int rootLabel,int depth)

    virtual int traceTree() = 0; //abstract class + make traceTree at childs
    //Getters and setters
    int get_node() const;

    int get_depth() const;

    const vector<Tree *> &get_childrens() const;

    void set_node(int n1);

    void set_depth(int d);



protected:
    int node;
    vector<Tree *> children;

private:
    int depth;


};


class CycleTree : public Tree {
public:
    CycleTree(int rootLabel, int currCycle);
    virtual int traceTree();
    Tree *Clone() const;

private:
    int currCycle;
};

class MaxRankTree : public Tree {
public:
    MaxRankTree(int rootLabel);

    virtual int traceTree();

    //copy assignment operator
    Tree *Clone() const;

    const Tree &CompareRank(Tree &MaxRank, const Tree &Curr);

    const Tree &FindMaxRank(Tree &MaxRank, Tree &Curr);


};

class RootTree : public Tree {
public:
    RootTree(int rootLabel);

    virtual int traceTree();

    Tree *Clone() const;

};


#endif
