#ifndef __PAGE__
#define __PAGE__

#include <fstream>

#include "Graph.h"
#include "Rect.h"
#include "Circle.h"
#include "CircleRect.h"
#include "vector"
#include "ContainerV.h"
class CPage{
private :
    int noumber;

public:
    ContainerV<CGraph*> pGraphs;
    CPage(){
        noumber = 0;
    }
    ~CPage(){
        pGraphs.clear();
        noumber = 0;
    }

    void AddGraphs(CGraph* graph){
        pGraphs.insert(graph);
        noumber++;
    }

    void ShowGraphs(){

        for(int i=0;i<noumber;i++)
            pGraphs[i]->ShowMsg();
    }
	

     void Save(fstream & file){

         file.write((char*)&noumber,sizeof(noumber));

         for(int i=0;i<noumber;i++)
             pGraphs[i]->Save(file);
     }

     void Load(fstream & file){

         int numg = 0;

         file.read((char*)&numg,sizeof(noumber));
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

             g->Load(file);
             AddGraphs(g);

         }

     }

};

#endif
