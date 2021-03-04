//
// Created by niv_d on 08-Jan-21.
//

#ifndef BOOST_ECHO_CLIENT_READWRITE_H
#define BOOST_ECHO_CLIENT_READWRITE_H
#include "connectionHandler.h"
#include <thread>
#include <mutex>
#include <condition_variable>

using namespace std;

class ReadWrite {
private:
    ConnectionHandler *connectionHandler;
    bool toTerminate;
    mutex& mut;
    condition_variable& condition;
public:
    ReadWrite(ConnectionHandler *handler, mutex& mut,condition_variable& condition);
    bool read();
    bool write();
};








#endif //BOOST_ECHO_CLIENT_READWRITE_H
