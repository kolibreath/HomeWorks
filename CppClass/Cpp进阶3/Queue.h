//
// Created by kolibreath on 18-6-9.
//

#ifndef CPP3_QUEUE_H
#define CPP3_QUEUE_H

#endif //CPP3_QUEUE_H

#include "vector"
using namespace std;

template <class T>
class queue{
public:

    vector<T> innerCounter;

    void enqueue(T t){
        this->innerCounter.push_back(t);
    }

    int dequeue(){
        if(size()!=0)
            this->innerCounter.pop_back();
    }

    void clear(){
        this->innerCounter.clear();
    }

    int size(){
        return this->innerCounter.size();
    }
};