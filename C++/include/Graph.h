#ifndef GRAPH_H_
#define GRAPH_H_

#include <vector>
#include <queue>
#include "../include/Tree.h"
//#include "Session.h"

class Tree;
class Session;
using namespace std;
enum HealthStatus {
    healthy,
    exposed,
    infected
};

class Graph {
public:
    //Constructor
    Graph(vector<vector<int>> matrix);

    Graph();


    // getter and setters
    void SetEdges(const vector<vector<int>> &matrix);

    HealthStatus GetNodeStatus(int node);

    void SetNodeStatus(int node,HealthStatus sit);

    vector<vector<int>>  getEdges() const ;


    void infectNode(int nodeInd);

    //initiallizing all HealthStatuses to healthy
   // void initialize();

    void ExposeNode(int nodeInd);

    bool isInfected(int nodeInd);

    int IsConnected(int i, int j);

    Tree * Bfs(Session &session,int rootlabel);
    int GetNodeToInfect(int node);

    void IsolateVertice(int v);

    bool IsTerminated();

    vector<int> AllInfectedNodes();
private:
    vector<vector<int>> edges;
    vector<HealthStatus> Situation;

};

#endif
