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
        char * figure = new char;
        int type = 0;
        int types;
        for(int i = 0;i<c->pGraphs.size();i++){
            type =  c->pGraphs.dequeue()->Type;
            switch (type){
                case CIRCLE:
                    figure = "Circle";
                    break;
                case CIRCLERECT:
                    figure = "CircleRect";
                    break;
                case CRECT:
                    figure = "Rect";
                    break;
            }

            cout<<figure<<" slides out"<<endl;
        }
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