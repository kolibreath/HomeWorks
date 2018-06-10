//
// Created by kolibreath on 18-6-9.
//

#ifndef CPP3_CMOTION_H
#define CPP3_CMOTION_H

#endif //CPP3_CMOTION_H

#include "Rect.h"

class CMotion{
public :
    template <class  T>
    void slideOut(T t){
    if(typeCheck(t)){
        T *t_p = &t;
        CPage *c  = dynamic_cast<T*>(t_p);
        c->pGraphs.clear();
    }else
        cout<<"the figure slides out"<<endl;
    }

    template <class  T>
    void disppear(T t){
        cout<<"the figure disappeared"<<endl;
    }

    template <class  T>
    void split(T t);

    template <class  T>
    void slideIn(CPage cPage,T t){
        cPage.pGraphs.clear();
    }

    template <class T>
    bool typeCheck(T t){
        T *t_p = &t;
        if(CPage *c = dynamic_cast<T* >(t_p)){
            return true;
        }else
            return  false;
    }


};