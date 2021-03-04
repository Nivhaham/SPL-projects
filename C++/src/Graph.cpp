
#include "../include/Graph.h"
#include "../include/Tree.h"


// Constructor
Graph::Graph(vector<vector<int>> matrix) : edges(matrix),Situation(vector<HealthStatus>(edges.size(), healthy)) {

}

Graph::Graph() : edges(),Situation(){
    //cout << "rly, empty constructor>?" << endl;
}

int Graph::IsConnected(int i, int j) {
    return edges[i][j];

}

void Graph::SetEdges(const vector<vector<int>> &matrix) {
    edges = matrix;
}
int Graph::GetNodeToInfect(int node) {
    for (unsigned int i = 0; i <edges[node].size() ; ++i) {

        if(edges[node][i]==1 && Situation[i]==healthy)
            return i;
    }
    return -1;

}

HealthStatus Graph::GetNodeStatus(int node) {
    return Situation[node];
}
void Graph::SetNodeStatus(int node,HealthStatus sit) {
    Situation[node]=sit;
}



vector<vector<int>> Graph::getEdges() const {
    return edges;
}

void Graph::infectNode(int nodeInd) {
    Situation[nodeInd] = infected;

}

/*
void Graph::initialize() {
    for (HealthStatus x:Situation) {
        x = healthy;
    }
}
 */

void Graph::ExposeNode(int nodeInd) {
    Situation[nodeInd] = exposed;
}

bool Graph::isInfected(int nodeInd) {
    if (Situation[nodeInd] == infected) {
        return true;
    }
    return false;

}

Tree *Graph::Bfs(Session &session, int rootlabel) {
    vector<bool> is_Visited(edges.size(), false);
    is_Visited[rootlabel] = true;
    queue<Tree *> bfsque;
    Tree *root = Tree::createTree(session, rootlabel);
    bfsque.push(root);

    while (!bfsque.empty()) {
        Tree *Curr = bfsque.front();
        int CurrV = Curr->get_node();
        bfsque.pop();
        for (unsigned int i = 0; i <= edges.size(); i++) {
            if (edges[CurrV][i]==1 && !is_Visited[i])
            {
                is_Visited[i]=true;
                Tree * child=Tree::createTree(session, i);
                child->set_depth(Curr->get_depth()+1);
                Curr->addChild(*child);
                bfsque.push(Curr->get_childrens().back());
                delete child;

            }

        }

    }

    return root;

}

void Graph::IsolateVertice(int v)
{

    for (unsigned int i = 0; i < edges.size(); ++i)
    {
        edges[v][i]=0;
        edges[i][v]=0;
    }

}

bool Graph::IsTerminated() {

    for(unsigned int i=0;i<edges.size();i++)
    {
        if(Situation[i]!=healthy)
        {
            if(Situation[i]==exposed)
            {
                return false;
            }
            for( unsigned int j=0;j<edges[i].size();j++)
            {
                if(edges[i][j]==1 && Situation[j]!=infected)
                {
                    return false;
                }
            }

        }
    }
    return true;
}

vector<int> Graph::AllInfectedNodes() {
    vector<int> x;
    for (unsigned int i = 0; i < edges.size(); ++i) {
        if (isInfected(i))
            x.push_back(i);
    }
    return x;
    }





