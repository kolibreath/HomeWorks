//
// Created by kolibreath on 18-6-9.
//

#ifndef CPP3_CONTAINERV_H
#define CPP3_CONTAINERV_H

#endif//CPP3_CONTAINERV_H

#include "vector"

using namespace std;
template <class T>
class QueueV{
public:
    vector<T> container;

    ~QueueV(){
        for(int i=0;i<container.size();i++){
            delete  container[i];
        }
    }
    void enqueue(T t){
        container.push_back(t);
    }

    T dequeue(){
        if(container.size()!=0) {
            T t = container.back();
            container.pop_back();
            return t;
        }
    }
    int size(){
        return container.size();
    }

    void alter(T t,int index){
        container[index] = t;
    }

    T &operator[](int index){
        if(container.size()!=0)
        return container[index];
    }

    void clear(){
        container.clear();
    }

    QueueV clone(){
        QueueV queueV;
        vector<T> cloneContainer (container);
        queueV.container = cloneContainer;
        return queueV;
    }
};