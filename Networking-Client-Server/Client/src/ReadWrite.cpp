#include <stdlib.h>
#include "../include/ReadWrite.h"
#include <string>
#include <iostream>

ReadWrite::ReadWrite(ConnectionHandler *handler, mutex &mut,condition_variable& condition) : connectionHandler(handler), toTerminate(false),
                                                               mut(mut),condition(condition) {}

// read from socket
bool ReadWrite::read() {
    string answer;

    while (!this->toTerminate) {
        char loader[4];
        if(!connectionHandler->getBytes(loader,4))
        {
            cout << "Disconnected. Exiting... read \n" << std::endl;
            //break;
        }

        short message=connectionHandler->bytesToShort(loader);
        char op[2];
        op[0]=loader[2];
        op[1]=loader[3];
        short Responseopcode=connectionHandler->bytesToShort(op);

        if(message==12)
        {
            string restOfMessage;
            answer="ACK ";
            std::cout << answer << Responseopcode << std::endl;
            if (Responseopcode==4) {
                condition.notify_all();
                toTerminate = true;
            }

            if (Responseopcode == 6 | Responseopcode == 7 | Responseopcode == 8 | Responseopcode == 9 |
                    Responseopcode == 11) {


                if(!connectionHandler->getLine(restOfMessage))
                {
                    cout << "Disconnected. Exiting... read \n" << std::endl;
                   break;
                }

                cout<<restOfMessage<<std::endl;

            }
            else
            {
                connectionHandler->getLine(restOfMessage);
            }


        }
        else
        {
            cout<<"Error "<<Responseopcode<<endl;
        }


    }




}


// write to socket
bool ReadWrite::write() {
    while (!this->toTerminate) {
        const short bufsize = 1024;
        char buf[bufsize];
        cin.getline(buf, bufsize);
        string line(buf);
        int len = line.length();
        int idx = 0;
        string command = "";
        string message = "";
        for (string::iterator it = line.begin(); *it != ' '; ++it) {
            idx++;
        }

      //  cout<<message<<endl;
        command = line.substr(0, idx);// to check if  the last one is included
        bool sent;
        if (command == "LOGOUT") {
            sent = connectionHandler->sendFrame4and11(4);
            unique_lock<std::mutex> lock(mut);
            condition.wait(lock);
        }
        else{
            if(command != "MYCOURSES")
            message = line.substr(idx+1);
        }


        if (command == "ADMINREG") {

            sent = connectionHandler->sendFrameUserAndPass(1, message);
        }

        if (command == "STUDENTREG") {
            sent = connectionHandler->sendFrameUserAndPass(2, message);
        }
        if (command == "LOGIN") {
            sent = connectionHandler->sendFrameUserAndPass(3, message);
        }
        if (command == "COURSEREG")
        {
            sent = connectionHandler->sendFrameCourseNum(5, message);
        }
        if (command == "KDAMCHECK")
        {
            sent = connectionHandler->sendFrameCourseNum(6, message);
        }
        if (command == "COURSESTAT")
        {
            sent = connectionHandler->sendFrameCourseNum(7, message);
        }
        if (command == "STUDENTSTAT")
        {
            sent = connectionHandler->sendFrameUserAndPass(8, message);
        }
        if (command == "ISREGISTERED")
        {
            sent = connectionHandler->sendFrameCourseNum(9, message);
        }
        if (command == "UNREGISTER")
        {
            sent = connectionHandler->sendFrameCourseNum(10, message);
        }
        if (command == "MYCOURSES")
        {
            message = "";
            sent = connectionHandler->sendFrame4and11(11);
        }


    }
}

