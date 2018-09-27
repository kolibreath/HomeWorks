//
// Created by kolibreath on 18-6-9.
//

#ifndef CPP3_CONTAINERV_H
#define CPP3_CONTAINERV_H

#endif//CPP3_CONTAINERV_H

#include "vector"

#include "Queue.h"
using namespace std;
template <class T>
class ContainerV{
public:
    vector<T> container;

    ~ContainerV(){
        for(int i=0;i<container.size();i++){
            delete  container[i];
        }
    }
    void insert(T t){
        container.push_back(t);
    }

    void remove(){
        container.pop_back();
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
};