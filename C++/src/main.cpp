#include <iostream>
#include "../include/Session.h"


//class Graph;

using namespace std;
using json = nlohmann::json;

int main(int argc, char** argv){

    if(argc != 2){
        cout << "usage cTrace <config_path>" << endl;
        return 0;
    }
   // "/home/spl211/CLionProjects/Assignment1/config1"
    Session sess(argv[1]);
    sess.simulate();


    //TODO: , check rule of 5,change bfs place ,how to compile and mkfile output.json
    return 0;

}
