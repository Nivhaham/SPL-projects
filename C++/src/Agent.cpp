#include "../include/Agent.h"




Agent::Agent() {

}

Agent::~Agent() {

}


ContactTracer::ContactTracer() {
}


Agent *ContactTracer::Clone() const {
    return new ContactTracer(*this);
}

void ContactTracer::act(Session &session) {
    if(!session.getInfected_Queue().empty()) {
        int dequeNode = session.dequeueInfected();
        Tree *root = session.get_Graph().Bfs(session, dequeNode);
        int IsolateIt = root->traceTree();
        Graph newGraph(session.get_Graph());
        newGraph.IsolateVertice(IsolateIt);
        session.get_Graph().IsolateVertice(IsolateIt);
        delete root;
        session.setGraph(newGraph);
    }
}

//TOdo change return type to Agent maybe
Agent *Virus::Clone() const {
    return new Virus(*this);
}


Virus::Virus(int nodeInd) :nodeInd(nodeInd){
}


void Virus::act(Session &session) {
    if (session.get_Graph().GetNodeStatus(nodeInd) == exposed) {
        session.get_Graph().SetNodeStatus(nodeInd, infected);
        session.enqueueInfected(nodeInd);
    }
    int othernode = session.get_Graph().GetNodeToInfect(nodeInd);

    if (othernode != -1) {
        session.get_Graph().SetNodeStatus(othernode, exposed);
        //TODO Hey
        session.addAgent(Virus(othernode));
        //session.get_Agents().push_back(new Virus(othernode));
    }

}
