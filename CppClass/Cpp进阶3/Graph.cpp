#include <iostream>
#include <fstream>
#include "Graph.h"
using namespace std;

CGraph::CGraph(int type,int color,int linewidth){
       Type = type;
       Color = color;
       Linewidth = linewidth;
    }

 void CGraph::Show(){
       cout<<endl<<"\tType:"<<Type <<"\tColor:"<<Color
       <<"\tLinewidth:"<<Linewidth<<endl;
    }

 void CGraph::Save(fstream& file){
   
    file.write((char*)&Type,sizeof(Type));
	file.write((char*)&Color,sizeof(Color));
	file.write((char*)&Linewidth,sizeof(Linewidth));
	 
}