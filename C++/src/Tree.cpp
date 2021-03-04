#include "../include/Tree.h"
#include "../include/Session.h"

//class Session;

//constructor
Tree::Tree(int rootLabel) : node(rootLabel),children(vector<Tree *>()) ,depth(0) {
    //vector<Tree*> children;
}

Tree::~Tree() {
    /*for (auto it = begin(children); it!=end(children);++it)
    {
        delete *it;
    }*/

    for (auto & i : children) {
        delete i;
    }
    children.clear();
    //children.clear();
}

// copy constructor
Tree::Tree(const Tree &tree) : node(tree.node),children(vector<Tree *>()), depth(tree.depth) {

    for (unsigned int i = 0; i < children.size(); ++i) {
        children.push_back((tree.children[i])->Clone());
    }

}


//copy operator=
Tree &Tree::operator=(const Tree &tree) {
    if(this!=&tree) {

        this->node = tree.node;
        this->depth = tree.depth;
        for (auto & i : children) {
            delete i;
        }
        children.clear();

      //  children.clear();
        for (unsigned int i = 0; i < tree.children.size(); ++i) {
            addChild(*(tree.children[i])->Clone());
           // this->children.push_back(tree.children[i]->Clone());
        }
    }
    return *this;
}

//move constructor
Tree::Tree(Tree &&tree) : node(tree.node),children(vector<Tree *>()), depth(tree.depth) {
    for (unsigned int i = 0; i < children.size(); i++) {
        delete children[i];
    }
    children.clear();
    children=tree.children;
    tree.children.clear();
}

//move assignment operator
Tree &Tree::operator=(const Tree &&tree) {
    if (this != &tree) {
        this->node = tree.node;
        this->depth = tree.depth;
        for (unsigned int i = 0; i < children.size(); ++i) {
            delete children[i];

        }
        children.clear();
       children=tree.children;


    }
    return *this;
}

void Tree::addChild(const Tree &child) {
    children.push_back(child.Clone());
}

/*
Tree* Tree::createTree(const Session &session, int rootLabel)
{
    session.
    Tree* root= new
    return nullptr;
}*/



int Tree::get_node() const {
    return this->node;
}

int Tree::get_depth() const {
    return this->depth;
}

const vector<Tree *> &Tree::get_childrens() const {
    return children;
}

void Tree::set_node(int n1) {
    this->node = n1;
}

void Tree::set_depth(int d) {
    this->depth = d;
}

Tree *Tree::createTree(const Session &session, int rootLabel) {
    Tree *x;
    if (session.getTreeType() == Cycle) {
        x = new CycleTree(rootLabel, session.get_currCycle());
    }
    else if (session.getTreeType() == MaxRank) {
        x = new MaxRankTree(rootLabel);
    }
    else
        x = new RootTree(rootLabel);

return x;
}


//CycleTree
//CycleTree constructor
CycleTree::CycleTree(int rootLabel, int currCycle) : Tree(rootLabel),currCycle(currCycle) {

}

Tree *CycleTree::Clone() const {
    CycleTree *treeclone= new CycleTree(get_node(),this->currCycle);
    if(treeclone->children.size()==0)
        return  treeclone;
    for (unsigned int i = 0; i<get_childrens().size(); i++){
        treeclone->addChild(*(this->get_childrens()[i]->Clone()));
    }
    return treeclone;
    //return new CycleTree(*this);
}


int CycleTree::traceTree() {
    Tree *tr= this;

    int c = currCycle;
    if (c == 0) {
        return this->get_node();
    } else
        while (c > 0 &&  !tr->get_childrens().empty()) {
            tr=tr->get_childrens()[0];
            c--;
        }

    return tr->get_node();
}


//MaxRankTree
//MaxRankTree Constructor
MaxRankTree::MaxRankTree(int rootLabel) :
        Tree(rootLabel) {

}


int MaxRankTree::traceTree() {
    return (FindMaxRank(*this, *this).get_node());

}


const Tree &MaxRankTree::CompareRank(Tree &MaxRank, const Tree &Curr) {
    if (MaxRank.get_childrens().size() < Curr.get_childrens().size()) {
        return Curr;
    } else {
        if (MaxRank.get_childrens().size() == Curr.get_childrens().size()) {
            if (MaxRank.get_depth() > Curr.get_depth()) {
                return Curr;
            } else if (MaxRank.get_depth() == Curr.get_depth()) {
                if (MaxRank.get_node() > Curr.get_node()) {
                    return Curr;
                }

            }
        }
    }
    return MaxRank;

}

const Tree &MaxRankTree::FindMaxRank(Tree &MaxRank, Tree &Curr) {
       //Tree &  x=Curr;
    if (Curr.get_childrens().size() != 0) {
        for (unsigned int j = 0; j < Curr.get_childrens().size(); j++) {
            const Tree &i = FindMaxRank(MaxRank, *Curr.get_childrens()[j]);
            //x = CompareRank(MaxRank, i);
            MaxRank = CompareRank(MaxRank, i);
        }
    }
    //return CompareRank(x, Curr);
    MaxRank = CompareRank(MaxRank, Curr);
    return MaxRank;

}

Tree *MaxRankTree::Clone() const {
    MaxRankTree *treeclone= new MaxRankTree(get_node());
    if(treeclone->children.size()==0)
        return  treeclone;
    for (unsigned int i = 0; i<get_childrens().size(); i++){
        treeclone->addChild(*(this->get_childrens()[i]->Clone()));
    }
    return treeclone;
    //return new MaxRankTree(*this);
}




//RootTree
//RootTree Constructor
RootTree::RootTree(int rootLabel) :
        Tree(rootLabel) {

}

int RootTree::traceTree() {

    return this->get_node();

}

Tree *RootTree::Clone() const {
    return new RootTree(*this);
}













