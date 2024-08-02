#include <QThread>
#include <QTcpServer>
#include <QTcpSocket>
#include <QDebug>
#include <iostream>
#include <mutex>
#include <thread>
#include <condition_variable>
#include <queue>
#include "ServerSend.pb.h"

#ifndef SendCommandThread_hpp
#define SendCommandThread_hpp

using namespace std;

class SendCommandThread: public QThread
{
    Q_OBJECT

public:
    SendCommandThread();
    void AddMessage(ZenboNurseHelperProtobuf::ReportAndCommand message);

    bool b_KeepLoop;
    QTcpSocket *pSocket = NULL;
    condition_variable cond_var_report_result;    

protected:
    void run();
    char str_results[4096];
    int str_results_len;
    mutex mutex_result_buffer;
    queue<ZenboNurseHelperProtobuf::ReportAndCommand> mQueue;

private:
};

#endif