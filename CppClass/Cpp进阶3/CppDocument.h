//
// Created by kolibreath on 18-6-10.
//

#ifndef CPP3_CPPDOCUMENT_H
#define CPP3_CPPDOCUMENT_H

#endif //CPP3_CPPDOCUMENT_H

#include "QueueV.h"
#include "CPage.h"
class CppDocument{
public:

    QueueV container;

    CPage clone(CPage cPage){
        CPage page;
        page.pGraphs = cPage.pGraphs.clone();
        return page;
    }

    ~CppDocument(){
        container.clear();
    }
    void dequeue(){
        container.dequeue();
    }


    void enqueue(CPage cPage){
        container.enqueue(cPage);
    }
};