#include "../include/connectionHandler.h"
#include "../include/ReadWrite.h"
#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>

using namespace  std;

int main (int argc, char *argv[]) {
    /*
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    */

   // std::string host = "127.0.0.1";
    std::string host = argv[1];
  //  short port = 7777;
    short port = atoi(argv[2]);
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    std::mutex mut;
    std::condition_variable condition;
    ReadWrite ReaderWriter(&connectionHandler,mut,condition);
    thread writerthread(&ReadWrite::write,&ReaderWriter);
    ReaderWriter.read();
    writerthread.join();

return 0;
}
