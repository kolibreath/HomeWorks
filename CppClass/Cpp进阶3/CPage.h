#ifndef __PAGE__
#define __PAGE__

#include <fstream>

#include "Graph.h"
#include "Rect.h"
#include "Circle.h"
#include "CircleRect.h"
#include "vector"
#include "QueueV.h"
class   CPage{
private :

public:
    QueueV<CGraph*> pGraphs;

    CPage(){
    }
    ~CPage(){
        pGraphs.clear();
    }

    void insertGraphs(CGraph *graph){
        pGraphs.enqueue(graph);
    }

    void showGraphs(){

        for(int i=0;i<pGraphs.size();i++)
            pGraphs[i]->showMessage();
    }
	

     void save(char *loc){

        int number = pGraphs.size();
         fstream file ;
         file.open(loc,ios::in|ios::out|ios::binary|ios::trunc);
         file.write((char*)&number,sizeof(number));

         for(int i=0;i<number;i++)
             pGraphs[i]->save(file);

         file.close();
    }

    void load(char *loc){

        int numg = 0;
        fstream file;
        file.open(loc,ios::in|ios::binary);
        file.read((char*)&numg,sizeof(pGraphs.size()));
        int type,color,lw;

        CGraph * g;

        for(int i=0;i<numg;i++){

            file.read((char*)&type, sizeof(type));
            file.read((char*)&color, sizeof(color));
            file.read((char*)&lw, sizeof(lw));


            switch (type){
                case 1:
                    g = new CCircle(color,lw);
                    break;
                case 2:
                    g = new CRect(color,lw);
                    break;
                case 3:
                    g = new CCircleRect(color,lw);
                    break;
            }

            g->load(file);
            insertGraphs(g);

        }
        file.close();
    }

};

#endif
